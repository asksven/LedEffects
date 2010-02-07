package com.asksven.ledeffects.data;

public class Effect
{
	private int  m_iEffect = 0;
	private boolean m_bNotification;
	
	public Effect(int iEffect, boolean bNotification)
	{
		m_iEffect = iEffect;
		m_bNotification = bNotification;
	}
	
	public int getEffect()
	{
		return m_iEffect;
	}
	
	public boolean getTimed()
	{
		return m_bNotification;
	}

}
