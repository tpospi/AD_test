package com.example.starfighter;
import android.app.Activity;
import android.os.Bundle;


public class SFGame extends Activity {
	private SFGameView gameView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		gameView = new SFGameView(this);
		setContentView(gameView);
	}

	
}
