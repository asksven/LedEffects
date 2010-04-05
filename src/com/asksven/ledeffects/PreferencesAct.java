package com.asksven.ledeffects;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectsFassade;

// see example http://bestsiteinthemultiverse.com/2009/02/android-dialog-screen-example/
public class PreferencesAct extends Activity 
{
	private Preferences m_myPrefs;
	private static int PLAY_DURATION = 10;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_preferences);
        m_myPrefs = new Preferences(this);
        
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

        // setup handler for test buttons
        Button btnTestRing = (Button) findViewById(R.id.ButtonTestRing);
        btnTestRing.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerRing);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
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
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
           }
        });
        
        Button btnTestMail = (Button) findViewById(R.id.ButtonTestMail);
        btnTestMail.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerMail);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
           }
        });
        
        Button btnTestIM = (Button) findViewById(R.id.ButtonTestIM);
        btnTestIM.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
        	   Spinner mySpinner 	= (Spinner) findViewById(R.id.SpinnerIM);
        	   EffectsFassade.getInstance().playEffect(PreferencesAct.this, mySpinner.getSelectedItemPosition(), PLAY_DURATION);
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
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterRing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinRing.setAdapter(myAdapterRing);

        Spinner mySpinCharge = (Spinner) findViewById(R.id.SpinnerCharge);
        ArrayAdapter myAdapterCharge = ArrayAdapter.createFromResource(
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterCharge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinCharge.setAdapter(myAdapterCharge);

        Spinner mySpinSMS = (Spinner) findViewById(R.id.SpinnerSMS);
        ArrayAdapter myAdapterSMS = ArrayAdapter.createFromResource(
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterSMS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSMS.setAdapter(myAdapterSMS);

        Spinner mySpinMail = (Spinner) findViewById(R.id.SpinnerMail);
        ArrayAdapter myAdapterMail = ArrayAdapter.createFromResource(
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterMail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinMail.setAdapter(myAdapterMail);

        Spinner mySpinIM = (Spinner) findViewById(R.id.SpinnerIM);
        ArrayAdapter myAdapterIM = ArrayAdapter.createFromResource(
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterIM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinIM.setAdapter(myAdapterIM);

        Spinner mySpinSleep = (Spinner) findViewById(R.id.SpinnerSleep);
        ArrayAdapter myAdapterSleep = ArrayAdapter.createFromResource(
                this, R.array.effects, android.R.layout.simple_spinner_item);
        myAdapterSleep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinSleep.setAdapter(myAdapterSleep);
        
        
        readPreferences();
    }

	/** load preferences to dialog */
    private void readPreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	CheckBox myNotifyRing	= (CheckBox) findViewById(R.id.CheckBoxNotifyRing);
    	CheckBox myNotifyCharge	= (CheckBox) findViewById(R.id.CheckBoxNotifyCharge);
    	CheckBox myNotifySMS	= (CheckBox) findViewById(R.id.CheckBoxNotifySMS);
    	CheckBox myNotifyMail	= (CheckBox) findViewById(R.id.CheckBoxNotifyMail);
    	CheckBox myNotifyIM		= (CheckBox) findViewById(R.id.CheckBoxNotifyMail);
    	
    	Spinner mySpinnerRing 	= (Spinner) findViewById(R.id.SpinnerRing);
    	Spinner mySpinnerCharge = (Spinner) findViewById(R.id.SpinnerCharge);
    	Spinner mySpinnerSMS 	= (Spinner) findViewById(R.id.SpinnerSMS);
    	Spinner mySpinnerMail 	= (Spinner) findViewById(R.id.SpinnerMail);
    	Spinner mySpinnerIM 	= (Spinner) findViewById(R.id.SpinnerIM);
    	Spinner mySpinnerSleep 	= (Spinner) findViewById(R.id.SpinnerSleep);
    	
    	myAutostart.setChecked(m_myPrefs.getAutostart());
        myNotifyRing.setChecked(m_myPrefs.getNotifyRing());
        myNotifyCharge.setChecked(m_myPrefs.getNotifyCharge());
        myNotifySMS.setChecked(m_myPrefs.getNotifySMS());
        myNotifyMail.setChecked(m_myPrefs.getNotifyMail());
        myNotifyIM.setChecked(m_myPrefs.getNotifyIM());
        
        mySpinnerRing.setSelection(m_myPrefs.getEffectRing());
        mySpinnerCharge.setSelection(m_myPrefs.getEffectCharge());
        mySpinnerSMS.setSelection(m_myPrefs.getEffectSMS());
        mySpinnerMail.setSelection(m_myPrefs.getEffectMail());
        mySpinnerIM.setSelection(m_myPrefs.getEffectIM());
        mySpinnerSleep.setSelection(m_myPrefs.getEffectSleep());       
    }
    
    /** persist changed preferences */
    private void savePreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	CheckBox myNotifyRing	= (CheckBox) findViewById(R.id.CheckBoxNotifyRing);
    	CheckBox myNotifyCharge	= (CheckBox) findViewById(R.id.CheckBoxNotifyCharge);
    	CheckBox myNotifySMS	= (CheckBox) findViewById(R.id.CheckBoxNotifySMS);
    	CheckBox myNotifyMail	= (CheckBox) findViewById(R.id.CheckBoxNotifyMail);
    	CheckBox myNotifyIM	= (CheckBox) findViewById(R.id.CheckBoxNotifyIM);

    	Spinner mySpinnerRing 	= (Spinner) findViewById(R.id.SpinnerRing);
    	Spinner mySpinnerCharge = (Spinner) findViewById(R.id.SpinnerCharge);
    	Spinner mySpinnerSMS 	= (Spinner) findViewById(R.id.SpinnerSMS);
    	Spinner mySpinnerMail 	= (Spinner) findViewById(R.id.SpinnerMail);
    	Spinner mySpinnerIM 	= (Spinner) findViewById(R.id.SpinnerIM);
    	Spinner mySpinnerSleep 	= (Spinner) findViewById(R.id.SpinnerSleep);

    	m_myPrefs.setAutostart(myAutostart.isChecked());
    	
    	m_myPrefs.setNotifyRing(myNotifyRing.isChecked());
    	m_myPrefs.setNotifyCharge(myNotifyCharge.isChecked());
    	m_myPrefs.setNotifySMS(myNotifySMS.isChecked());
    	m_myPrefs.setNotifyMail(myNotifyMail.isChecked());
    	m_myPrefs.setNotifyIM(myNotifyIM.isChecked());
    	
    	m_myPrefs.setEffectRing(mySpinnerRing.getSelectedItemPosition());
    	m_myPrefs.setEffectCharge(mySpinnerCharge.getSelectedItemPosition());
    	m_myPrefs.setEffectSMS(mySpinnerSMS.getSelectedItemPosition());
    	m_myPrefs.setEffectMail(mySpinnerMail.getSelectedItemPosition());
    	m_myPrefs.setEffectIM(mySpinnerIM.getSelectedItemPosition());
    	m_myPrefs.setEffectSleep(mySpinnerSleep.getSelectedItemPosition());
    	
    	m_myPrefs.save();	
    	m_myPrefs.applySleep();
    }
}

