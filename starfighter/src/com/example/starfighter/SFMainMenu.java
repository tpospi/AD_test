package com.example.starfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;

public class SFMainMenu extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starfighter);

		/** spust vlakno pro hudbu */
		SFEngine.musicThread = new Thread() {
			public void run() {
				Intent bgmusic = new Intent(getApplicationContext(),
						SFMusic.class);
				startService(bgmusic);
				SFEngine.context = getApplicationContext();
			}
		};
		SFEngine.musicThread.start();

		final SFEngine engine = new SFEngine();

		/* nastav volby po tlacitka nabidky pruhkednost hapticka odezva */
		ImageButton start = (ImageButton) findViewById(R.id.btnStart);
		ImageButton exit = (ImageButton) findViewById(R.id.btnExit);
		start.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA); // nebude
																	// mit
																	// pozadi
		start.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK);
		exit.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA); // nebude mit
																	// pozadi
		exit.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK);

		/* stisknuti tlac start */
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/** spust hru */
				Intent game = new Intent(getApplicationContext(), SFGame.class);
				SFMainMenu.this.startActivity(game);
			}
		});

		/* stisknuti tlac exit */
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean clean = false;
				clean = engine.onExit(v);
				if (clean) {
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
			}
		});
	}
}
