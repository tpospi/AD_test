package com.example.starfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StarfighterActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*zobraz uvodni obrazovku*/
		setContentView(R.layout.splashscreen);
		
		/*nove HERNI vlakno prez handler s prodlevou vytvoreni uvodniho menu*/
		new Handler().postDelayed(new Thread(){
			@Override
			public void run(){
				/*intent - operace kterou ma android udelat*/
				Intent mainMenu =
						new Intent(StarfighterActivity.this, SFMainMenu.class);
				StarfighterActivity.this.startActivity(mainMenu);
				StarfighterActivity.this.finish();
				/*prolnuti uvodni obr a menu*/
				overridePendingTransition(R.layout.fadein,R.layout.fadeout);
			}
		}, SFEngine.GAME_THREAD_DELAY);
}
}
