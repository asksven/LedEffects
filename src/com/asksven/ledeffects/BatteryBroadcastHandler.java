package com.asksven.ledeffects;

/**
 * 
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.asksven.ledeffects.data.EffectsState;
import com.asksven.ledeffects.data.Preferences;

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

		// Apply the effect for current state
		EffectManager.doEffect(myPrefs.getEffectForState(myState.getState()));
	}
}
