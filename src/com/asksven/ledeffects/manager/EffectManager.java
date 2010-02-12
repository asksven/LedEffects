package com.asksven.ledeffects.manager;

import java.io.DataOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.asksven.ledeffects.MainAct;
import com.asksven.ledeffects.R;
import com.asksven.ledeffects.R.drawable;
import com.asksven.ledeffects.R.string;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * EffectManager encapsulates the writing to the dbgfs ressources responsible
 * for the handling the effect (kernel level)  
 * @author sven
 *
 */
public class EffectManager
{
	private static int m_iCurrentState				= 0;
	
	private static final String FILE_EFFECTS 		= "/dbgfs/micropklt_dbg/effects";
	private static final String FILE_SLEEP_EFFECTS 	= "/dbgfs/micropklt_dbg/sleep_effects";
	
	/** 
	 * Apply the effect by echoing to FILE_EFFECTS 
	 */
	protected static boolean doEffect(int iEffect)
	{
		boolean bChanged = false;
		Log.d("EffectManager.doEffect", "Writing effect " + iEffect + " to filesystem");
		if (iEffect != m_iCurrentState)
		{
			bChanged = true;
			m_iCurrentState = iEffect;
			try
			{
				// dirty hack: http://code.google.com/p/market-enabler/wiki/ShellCommands
				Process process = Runtime.getRuntime().exec("su");
				DataOutputStream os = new DataOutputStream(process.getOutputStream());
				String strCommand = "echo " + iEffect + " > " + FILE_EFFECTS;
				os.writeBytes(strCommand + "\n");
				os.flush();
				os.writeBytes("exit\n");
				os.flush();
				process.waitFor();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			bChanged = false;
		}
		
		return bChanged;
	}
	
	/** 
	 * Save the sleep effect by echoing to FILE_SLEEP_EFFECTS
	 */
	protected static void writeSleepEffect(int iEffect)
	{
		Log.d("EffectManager.writeSleepEffect", "Writing effect " + iEffect + " to filesystem");
		m_iCurrentState = iEffect;
		try
		{
			// dirty hack: http://code.google.com/p/market-enabler/wiki/ShellCommands
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			String strCommand = "echo " + iEffect + " > " + FILE_SLEEP_EFFECTS;
			os.writeBytes(strCommand + "\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
