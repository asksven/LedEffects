/**
 * 
 */
package com.asksven.ledeffects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.asksven.ledeffects.data.EffectsState;
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
        EffectsState myState = EffectsState.getInstance();
        

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			Log.i(getClass().getSimpleName(), "Received Broadcast ACTION_BOOT_COMPLETED");
			if (bAutostart)
			{
				Log.i(getClass().getSimpleName(), "Autostart is set so run");
				context.startService(new Intent(context, EffectsService.class));
			}
		}

		// Event for SMS
		// Incoming SMS
		if (intent.getAction().equals(ACTION_SMS))
		{
			myState.setNotifySMS(true);
		}

		// Events for call
		// Incoming Call 
		if (intent.getAction().equals(ACTION_CALL))
		{
			String phoneState = intent.getExtras().getString("state");

			// hung up
			if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_IDLE))
			{
				myState.setStateRinging(false);
			}
			
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_RINGING))
			{
				myState.setStateRinging(true);
			}
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK))
			{
				myState.setStateRinging(false);
			}
			
		}
		
		// Apply the effect for current state
		EffectManager.doEffect(context, myPrefs.getEffectForState(myState.getState()));
	}
}
