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
	private boolean m_bNotification = false;
	
	/** the effect's description */
	private String m_strText 		= "";
	
	/** true if a notification should be displayed in the status bar */
	private boolean m_bNotify		= false;
	
	/** public cctor */
	public Effect(int iEffect, boolean bNotification, String strText, boolean bNotify)
	{
		m_iEffect 		= iEffect;
		m_bNotification = bNotification;
		m_strText		= strText;
		m_bNotify		= bNotify;
		
	}
	
	/** returns the effect key */
	public int getEffect()
	{
		return m_iEffect;
	}
	
	/** returns whether the notification should be applied only for a short time or permanently till the state changes */
	public boolean getTimed()
	{
		return m_bNotification;
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
}
