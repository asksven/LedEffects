package com.asksven.ledeffects.manager;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.asksven.ledeffects.MainAct;
import com.asksven.ledeffects.R;
import com.asksven.ledeffects.data.Effect;
import com.asksven.ledeffects.data.Preferences;

/**
 * Fassade to all effect-related processing
 * EffectFassade is implemented as Singleton
 * @author sven
 *
 */
public class EffectsFassade
{

	/** the singleton instance */
	private static EffectsFassade m_oFassade = null;

	/** returns the singleton's instance */
	public static EffectsFassade getInstance()
	{
		if (m_oFassade == null)
		{
			m_oFassade = new EffectsFassade();
		}
		
		return m_oFassade;
	}

	/** apply the effect following the current state */
	public void doEffect(Context ctx)
	{
		EffectsState myState = EffectsState.getInstance();
		Preferences myPrefs = new Preferences(ctx.getSharedPreferences(Preferences.PREFS_NAME, 0));
    	Effect oEffect = myPrefs.getEffectForState(EffectsState.getInstance().getState());
		boolean bChanged = EffectManager.doEffect(oEffect.getEffect());
		if ((bChanged) && (oEffect.getEffect() != 0))
		{
			// show text in status bar if this is set
			if (oEffect.getNotify())
			{
				this.notify(ctx, oEffect.getText());
			}
			// if it's a notification we must add a timer to stop it
			if (oEffect.getTimed())
			{
		    	Timer timer = new Timer();
		    	
		    	timer.schedule(
		    			new TimerTask()
		    			{
					        public void run()
					        {
					        	
					        	EffectManager.doEffect(0);
					        	EffectsState.getInstance().setNotifyReadAll();
					        }
					     }, 5*1000);
			}
		}
		
	}

	/** plays an effect for a limitied time */
	public void playEffect(Context ctx, int iEffect, int iDuration)
	{
		EffectManager.doEffect(iEffect);
		// todo: add timer
		// create a timer to end the effect
    	Timer timer = new Timer();
    	
    	timer.schedule(
    			new TimerTask()
    			{
			        public void run()
			        {
			        	
			        	EffectManager.doEffect(0);
			        }
			     }, 5*1000);
	}
	
	/** persists a given effect to be applied when phone goes to sleep */
	public void writeSleepEffect(int iEffect)
	{
		EffectManager.writeSleepEffect(iEffect);
		
	}
	
	/** notifications are limitied in time and can be cleared. Upon clearing them a permanent effect may be reapplied (like e.g. charging) */ 
	public void clearAllNotifications(Context ctx)
	{
        Preferences myPrefs = new Preferences(ctx.getSharedPreferences(Preferences.PREFS_NAME, 0));
        EffectsState.getInstance().setNotifyReadAll();
        Effect oEffect = myPrefs.getEffectForState(EffectsState.getInstance().getState());
        EffectManager.doEffect(oEffect.getEffect());

	}
	
	/** the cctor is private as EffectFassade is a singleton */
	private EffectsFassade()
	{
		
	}
	
	/** writes a notification to the status bar */
	private void notify(Context ctx, String strNote)
    {
	    NotificationManager	mNM = (NotificationManager)ctx.getSystemService(ctx.NOTIFICATION_SERVICE);	
    	Notification notification = new Notification(R.drawable.icon, strNote, System.currentTimeMillis());
    	PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainAct.class), 0);
    	notification.setLatestEventInfo(ctx, ctx.getText(R.string.local_service_label), strNote, contentIntent);
    	mNM.notify(R.string.app_name, notification);
    }
	    
	/** applies the charging state */
	public void setStateCharging(boolean bState)
	{
		EffectsState.getInstance().setStateCharging(bState);
	}
	
	/** applies the incoming call state */
	public void setStateRinging(boolean bState)
	{
		EffectsState.getInstance().setStateRinging(bState);
	}

	/** applies the incoming SMS state */
	public void setNotifySMS(boolean bState)
	{
		EffectsState.getInstance().setNotifySMS(bState);
	}

	/** applies the incoming mail state */
	public void setNotifyMail(boolean bState)
	{
		EffectsState.getInstance().setNotifyMail(bState);
	}

}
