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

public class EffectManager
{
/*	public static final String EFFECT_NONE 		= "0";
	public static final String EFFECT_RING 		= "1";
	public static final String EFFECT_BLINK 	= "2";
	public static final String EFFECT_BREATHE 	= "3";
	public static final String EFFECT_FADE 		= "4";
	public static final String EFFECT_ROTATE 	= "5";
	public static final String EFFECT_VERTICAL	= "6";
*/	
	private static int m_iCurrentState		= 0;
	
	private static final String FILE_EFFECTS 	= "/dbgfs/micropklt_dbg/effects";
	private static final String FILE_SLEEP_EFFECTS 	= "/dbgfs/micropklt_dbg/sleep_leds";
	
	/** Apply the effect by echoing to FILE_EFFECTS */
	protected static boolean doEffect(int iEffect)
	{
		boolean bChanged = false;
		
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
/*			finally
			{
				if (iEffect != 0)
				{
					NotificationManager mNM = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
					String strNote = "Applying effect " + iEffect;
			    	Notification notification = new Notification(R.drawable.icon, strNote, System.currentTimeMillis());
			    	PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
			                new Intent(ctx, MainAct.class), 0);
			    	notification.setLatestEventInfo(ctx, ctx.getText(R.string.local_service_label), strNote, contentIntent);
			    	mNM.notify(R.string.app_name, notification);
			    	
			    	
				}
	
			}
			*/
		}
		else
		{
			bChanged = false;
		}
		
		return bChanged;
	}
	/** Save the effect by echoing to FILE_SLEEP_EFFECTS */
	protected static void writeSleepEffect(int iEffect)
	{
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
