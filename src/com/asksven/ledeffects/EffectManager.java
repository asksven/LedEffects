package com.asksven.ledeffects;

import java.io.DataOutputStream;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class EffectManager
{
	public static final String EFFECT_NONE 		= "0";
	public static final String EFFECT_RING 		= "1";
	public static final String EFFECT_BLINK 	= "2";
	public static final String EFFECT_BREATHE 	= "3";
	public static final String EFFECT_FADE 		= "4";
	public static final String EFFECT_ROTATE 	= "5";
	public static final String EFFECT_VERTICAL	= "6";
	
	private static String m_strCurrentState		= EFFECT_NONE;
	
	private static final String FILE_EFFECTS 	= "/dbgfs/micropklt_dbg/effects";
	
	/** Apply the effect by echoing to FILE_EFFECTS */
	public static boolean doEffect(Context ctx, String strEffect)
	{
		boolean bChanged = false;
		
		if (!strEffect.equals(m_strCurrentState))
		{
			bChanged = true;
			m_strCurrentState = strEffect;
			try
			{
				// dirty hack: http://code.google.com/p/market-enabler/wiki/ShellCommands
				Process process = Runtime.getRuntime().exec("su");
				DataOutputStream os = new DataOutputStream(process.getOutputStream());
				String strCommand = "echo " + strEffect + " > " + FILE_EFFECTS;
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
			finally
			{
				if (!strEffect.equals(EFFECT_NONE))
				{
					NotificationManager mNM = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
					String strNote = "Applying effect " + strEffect;
			    	Notification notification = new Notification(R.drawable.icon, strNote, System.currentTimeMillis());
			    	PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
			                new Intent(ctx, MainAct.class), 0);
			    	notification.setLatestEventInfo(ctx, ctx.getText(R.string.local_service_label), strNote, contentIntent);
			    	mNM.notify(R.string.app_name, notification);
				}
	
			}
		}
		else
		{
			bChanged = false;
		}
		
		return bChanged;
	}
}
