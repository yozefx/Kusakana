package com.negrorevolutio.nereengine.asm.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.negrorevolutio.nereengine.KidBrain;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMBackground extends KuASMObject{

	public boolean Desactivated = false; 
	public float Persistence = 1f; 
	/**
	 * 
	 * @param Region
	 */
	public KuASMBackground(KuASMRegion Region)
	{
		super(Region);
		this.ChangeRegion(Region);
		Kind = "Background";
		ResizeByFrame = false;
	}	
	/**
	 * 
	 * @param Obj
	 */
	public void SendToBackground(KuASMBackground Obj)
	{
		if(this.Region == null)return;
		 this.Region.SendToBackground(Obj);
	}
	/**
	 * 
	 * @param Obj
	 */
	public void SendToForeground(KuASMBackground Obj)
	{
		if(this.Region == null)return;
		 this.Region.SendToForeground(Obj);
	} 
	
	 @Override
	 public void Render(SpriteBatch Batch)
	 { 
		if(KidBrain.DebugPaper)
		{
			if(spr != null)
			spr.draw(Batch);   
		} 
		if(Visible)
		{
			if(SpriteIndex != null)
			{				 
				Batch.draw(
						SpriteIndex.CroppedImg.getTexture(), X, Y, OriginX, OriginX,
						(int)SpriteIndex.Width, (int)SpriteIndex.Height, 
						ScaleX, ScaleY, Angle, 
						(int)SpriteIndex.X, (int)SpriteIndex.Y, 
						(int)SpriteIndex.Width2, (int)SpriteIndex.Height2, 
						FlipX, FlipY); 
			}
		}
		Draw();
	 }
	 
	
}
