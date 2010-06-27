package com.asksven.ledeffects;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.asksven.ledeffects.data.Preferences;

/**
 * Application Settings
 * @author sven
 *
 */
public class SettingsAct extends Activity 
{
	/** preferences valueholder */
	private Preferences m_myPrefs;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_master_preferences);
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

        Spinner mySpinPhone = (Spinner) findViewById(R.id.SpinnerPhone);
        ArrayAdapter myAdapterRing = ArrayAdapter.createFromResource(
                this, R.array.phones, android.R.layout.simple_spinner_item);
        myAdapterRing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinPhone.setAdapter(myAdapterRing);

        readPreferences();
    }

	
	/** load preferences to dialog */
    private void readPreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	Spinner mySpinnerPhone 	= (Spinner) findViewById(R.id.SpinnerPhone);
    	
    	myAutostart.setChecked(m_myPrefs.getAutostart());
        mySpinnerPhone.setSelection(m_myPrefs.getPhoneModel());
    }
    
    /** persist changed preferences */
    private void savePreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	Spinner mySpinnerPhone 	= (Spinner) findViewById(R.id.SpinnerPhone);

    	m_myPrefs.setAutostart(myAutostart.isChecked());
    	m_myPrefs.setPhoneModel(mySpinnerPhone.getSelectedItemPosition());
        
    	m_myPrefs.save();	
    }
}

