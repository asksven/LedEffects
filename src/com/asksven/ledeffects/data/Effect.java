package com.asksven.ledeffects.data;

/**
 * The value-holder class for effects. An effect has a key (see array.xml) and
 * additional info about how it is to be applied and handeled.
 * @author sven
 *
 */
public class Effect
{
	/** the effect's key */
	private int  m_iEffect 			= 0;
	
	/** temporary or permanent effect */
	private boolean m_bTimed = false;
	
	/** the effect's description */
	private String m_strText 		= "";
	
	/** true if a notification should be displayed in the status bar */
	private boolean m_bNotify		= false;
	
	/** true if vibration should be applied */
	private boolean m_bVibrate		= false;
	
	/** non empty if a sound should be played */
	private String m_strSound 		= "";
	
	/** public cctor with basic options */
	public Effect(int iEffect, boolean bTimed, String strText, boolean bNotify)
	{
		m_iEffect 		= iEffect;
		m_bTimed 		= bTimed;
		m_strText		= strText;
		m_bNotify		= bNotify;
		m_bVibrate		= false;
		m_strSound		= "";		
	}

	/** public cctor with extended options */
	public Effect(int iEffect, boolean bTimed, String strText, boolean bNotify, boolean bVibrate, String strSound)
	{
		m_iEffect 		= iEffect;
		m_bTimed 		= bTimed;
		m_strText		= strText;
		m_bNotify		= bNotify;
		m_bVibrate		= bVibrate;
		m_strSound		= strSound;	
	}

	
	/** returns the effect key */
	public int getEffect()
	{
		return m_iEffect;
	}
	
	/** returns whether the notification should be applied only for a short time or permanently till the state changes */
	public boolean getTimed()
	{
		return m_bTimed;
	}
	
	/** returns the description of the notification */
	public String getText()
	{
		return m_strText;
	}
	
	/** true if the effect should be notified to the status bar */
	public boolean getNotify()
	{
		return m_bNotify;
	}
	
	/** true if vibration is associated to the effect */
	public boolean getVibrate()
	{
		return m_bVibrate;
	}
	
	/** true if sound is associated to the effect */
	public String getSound()
	{
		return m_strSound;
	}

}
