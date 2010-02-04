package com.asksven.ledeffects.data;

import com.asksven.ledeffects.manager.EffectManager;
import com.asksven.ledeffects.manager.EffectsState;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences
{
	public static final String PREFS_NAME = "LedEffectsPrefs";
	
	/** Prefs handler */
	SharedPreferences m_mySettings;
	
	/** shall service be started automatically upon boot */
	private boolean m_bAutostart;

	/** Timer frequency in seconds*/
	private int m_iPollInterval;

	
	/**
	 * @return the autostart
	 */
	public boolean getAutostart()
	{
		return m_bAutostart;
	}

	/**
	 * @param autostart the autostart to set
	 */
	public void setAutostart(boolean autostart)
	{
		m_bAutostart = autostart;
	}

	public Preferences(Activity myActivity)
	{
		m_mySettings = myActivity.getSharedPreferences(PREFS_NAME, 0);
		this.init();
	}
	
	public Preferences(Context myContext)
	{
		m_mySettings = myContext.getSharedPreferences(PREFS_NAME, 0);
		this.init();
	}

	public Preferences(SharedPreferences myPrefs)
	{
		m_mySettings = myPrefs;
		this.init();
	}
	
	/** initialize value holder */
	private void init()
	{
	    int iPollInterval 	= m_mySettings.getInt("pollInterval", 30);
	    boolean bAutostart 	= m_mySettings.getBoolean("autostart", false);
	    int iLogLines 		= m_mySettings.getInt("logLines", 100);
	    
	    setPollInterval(iPollInterval);
	    setAutostart(bAutostart);
	}
	
	public void save()
	{
		
		SharedPreferences.Editor editor = m_mySettings.edit();
		
	    editor.putInt("pollInterval", getPollInterval());
	    editor.putBoolean("autostart", getAutostart());
	    
	    editor.commit();
	}
	
	
	/**
	 * @return the pollInterval
	 */
	public int getPollInterval() {
		return m_iPollInterval;
	}
	/**
	 * @param pollInterval the pollInterval to set
	 */
	public void setPollInterval(int pollInterval) {
		m_iPollInterval = pollInterval;
	}

	public String getEffectForState(int iState)
	{
		switch (iState)
		{
			case EffectsState.STATE_NONE :
				return EffectManager.EFFECT_NONE;

			case EffectsState.STATE_CHARGING :
				return EffectManager.EFFECT_BREATHE;

			case EffectsState.STATE_RINGING :
				return EffectManager.EFFECT_RING;

			case EffectsState.NOTIFY_SMS :
				return EffectManager.EFFECT_ROTATE;

			case EffectsState.NOTIFY_IM :
				return EffectManager.EFFECT_FADE;

			case EffectsState.NOTIFY_MAIL :
				return EffectManager.EFFECT_VERTICAL;

			default :
				return EffectManager.EFFECT_NONE;		
		}
	}
}
