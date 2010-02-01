package com.asksven.ledeffects;

import java.io.DataOutputStream;

public class EffectManager
{
	public static final String EFFECT_NONE 		= "0";
	public static final String EFFECT_RING 		= "1";
	public static final String EFFECT_BLINK 	= "2";
	public static final String EFFECT_BREATHE 	= "3";
	public static final String EFFECT_FADE 		= "4";
	public static final String EFFECT_ROTATE 	= "5";
	public static final String EFFECT_VERTICAL	= "6";
	
	private static final String FILE_EFFECTS 	= "/dbgfs/micropklt_dbg/effects";
	
	/** Apply the effect by echoing to FILE_EFFECTS */
	public static void doEffect(String strEffect)
	{
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
	}
}
