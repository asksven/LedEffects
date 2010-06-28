package com.asksven.ledeffects.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.asksven.ledeffects.R;
import com.asksven.ledeffects.manager.EffectsFassade;
import com.asksven.ledeffects.manager.EffectsState;


/**
 * Singleton value holder for Preferences
 * @author sven
 *
 */
public class Preferences
{
	public static final String PREFS_NAME = "LedEffectsPrefs";
	
	private static Preferences m_myPrefs; 
	
	/** Prefs handler */
	SharedPreferences m_mySettings;
	
	/** initialized already ? */
	boolean m_bInitDone = false;
	
	/** shall service be started automatically upon boot */
	private boolean m_bAutostart;
	
	private boolean m_bNotifyRing;
	private boolean m_bNotifyCharge;
	private boolean m_bNotifySMS;
	private boolean m_bNotifyMail;
	private boolean m_bNotifyIM;
	
	private boolean m_bVibrateRing;
	private boolean m_bVibrateSMS;
	private boolean m_bVibrateMail;
	private boolean m_bVibrateIM;

	private boolean m_bSoundRing;
	private boolean m_bSoundSMS;
	private boolean m_bSoundMail;
	private boolean m_bSoundIM;
	
	private String m_strSoundRing;
	private String m_strSoundSMS;
	private String m_strSoundMail;
	private String m_strSoundIM;
	
	private int m_iEffectRing;
	private int m_iEffectCharge;
	private int m_iEffectSMS;
	private int m_iEffectMail;
	private int m_iEffectIM;
	private int m_iEffectSleep;

	/** option to silence vibration at night */
	private boolean m_bSilenceVibration 	= false;
	/** timepspan for silencing vibration */
	private TimeSpan m_myVibrationOffTime;

	/** option to silence sound at night */
	private boolean m_bSilenceSound			= false;
	/** timepspan for silencing sound */
	private TimeSpan m_mySoundOffTime;
	
	/** phone we run effects on @see SupportedPhones*/
	private int m_iPhoneModel	= 0;
	
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

	/** getters / setter for notifying effects */
	public boolean getNotifyRing()
	{
		return m_bNotifyRing;
	}

	public void setNotifyRing(boolean bNotify)
	{
		m_bNotifyRing = bNotify;
	}

	public boolean getNotifyCharge()
	{
		return m_bNotifyCharge;
	}

	public void setNotifyCharge(boolean bNotify)
	{
		m_bNotifyCharge = bNotify;
	}
	public boolean getNotifySMS()
	{
		return m_bNotifySMS;
	}

	public void setNotifySMS(boolean bNotify)
	{
		m_bNotifySMS = bNotify;
	}

	public boolean getNotifyMail()
	{
		return m_bNotifyMail;
	}

	public void setNotifyMail(boolean bNotify)
	{
		m_bNotifyMail = bNotify;
	}

	public boolean getNotifyIM()
	{
		return m_bNotifyIM;
	}

	public void setNotifyIM(boolean bNotify)
	{
		m_bNotifyIM = bNotify;
	}

	/** getters / setter for vibrate effects */
	public boolean getVibrateRing()
	{
		return m_bVibrateRing;
	}
	
	public void setVibrateRing(boolean bNotify)
	{
		m_bVibrateRing = bNotify;
	}
	
	public boolean getVibrateSMS()
	{
		return m_bVibrateSMS;
	}
	
	public void setVibrateSMS(boolean bNotify)
	{
		m_bVibrateSMS = bNotify;
	}
	
	public boolean getVibrateMail()
	{
		return m_bVibrateMail;
	}
	
	public void setVibrateMail(boolean bNotify)
	{
		m_bVibrateMail = bNotify;
	}
	
	public boolean getVibrateIM()
	{
		return m_bVibrateIM;
	}
	
	public void setVibrateIM(boolean bNotify)
	{
		m_bVibrateIM = bNotify;
	}

	/** getters / setter for sound effects */
	public boolean getPlaySoundRing()
	{
		return m_bSoundRing;
	}
	
	public void setPlaySoundRing(boolean bNotify)
	{
		m_bSoundRing = bNotify;
	}
	
	public boolean getPlaySoundSMS()
	{
		return m_bSoundSMS;
	}
	
	public void setPlaySoundSMS(boolean bNotify)
	{
		m_bSoundSMS = bNotify;
	}
	
	public boolean getPlaySoundMail()
	{
		return m_bSoundMail;
	}
	
	public void setPlaySoundMail(boolean bNotify)
	{
		m_bSoundMail = bNotify;
	}
	
	public boolean getPlaySoundIM()
	{
		return m_bSoundIM;
	}
	
	public void setPlaySoundIM(boolean bNotify)
	{
		m_bSoundIM = bNotify;
	}

	/** getters / setter for sound effects */
	public String getSoundRing()
	{
		return m_strSoundRing;
	}
	
	public void setSoundRing(String strNotify)
	{
		m_strSoundRing = strNotify;
	}
	
	public String getSoundSMS()
	{
		return m_strSoundSMS;
	}
	
	public void setSoundSMS(String strNotify)
	{
		m_strSoundSMS = strNotify;
	}
	
	public String getSoundMail()
	{
		return m_strSoundMail;
	}
	
	public void setSoundMail(String strNotify)
	{
		m_strSoundMail = strNotify;
	}
	
	public String getSoundIM()
	{
		return m_strSoundIM;
	}
	
	public void setSoundIM(String strNotify)
	{
		m_strSoundIM = strNotify;
	}
	

	public int getEffectRing()
	{
		return m_iEffectRing;
	}

	public void setEffectRing(int iPos)
	{
		m_iEffectRing = iPos;
	}

	public int getEffectCharge()
	{
		return m_iEffectCharge;
	}

	public void setEffectCharge(int iPos)
	{
		m_iEffectCharge = iPos;
	}

	public int getEffectSMS()
	{
		return m_iEffectSMS;
	}

	public void setEffectSMS(int iPos)
	{
		m_iEffectSMS = iPos;
	}

	public int getEffectMail()
	{
		return m_iEffectMail;
	}

	public void setEffectMail(int iPos)
	{
		m_iEffectMail = iPos;
	}

	public int getEffectIM()
	{
		return m_iEffectIM;
	}

	public void setEffectIM(int iPos)
	{
		m_iEffectIM = iPos;
	}

	public int getEffectSleep()
	{
		return m_iEffectSleep;
	}

	public void setEffectSleep(int iPos)
	{
		m_iEffectSleep = iPos;
	}

	public void setVibrateOff(boolean bValue)
	{
		m_bSilenceVibration = bValue;
	}

	public void setVibrateOffTimespan(int iStartIndex, int iEndIndex)
	{
		m_myVibrationOffTime = new TimeSpan(iStartIndex, 0, iEndIndex, 00);
	}

	public void setSoundOffTimespan(int iStartIndex, int iEndIndex)
	{
		m_mySoundOffTime = new TimeSpan(iStartIndex, 0, iEndIndex, 00);
	}

	public TimeSpan getVibrateOffTimespan()
	{
		return m_myVibrationOffTime;
	}

	public TimeSpan getSoundOffTimespan()
	{
		return m_mySoundOffTime;
	}
	
	public void setSoundOff(boolean bValue)
	{
		m_bSilenceSound = bValue;
	}

	public boolean getVibrateOff()
	{
		return m_bSilenceVibration;
	}

	public boolean getSoundOff()
	{
		return m_bSilenceSound;
	}

	public int getPhoneModel()
	{
		return m_iPhoneModel;
	}

	public void setPhoneModel(int iModel)
	{
		// if phone model changed make sure to cancel all effects
		if ((m_iPhoneModel != iModel) && (m_iPhoneModel != 0))
		{
			m_iEffectRing 	= 0;
			m_iEffectCharge = 0;
			m_iEffectSMS	= 0;
			m_iEffectMail	= 0;
			m_iEffectIM 	= 0;
			m_iEffectSleep 	= 0;
		}	
		
		m_iPhoneModel = iModel;
	}

	
	private Preferences(Activity myActivity)
	{
		m_mySettings = myActivity.getSharedPreferences(PREFS_NAME, 0);
		this.init();
	}
	
	private Preferences(Context myContext)
	{
		m_mySettings = myContext.getSharedPreferences(PREFS_NAME, 0);
		this.init();
	}

	private Preferences(SharedPreferences myPrefs)
	{
		m_mySettings = myPrefs;
		this.init();
	}
	
	/** return singleton */
	public static Preferences getInstance(Context ctx)
	{
		if (m_myPrefs == null)
		{
			m_myPrefs = new Preferences(ctx);
		}
		return m_myPrefs;
	}
	
	/** initialize value holder */
	private void init()
	{
		// init only once
		if (m_bInitDone)
		{
			return;
		}
		
		m_bInitDone = true;
		
	    int iPollInterval 	= m_mySettings.getInt("pollInterval", 30);
	    boolean bAutostart 	= m_mySettings.getBoolean("autostart", false);
	    
	    setPollInterval(iPollInterval);
	    setAutostart(bAutostart);
	    
	    setNotifyRing(m_mySettings.getBoolean("notifyRing", false));
	    setNotifyCharge(m_mySettings.getBoolean("notifyCharge", false));
	    setNotifySMS(m_mySettings.getBoolean("notifySMS", false));
	    setNotifyMail(m_mySettings.getBoolean("notifyMail", false));
	    setNotifyIM(m_mySettings.getBoolean("notifyIM", false));
	    
	    setVibrateRing(m_mySettings.getBoolean("vibrateRing", false));
	    setVibrateSMS(m_mySettings.getBoolean("vibrateSMS", false));
	    setVibrateMail(m_mySettings.getBoolean("vibrateMail", false));
	    setVibrateIM(m_mySettings.getBoolean("vibrateIM", false));

	    setPlaySoundRing(m_mySettings.getBoolean("playSoundRing", false));
	    setPlaySoundSMS(m_mySettings.getBoolean("playSoundSMS", false));
	    setPlaySoundMail(m_mySettings.getBoolean("playSoundMail", false));
	    setPlaySoundIM(m_mySettings.getBoolean("playSoundIM", false));

	    setSoundRing(m_mySettings.getString("soundRing", ""));
	    setSoundSMS(m_mySettings.getString("soundSMS", ""));
	    setSoundMail(m_mySettings.getString("soundMail", ""));
	    setSoundIM(m_mySettings.getString("soundIM", ""));

	    setEffectRing(m_mySettings.getInt("effectRing", 0));	    
	    setEffectCharge(m_mySettings.getInt("effectCharge", 0));
	    setEffectSMS(m_mySettings.getInt("effectSMS", 0));
	    setEffectMail(m_mySettings.getInt("effectMail", 0));
	    setEffectIM(m_mySettings.getInt("effectIM", 0));
	    setEffectSleep(m_mySettings.getInt("effectSleep", 3));
	    
		setVibrateOff(m_mySettings.getBoolean("vibrateOff", false));
		setSoundOff(m_mySettings.getBoolean("SoundOff", false));

		setVibrateOffTimespan(m_mySettings.getInt("vibrateOffFrom", 0), m_mySettings.getInt("vibrateOffTo", 0));
		setSoundOffTimespan(m_mySettings.getInt("soundOffFrom", 0), m_mySettings.getInt("SoundOffTo", 0));
		
		setPhoneModel(m_mySettings.getInt("phoneModel", 0)); 
	}
	
	public void save()
	{
		
		SharedPreferences.Editor editor = m_mySettings.edit();
		
	    editor.putInt("pollInterval", getPollInterval());
	    editor.putBoolean("autostart", getAutostart());
	    
	    editor.putBoolean("notifyRing", getNotifyRing());
	    editor.putBoolean("notifyCharge", getNotifyCharge());
	    editor.putBoolean("notifySMS", getNotifySMS());
	    editor.putBoolean("notifyMail", getNotifyMail());
	    editor.putBoolean("notifyIM", getNotifyIM());
	    
	    editor.putBoolean("vibrateRing", getVibrateRing());
	    editor.putBoolean("vibrateSMS", getVibrateSMS());
	    editor.putBoolean("vibrateMail", getVibrateMail());
	    editor.putBoolean("vibrateIM", getVibrateIM());

	    editor.putBoolean("playSoundRing", getPlaySoundRing());
	    editor.putBoolean("playSoundSMS", getPlaySoundSMS());
	    editor.putBoolean("playSoundMail", getPlaySoundMail());
	    editor.putBoolean("playSoundIM", getPlaySoundIM());
	    
	    editor.putString("soundRing", getSoundRing());
	    editor.putString("soundSMS", getSoundSMS());
	    editor.putString("soundMail", getSoundMail());
	    editor.putString("soundIM", getSoundIM());

	    editor.putInt("effectRing", getEffectRing());
	    editor.putInt("effectCharge", getEffectCharge());
	    editor.putInt("effectSMS", getEffectSMS());
	    editor.putInt("effectMail", getEffectMail());
	    editor.putInt("effectIM", getEffectIM());
	    editor.putInt("effectSleep", getEffectSleep());

	    editor.putBoolean("vibrateOff", getVibrateOff());
	    editor.putBoolean("SoundOff", getSoundOff());
	    
	    editor.putInt("vibrateOffFrom", getVibrateOffTimespan().getFromHours());
	    editor.putInt("vibrateOffTo", getVibrateOffTimespan().getToHours());
	    editor.putInt("soundOffFrom", getSoundOffTimespan().getFromHours());
	    editor.putInt("soundOffTo", getSoundOffTimespan().getToHours());
	    
	    editor.putInt("phoneModel", getPhoneModel());

	    applySleep();
	    
	    editor.commit();
	}
	
	/** applies sleep mode */
	public void applySleep()
	{
	    // write to kernel if sleep is to be applied
    	EffectsFassade.getInstance().writeSleepEffect(getEffectSleep());

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

	public Effect getEffectForState(int iState)
	{
		switch (iState)
		{
			case EffectsState.STATE_NONE :
				return new Effect(0, false, "", false, false, "");

			case EffectsState.STATE_CHARGING :
				return new Effect(m_iEffectCharge, false, "Charging battery", getNotifyCharge(), false, "");

			case EffectsState.STATE_RINGING :
				if (getPlaySoundMail())
				{
					return new Effect(m_iEffectRing, false, "Incoming call", getNotifyRing(), getVibrateRing(), getSoundRing());
				}
				else
				{
					return new Effect(m_iEffectRing, false, "Incoming call", getNotifyRing(), getVibrateRing(), "");
				}
				
		case EffectsState.NOTIFY_SMS :
				if (getPlaySoundSMS())
				{
					return new Effect(m_iEffectSMS, true, "Incoming SMS", getNotifySMS(), getVibrateSMS(), getSoundSMS());
				}
				else
				{
					return new Effect(m_iEffectSMS, true, "Incoming SMS", getNotifySMS(), getVibrateSMS(), "");
				}

			case EffectsState.NOTIFY_IM :
				if (getPlaySoundIM())
				{
					return new Effect(m_iEffectIM, true, "Incoming IM", getNotifyIM(), getVibrateIM(), getSoundIM());
				}
				else
				{
					return new Effect(m_iEffectIM, true, "Incoming IM", getNotifyIM(), getVibrateIM(), "");
				}

			case EffectsState.NOTIFY_MAIL :
				if (getPlaySoundMail())
				{
					return new Effect(m_iEffectMail, true, "Incoming mail", getNotifyMail(), getVibrateMail(), getSoundMail());
				}
				else
				{
					return new Effect(m_iEffectMail, true, "Incoming mail", getNotifyMail(), getVibrateMail(), "");
				}
				
			case EffectsState.STATE_SLEEPING :
				return new Effect(m_iEffectSleep, false, "Sleeping", false, false, "");

			default :
				return new Effect(0, false, "", false, false, "");		
		}
	}
	
	/** returns the resource Id for enumerating effects (depends on phone model) */
	public int getEffectEnumId()
	{
		switch (m_iPhoneModel)
		{
			case SupportedPhones.RAPH_DIAM :
				return R.array.effects_raph_diam;
			case SupportedPhones.TOPA :
				return R.array.effects_topa;
			case SupportedPhones.DESIRE_UNROOTED :
				return R.array.effects_desire;
			case SupportedPhones.GENERIC :
				return R.array.effects_gen;
	
			default :
				return R.array.effects_gen; //default
		}
			
	}
}
