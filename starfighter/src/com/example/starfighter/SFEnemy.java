package com.example.starfighter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;

public class SFEnemy {

	public float posX = 0f; // x pozice nepritele
	public float posY = 0f; // y pozice nepritele
	public float posT = 0f; // t pro beziera

	public float incrementXToTarget = 0f; // x prirustek pro dosazeni cile
	public float incrementYToTarget = 0f; // y prirustek pro dosazeni cile

	public int attackDirection = 0; // smer utoku lode
	public boolean isDestroyed = false; // lod znicena?
	public int enemyType = 0; // ktery typ lode

	public boolean isLockedOn = false; // zameril se nepritel na cil?
	public float lockOnPosX = 0f; // x pozice cile
	public float lockOnPosY = 0f; // y pozice cile

	private Random randomPos = new Random();
	/* OPENG GL */
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;

	private float vertices[] = {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f, };

	private float texture[] = { 
			0.0f, 0.0f,
			0.25f, 0.0f,
			0.25f, 0.25f,
			0.0f, 0.25f,

	};
	// rohy spritu textury

	private byte indices[] = { 
			0, 1, 2,
			0, 2, 3,
	};

	/**
	 * Konstruktor
	 * 
	 * @param typ
	 *            lodi, smer od kud prileti
	 * */
	public SFEnemy(int type, int direction) {
		enemyType = type;
		attackDirection = direction;
		// startovni y pozice
		posY = (randomPos.nextFloat() * 4) + 4; // pohyb na ose od 0 po 4 a
												// pricteme 4 aby byl ven z obr.

		// startovni x pozice
		switch (attackDirection) {
		case SFEngine.ATTACK_LEFT:
			posX = 0;
			break;

		case SFEngine.ATTACK_RANDOM:
			posX = randomPos.nextFloat() * 3;
			break;

		case SFEngine.ATTACK_RIGHT:
			posX = 3;
			break;
		}

		posT = SFEngine.SCOUT_SPEED;

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);// nasobime 4 protoze folat = byte*4
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(texture.length * 4); // nasobime 4
																	// protoze
																	// folat =
																	// byte*4
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);

		indexBuffer = ByteBuffer.allocateDirect(indices.length); // nenasobime
																	// je to
																	// byte -
																	// pouze
																	// pocet
																	// indexu
		indexBuffer.put(indices); // vlozime indexy
		indexBuffer.position(0); // nastavime pozici na 0
	}

	public float getNextScoutX() {
		if (attackDirection == SFEngine.ATTACK_LEFT) {
			return (float)((SFEngine.BEZIER_X_4*(posT*posT*posT)) + 
	                (SFEngine.BEZIER_X_3 * 3 * (posT * posT) * (1-posT)) +     
	                (SFEngine.BEZIER_X_2 * 3 * posT * ((1-posT) * (1-posT))) +     
	                (SFEngine.BEZIER_X_1 * ((1-posT) * (1-posT) * (1-posT))));
		} else {

			return (float)((SFEngine.BEZIER_X_1*(posT*posT*posT)) + 
	                (SFEngine.BEZIER_X_2 * 3 * (posT * posT) * (1-posT)) +     
	                (SFEngine.BEZIER_X_3 * 3 * posT * ((1-posT) * (1-posT))) + 
	                (SFEngine.BEZIER_X_4 * ((1-posT) * (1-posT) * (1-posT)))); 
		}
	}

	public float getNextScoutY() {
		return (float)((SFEngine.BEZIER_Y_1*(posT*posT*posT)) + 
	            (SFEngine.BEZIER_Y_2 * 3 * (posT * posT) * (1-posT)) +     
	            (SFEngine.BEZIER_Y_3 * 3 * posT * ((1-posT) * (1-posT))) + 
	            (SFEngine.BEZIER_Y_4 * ((1-posT) * (1-posT) * (1-posT)))); 

	}

	public void draw(GL10 gl, int[] spriteSheet) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[0]); // svaze texturu s
																// cilem-nahraje
																// a je
																// pripravena

		gl.glFrontFace(GL10.GL_CCW); // front facing proti hodinovym rucickam
		gl.glEnable(GL10.GL_CULL_FACE); // zapnuti cullingu - myslim ze by to
										// melo byt az za cullingem
		gl.glCullFace(GL10.GL_BACK); // odstraneni zadni steni - neni videt

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // aktivace vrcholu
														// souradnic
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); // aktivace textur
																// souradnic

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); // nahrani
																// mezipameti do
																// opengl
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer); // nahrani
																	// mezipameti
																	// do opengl

		/* textura se nakresli na vrcholy */
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_BYTE, indexBuffer);

		/* aktivovane stavy se deaktivuji */
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

}
