package com.asksven.ledeffects;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.asksven.ledeffects.data.Preferences;

/**
 * The LedEffects Service keeps running even if the main Activity is not displayed/never called
 * The Services takes care of always running tasks and of tasks taking place once in the lifecycle
 * without user interaction.
 * @author sven
 *
 */
public class EffectsService extends Service
{
	private NotificationManager mNM;

	private BatteryBroadcastHandler m_oBatHandler = null;
	private boolean m_bRegistered = false;
	
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder
    {
        EffectsService getService()
        {
            return EffectsService.this;
        }
    }

    @Override
    public void onCreate()
    {
    	Log.i(getClass().getSimpleName(), "onCreate called");

        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
        // tried to fix bug http://code.google.com/p/android/issues/detail?id=3259
		// by programmatically registering to the event
        if (!m_bRegistered)
        {
        	m_oBatHandler = new BatteryBroadcastHandler();
            registerReceiver(m_oBatHandler, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            m_bRegistered = true;
        }
    }

    /** 
     * Called when service is started
     */
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // read the Preferences upon start
    	loadPrefs();
    	
        Log.i(getClass().getSimpleName(), "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return Service.START_STICKY;
    }
    @Override
    /**
     * Called when Service is terminated
     */
    public void onDestroy()
    {
        // Cancel the persistent notification.
        mNM.cancel(R.string.app_name);
        
        // unregister the broadcastreceiver
        unregisterReceiver(m_oBatHandler);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     *  read the persisited preferences and set the sleep-effect when the service starts
     */
    private void loadPrefs()
    {
        Preferences myPrefs = Preferences.getInstance(this);
        myPrefs.applySleep();

    }
}

