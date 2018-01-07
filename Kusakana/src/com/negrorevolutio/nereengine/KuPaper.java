package com.negrorevolutio.nereengine;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.negrorevolutio.nereengine.asm.sprites.KuASMSprite;
/**
 * 
 * @author yozefx
 *
 */
public class KuPaper implements KuSupport{

	public Pixmap pix;
	public Texture tex;
	public Sprite spr;
	public float X, Y, Width, Height; 
	public KuASMSprite SpriteIndex;
	public float Angle;
	public boolean FlipX;
	public boolean FlipY; 
	/**
	 * 
	 */
	public KuPaper(){ 
		X = Y = 0;
		Angle = 0;
		Width = Height = 1;  
		SpriteIndex = null; 
		FlipX = false;
		FlipY = false;
	}
	/**
	 * 
	 */
	public void SetAppearance(){
		if(KidBrain.DebugPaper){
			if(pix != null)pix.dispose();
			if(tex != null)tex.dispose();
			pix = new Pixmap(2, 2, Format.RGBA8888);
			pix.setColor( 
					MathUtils.random(0, 255)/255.0f, MathUtils.random(0, 255)/255.0f, 
					MathUtils.random(0, 255)/255.0f, 1);
			pix.drawRectangle(0, 0, 2, 2);
			tex = new Texture(pix);
			spr = new Sprite(tex);
			spr.setOrigin(Width/2, Height/2);
			spr.setSize(Width, Height);
			spr.setScale(1, 1);
			spr.setPosition(X, Y);
		}
	}
	 
	/**
	 * 
	 */
	public void TearUp()
	{
		if(KidBrain.DebugPaper)
		{
			if(pix != null)pix.dispose();
			if(tex != null)tex.dispose();
			spr = null;
		} 
	}
	/**
	 * 
	 * @param DeltaTime
	 */
	public void Update(float DeltaTime)
	{
		 	
	}
	/**
	 * 
	 * @param Batch
	 */
	public void Render(SpriteBatch Batch)
	{
		if(KidBrain.DebugPaper)
		{
			if(spr != null)
			spr.draw(Batch); 
		}
	}
	
}
