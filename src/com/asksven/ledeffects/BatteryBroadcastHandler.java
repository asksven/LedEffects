package com.asksven.ledeffects;

/**
 * 
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.asksven.ledeffects.manager.EffectsFassade;

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
        EffectsFassade myEffectMgr = EffectsFassade.getInstance();
        
		// Events for power state	
		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
		{
			myEffectMgr.setStateCharging(true);
		}
		
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
		{
			myEffectMgr.setStateCharging(false);
		}

		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))
		{
			Log.i(getClass().getSimpleName(), "Received Broadcast ACTION_BATTERY_CHANGED");

		    
            int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
            

            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
            	myEffectMgr.setStateCharging(true);
            }
            else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING)
            {
            	// status battery discharging
            	myEffectMgr.setStateCharging(false);
            }
            else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
            {
                // status battery not charging
            	myEffectMgr.setStateCharging(false);

            }
            else
            {
                // status unknown
            	myEffectMgr.setStateCharging(false);
            }
		}

		// Apply the effect for current state
		EffectsFassade.getInstance().doEffect(context);
	}
}
