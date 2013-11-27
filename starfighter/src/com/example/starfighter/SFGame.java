package com.example.starfighter;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;


public class SFGame extends Activity {
	private SFGameView gameView;


	/**
	 * Oprava pro starsi zarizeni. getSize/getWidth a getHeight
	 * Vypocitana velikost dotykove plochy
	 * */
	@SuppressWarnings("deprecation")
	public void setDisplaySizes(){
		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 13 ) {



			Display disp = getWindowManager().getDefaultDisplay(); 
			SFEngine.width = disp.getWidth();
			SFEngine.height = disp.getHeight();



		} else {
			//zjistime velikost obrazovky
			Display disp = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			disp.getSize(size);
			SFEngine.width = size.x;
			SFEngine.height = size.y;
		}
		SFEngine.playableArea = (SFEngine.width/4) -SFEngine.height;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){ //standardni naslouchani
		float x = event.getX(); //poloha dotyku na displaji
		float y = event.getY(); //poloha dotyku na displaji

		if (y > SFEngine.playableArea){ //pokud jsem v hratelne zone
			switch (event.getAction()){
			case MotionEvent.ACTION_DOWN: //je treba porovnat na ktere strane obr. se hrac dotknul
				if (x < SFEngine.width/2){
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
		setDisplaySizes();
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
