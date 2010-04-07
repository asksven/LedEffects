package com.asksven.ledeffects.data;

import java.util.Calendar;


/** Valueobject to keep track of a timespan.
 * @author sven
 *
 */
public class TimeSpan
{
	private Calendar m_oFrom;
	private Calendar m_oTo;
	
	public TimeSpan(int iFromH, int iFromM, int iToH, int iToM)
	{
		m_oFrom = Calendar.getInstance();
		m_oFrom.set(Calendar.HOUR_OF_DAY, iFromH);
		m_oFrom.set(Calendar.MINUTE, iFromM);

		m_oTo = Calendar.getInstance();
		m_oTo.set(Calendar.HOUR_OF_DAY, iToH);
		m_oTo.set(Calendar.MINUTE, iToM);
		
		// if to is on next day add one for proper comparison
		if (m_oTo.before(m_oFrom))
		{
			m_oTo.set(Calendar.DAY_OF_YEAR, m_oFrom.get(Calendar.DAY_OF_YEAR)+1);
		}
	}
	
	public int getFromHours()
	{
		int myRet = m_oFrom.get(Calendar.HOUR_OF_DAY);
		return myRet;
	}

	public int getToHours()
	{
		int myRet = m_oTo.get(Calendar.HOUR_OF_DAY);
		return myRet;
	}
	
	public boolean isBetween(Calendar oTime)
	{
		return (oTime.after(m_oFrom) && oTime.before(m_oTo));
	}
	
	
	
}
