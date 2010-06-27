package com.asksven.ledeffects.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.asksven.ledeffects.MainAct;
import com.asksven.ledeffects.R;
import com.asksven.ledeffects.data.Effect;
import com.asksven.ledeffects.data.Preferences;

/**
 * Fassade to all effect-related processing
 * EffectFassade is implemented as Singleton
 * @author sven
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
		final Preferences myPrefs = Preferences.getInstance(ctx);
    	Effect oEffect = myPrefs.getEffectForState(EffectsState.getInstance().getState());
		boolean bChanged = EffectManager.doEffect(myPrefs.getPhoneModel(), oEffect.getEffect());
		Log.d(getClass().getSimpleName(), "Applying effect " + oEffect.getEffect());
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
		    	Log.d(getClass().getSimpleName(), "Starting timer to stop effect");
		    	timer.schedule(
		    			new TimerTask()
		    			{
					        public void run()
					        {
					        	EffectManager.doEffect(myPrefs.getPhoneModel(), 0);
					        	EffectsState.getInstance().setNotifyReadAll();
					        }
					     }, 5*1000);
			}
			
			// What time is it now?
			Calendar myNow = Calendar.getInstance();
			myNow.setTime(new Date(System.currentTimeMillis()));
			// vibrate ?
			if (oEffect.getVibrate())
			{
				// check if vibration is allowed at current time
				if (!myPrefs.getVibrateOff() || (!myPrefs.getVibrateOffTimespan().isBetween(myNow)))
				{
					Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
					// 2. Vibrate in a Pattern with 500ms on, 300ms off for 2 times  
					long[] pattern = { 0, 100, 300, 100, 500, 150 };  
					v.vibrate(pattern, -1);
				}
			}
			
			// play sound ?
			if (!oEffect.getSound().equals(""))
			{
				// check if sound is allowed at current time
				if (!myPrefs.getSoundOff() || (!myPrefs.getSoundOffTimespan().isBetween(myNow)))
				{
					MediaPlayer myPlayer = new MediaPlayer();
					try
					{
						Uri myNewUri = Uri.parse(oEffect.getSound());
						myPlayer.setDataSource(ctx, myNewUri);
						myPlayer.prepare();
						myPlayer.start();
					}
					catch (Exception e)
					{
						Log.d(getClass().getSimpleName(), e.getMessage());
					}
				}
				
			}
		}
		
	}

	/** plays a visual effect for a limited time */
	public void playEffect(Context ctx, int iEffect, int iDuration)
	{
		final Preferences myPrefs = Preferences.getInstance(ctx);
		EffectManager.doEffect(myPrefs.getPhoneModel(), iEffect);

		// create a timer to end the effect
    	Timer timer = new Timer();
    	
    	timer.schedule(
    			new TimerTask()
    			{
			        public void run()
			        {
			        	
			        	EffectManager.doEffect(myPrefs.getPhoneModel(), 0);
			        }
			     }, 5*1000);
	}

	/** plays a visual effect together with vibration and sound for a limited time */
	public void playEffect(Context ctx, int iEffect, int iDuration, boolean bVibrate, String strSound)
	{
		final Preferences myPrefs = Preferences.getInstance(ctx);
		EffectManager.doEffect(myPrefs.getPhoneModel(), iEffect);

		// create a timer to end the effect
    	Timer timer = new Timer();
    	
    	timer.schedule(
    			new TimerTask()
    			{
			        public void run()
			        {
			        	
			        	EffectManager.doEffect(myPrefs.getPhoneModel(), 0);
			        }
			     }, 5*1000);

    	// vibrate ?
		if (bVibrate)
		{
			Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 0, 100, 300, 100, 500, 150 };  
			v.vibrate(pattern, -1); 
		}
		
		// play sound ?
		if (!strSound.equals(""))
		{
			MediaPlayer myPlayer = new MediaPlayer();
			try
			{
				Uri myNewUri = Uri.parse(strSound);
				myPlayer.setDataSource(ctx, myNewUri);
				myPlayer.prepare();
				myPlayer.start();
			}
			catch (Exception e)
			{
				Log.d(getClass().getSimpleName(), e.getMessage());
			}
			
		}
    	
	}
	
	/** persists a given effect to be applied when phone goes to sleep */
	public void writeSleepEffect(int iEffect)
	{
		EffectManager.writeSleepEffect(iEffect);
		
	}
	
	/** notifications are limitied in time and can be cleared. Upon clearing them a permanent effect may be reapplied (like e.g. charging) */ 
	public void clearAllNotifications(Context ctx)
	{
		Log.d(getClass().getSimpleName(), "Clearing all temporary notifications and apply permanent effects");
        Preferences myPrefs = Preferences.getInstance(ctx);
        EffectsState.getInstance().setNotifyReadAll();
        Effect oEffect = myPrefs.getEffectForState(EffectsState.getInstance().getState());
        EffectManager.doEffect(myPrefs.getPhoneModel(), oEffect.getEffect());

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

	/** applies the incoming IM state */
	public void setNotifyIM(boolean bState)
	{
		EffectsState.getInstance().setNotifyIM(bState);
	}

	
}
