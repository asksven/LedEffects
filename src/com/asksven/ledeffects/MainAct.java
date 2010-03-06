package com.asksven.ledeffects;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.asksven.ledeffects.manager.EffectsFassade;

/**
 * The main Activity of LedEffects
 * @author sven
 *
 */
public class MainAct extends Activity
{
    private boolean m_bIsStarted;
    
    static final int DIALOG_PREFERENCES_ID 	= 0;
    static final int DIALOG_ABOUT_ID 	= 1;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set UI stuff
        setContentView(R.layout.main);

                
        // run the underlying service
        startService();
        
    }
    @Override
    /**
     * Called when Activity is paused
     */
    protected void onPause()
    {
    	super.onPause();
    }
    
    @Override
    /**
     * Called when activity is stopped
     */
    public void onStop()
    {
    	super.onStop();
                
    }
    
    @Override
    /**
     * Called after Activity was first created and/or when it resumes
     */
    protected void onResume()
    {
        // cancel any notification that we started by us
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(R.string.app_name);
        
        // when we open the main activity we want all notifications to be cleared
        EffectsFassade.getInstance().clearAllNotifications(this);

    	super.onResume();                
    }
        
    /**
     * Called only the first time the options menu is displayed.
     * Create the menu entries.
     * Menu adds items in the order shown.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("Preferences");
        menu.add("About");
        SubMenu myGroup = menu.addSubMenu("More...");
        myGroup.add("Stop Service");
        myGroup.add("Start Service");

        return true;
    }

    /**
     * handle menu selected
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	if (item.getTitle().equals("Preferences"))
    	{
    		Intent intent = new Intent(this, com.asksven.ledeffects.PreferencesAct.class);
   			startActivityForResult(intent, DIALOG_PREFERENCES_ID);
    		return true;
    	}
    	if (item.getTitle().equals("About"))
    	{
    		Intent intent = new Intent(this, com.asksven.ledeffects.AboutAct.class);
   			startActivityForResult(intent, DIALOG_ABOUT_ID);
    		return true;
    	}

    	else if (item.getTitle().equals("Stop Service"))
    	{
    		stopService();
    		return true;
    	}
   		else if (item.getTitle().equals("Start Service"))
   		{
   			startService();
   			return true;
   		}
    	else
    	{
    		return false;
    	}
    }

    /** 
     * Starts the service 
     */
	private void startService()
	{
		if( m_bIsStarted )
		{
			Toast.makeText(this, "Service already started", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Intent i = new Intent();
			i.setClassName( "com.asksven.ledeffects", "com.asksven.ledeffects.EffectsService" );
			startService( i );
			Log.i(getClass().getSimpleName(), "startService()");
			m_bIsStarted = true;
		}
	}

	/**
	 * Stops the service
	 */
	private void stopService()
	{
		if( m_bIsStarted )
		{
			Intent i = new Intent();
			i.setClassName( "com.asksven.ledeffects", "com.asksven.ledeffects.EffectsService" );
			stopService( i );
			Log.i(getClass().getSimpleName(), "stopService()");
			m_bIsStarted = false;
		}
		else
		{
			Toast.makeText(this, "Service already started", Toast.LENGTH_SHORT).show();
		}
	}   
}