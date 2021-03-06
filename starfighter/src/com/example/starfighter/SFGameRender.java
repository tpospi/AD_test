package com.example.starfighter;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SFGameRender implements Renderer {
	private SFBackground background = new SFBackground();
	private SFBackground background2 = new SFBackground();
	private SFGoodGuy player1 = new SFGoodGuy(); // vytvoreni nove instance postavy


	private SFEnemy[] enemies =
			new SFEnemy[SFEngine.TOTAL_INTERCEPTORS+SFEngine.TOTAL_SCOUTS+SFEngine.TOTAL_WARSHIPS];
	private SFTextures textureLoader;
	private int[] spriteSheets = new int [2];
	private SFWeapon[] playerFire = new SFWeapon[4];

	private float bgScroll1; // uchovava pozici pozadi
	private float bgScroll2; // uchovava pozici pozadi
	private int goodGuyBankFrames = 0; // promena na pocitani iteraci herniho cyklu od posledni zmeny pozice ve spritu postavy

	// nastaveni vypnuti prodlevy na polem zarizeni
	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0; // zjistime delku jednoho cyklu

	/**
	 * metoda pro interceptory
	 * */
	private void initializeInterceptors(){
		for(int x = 0; x<SFEngine.TOTAL_INTERCEPTORS; x++){
			SFEnemy interceptor = new SFEnemy(SFEngine.TYPE_INTERCEPTOR, SFEngine.ATTACK_RANDOM);
			enemies[x]=interceptor;
		}
	}

	/** --rozdil
	 * metoda pro Scouty
	 * */
	private void initializeScouts(){
		for(int x = 0; x<SFEngine.TOTAL_SCOUTS; x++){
			SFEnemy scout;
			if(x>=SFEngine.TOTAL_SCOUTS/2){
				scout = new SFEnemy(SFEngine.TYPE_SCOUT, SFEngine.ATTACK_LEFT);

			}
			else{
				scout = new SFEnemy(SFEngine.TYPE_SCOUT, SFEngine.ATTACK_RANDOM);	
			}

			enemies[x+SFEngine.TOTAL_INTERCEPTORS]=scout;
		}
	}

	/**
	 * metoda pro Warships
	 * */
	private void initializeWarships(){
		for(int x = 0; x<SFEngine.TOTAL_WARSHIPS; x++){
			SFEnemy warship = new SFEnemy(SFEngine.TYPE_WARSHIP, SFEngine.ATTACK_RIGHT);
			enemies[x+SFEngine.TOTAL_INTERCEPTORS+SFEngine.TOTAL_SCOUTS]=warship;
		}
	}

	/**
	 * metoda pro Wzbrane
	 * */
	private void initializePlayerWeapons(){
		for(int x = 0; x < 4; x++){
			SFWeapon weapon = new SFWeapon();
			playerFire[x] = weapon;
		}
		//prvni strela
		playerFire[0].shotFired = true;
		playerFire[0].posX = SFEngine.playerBankPosX;
		playerFire[0].posY = 1.25f;
	}
	
	private void firePlayerWeapon(GL10 gl){
		for (int x = 0; x < 4; x++){
			if (playerFire[x].shotFired){
				int nextShot=0; //uchovava strelu ready pro vystreleni
				if (playerFire[x].posY>4.75){
					playerFire[x].shotFired = false;
					
				}
				else
				{
					//je treba vystrelit dalsi strelu?
					if (playerFire[x].posY>2){
						if (x==3){
							nextShot = 0;
						}
						else{
							nextShot = x+1;
						}
					}
					
					if (playerFire[nextShot].shotFired ==false){
						playerFire[nextShot].shotFired = true;
						playerFire[nextShot].posX = SFEngine.playerBankPosX;
						playerFire[nextShot].posY = 1.25f;
					}
				//kreslime strelu
					playerFire[x].posY += SFEngine.PLAYER_BULLET_SPEED; //inkrementace strel
					
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(0.1f, 0.1f, 0.1f);
					gl.glTranslatef(playerFire[x].posX, playerFire[x].posY, 0f);
					
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0f, 0f, 0f); //prvni sprit
					playerFire[x].draw(gl, spriteSheets);
					
					gl.glPopMatrix();
					gl.glLoadIdentity();
					
					
					
				}
			}
		}
	}

	
	private void moveEnemy(GL10 gl){
		for (int x = 0; x < SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS; x++){
			if(!enemies[x].isDestroyed){ //jestlize neni jeste znicen tak...
				Random randomPos = new Random();

				switch (enemies[x].enemyType){
				case SFEngine.TYPE_INTERCEPTOR:
					if (enemies[x].posY <=-1){
						enemies[x].posY = randomPos.nextFloat() * 4 + 4;
						enemies[x].posX = randomPos.nextFloat() * 3;
						enemies[x].isLockedOn = false;
						enemies[x].lockOnPosX = 0;
					}
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(0.25f, 0.25f, 1);
					//nez se nepritel zameri na hrace
					if (enemies[x].posY >= 3){
						enemies[x].posY -= SFEngine.INTERCEPTOR_SPEED; //jede primo domu konstantni rychlosti
					}
					else{
						if(!enemies[x].isLockedOn){
							enemies[x].lockOnPosX = SFEngine.playerBankPosX; //Nepritel se zameri na hrace a zalokuje si pozici
							enemies[x].isLockedOn = true;
							enemies[x].incrementXToTarget = 
									(float)((enemies[x].lockOnPosX - enemies[x].posX)/(enemies[x].posY / (SFEngine.INTERCEPTOR_SPEED *4))); //vzorec pro zjisteni posunu k hraci
						}
						//posun
						enemies[x].posY -=(SFEngine.INTERCEPTOR_SPEED *4);
						enemies[x].posX +=enemies[x].incrementXToTarget;
						

						//aktualizace openGL
						gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
						gl.glMatrixMode(GL10.GL_TEXTURE);
						gl.glLoadIdentity();
						gl.glTranslatef(0.25f, 0.25f, 0.0f);

					}

					enemies[x].draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();

					break;

				case SFEngine.TYPE_SCOUT:
					if (enemies[x].posY <= -1){
						enemies[x].posY = randomPos.nextFloat() * 4 + 4;
						//enemies[x].posX = randomPos.nextFloat() * 3;
						enemies[x].isLockedOn = false;
						enemies[x].posT = SFEngine.SCOUT_SPEED;
						enemies[x].lockOnPosX = enemies[x].getNextScoutX();
						enemies[x].lockOnPosX = enemies[x].getNextScoutY();

						if (enemies[x].attackDirection==SFEngine.ATTACK_LEFT){
							enemies[x].posX=0;
						}
						else{
							enemies[x].posX=3;
						}
					}
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(0.25f, 0.25f, 1);

					//nahodny pohyb pruzkumnika
					if(enemies[x].posY>=2.75f){
						enemies[x].posY -=SFEngine.SCOUT_SPEED;

					}
					else{
						enemies[x].posX = enemies[x].getNextScoutX();
						enemies[x].posY = enemies[x].getNextScoutY();
						enemies[x].posT +=SFEngine.SCOUT_SPEED;


					}

					//aktualizace openGL
					gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.75f, 0.25f, 0.0f);

					enemies[x].draw(gl, spriteSheets);

					gl.glPopMatrix();
					gl.glLoadIdentity();

					break;

				case SFEngine.TYPE_WARSHIP:
					if (enemies[x].posY < -1){
						enemies[x].posY = randomPos.nextFloat() * 4 + 4;
						enemies[x].posX = randomPos.nextFloat() * 3;
						enemies[x].isLockedOn = false;
						enemies[x].lockOnPosX = 0;
					}
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(0.25f, 0.25f, 1);
					//nez se nepritel zameri na hrace
					if (enemies[x].posY>=3){
						enemies[x].posY -= SFEngine.WARSHIP_SPEED; //jede primo domu konstantni rychlosti
					}
					else{
						if(!enemies[x].isLockedOn){
							enemies[x].lockOnPosX = randomPos.nextFloat()*3; //Nepritel se zameri na nahodnou poz
							enemies[x].isLockedOn = true;
							enemies[x].incrementXToTarget = 
									(float)((enemies[x].lockOnPosX - enemies[x].posX)/(enemies[x].posY / (SFEngine.WARSHIP_SPEED *4))); //vzorec pro zjisteni posunu k hraci
						}
						//posun
						enemies[x].posX +=enemies[x].incrementXToTarget;
						enemies[x].posY -=(SFEngine.WARSHIP_SPEED *2);
					}
					//aktualizace openGL
					gl.glTranslatef(enemies[x].posX, enemies[x].posY, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.50f, 0.25f, 0.0f);



					enemies[x].draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();
					break;

				}
			}

		}
	}



	/**
	 * Ustredni metoda pro pohyb hrace
	 * */
	private void movePlayer1(GL10 gl) {
		
		if(!player1.isDestroyed){
		switch (SFEngine.playerFlightAction) {

		case SFEngine.PLAYER_BANK_LEFT_1:
			// rezim pohybu po ose x - modelova matice
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.1f, 0.1f, 1f);
			// pohyb
			if (SFEngine.playerBankPosX > 0
					&& goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { // testovani kde je hrac
				SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);

				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.25f, 0.0f, 0.0f); // sprite poloha na texture
				goodGuyBankFrames += 1; // inkrementace pro zjisteni na to kdy
				// prejit na dalsi sprit
			} else if (SFEngine.playerBankPosX > 0
					&& goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { // pokud neni na kraji ale uplynulo 9 snimku - naklon cislo 2
				SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.50f, 0.0f, 0.0f); // sprite poloha left 2 na
				// texture

			} else { // hrac je na levem kraji obrazovky
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 0.0f); // sprite poloha na texture

			}

			player1.draw(gl, spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();

			break;

		case SFEngine.PLAYER_BANK_RIGHT_1:
			// rezim pohybu po ose x - modelova matice
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.1f, 0.1f, 1f);
			// pohyb
			if (SFEngine.playerBankPosX < 9
					&& goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { // testovani
				// kde
				// je
				// hrac
				SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);

				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.75f, 0.0f, 0.0f); // sprite poloha na texture

				goodGuyBankFrames += 1; // inkrementace pro zjisteni na to kdy
				// prejit na dalsi sprit
			} else if (SFEngine.playerBankPosX < 9
					&& goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI) { // pokud neni na kraji ale uplynulo 9 snimku - naklon cislo 2
				SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.25f, 0.0f); // sprite poloha left 2 na
				// texture

			} else { // hrac je na pravem kraji obrazovky
				gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
				// rezim posunuti textury na spravny sprite
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 0.0f); // sprite poloha na texture
			}

			player1.draw(gl, spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			break;

		case SFEngine.PLAYER_RELEASE: // zatim se deje to same jako by hrac nic
			// nedelal. proste po uvolneni tlacitka
			// se zobrazi prvni sprit
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.1f, 0.1f, 1f); // scale hrace na 25procent
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); // pozice
			// aktualni.
			// nedochazi k
			// pohybu
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0f, 0f);

			player1.draw(gl, spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			goodGuyBankFrames += 1; // pocitame snimky od uvolneni
			break;

		default:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(0.1f, 0.1f, 1f); // scale hrace na 25procent
			gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); // pozice
			// aktualni.
			// nedochazi k
			// pohybu
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0f, 0f);

			player1.draw(gl,spriteSheets);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			break;
		}
		firePlayerWeapon(gl); //dame to sem protoze hrac muze byt treba mrtev
		}
		
		
	}

	private void scrollBackground1(GL10 gl) {
		if (bgScroll1 == Float.MAX_VALUE) // nesmi presahnout float max hodnutu
			bgScroll1 = 0f;

		gl.glMatrixMode(GL10.GL_MODELVIEW); // nastaveni model matice
		gl.glLoadIdentity(); // resetovani model matice do defaultu
		gl.glPushMatrix(); // ulozeni matice
		gl.glScalef(1f, 1f, 1f); // zvetseni na 1
		gl.glTranslatef(0f, 0f, 0f); // posunuti o 0

		gl.glMatrixMode(GL10.GL_TEXTURE); // nastaveni matice na texturovaci
		gl.glLoadIdentity(); // resetovani model matice do defaultu
		gl.glTranslatef(0.0f, bgScroll1, 0.0f); // posun pozadi o konstantu ve
		// smetu y

		background.draw(gl);
		gl.glPopMatrix(); // vyzvedneme si uloyenou matici
		bgScroll1 += SFEngine.SCROLL_BACKGROUND_1; // incrementace
		gl.glLoadIdentity();

	}

	private void scrollBackground2(GL10 gl) {
		if (bgScroll2 == Float.MAX_VALUE) // nesmi presahnout float max hodnutu
			bgScroll2 = 0f;

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
		bgScroll2 += SFEngine.SCROLL_BACKGROUND_2;
		gl.glLoadIdentity();
	}

	/* vykreslovaci jednotka kresli snimek */
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		loopStart = System.currentTimeMillis(); // zacatek cas

		try {
			if (loopRunTime < SFEngine.GAME_THREAD_FPS_SLEEP) { // pokud je
				// delka cyklu
				// kratsi nez
				// konstanta pak
				// sleep
				Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP - loopRunTime); // vse se bude
				// vykonavat max
				// 60x za sec
			}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

		// zde se bude volat veskere dalsi vykreslovani
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // vycistime
		// mezipameti
		scrollBackground1(gl); // pohyb pozadi
		scrollBackground2(gl);// pohyb druheho pozadi
		movePlayer1(gl); // pohyb postavy
		moveEnemy(gl);
		
		detectCollision();

		gl.glEnable(GL10.GL_BLEND);// zobrazeni prhlednosti
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE); // zobrazeni prhlednosti

		loopEnd = System.currentTimeMillis(); // konec cas
		loopRunTime = (loopEnd - loopStart); // rozdil casu
	}

	/*
	 * zmena velikosti okna, priprava obrazku pri zmene orientace nebo rozmeru
	 * okna
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height); // velikost cele obrazovky
		gl.glMatrixMode(GL10.GL_PROJECTION); // projekcni matice
		gl.glLoadIdentity(); // nastaveni do defaultu
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f); // dvourozmerne vykresleni sceny -
		// parametry orezove roviny

	}

	/* val se pri vytvoreni zobrazeni GLSurfaceView */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initializeInterceptors();
		initializeScouts();
		initializeWarships();
		initializePlayerWeapons();

		//textury - arch spritu
		textureLoader = new SFTextures(gl);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.CHARACTER_SHEET, SFEngine.context,1); //hrac a potvory
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.WEAPON_SHEET, SFEngine.context,2); //zbrane



		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_TEXTURE_2D); // aktivace openGL pro mapovani 2d
		// textur
		gl.glClearDepthf(1.0f); // poradi predmetu
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		//gl.glEnable(GL10.GL_BLEND);// mixovani pruhlednost
		//gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);// mixovani pruhlednost
		/* zacneme pridavat textury */

		background.loadTexture(gl, SFEngine.BACKGROUND_LAYER_ONE,
				SFEngine.context);/* nahreajeme texturu pozadi */
		background2.loadTexture(gl, SFEngine.BACKGROUND_LAYER_TWO,
				SFEngine.context);/* nahreajeme texturu pozadi */
		//player1.loadTexture(gl, SFEngine.PLAYER_SHIP, SFEngine.context);/** nahreajeme texturu pozadi*/

	}
	
	private void detectCollision(){
		for (int y = 0; y < 3; y++){ //v�echny st�ely
			if (playerFire[y].shotFired){
				for (int x=0; x< SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS; x++){
					if(!enemies[x].isDestroyed && enemies[x].posY<4.25){ //potvora neni jeste znicena a je na obrazovce
						//test kolize
						if ((playerFire[y].posY >= enemies[x].posY -1 &&
							playerFire[y].posY <= enemies[x].posY	)&&
							(playerFire[y].posX <= enemies[x].posX +1 &&
							playerFire[y].posX >= enemies[x].posX - 1)){
							
							int nextShot = 0;
							
							enemies[x].applyDamage();
							playerFire[y].shotFired = false;
							if (y==3){
								nextShot = 0;
								
							}
							else {
								nextShot = y+1;
							}
							
							if (playerFire[nextShot].shotFired == false){
								playerFire[nextShot].shotFired = true;
								playerFire[nextShot].posX = SFEngine.playerBankPosX;
								playerFire[nextShot].posY = 1.25f;
							}
						}
					}
				}
			}
		}
	}

}
