package com.asksven.ledeffects;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.asksven.ledeffects.data.Preferences;
import com.asksven.ledeffects.manager.EffectManager;

// see example http://bestsiteinthemultiverse.com/2009/02/android-dialog-screen-example/
public class PreferencesAct extends Activity 
{
	private Preferences m_myPrefs;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_preferences);
        m_myPrefs = new Preferences(this);
        readPreferences();
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
        
        // setup handler for the Test Buttons
        Button btnTest1 = (Button) findViewById(R.id.ButtonOff);
        btnTest1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
 	           EffectManager.doEffect(PreferencesAct.this, EffectManager.EFFECT_NONE);
            }
         });

        // setup handler for the Test Buttons
        Button btnTest2 = (Button) findViewById(R.id.Button1);
        btnTest2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
 	           EffectManager.doEffect(PreferencesAct.this, EffectManager.EFFECT_BREATHE);
            }
         });
        
    }

	/** load preferences to dialog */
    private void readPreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	EditText myTimeout		= (EditText) findViewById(R.id.EditTextTimeout);

    	
    	myAutostart.setChecked(m_myPrefs.getAutostart());
        myTimeout.setText	(String.valueOf(m_myPrefs.getPollInterval()));
    }
    
    /** persist changed preferences */
    private void savePreferences()
    {

    	CheckBox myAutostart	= (CheckBox) findViewById(R.id.CheckBoxAutostart);
    	EditText myTimeout		= (EditText) findViewById(R.id.EditTextTimeout);

    
    	m_myPrefs.setAutostart(myAutostart.isChecked());
    	m_myPrefs.setPollInterval(Integer.parseInt(myTimeout.getText().toString()));
    	
    	m_myPrefs.save();	
    }
}

