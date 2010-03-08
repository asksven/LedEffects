package com.asksven.ledeffects;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.os.Handler;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectsFassade;
import com.asksven.ledeffects.xmmp.XmppClient;

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
	private Timer timer = new Timer();
	
	/** the timer update freq. in ms */
	private long m_lUpdateInterval = 30*1000;
	
	/** should we connect xmpp? */
	boolean m_bXmppConnect = false;
	
	private BatteryBroadcastHandler m_oBatHandler = null;
	private boolean m_bRegistered = false;
	
	/** Optional XMPP client managed by service */
	private XmppClient m_myXmppClient;
	
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
        
        //        startTimer();

        // tried to fix bug http://code.google.com/p/android/issues/detail?id=3259
		// by programmatically registering to the event
        if (!m_bRegistered)
        {
        	m_oBatHandler = new BatteryBroadcastHandler();
            registerReceiver(m_oBatHandler, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            m_bRegistered = true;
        }
        m_myXmppClient = new XmppClient(this);
    }

    /** 
     * Called when service is started
     */
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // read the Preferences upon start
    	loadPrefs();
    	
    	// connect to xmpp
    	if (m_bXmppConnect)
    	{
    		m_myXmppClient.connect();
    	}
    	
    	// run the timer
// timer's not needed    	startTimer();
    	
        Log.i(getClass().getSimpleName(), "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return 1; // Service.START_STICKY;
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

        stopTimer();
        
        m_myXmppClient.disconnect();
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
    private void updateEffects()
    {
		// Apply the effect for current state
    	EffectsFassade.getInstance().doEffect(this);
    	Preferences myPrefs = new Preferences(this.getSharedPreferences(Preferences.PREFS_NAME, 0));
    }
    
    /**
     * Start the timer when starting the service
     */
    private void startTimer()
    {        
    	timer.scheduleAtFixedRate(
    			new TimerTask()
    			{
			        public void run()
			        {
			        	updateEffects();
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
    
    /**
     *  read the persisited preferences and set the sleep-effect when the service starts
     */
    private void loadPrefs()
    {
        Preferences myPrefs = new Preferences(this.getSharedPreferences(Preferences.PREFS_NAME, 0));
        m_lUpdateInterval 	= myPrefs.getPollInterval() * 1000; // as timer is in ms
        m_bXmppConnect = myPrefs.getXmppConnect();
        myPrefs.applySleep();

    }
}

