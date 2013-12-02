package com.example.starfighter;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;

public class SFEngine {
	/* konstanty */
	public static final int GAME_THREAD_DELAY = 1000; // spozdeni uvodni
														// obrazovky
	public static final int MENU_BUTTON_ALPHA = 0; // pruhlednost tlacitka
	public static final boolean HAPTIC_BUTTON_FEEDBACK = true; // hapticka
																// odezva
																// tlacitek
	public static final int SPLASH_SCREEN_MUSIC = R.raw.warfieldedit; // muzika
																		// menu
	public static final int R_VOLUME = 0; // hlasitost
	public static final int L_VOLUME = 0; // hlasitost
	public static final boolean LOOP_BACKGROUND_MUSIC = true; // loop ano ne
	public static final int BACKGROUND_LAYER_ONE = R.drawable.backgroundstars; // textura
	public static final float SCROLL_BACKGROUND_1 = .002f;
	public static final int BACKGROUND_LAYER_TWO = R.drawable.debris;
	public static final float SCROLL_BACKGROUND_2 = .007f;
	public static final int GAME_THREAD_FPS_SLEEP = (1000 / 60); // 60 snimku za 1 sec
	// public static final int PLAYER_SHIP = R.drawable.good_sprite;//textura
	// lodi
	public static final int PLAYER_BANK_LEFT_1 = 1; // akce kterou player
													// provadi
	public static final int PLAYER_RELEASE = 3; // akce kterou player provadi
	public static final int PLAYER_BANK_RIGHT_1 = 4; // akce kterou player
														// provadi
	public static final int PLAYER_FRAMES_BETWEEN_ANI = 9; // 9 iteraci herniho
															// cyklu se nakresli
															// jeden snimek
	public static final float PLAYER_BANK_SPEED = 0.1f; // rychlost zleva
														// doprava

	public static final int TYPE_INTERCEPTOR = 1;
	public static final int TYPE_SCOUT = 2;
	public static final int TYPE_WARSHIP = 3;
	public static final int ATTACK_RANDOM = 0;
	public static final int ATTACK_RIGHT = 1;
	public static final int ATTACK_LEFT = 2;
	public static final float BEZIER_X_1 = 0f;
	public static final float BEZIER_X_2 = 1f;
	public static final float BEZIER_X_3 = 2.5f;
	public static final float BEZIER_X_4 = 3f;
	public static final float BEZIER_Y_1 = 0f;
	public static final float BEZIER_Y_2 = 2.4f;
	public static final float BEZIER_Y_3 = 1.5f;
	public static final float BEZIER_Y_4 = 2.6f;
	public static final int WEAPON_SHEET = R.drawable.destruction;
	public static final int PLAYER_SHIELD = 5; //pocet zasahu nez dojde ke zniceni
	public static final int SCOUT_SHIELDS = 3;
	public static final int INTERCEPTOR_SHIELDS = 1;
	public static final int WARSHIP_SHIELDS = 5;
	public static final float PLAYER_BULLET_SPEED = 0.125f; //rychlost strely
	
	
	

	public static int CHARACTER_SHEET = R.drawable.character_sprite;
	public static int TOTAL_INTERCEPTORS = 10;
	public static int TOTAL_SCOUTS = 15;
	public static int TOTAL_WARSHIPS = 5;
	public static float INTERCEPTOR_SPEED = SCROLL_BACKGROUND_1 * 4f;
	public static float SCOUT_SPEED = SCROLL_BACKGROUND_1 * 6f;
	public static float WARSHIP_SPEED = SCROLL_BACKGROUND_2 * 4f;

	public static Context context; // aktuani kontext vlakna vnemz je prehravana
									// hudba
	public static Thread musicThread; // vlakno pro muziku
	public static Display display;
	public static int playerFlightAction = 0;
	public static float playerBankPosX = 1.75f; // aktualni pozice lode
	public static int width = 0;
	public static int height = 0;
	public static int playableArea = 0;

	/* uklid po ukonceni hry */
	public boolean onExit(View v) {
		try {
			/* vypneme muziku onExit */
			Intent bgmusic = new Intent(context, SFMusic.class);
			context.stopService(bgmusic);
			Thread.sleep(1000);
			musicThread.interrupt();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
