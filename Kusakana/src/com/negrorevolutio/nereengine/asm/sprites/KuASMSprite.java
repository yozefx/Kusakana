package com.negrorevolutio.nereengine.asm.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.negrorevolutio.nereengine.KuAsset;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMSprite {

	public TextureRegion CroppedImg; 
	public float X, Y;
	public float Width, Height;
	public float Width2, Height2;
	public String Name;
	public float Wait;
	public float StateTime;  
	public KuAsset Asset; 
	/**
	 * 
	 * @param Asset
	 */
	public KuASMSprite(KuAsset Asset)
	{
		Init(Asset);
	} 
	/**
	 * 
	 * @param Asset
	 */
	public void Init(KuAsset Asset)
	{
		CroppedImg 	= null; 
		X = Y = 0;
		Width = Height = 1;
		Name	= "Undefined";
		Wait	= 0.1f;
		StateTime = 0.0f; 
		//Shape	= null;
		this.Asset = Asset;
	}	
	/**
	 * 
	 * @param Shape
	 */
	public void SetShape(/*KuASMDefenseShape Shape, */String name, float x, float y, float width, float height){
		/*if(Shape == null)return;
		this.Shape 		= Shape;
		Shape.Name 		= name;
		Shape.X 		= x;
		Shape.Y 		= y; 
		Shape.RelX		= x;
		Shape.RelY		= y;
		Shape.Width 	= width;
		Shape.Height 	= height;  
		this.Shape.Creation(); */
	} 
	/**
	 * 
	 * @param Crop
	 * @param Name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param Wait
	 */
	public void Init(TextureRegion Crop, String Name, float x, float y, float Wait){ 
		this.CroppedImg = Crop; 
		X = x;
		Y = y;
		Width = Width2 = CroppedImg.getRegionWidth();
		Height = Height2 =CroppedImg.getRegionHeight();
		this.Name = Name;	 
		this.Wait = Wait; 
	}
	/**
	 * 
	 * @param Crop
	 * @param Name
	 * @param x
	 * @param y
	 * @param Wait
	 * @param w
	 * @param h
	 */
	public void Init(TextureRegion Crop, String Name, int x, int y, 
			float Wait, int w, int h)
	{	 
		this.CroppedImg = new TextureRegion(Crop.getTexture(), x, y, w, h);
		X = x;
		Y = y;
		Width = CroppedImg.getRegionWidth();
		Height = CroppedImg.getRegionHeight();
		Width2 = w;
		Height2 = h;
		this.Name = Name;	 
		this.Wait = Wait;  
	}	 
	/**
	 * 
	 * @param delta
	 * @return
	 */
	public boolean Update(float delta){ 
		if(StateTime <= Wait){
			StateTime += delta;
			return true;
		}
		return false;
	}
	/**
	 * 
	 */
	public void Dispose(){
		CroppedImg	= null; 
		Asset		= null; 
	}
	
}
