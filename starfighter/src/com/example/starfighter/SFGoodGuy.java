package com.example.starfighter;
//import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.opengl.GLUtils;

/**
 * Trida pilota
 * */
public class SFGoodGuy {
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
	private int[] textures = new int[1];

	private float vertices[] = {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
	};
	
	
	private float texture[]={
			0.0f, 0.0f,
			0.25f, 0.0f,
			0.25f, 0.25f,
			0.0f, 0.25f,
			
	};
	//rohy spritu textury
	
	private byte indices[] = {
			0, 1, 2,
			0, 2, 3,
	};
	
	
	public SFGoodGuy(){
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length*4);//nasobime 4 protoze folat = byte*4
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer(); 
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(texture.length*4); //nasobime 4 protoze folat = byte*4
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		indexBuffer = ByteBuffer.allocateDirect(indices.length); //nenasobime je to byte - pouze pocet indexu
		indexBuffer.put(indices); //vlozime indexy
		indexBuffer.position(0); //nastavime pozici na 0 
	}
	
	public void draw(GL10 gl){
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]); //svaze texturu s cilem-nahraje a je pripravena
		
		gl.glFrontFace(GL10.GL_CCW); //front facing proti hodinovym rucickam 
		gl.glEnable(GL10.GL_CULL_FACE); //zapnuti cullingu - myslim ze by to melo byt az za cullingem
		gl.glCullFace(GL10.GL_BACK); //odstraneni zadni steni - neni videt
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); //aktivace vrcholu souradnic
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); //aktivace textur souradnic		
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); //nahrani mezipameti do opengl
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer); //nahrani mezipameti do opengl
		
		/*textura se nakresli na vrcholy*/
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
		
		/*aktivovane stavy se deaktivuji*/
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
//	
//	public void loadTexture(GL10 gl, int texture, Context context){
//		InputStream imagestream = context.getResources().openRawResource(texture); //bereme ukazatel na texturu
//		Bitmap bitmap = null;
//		
//		try{
//			bitmap = BitmapFactory.decodeStream(imagestream); //nahrajeme do bitmapoveho proudu
//		}
//		catch(Exception e){
//		}
//		finally{
//			//vzdy vycistit a zavrit
//			try{
//				imagestream.close(); //stream uzavreme
//				imagestream = null;
//			}
//			catch(Exception e){
//			}
//		}
//	
//	/*generujeme ukazatel na texturu. 
//	 *textura je jen jedna a proto generujeme jen jeden nazev.
//	 *druhy arg je pro pole hodnot int pro kaydou texturu. mame jen jednu :)
//	 *treti je posun v poli. pole je od 0 proto zadny posun*/
//	//nasledujici dva radky opakujeme pro kazdou texturu
//	gl.glGenTextures(1, textures,0); 
//	gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);//vaze texturu k opengl
//	
//	//dva radky na mapovani texturu na vrcholy -
//	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//			GL10.GL_NEAREST); //co elat pri zmenseni, nearest je rychle - nejbliysi sousede
//	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//			GL10.GL_LINEAR); //co pro zvetseni - linearni interpolace, pomale pekne
//	
//	/*stale opakovani textury ve smeru s a t*/
//	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
//			GL10.GL_CLAMP_TO_EDGE);
//	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
//			GL10.GL_CLAMP_TO_EDGE);
//	
//	/*spojime bitmapovy proud s texturou, proud znicime*/	
//	GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//	bitmap.recycle();
//	}
//	
	
}
