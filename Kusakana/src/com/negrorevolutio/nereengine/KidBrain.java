package com.negrorevolutio.nereengine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.fonts.KuASMImportedFont;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.objects.KuASMObjectCollisionMask;
import com.negrorevolutio.nereengine.asm.objects.KuASMShape;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
import com.negrorevolutio.nereengine.asm.scenes.KuASMScene;
import com.negrorevolutio.nereengine.asm.scenes.KuDepth;
/**
 * 
 * @author yozefx
 *
 */
public class KidBrain implements KidPart{

	public static boolean DebugPaper = false;
	public KuASMScene CurrentScene = null;
	SpriteBatch Mind;
	public KuAsset Asset = null;
	public KuKid Kid = null;
	Array<KuASMObject> DestroyObjAfter = new Array<KuASMObject>(); 
	Array<KuASMObject> SilentDestroyObjAfter = new Array<KuASMObject>();
	public Array<KuASMImportedFont> Fonts = null; 
	public String CurrentSceneName, PreviousSceneName;
	/**
	 * 
	 */
	public KidBrain(KuKid Kid){
		Mind = new SpriteBatch();
		this.Kid = Kid;
	}
	
	@Override
	public void Sleep() {
		CurrentScene = null;
		Mind.dispose();
	}
	/**
	 * 
	 */
	public void Render() { 
		if(CurrentScene == null)return; 

		Mind.begin();
		for(KuASMRegion Region: CurrentScene.RegionMap)
		{
			if(Region == null)continue;
			if(!Region.Activated)continue;
			Region.Render(Mind); 
			for(KuDepth Depth: CurrentScene.Depths)
			{
				for(KuASMObject Obj: Depth.Objects)
				{
					if(Obj == null)continue;
					if(Obj.Kind == "~@Off")continue;
					if(Obj.Destroyed)continue; 
					if(!Obj.Activated && !Obj.Persistent)continue; 
					Obj.Render(Mind);  
				}
			}
		}
		Kid.Eyes.Render(Mind);
		Mind.end(); 
		Kid.Hands.ObjectsDestroyNow( this.DestroyObjAfter);
		Kid.Hands.ObjectsSilentDestroyNow(SilentDestroyObjAfter);
	}
	/**
	 *  
	 */
	public void RenderBackground( ) {
		Mind.setProjectionMatrix(Kid.Eyes.Background.combined);
		Mind.begin();
		Kid.Hands.UpdateBackground();
		for(KuASMObject Obj: CurrentScene.BackgroundDepths)
		{
			 Obj.Render(Mind);
		}
		Mind.end(); 
	}
	/**
	 * 
	 */
	public void RenderForeground() {
		Mind.setProjectionMatrix(Kid.Eyes.Foreground.combined);
		Mind.begin();
		Kid.Hands.UpdateForeground();
		for(KuASMObject Obj: CurrentScene.ForegroundDepths)
		{
			 Obj.Render(Mind);
		}
		if(this.Fonts != null)
		{
			for(KuASMImportedFont font: Fonts)
			{
				font.Render(Mind);
			}
		}
		Mind.end();  
	}
	/**
	 * 
	 * @param Obj
	 */
	public void DestroyAfter(KuASMObject Obj) { 
		this.DestroyObjAfter.add(Obj);
		this.DestroyMasksRightNow(Obj);
	}
	/**
	 * 
	 * @param Obj
	 */
	void DestroyMasksRightNow(KuASMObject Obj){
		if(Obj.Masks != null)
		{
			for(KuASMObjectCollisionMask Mask: Obj.Masks)
			{ 
				Mask.Dispose();
			}
			Obj.Masks.clear();
		}
	 
		if(Obj.Shapes != null)
		{
			for(KuASMShape Shape: Obj.Shapes)
			{
				Shape.Dispose();
			}
			Obj.Shapes.clear();
		}
	 
		Obj.BlackListed = true;
	}
	/**
	 * 
	 * @param Obj
	 */
	public void SilentDestroyAfter(KuASMObject Obj) {
		this.SilentDestroyObjAfter.add(Obj);
		this.DestroyMasksRightNow(Obj);
	}
	
}
