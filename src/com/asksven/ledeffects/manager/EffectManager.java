package com.asksven.ledeffects.manager;

import java.io.DataOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.asksven.ledeffects.MainAct;
import com.asksven.ledeffects.R;
import com.asksven.ledeffects.R.drawable;
import com.asksven.ledeffects.R.string;
import com.asksven.ledeffects.data.SupportedPhones;
import com.asksven.ledeffects.manager.phones.HtcDesire;

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
	
	/** undefined dpad effect file */
	private static final String FILE_UNDEF_LED_EFFECTS 		= "/dev/null";
	
	/** raph /diam dpad ring effect file */
	private static final String FILE_RAPHDIAM_RING_LED_EFFECTS 		= "/dbgfs/micropklt_dbg/effects";

	/** raph / diam sleep effect file */
	private static final String FILE_RAPHDIAM_RING_LED_SLEEP_EFFECTS 	= "/dbgfs/micropklt_dbg/sleep_effects";
	
	/** topa color led effect file
	 * e.g. 	echo 3 > /dbgfs/micropklt_dbg/color_leds
	 * @see http://htc-linux.org/wiki/index.php?title=TopazColorLED */
	private static final String FILE_TOPA_COLORS_EFFECTS 		= "/dbgfs/micropklt_dbg/color_led";
	
	/** nexus one (rooted) lef effect file
	 * e.g. echo "255 0 0" > /sys/devices/platform/i2c-adapter/i2c-0/0-0066/leds/jogball-backlight/color
	 * @see http://forum.xda-developers.com/showpost.php?p=5308427&postcount=8 */
	private static final String FILE_NEXUS_COLORS_EFFECTS 		= "/sys/devices/platform/i2c-adapter/i2c-0/0-0066/leds/jogball-backlight/color";
	
	/** 
	 * Apply the effect by echoing to FILE_EFFECTS 
	 */
	protected static boolean doEffect(int iPhoneModel, int iEffect)
	{
		boolean bChanged = false;
		Log.d("EffectManager.doEffect", "was called with effect " + iEffect);
		if (iEffect != m_iCurrentState)
		{
			bChanged = true;
			m_iCurrentState = iEffect;
	
			String strEffectFile = FILE_UNDEF_LED_EFFECTS;
			switch (iPhoneModel)
			{
				case SupportedPhones.RAPH_DIAM :
					writeEffect(FILE_RAPHDIAM_RING_LED_EFFECTS, iEffect);
					
				case SupportedPhones.TOPA :
					writeEffect(FILE_TOPA_COLORS_EFFECTS, iEffect);
				case SupportedPhones.DESIRE_UNROOTED :
					HtcDesire.writeEffect(iEffect);	
				default :
					writeEffect(FILE_UNDEF_LED_EFFECTS, iEffect);
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
			String strCommand = "echo " + iEffect + " > " + FILE_RAPHDIAM_RING_LED_SLEEP_EFFECTS;
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
	
	private static void writeEffect(String strEffectFile, int iEffect)
	{
		try
		{
			Log.d("EffectManager.doEffect", "Writing effect " + iEffect + " to filesystem");
			// dirty hack: http://code.google.com/p/market-enabler/wiki/ShellCommands
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			String strCommand = "echo " + iEffect + " > " + strEffectFile;
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
