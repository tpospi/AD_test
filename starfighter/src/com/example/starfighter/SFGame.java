package com.example.starfighter;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;


public class SFGame extends Activity {
	private SFGameView gameView;
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){ //standardni naslouchani
		float x = event.getX(); //poloha dotyku na displaji
		float y = event.getY(); //poloha dotyku na displaji
		
		//zjistime velikost obrazovky
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;

		//nastaveni polohy pro dotyk
		int height = size.y / 4;
		int playableArea = size.y - height;
		
		if (y > playableArea){ //pokud jsem v hratelne zone
			switch (event.getAction()){
			case MotionEvent.ACTION_DOWN: //je treba porovnat na ktere strane obr. se hrac dotknul
				if (x < width/2){
					SFEngine.playerFlightAction = SFEngine.PLAYER_BANK_LEFT_1;
				}else{
					SFEngine.playerFlightAction = SFEngine.PLAYER_BANK_RIGHT_1;
				}
				
				
			break;
			
			case MotionEvent.ACTION_UP:
				SFEngine.playerFlightAction = SFEngine.PLAYER_RELEASE;
			break;
			
			}
		}
		
		return false;
	}
	
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
