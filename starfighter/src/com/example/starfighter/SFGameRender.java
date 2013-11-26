package com.example.starfighter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SFGameRender implements Renderer {
	private SFBackground background = new SFBackground();
	private float bgScroll1; //uchovava pozici pozadi
	private SFBackground background2 = new SFBackground();
	private float bgScroll2; //uchovava pozici pozadi
	private SFGoodGuy player1 = new SFGoodGuy(); //vytvoreni nove instance postavy
	private int goodGuyBankFrames = 0; //promena na pocitani iteraci herniho cyklu od posledni zmeny pozice ve spritu postavy

	/**
	 * Ustredni metoda pro pohyb hrace
	 * */
	private void movePlayer1(GL10 gl){
		switch (SFEngine.playerFlightAction){
		
		case SFEngine.PLAYER_BANK_LEFT_1:
			//rezim pohybu po ose x - modelova matice
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.25f, 0.25f, 1f);
			//pohyb
			if (SFEngine.playerBankPosX>0 && goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { //testovani kde je hrac
			SFEngine.playerBankPosX-=SFEngine.PLAYER_BANK_SPEED;
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
			
			//rezim posunuti textury na spravny sprite
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0.75f, 0.0f, 0.0f); //sprite poloha na texture
			goodGuyBankFrames+=1; //inkrementace pro zjisteni na to kdy prejit na dalsi sprit
			} else if (SFEngine.playerBankPosX > 0 && goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI){ //pokud neni na kraji ale uplynulo 9 snimku - naklon cislo 2
				SFEngine.playerBankPosX -=SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				//rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.25f, 0.0f); //sprite poloha left 2 na texture
				
			}
			else { //hrac je na levem kraji obrazovky
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				//rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 0.0f); //sprite poloha na texture
			}
			
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			
			break;
			
		case SFEngine.PLAYER_BANK_RIGHT_1:
			//rezim pohybu po ose x - modelova matice
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.25f, 0.25f, 1f);
			//pohyb
			if (SFEngine.playerBankPosX<3 && goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { //testovani kde je hrac
			SFEngine.playerBankPosX+=SFEngine.PLAYER_BANK_SPEED;
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
			
			//rezim posunuti textury na spravny sprite
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0.25f, 0.0f, 0.0f); //sprite poloha na texture
			goodGuyBankFrames+=1; //inkrementace pro zjisteni na to kdy prejit na dalsi sprit
			} else if (SFEngine.playerBankPosX > 0 && goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI){ //pokud neni na kraji ale uplynulo 9 snimku - naklon cislo 2
				SFEngine.playerBankPosX +=SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				//rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.50f, 0.0f, 0.0f); //sprite poloha left 2 na texture
				
			}
			else { //hrac je na levem kraji obrazovky
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				//rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 0.0f); //sprite poloha na texture
			}
			
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			break;
			
		case SFEngine.PLAYER_RELEASE: //zatim se deje to same jako by hrac nic nedelal. proste po uvolneni tlacitka se zobrazi prvni sprit
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.25f, 0.25f, 1f); //scale hrace na 25procent
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); //pozice aktualni. nedochazi k pohybu
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0f, 0f);
			
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			goodGuyBankFrames+=1; //pocitame snimky od uvolneni
			break;
			
		default:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.25f, 0.25f, 1f); //scale hrace na 25procent
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); //pozice aktualni. nedochazi k pohybu
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0f, 0f);
			
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();
		break;
		}
	}
	
	private void scrollBackground1(GL10 gl){
		if (bgScroll1 == Float.MAX_VALUE) //nesmi presahnout float max hodnutu
			bgScroll1 =0f;
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); //nastaveni model matice
		gl.glLoadIdentity(); //resetovani model matice do defaultu
		gl.glPushMatrix(); //ulozeni matice
		gl.glScalef(1f, 1f, 1f); //zvetseni na 1
		gl.glTranslatef(0f, 0f, 0f); //posunuti o 0
		
		gl.glMatrixMode(GL10.GL_TEXTURE); //nastaveni matice na texturovaci
		gl.glLoadIdentity(); //resetovani model matice do defaultu
		gl.glTranslatef(0.0f, bgScroll1, 0.0f); //posun pozadi o konstantu ve smetu y
		
		background.draw(gl);
		gl.glPopMatrix(); //vyzvedneme si uloyenou matici
		bgScroll1 += SFEngine.SCROLL_BACKGROUND_1; //incrementace
		gl.glLoadIdentity();
		
		
		
		
	}
	
	private void scrollBackground2(GL10 gl){
		if (bgScroll2 == Float.MAX_VALUE) //nesmi presahnout float max hodnutu
			bgScroll2 =0f;
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f, 1f, 1f);
		gl.glTranslatef(0f, 0f, 0f);
		
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, bgScroll2, 0.0f);
		background2.draw(gl);
		gl.glPopMatrix();
		bgScroll2+=SFEngine.SCROLL_BACKGROUND_2;
		gl.glLoadIdentity();
	}
	
	/*vykreslovaci jednotka kresli snimek*/
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP); //vse se bude vykonavat max 60x za sec
		}
		catch(InterruptedException e){
			e.printStackTrace();
			
		}
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); //vycistime mezipameti
		scrollBackground1(gl);
		scrollBackground2(gl);
		
		//zde se bude volat veskere dalsi vykreslovani
		
		gl.glEnable(GL10.GL_BLEND);//zobrazeni prhlednosti
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE); //zobrazeni prhlednosti
	}

	/*zmena velikosti okna, priprava obrazku pri zmene orientace nebo rozmeru okna*/
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height); //velikost cele obrazovky
		gl.glMatrixMode(GL10.GL_PROJECTION); //projekcni matice
		gl.glLoadIdentity(); //nastaveni do defaultu
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f); //dvourozmerne vykresleni sceny - parametry orezove roviny
		
	}

	/*val se pri vytvoreni zobrazeni GLSurfaceView*/
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_TEXTURE_2D); //aktivace openGL pro mapovani 2d textur
		gl.glClearDepthf(1.0f); //poradi predmetu
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glEnable(GL10.GL_BLEND);//mixovani pruhlednost
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);//mixovani pruhlednost
		/*zacneme pridavat textury*/
		
		background.loadTexture(gl, SFEngine.BACKGROUND_LAYER_ONE, SFEngine.context);/*nahreajeme texturu pozadi*/
		background2.loadTexture(gl, SFEngine.BACKGROUND_LAYER_TWO, SFEngine.context);/*nahreajeme texturu pozadi*/
		player1.loadTexture(gl, SFEngine.PLAYER_SHIP, SFEngine.context);/*nahreajeme texturu pozadi*/
		
	}

}
