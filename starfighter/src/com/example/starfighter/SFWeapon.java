package com.example.starfighter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class SFWeapon {
public float posY = 0f;
public float posX = 0f;
public boolean shotFired = false;

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
		0, 2, 3, };

public SFWeapon(){
	
ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);// nasobime 4 protoze folat = byte*4
byteBuf.order(ByteOrder.nativeOrder());
vertexBuffer = byteBuf.asFloatBuffer();
vertexBuffer.put(vertices);
vertexBuffer.position(0);

byteBuf = ByteBuffer.allocateDirect(texture.length * 4); // nasobime 4 protoze folat = byte*4
byteBuf.order(ByteOrder.nativeOrder());
textureBuffer = byteBuf.asFloatBuffer();
textureBuffer.put(texture);
textureBuffer.position(0);

indexBuffer = ByteBuffer.allocateDirect(indices.length); // nenasobime je to byte - pouze pocet indexu
indexBuffer.put(indices); // vlozime indexy
indexBuffer.position(0); // nastavime pozici na 0
}

public void draw(GL10 gl, int[] spriteSheet) {
	gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[1]); // svaze texturu s
														// cilem-nahraje a
														// je pripravena

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
