package com.example.starfighter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SFGameRender implements Renderer {
	private SFBackground background = new SFBackground();
	private float bgScroll1; //uchovava pozici pozadi

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
	
	/*vykreslovaci jednotka kresli snimek*/
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
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
		
	}

}
