package com.asksven.ledeffects;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.asksven.ledeffects.data.Preferences;

public class EffectsService extends Service
{
	private NotificationManager mNM;
	private Timer timer = new Timer();
	
	/** the timer update freq. in ms */
	private long m_lUpdateInterval = 30*1000;
	
	
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
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Log.i(getClass().getSimpleName(), "onCreate called");
        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
        
        // start the timer
        startTimer();
    }

    
//	    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // read the Preferences upon start
    	loadPrefs();
    	
        Log.i(getClass().getSimpleName(), "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return 1; // Service.START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // Cancel the persistent notification.
        mNM.cancel(R.string.local_service_started);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        stopTimer();
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
     * Check for location changes at each timer occurrence
     */
    private void updateSituation()
    {
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification()
    {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text, System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainAct.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label), text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.local_service_started, notification);
    }
    
    private void notify(String strNote)
    {
    	Notification notification = new Notification(R.drawable.icon, strNote, System.currentTimeMillis());
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainAct.class), 0);
    	notification.setLatestEventInfo(this, getText(R.string.local_service_label), strNote, contentIntent);
    	mNM.notify(R.string.local_service_started, notification);
    }
    
    /**
     * Start the timer when starting the service
     */
    private void startTimer()
    {
    	// read the Preferences upon start
        loadPrefs();
        
    	timer.scheduleAtFixedRate(
    			new TimerTask()
    			{
			        public void run()
			        {
			        	
			        	updateSituation();
			        }
			     }, 0, m_lUpdateInterval);
			  Log.i(getClass().getSimpleName(), "Timer started!!! (timeout=" + m_lUpdateInterval + " ms)");
    }

    /**
     * Stop the timer when stopping the service
     */
    private void stopTimer()
    {
    	if (timer != null) timer.cancel();
		Log.i(getClass().getSimpleName(), "Timer stopped!!!");
	}
    
    /** read the current preferences */
    private void loadPrefs()
    {
        Preferences myPrefs = new Preferences(this.getSharedPreferences(Preferences.PREFS_NAME, 0));
        m_lUpdateInterval 	= myPrefs.getPollInterval() * 1000; // as timer is in ms

    }
    
}

