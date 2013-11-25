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
	
	
	//obnova hry , napr z tel. hovoru
	@Override
	protected void onResume(){
		super.onResume();
		gameView.onResume();
	}
	
	//pauza
	@Override
	protected void onPause(){
		super.onPause();
		gameView.onPause();
	}

	
}
