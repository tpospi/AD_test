package com.example.starfighter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SFGameRender implements Renderer {

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
		
	}

}
