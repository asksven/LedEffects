package com.asksven.ledeffects;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectsFassade;

// see example http://bestsiteinthemultiverse.com/2009/02/android-dialog-screen-example/
public class PreferencesAct extends Activity 
{
	/** Returncode for the file picker intent depends on the picked effect */
	protected static final int PICK_FILE_TEST	= 99;
	protected static final int PICK_FILE_RING	= 100;
	protected static final int PICK_FILE_SMS	= 101;
	protected static final int PICK_FILE_IM		= 102;
	protected static final int PICK_FILE_MAIL	= 103;
	
	/** URIs for sound associated to effects */
	private String m_strUriRing 	= "";
	private String m_strUriSMS 		= "";
	private String m_strUriIM		= "";
	private String m_strUriMail		= "";
	
	
	/** preferences valueholder */
	private Preferences m_myPrefs;
	
	/** default play duration for effects */
	private static int PLAY_DURATION = 10;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_preferences);
        m_myPrefs = Preferences.getInstance(this);
        
        // setup handler for Ok button
        Button btnOk = (Button) findViewById(R.id.ButtonOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   savePreferences();
	           setResult(RESULT_OK);
	           finish();
           }
        });
        // setup handler for the Cancel button
        Button btnCancel = (Button) findViewById(R.id.ButtonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
 	           setResult(RESULT_CANCELED);
 	           finish();
            }
         });

        // setup handler for sound picker buttons
        Button btnPickRing = (Button) findViewById(R.id.ButtonPickRing);
        btnPickRing.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
	        Intent intentBrowseFiles = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	        PreferencesAct.this.startActivityForResult(intentBrowseFiles, PICK_FILE_RING);
           }
        });

        Button btnPickSMS = (Button) findViewById(R.id.ButtonPickSMS);
        btnPickSMS.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
	        Intent intentBrowseFiles = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	        PreferencesAct.this.startActivityForResult(intentBrowseFiles, PICK_FILE_SMS);
           }
        });

        Button btnPickIM = (Button) findViewById(R.id.ButtonPickIM);
        btnPickIM.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
	        Intent intentBrowseFiles = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	        PreferencesAct.this.startActivityForResult(intentBrowseFiles, PICK_FILE_IM);
           }
        });
        
        Button btnPickMail = (Button) findViewById(R.id.ButtonPickMail);
        btnPickMail.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
	        Intent intentBrowseFiles = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	        PreferencesAct.this.startActivityForResult(intentBrowseFiles, PICK_FILE_MAIL);
           }
        });

        
        // setup handler for test buttons
        Button btnTestRing = (Button) findViewById(R.id.ButtonTestRing);
        btnTestRing.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerRing);
        	   CheckBox myCheckVibrate	= (CheckBox) findViewById(R.id.CheckBoxVibrateRing);
        	   CheckBox myCheckSound	= (CheckBox) findViewById(R.id.CheckBoxSoundRing);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION, myCheckVibrate.isChecked(), m_strUriRing);
           }
        });
        Button btnTestCharge = (Button) findViewById(R.id.ButtonTestCharge);
        btnTestCharge.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerCharge);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
           }
        });
        Button btnTestSMS = (Button) findViewById(R.id.ButtonTestSMS);
        btnTestSMS.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerSMS);
        	   CheckBox myCheckVibrate	= (CheckBox) findViewById(R.id.CheckBoxVibrateSMS);
        	   CheckBox myCheckSound	= (CheckBox) findViewById(R.id.CheckBoxSoundSMS);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION, myCheckVibrate.isChecked(), m_strUriSMS);
           }
        });
        
        Button btnTestMail = (Button) findViewById(R.id.ButtonTestMail);
        btnTestMail.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerMail);
        	   CheckBox myCheckVibrate	= (CheckBox) findViewById(R.id.CheckBoxVibrateMail);
        	   CheckBox myCheckSound	= (CheckBox) findViewById(R.id.CheckBoxSoundMail);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION, myCheckVibrate.isChecked(), m_strUriMail);
           }
        });
        
        Button btnTestIM = (Button) findViewById(R.id.ButtonTestIM);
        btnTestIM.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerIM);
        	   CheckBox myCheckVibrate	= (CheckBox) findViewById(R.id.CheckBoxVibrateIM);
        	   CheckBox myCheckSound	= (CheckBox) findViewById(R.id.CheckBoxSoundIM);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION, myCheckVibrate.isChecked(), m_strUriIM);
           }
        });
       
        Button btnTestSleep = (Button) findViewById(R.id.ButtonTestSleep);
        btnTestSleep.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerSleep);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
           }
        });

        Spinner mySpinRing = (Spinner) findViewById(R.id.SpinnerRing);
        ArrayAdapter myAdapterRing = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterRing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinRing.setAdapter(myAdapterRing);

        Spinner mySpinCharge = (Spinner) findViewById(R.id.SpinnerCharge);
        ArrayAdapter myAdapterCharge = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterCharge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinCharge.setAdapter(myAdapterCharge);

        Spinner mySpinSMS = (Spinner) findViewById(R.id.SpinnerSMS);
        ArrayAdapter myAdapterSMS = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterSMS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSMS.setAdapter(myAdapterSMS);

        Spinner mySpinMail = (Spinner) findViewById(R.id.SpinnerMail);
        ArrayAdapter myAdapterMail = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterMail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinMail.setAdapter(myAdapterMail);

        Spinner mySpinIM = (Spinner) findViewById(R.id.SpinnerIM);
        ArrayAdapter myAdapterIM = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterIM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinIM.setAdapter(myAdapterIM);

        Spinner mySpinSleep = (Spinner) findViewById(R.id.SpinnerSleep);
        ArrayAdapter myAdapterSleep = ArrayAdapter.createFromResource(
                this, m_myPrefs.getEffectEnumId(), android.R.layout.simple_spinner_item);
        myAdapterSleep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSleep.setAdapter(myAdapterSleep);
        
        Spinner mySpinVibrateOffFrom = (Spinner) findViewById(R.id.SpinnerVibrationOffFrom);
        ArrayAdapter myAdapterVibrateOffFrom = ArrayAdapter.createFromResource(
                this, R.array.times, android.R.layout.simple_spinner_item);
        myAdapterVibrateOffFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinVibrateOffFrom.setAdapter(myAdapterVibrateOffFrom);

        Spinner mySpinVibrateOffTo = (Spinner) findViewById(R.id.SpinnerVibrationOffTo);
        ArrayAdapter myAdapterVibrateOffTo = ArrayAdapter.createFromResource(
                this, R.array.times, android.R.layout.simple_spinner_item);
        myAdapterVibrateOffTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinVibrateOffTo.setAdapter(myAdapterVibrateOffTo);

        Spinner mySpinSoundOffFrom = (Spinner) findViewById(R.id.SpinnerSoundOffFrom);
        ArrayAdapter myAdapterSoundOffFrom = ArrayAdapter.createFromResource(
                this, R.array.times, android.R.layout.simple_spinner_item);
        myAdapterSoundOffFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSoundOffFrom.setAdapter(myAdapterSoundOffFrom);

        Spinner mySpinSoundOffTo = (Spinner) findViewById(R.id.SpinnerSoundOffTo);
        ArrayAdapter myAdapterSoundOffTo = ArrayAdapter.createFromResource(
                this, R.array.times, android.R.layout.simple_spinner_item);
        myAdapterSoundOffTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSoundOffTo.setAdapter(myAdapterSoundOffTo);
        
        readPreferences();
    }

	// meanwhile, back in your activity...
	protected final void onActivityResult(final int requestCode, final int resultCode, final Intent i)
	{
		super.onActivityResult(requestCode, resultCode, i);

		// this matches the request code in the above call
		Uri myUri = i.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		if (myUri != null)
		{
			String strUri = myUri.toString();
			
			switch (requestCode)
			{
				case PICK_FILE_RING:
					m_strUriRing = strUri;
					break;

				case PICK_FILE_SMS:
					m_strUriSMS = strUri;
					break;
					
				case PICK_FILE_IM:
					m_strUriIM = strUri;
					break;
					
				case PICK_FILE_MAIL:
					m_strUriMail = strUri;
					break;
					
				case PICK_FILE_TEST:
					MediaPlayer myPlayer = new MediaPlayer();
					try
					{
						Uri myNewUri = Uri.parse(strUri);
						myPlayer.setDataSource(this, myNewUri);
						myPlayer.prepare();
						myPlayer.start();
					}
					catch (Exception e)
					{
						Log.d(getClass().getSimpleName(), e.getMessage());
					}
					break;
	
				default:
			}
		}
	}
	
	/** load preferences to dialog */
    private void readPreferences()
    {

    	CheckBox myNotifyRing	= (CheckBox) findViewById(R.id.CheckBoxNotifyRing);
    	CheckBox myNotifyCharge	= (CheckBox) findViewById(R.id.CheckBoxNotifyCharge);
    	CheckBox myNotifySMS	= (CheckBox) findViewById(R.id.CheckBoxNotifySMS);
    	CheckBox myNotifyMail	= (CheckBox) findViewById(R.id.CheckBoxNotifyMail);
    	CheckBox myNotifyIM		= (CheckBox) findViewById(R.id.CheckBoxNotifyIM);

    	CheckBox myVibrateRing	= (CheckBox) findViewById(R.id.CheckBoxVibrateRing);
    	CheckBox myVibrateSMS	= (CheckBox) findViewById(R.id.CheckBoxVibrateSMS);
    	CheckBox myVibrateMail	= (CheckBox) findViewById(R.id.CheckBoxVibrateMail);
    	CheckBox myVibrateIM	= (CheckBox) findViewById(R.id.CheckBoxVibrateIM);

    	CheckBox mySoundRing	= (CheckBox) findViewById(R.id.CheckBoxSoundRing);
    	CheckBox mySoundSMS		= (CheckBox) findViewById(R.id.CheckBoxSoundSMS);
    	CheckBox mySoundMail	= (CheckBox) findViewById(R.id.CheckBoxSoundMail);
    	CheckBox mySoundIM		= (CheckBox) findViewById(R.id.CheckBoxSoundIM);
    	
    	CheckBox myVibrateOff	= (CheckBox) findViewById(R.id.CheckBoxVibrationOff);
    	CheckBox mySoundOff	= (CheckBox) findViewById(R.id.CheckBoxSoundOff);
    	
    	Spinner mySpinnerRing 	= (Spinner) findViewById(R.id.SpinnerRing);
    	Spinner mySpinnerCharge = (Spinner) findViewById(R.id.SpinnerCharge);
    	Spinner mySpinnerSMS 	= (Spinner) findViewById(R.id.SpinnerSMS);
    	Spinner mySpinnerMail 	= (Spinner) findViewById(R.id.SpinnerMail);
    	Spinner mySpinnerIM 	= (Spinner) findViewById(R.id.SpinnerIM);
    	Spinner mySpinnerSleep 	= (Spinner) findViewById(R.id.SpinnerSleep);
    	
    	Spinner mySpinnerVFrom 	= (Spinner) findViewById(R.id.SpinnerVibrationOffFrom);
    	Spinner mySpinnerVTo 	= (Spinner) findViewById(R.id.SpinnerVibrationOffTo);
    	Spinner mySpinnerSFrom 	= (Spinner) findViewById(R.id.SpinnerSoundOffFrom);
    	Spinner mySpinnerSTo 	= (Spinner) findViewById(R.id.SpinnerSoundOffTo);

    	
        myNotifyRing.setChecked(m_myPrefs.getNotifyRing());
        myNotifyCharge.setChecked(m_myPrefs.getNotifyCharge());
        myNotifySMS.setChecked(m_myPrefs.getNotifySMS());
        myNotifyMail.setChecked(m_myPrefs.getNotifyMail());
        myNotifyIM.setChecked(m_myPrefs.getNotifyIM());
        
        myVibrateRing.setChecked(m_myPrefs.getVibrateRing());
        myVibrateSMS.setChecked(m_myPrefs.getVibrateSMS());
        myVibrateMail.setChecked(m_myPrefs.getVibrateMail());
        myVibrateIM.setChecked(m_myPrefs.getVibrateIM());

        mySoundRing.setChecked(m_myPrefs.getPlaySoundRing());
        mySoundSMS.setChecked(m_myPrefs.getPlaySoundSMS());
        mySoundMail.setChecked(m_myPrefs.getPlaySoundMail());
        mySoundIM.setChecked(m_myPrefs.getPlaySoundIM());

        mySpinnerRing.setSelection(m_myPrefs.getEffectRing());
        mySpinnerCharge.setSelection(m_myPrefs.getEffectCharge());
        mySpinnerSMS.setSelection(m_myPrefs.getEffectSMS());
        mySpinnerMail.setSelection(m_myPrefs.getEffectMail());
        mySpinnerIM.setSelection(m_myPrefs.getEffectIM());
        mySpinnerSleep.setSelection(m_myPrefs.getEffectSleep());
        
        // sounds
        m_strUriRing 	= m_myPrefs.getSoundRing();	 
        m_strUriSMS 	= m_myPrefs.getSoundSMS();
        m_strUriIM 		= m_myPrefs.getSoundIM();
        m_strUriMail	= m_myPrefs.getSoundMail();
        
        // silence options
        myVibrateOff.setChecked(m_myPrefs.getVibrateOff());
        mySoundOff.setChecked(m_myPrefs.getSoundOff());
        
        mySpinnerVFrom.setSelection(m_myPrefs.getVibrateOffTimespan().getFromHours());
        mySpinnerVTo.setSelection(m_myPrefs.getVibrateOffTimespan().getToHours());
        mySpinnerSFrom.setSelection(m_myPrefs.getSoundOffTimespan().getFromHours());
        mySpinnerSTo.setSelection(m_myPrefs.getSoundOffTimespan().getToHours());

    }
    
    /** persist changed preferences */
    private void savePreferences()
    {
    	CheckBox myNotifyRing	= (CheckBox) findViewById(R.id.CheckBoxNotifyRing);
    	CheckBox myNotifyCharge	= (CheckBox) findViewById(R.id.CheckBoxNotifyCharge);
    	CheckBox myNotifySMS	= (CheckBox) findViewById(R.id.CheckBoxNotifySMS);
    	CheckBox myNotifyMail	= (CheckBox) findViewById(R.id.CheckBoxNotifyMail);
    	CheckBox myNotifyIM	= (CheckBox) findViewById(R.id.CheckBoxNotifyIM);

    	CheckBox myVibrateRing	= (CheckBox) findViewById(R.id.CheckBoxVibrateRing);
    	CheckBox myVibrateSMS	= (CheckBox) findViewById(R.id.CheckBoxVibrateSMS);
    	CheckBox myVibrateMail	= (CheckBox) findViewById(R.id.CheckBoxVibrateMail);
    	CheckBox myVibrateIM	= (CheckBox) findViewById(R.id.CheckBoxVibrateIM);

    	CheckBox mySoundRing	= (CheckBox) findViewById(R.id.CheckBoxSoundRing);
    	CheckBox mySoundSMS		= (CheckBox) findViewById(R.id.CheckBoxSoundSMS);
    	CheckBox mySoundMail	= (CheckBox) findViewById(R.id.CheckBoxSoundMail);
    	CheckBox mySoundIM		= (CheckBox) findViewById(R.id.CheckBoxSoundIM);
    	
    	CheckBox myVibrateOff	= (CheckBox) findViewById(R.id.CheckBoxVibrationOff);
    	CheckBox mySoundOff	= (CheckBox) findViewById(R.id.CheckBoxSoundOff);


    	Spinner mySpinnerRing 	= (Spinner) findViewById(R.id.SpinnerRing);
    	Spinner mySpinnerCharge = (Spinner) findViewById(R.id.SpinnerCharge);
    	Spinner mySpinnerSMS 	= (Spinner) findViewById(R.id.SpinnerSMS);
    	Spinner mySpinnerMail 	= (Spinner) findViewById(R.id.SpinnerMail);
    	Spinner mySpinnerIM 	= (Spinner) findViewById(R.id.SpinnerIM);
    	Spinner mySpinnerSleep 	= (Spinner) findViewById(R.id.SpinnerSleep);
    	
    	Spinner mySpinnerVFrom 	= (Spinner) findViewById(R.id.SpinnerVibrationOffFrom);
    	Spinner mySpinnerVTo 	= (Spinner) findViewById(R.id.SpinnerVibrationOffTo);
    	Spinner mySpinnerSFrom 	= (Spinner) findViewById(R.id.SpinnerSoundOffFrom);
    	Spinner mySpinnerSTo 	= (Spinner) findViewById(R.id.SpinnerSoundOffTo);


    	m_myPrefs.setNotifyRing(myNotifyRing.isChecked());
    	m_myPrefs.setNotifyCharge(myNotifyCharge.isChecked());
    	m_myPrefs.setNotifySMS(myNotifySMS.isChecked());
    	m_myPrefs.setNotifyMail(myNotifyMail.isChecked());
    	m_myPrefs.setNotifyIM(myNotifyIM.isChecked());

    	m_myPrefs.setVibrateRing(myVibrateRing.isChecked());
    	m_myPrefs.setVibrateSMS(myVibrateSMS.isChecked());
    	m_myPrefs.setVibrateMail(myVibrateMail.isChecked());
    	m_myPrefs.setVibrateIM(myVibrateIM.isChecked());

    	m_myPrefs.setPlaySoundRing(mySoundRing.isChecked());
    	m_myPrefs.setPlaySoundSMS(mySoundSMS.isChecked());
    	m_myPrefs.setPlaySoundMail(mySoundMail.isChecked());
    	m_myPrefs.setPlaySoundIM(mySoundIM.isChecked());

    	m_myPrefs.setEffectRing(mySpinnerRing.getSelectedItemPosition());
    	m_myPrefs.setEffectCharge(mySpinnerCharge.getSelectedItemPosition());
    	m_myPrefs.setEffectSMS(mySpinnerSMS.getSelectedItemPosition());
    	m_myPrefs.setEffectMail(mySpinnerMail.getSelectedItemPosition());
    	m_myPrefs.setEffectIM(mySpinnerIM.getSelectedItemPosition());
    	m_myPrefs.setEffectSleep(mySpinnerSleep.getSelectedItemPosition());
    	
    	m_myPrefs.setSoundRing(m_strUriRing);	 
    	m_myPrefs.setSoundSMS(m_strUriSMS);
    	m_myPrefs.setSoundIM(m_strUriIM);
    	m_myPrefs.setSoundMail(m_strUriMail);

        // silence options
    	m_myPrefs.setVibrateOff(myVibrateOff.isChecked());
    	m_myPrefs.setSoundOff(mySoundOff.isChecked());
        
        m_myPrefs.setVibrateOffTimespan(mySpinnerVFrom.getSelectedItemPosition(), mySpinnerVTo.getSelectedItemPosition());
        m_myPrefs.setSoundOffTimespan(mySpinnerSFrom.getSelectedItemPosition(), mySpinnerSTo.getSelectedItemPosition());
                
    	m_myPrefs.save();	
    	m_myPrefs.applySleep();
    }
}

