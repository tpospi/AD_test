package com.example.starfighter;

import android.content.Context;
import android.content.Intent;
import android.view.View;


public class SFEngine {
/*konstanty*/
	public static final int GAME_THREAD_DELAY=1000; //spozdeni uvodni obrazovky
	public static final int MENU_BUTTON_ALPHA=0; //pruhlednost tlacitka
	public static final boolean HAPTIC_BUTTON_FEEDBACK=true; //hapticka odezva tlacitek
	public static final int SPLASH_SCREEN_MUSIC=R.raw.warfieldedit; //muzika menu
	public static final int R_VOLUME=100; //hlasitost
	public static final int L_VOLUME=100; //hlasitost
	public static final boolean LOOP_BACKGROUND_MUSIC=true; //loop ano ne
	
	public static Context context; //aktuani kontext vlakna vnemz je prehravana hudba
	public static Thread musicThread;

	/*Equaled po ukonceni hry*/
	public boolean onExit(View v){
		try{
			/*vypneme muziku onExit*/
			Intent bgmusic = new Intent(context, SFMusic.class);
			context.stopService(bgmusic);
			Thread.sleep(1000);
			musicThread.interrupt();

			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
