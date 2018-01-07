package com.negrorevolutio.nereengine;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.fonts.KuASMImportedFont;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
import com.negrorevolutio.nereengine.asm.scenes.KuASMScene;
import com.negrorevolutio.nereengine.asm.scenes.KuDepth;
/**
 * 
 * @author yozefx
 *
 */
public class KidHand implements KidPart{

	public KuASMScene CurrentScene = null;
	public KuASMScene LastScene = null;
	public static boolean CollisionByDepthEnabled = false;
	Array<KuASMObject> Abattoir = new Array<KuASMObject>(); 
	Array<KuASMObject> KickItOut = new Array<KuASMObject>(); 
	public Vector3 Fingers = new Vector3();
	KuKid Kid;
	public Array<KuASMImportedFont> Fonts = null;
	/**
	 * 
	 */
	public KidHand(KuKid Kid){
		this.Kid = Kid;
	}
	
	@Override
	public void Sleep() {
		CurrentScene = null;
	}
	/**
	 * 
	 * @param DeltaTime
	 */
	public void Update(float DeltaTime) { 
		if(CurrentScene == null)return; 
		Kid.Brain.CurrentSceneName = CurrentScene.Name;
		
		if(this.Fonts != null)
		{
			for(KuASMImportedFont font: Fonts)
			{
				font.Update(DeltaTime);
			}
		}
		CurrentScene.UpdateUnAuthorized();
		DestroyObject();
		SilentDestroyObject();
		Array<KuASMObject> rObjs = new Array<KuASMObject>();
		for(KuASMObject Obj: CurrentScene.GameObjects)
		{
			if(Obj.IsRemovedFromGameObjects()){
				rObjs.add(Obj);
			}
		}
		for(KuASMObject Obj: rObjs)
		{
			CurrentScene.GameObjects.removeValue(Obj, false);
		}
		rObjs.clear();
		rObjs = null;
		for(KuASMRegion Region: CurrentScene.RegionMap){
			if(Region == null)continue;
			for(KuASMObject Obj: CurrentScene.GameObjects)
			{
				if(!Obj.AdventureMode || !Obj.Persistent)continue;
				 Rectangle A = new Rectangle(Region.X, Region.Y, Region.Width, Region.Height);
				 Rectangle B = new Rectangle(Obj.X, Obj.Y, Obj.Width, Obj.Height);
				 if(A.contains(B)||A.overlaps(B)){
					Obj.ChangeRegion(Region);
				 }
			}
		}
		if(!CurrentScene.Conversion)
		{			
			for(KuASMRegion Region: CurrentScene.RegionMap)
			{
				if(Region == null)continue;
				for(KuASMObject Obj: Region.RegionObjects)
				{
					if(Obj == null)continue;
					Obj.X = Region.X + Obj.X;
					Obj.Y = Region.Y + Obj.Y;
				}
			}
			CurrentScene.Conversion = true;
		}
		 
		for(KuASMRegion Region: CurrentScene.RegionMap)
		{
			if(Region == null)continue; 
			Region.Update(DeltaTime);
			if(Kid.Eyes.Target != null)
			{
				if( Kid.Eyes.Target.AdventureMode)
				{
					if(Region.Activated)
					{
						Rectangle Bounds = new Rectangle(
								Region.X, Region.Y, Region.Width, Region.Height);
						Rectangle Target = new Rectangle(
								 Kid.Eyes.Target.X,  Kid.Eyes.Target.Y, 
								 Kid.Eyes.Target.Width,  Kid.Eyes.Target.Height);
						
						if(Bounds.overlaps(Target)||Bounds.contains(Target))
						{
							 Kid.Eyes.Target.Activated = true;
							 Kid.Eyes.Target.ChangeRegion(Region);
						}						
					} 
				}
			}
			UpdateDepth();
			for(KuDepth Depth: CurrentScene.Depths)
			{
				for(KuASMObject Obj: Depth.Objects)
				{
					if(Obj == null)continue;
					if(Obj.Kind == "~@Off")continue;
					if(Obj.Destroyed)continue; 
					if(!Obj.Activated && !Obj.Persistent)continue; 
					if(!Obj.Region.equals(Region))continue; 
					Obj.DepthLoopTime += 1;
					if(Obj.DepthLoopTime>1)
					{
						continue;
					}
					float LastX = Obj.X;
					float LastY = Obj.Y;
					Obj.Update(DeltaTime);
					float OffsetX = Obj.X - LastX;
					float OffsetY = Obj.Y - LastY;
					Obj.Xprevious = Obj.PreviousX - OffsetX; 
					Obj.Yprevious  = Obj.PreviousY - OffsetY;
					if(CollisionByDepthEnabled)
					this.UpdateCollision(Depth, Obj);
					else
					this.UpdateCollision(CurrentScene, Obj);  
				}
			}
			UpdateDepth();
		} 

		for(KuDepth Depth: CurrentScene.Depths)
		{
			for(KuASMObject Obj: Depth.Objects)
			{
				if(Obj == null)continue; 
				if(Obj.Kind == "~@Off")continue;
				if(Obj.Destroyed)continue;    
				Obj.DepthLoopTime = 0;
			}
		}
	}
	/**
	 * 
	 * @param Depth
	 * @param Obj
	 */
	void UpdateCollision(KuDepth Depth, KuASMObject Obj)
	{
		Obj.Other = null; 
		for(int i = 0; i<Depth.Objects.size; ++i)
		{
			if(!Obj.CheckingCollision)break;
			KuASMObject O = Depth.Objects.get(i);
			if(O  == null)continue;
			if(Obj.Kind == "~@Off")continue;
			if(O .equals(Obj))continue;
			if(O .Destroyed)continue;
			if(!O .Activated)continue;
			if(!O .Solid)continue;
			Rectangle Other = new Rectangle(O.X, O.Y, O.Width, O.Height);
			Rectangle _this = new Rectangle(Obj.X, Obj.Y, Obj.Width, Obj.Height);
			if(Other.overlaps(_this) || Other.contains(_this))
			{
				Obj.Other = O; 
			} 
		}
	}
	/**
	 * 
	 * @param Scene
	 * @param Obj
	 */
	void UpdateCollision(KuASMScene Scene, KuASMObject Obj)
	{
		Obj.Other = null; 
		for(int j = 0; j<Scene.Depths.size; ++j){
			KuDepth Depth = Scene.Depths.get(j);
			if(Depth == null)continue;
			for(int i = 0; i<Depth.Objects.size; ++i)
			{
				if(!Obj.CheckingCollision)break;
				KuASMObject O = Depth.Objects.get(i);
				if(O  == null)continue;
				if(Obj.Kind == "~@Off")continue;
				if(O .equals(Obj))continue;
				if(O .Destroyed)continue;
				if(!O .Activated)continue;
				if(!O .Solid)continue;
				Rectangle Other = new Rectangle(O.X, O.Y, O.Width, O.Height);
				Rectangle _this = new Rectangle(Obj.X, Obj.Y, Obj.Width, Obj.Height);
				if(Other.overlaps(_this) || Other.contains(_this))
				{
					Obj.Other = O; 
				} 
			}
		}
	}
	/**
	 * 
	 */
	public void UpdateDepth() 
	{ 
		for(KuDepth Depth: CurrentScene.Depths)
		{
			Depth.Objects.clear();
		}
		for(KuDepth Depth: CurrentScene.Depths)
		{
			for(KuASMObject Obj: CurrentScene.GameObjects)
			{
				if(Obj == null)continue;
				if(Obj.Kind == "~@Off")continue;
				if(Obj.Destroyed)continue;
				if(!Obj.Activated && !Obj.Persistent)continue;
				if(Obj.Zindex == Depth.index)
				{
					Depth.Objects.add(Obj);
				}
			}
		}
	}
	/**
	 * 
	 * @param Obj
	 */
	public void Gabarage(KuASMObject Obj) {
		 if(Obj == null)return;
		 if(Obj.Destroyed)return;
		 Abattoir.add(Obj);
	}
	/**
	 * 
	 */
	void DestroyObject()
	{
		for(KuASMObject Obj: Abattoir)
		{
			Obj.Destroy();
			Obj.Dispose();
		}
		Abattoir.clear();
	}
	/**
	 * 
	 * @param Obj
	 */
	public void KickOut(KuASMObject Obj) {
		 if(Obj == null)return;
		 if(Obj.Destroyed)return;
		 KickItOut.add(Obj);
	} 
	/**
	 * 
	 */
	void SilentDestroyObject()
	{
		for(KuASMObject Obj: KickItOut)
		{  
			Obj.Dispose();
		}
		KickItOut.clear();
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void GetCoordinates(float x, float y)
	{
		Fingers.set(x, y, 0);
		Fingers = Kid.Eyes.Perception.unproject(Fingers);
	}
	/**
	 * 
	 */
	public void UpdateBackground() {
		 for(KuASMObject Obj: CurrentScene.BackgroundDepths)
		 {
			 Obj.Update(CurrentScene.DeltaTime);
		 }
	}
	/**
	 * 
	 */
	public void UpdateForeground() {
		for(KuASMObject Obj: CurrentScene.ForegroundDepths)
		 {
			 Obj.Update(CurrentScene.DeltaTime);
		 }
	}
	/**
	 * 
	 * @param DestroyObjAfter
	 */
	public void ObjectsDestroyNow(Array<KuASMObject> DestroyObjAfter) {		 
		for(KuASMObject Obj: DestroyObjAfter){
			this.CurrentScene.GameObjects.removeValue(Obj, false);
			Gabarage(Obj);
		}
		DestroyObjAfter.clear();
	}
	/**
	 * 
	 * @param SilentDestroyObjAfter
	 */
	public void ObjectsSilentDestroyNow(Array<KuASMObject> SilentDestroyObjAfter) {
		for(KuASMObject Obj: SilentDestroyObjAfter){
			this.CurrentScene.GameObjects.removeValue(Obj, false);
			KickOut(Obj);
		}
		SilentDestroyObjAfter.clear();
	} 

}
