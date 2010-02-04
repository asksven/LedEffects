package com.asksven.ledeffects;

/**
 * 
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectManager;
import com.asksven.ledeffects.manager.EffectsState;

/**
 * @author sven
 *
 */
public class BatteryBroadcastHandler extends BroadcastReceiver
{
	
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Preferences myPrefs = new Preferences(context);
        boolean bAutostart = myPrefs.getAutostart();
        EffectsState myState = EffectsState.getInstance();
        
		// Events for power state	
		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
		{
			myState.setStateCharging(true);
		}
		
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
		{
			myState.setStateCharging(false);
		}

		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))
		{
			Log.i(getClass().getSimpleName(), "Received Broadcast ACTION_BATTERY_CHANGED");

		    
            int plugType = intent.getIntExtra("plugged", 0);
            int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
            

            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
            	myState.setStateCharging(true);
            }
            else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING)
            {
            	// status battery discharging
            	myState.setStateCharging(false);
            }
            else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
            {
                // status battery not charging
            	myState.setStateCharging(false);

            }
            else
            {
                // status unknown
            	myState.setStateCharging(false);
            }
		}

		// Apply the effect for current state
		EffectManager.doEffect(context, myPrefs.getEffectForState(myState.getState()));
	}
}
