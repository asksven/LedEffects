package com.asksven.ledeffects.data;

public class EffectsState
{
	private boolean bStateRinging;

	private boolean bStateCharging;
	
	private boolean bNotifySMS;
	private boolean bNotifyIM;
	private boolean bNotifyMail;
	
	public static final int STATE_NONE 		= 0;
	public static final int NOTIFY_SMS 		= 1;
	public static final int NOTIFY_IM 		= 2;
	public static final int NOTIFY_MAIL		= 3;
	
	public static final int STATE_CHARGING	= 10;
	public static final int STATE_RINGING	= 11;
	
	private static EffectsState m_oState = null;
	
	/** returns the Singleton instance */
	public static EffectsState getInstance()
	{
		if (m_oState == null)
		{
			m_oState = new EffectsState();
		}
		
		return m_oState;
	}
	
	/** 
	 * Returns the current state, taking priorities of notifications over state into account
	 * First prio has ringing
	 * then comes notifications (SMS, IM, Mail)
	 * and finally comes charging
	 */
	public int getState()
	{
		if (this.getStateRinging())
		{
			return STATE_RINGING;
		}
		else if (this.getNotifySMS())
		{
			return NOTIFY_SMS;
		}
		else if (this.getNotifyIM())
		{
			return NOTIFY_IM;
		}
		else if (this.getNotifyMail())
		{
			return NOTIFY_MAIL;
		}
		else if (this.getStateCharging())
		{
			return STATE_CHARGING;
		}
		else
		{
			return STATE_NONE;
		}
	}
	
	/** private cctor, instance is provided as Singleton */
	private EffectsState()
	{
		bStateRinging 	= false;
		bStateCharging	= false;
		
		bNotifySMS		= false;
		bNotifyIM		= false;
		bNotifyMail		= false;
	}

	/**
	 * @return the Ringing State
	 */
	public boolean getStateRinging()
	{
		return bStateRinging;
	}

	/**
	 * @return the Charging State
	 */
	public boolean getStateCharging() 
	{
		return bStateCharging;
	}

	/**
	 * @return the SMS Notify State
	 */
	public boolean getNotifySMS()
	{
		return bNotifySMS;
	}

	/**
	 * @return the IM Notify State
	 */
	public boolean getNotifyIM()
	{
		return bNotifyIM;
	}

	/**
	 * @return the Mail Notify State
	 */
	public boolean getNotifyMail() 
	{
		return bNotifyMail;
	}

	/**
	 * Sets the Ringing State
	 * @param bStateRinging the bStateRinging to set
	 */
	public void setStateRinging(boolean bStateRinging)
	{
		this.bStateRinging = bStateRinging;
	}

	/**
	 * Sets the Charging State
	 * @param bStateCharging the bStateCharging to set
	 */
	public void setStateCharging(boolean bStateCharging)
	{
		this.bStateCharging = bStateCharging;
	}

	/**
	 * Sets the SMS Notification
	 * @param bNotifySMS the bNotifySMS to set
	 */
	public void setNotifySMS(boolean bNotifySMS)
	{
		this.bNotifySMS = bNotifySMS;
	}

	/**
	 * Sets the IM Notification
	 * @param bNotifyIM the bNotifyIM to set
	 */
	public void setNotifyIM(boolean bNotifyIM)
	{
		this.bNotifyIM = bNotifyIM;
	}

	/**
	 * Sets the Mail Notification
	 * @param bNotifyMail the bNotifyMail to set
	 */
	public void setNotifyMail(boolean bNotifyMail)
	{
		this.bNotifyMail = bNotifyMail;
	}
}
