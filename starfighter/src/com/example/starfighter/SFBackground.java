package com.example.starfighter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SFBackground {
	private FloatBuffer vertexBuffer; //mezipamet pro vrcholy
	private FloatBuffer textureBuffer;//mezipamet pro textury
	private ByteBuffer indexBuffer;//mezipamet pro indexy
	
	
	private int[] textures = new int[1];
	private float vertices [] = {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
	}; //body rohu ctverce x,y,z
	
	private float texture[] = {
			0.0f, 0.0f,
			1.0f, 0f,
			1, 1.0f,
			0f, 1f,
	}; //rohy textury
	
	private byte indices[] = {
			0, 1, 2,
			0, 2, 3,
	}; //urcuje vrcholy trojuhelniku ve smeru hodinovych rucicek - z poradi verticles
	
	public SFBackground(){
		//vytvarime objekt ByteBuffer pro vertex, texturu a indexy
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
	indexBuffer.put(indices); //vlozime indexi
	indexBuffer.position(0); //nastavime pozici na 0
	
	
	}

}
