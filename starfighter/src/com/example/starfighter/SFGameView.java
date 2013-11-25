package com.example.starfighter;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SFGameView extends GLSurfaceView {
	private SFGameRender renderer;
	
	public SFGameView(Context context){
		super (context);
		renderer = new SFGameRender();
		this.setRenderer(renderer);
	}
}
