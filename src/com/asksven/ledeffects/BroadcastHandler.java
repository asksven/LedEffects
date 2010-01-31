/**
 * 
 */
package com.asksven.ledeffects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.asksven.ledeffects.data.Preferences;

/**
 * @author sven
 *
 */
public class BroadcastHandler extends BroadcastReceiver
{
	private static final String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";
	private static final String ACTION_CALL = "android.intent.action.PHONE_STATE";
	
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Preferences myPrefs = new Preferences(context);
        boolean bAutostart = myPrefs.getAutostart();

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			Log.i(getClass().getSimpleName(), "Received Broadcast ACTION_BOOT_COMPLETED");
			if (bAutostart)
			{
				Log.i(getClass().getSimpleName(), "Autostart is set so run");
				context.startService(new Intent(context, EffectsService.class));
			}
		}

		// Incoming SMS
		if (intent.getAction().equals(ACTION_SMS))
		{
			EffectManager.doEffect(EffectManager.EFFECT_ROTATE);
		}

		// Incoming Call 
		if (intent.getAction().equals(ACTION_CALL))
		{
			String phoneState = intent.getExtras().getString("state");

			// hung up
			if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_IDLE))
			{
				EffectManager.doEffect(EffectManager.EFFECT_NONE);
			}
			
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_RINGING))
			{
				EffectManager.doEffect(EffectManager.EFFECT_RING);
			}
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK))
			{
				EffectManager.doEffect(EffectManager.EFFECT_NONE);
			}
			
		}

			
		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
		{
           	EffectManager.doEffect(EffectManager.EFFECT_BREATHE);
		}
		
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
		{
           	EffectManager.doEffect(EffectManager.EFFECT_NONE);
		}
		
	}
}
