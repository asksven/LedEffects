/**
 * 
 */
package com.asksven.ledeffects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectsFassade;

/**
 * General broadcast handler: handles event as registered on Manifest
 * @author sven
 *
 */
public class BroadcastHandler extends BroadcastReceiver
{
	private static final String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";
	private static final String ACTION_CALL = "android.intent.action.PHONE_STATE";
	private static final String ACTION_MAIL = "com.fsck.k9.intent.action.EMAIL_RECEIVED";
	
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Preferences myPrefs = new Preferences(context);
        boolean bAutostart = myPrefs.getAutostart();
        EffectsFassade myEffectsMgr = EffectsFassade.getInstance();
        
        if ((intent.getAction().equals(ACTION_MAIL)))
        {
        	myEffectsMgr.setNotifyMail(true);
        }
        
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
			myEffectsMgr.setNotifySMS(true);
		}

		// Events for call
		// Incoming Call 
		if (intent.getAction().equals(ACTION_CALL))
		{
			String phoneState = intent.getExtras().getString("state");

			// hung up
			if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_IDLE))
			{
				myEffectsMgr.setStateRinging(false);
			}
			
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_RINGING))
			{
				myEffectsMgr.setStateRinging(true);
			}
			else if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK))
			{
				myEffectsMgr.setStateRinging(false);
			}
			
		}
		
		// Apply the effect for current state
		myEffectsMgr.doEffect(context);
	}
}
