package com.negrorevolutio.nereengine.asm.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KidBrain;
import com.negrorevolutio.nereengine.asm.KuIdea;
/**
 * 
 * @author yozefx
 *
 */
public class KuASMShape extends KuIdea{
  
	public float PreviousX, PreviousY; 
	public float StartX, StartY;
	public Array<KuASMObjectCollisionMask> Masks; 
	public float OriginX, OriginY;
	public float ScaleX, ScaleY; 
	public KuASMShape Other;  
	public boolean Visible;
	public KuASMObject ObjOwner = null;
	/**
	 * 
	 * @param ObjOwner
	 */
	public KuASMShape(KuASMObject ObjOwner)
	{
		super(ObjOwner.Kid); 
		Kind = "Shape";  
		PreviousX = ObjOwner.PreviousX;
		PreviousY = ObjOwner.PreviousY;
		Masks = new Array<KuASMObjectCollisionMask>();
		FlipX = false;
		FlipY = true;
		OriginX = 0;
		OriginY = 0;
		ScaleX = 1;
		ScaleY = 1; 
		Other = null; 
		Visible = true;
		this.ObjOwner = ObjOwner;
		X = ObjOwner.X;
		Y = ObjOwner.Y;
	}  
	/**
	 * 
	 * @param Mask
	 */
	public void AddCollisionMask(KuASMObject Obj)
	{
		if(Masks == null)return;
		KuASMObjectCollisionMask Mask = new KuASMObjectCollisionMask(this, Obj);
		Masks.add(Mask);
	}
	/**
	 * 
	 * @param MaskName
	 * @return
	 */
	public KuASMObjectCollisionMask GetCollisionMaskByName(String MaskName)
	{
		if(Masks == null)return null;
		for(int i=0; i<Masks.size; ++i){
			KuASMObjectCollisionMask Mask = Masks.get(i);
			if(Mask == null)continue;
			if(Mask.GetName() == MaskName)return Mask;
		} 
		return null;
	}
	
	@Override
	public void Dispose()
	{ 
		if(Masks != null){
			for(int i=0; i<Masks.size; ++i){
				KuASMObjectCollisionMask Mask = Masks.get(i);
				if(Mask == null)continue;
				Mask.Dispose();
			} 
			Masks.clear();
		}		 
		Other = null; 
		ObjOwner = null;
		super.Dispose(); 
	}
	
	 @Override
	 public void Render(SpriteBatch Batch)
	 { 
		if(KidBrain.DebugPaper)
		{
			if(Visible){
				if(spr != null)
					spr.draw(Batch); 
			}
			  
		} 
	 }
	 
	 @Override
	 public void Update(float DeltaTime)
	 { 
		this.X = this.ObjOwner.X + this.StartX;
		this.Y = this.ObjOwner.Y + this.StartY;
		if(Masks != null)
		{
			for(int i=0; i<Masks.size; ++i){
				KuASMObjectCollisionMask Mask = Masks.get(i);
				if(Mask == null)continue;
				 Mask.UpdateShape(DeltaTime);
			}  
		} 		
		Step();
		if(Masks != null)
		{
			for(int i=0; i<Masks.size; ++i){
				KuASMObjectCollisionMask Mask = Masks.get(i);
				if(Mask == null)continue;
				 Mask.UpdateShape(DeltaTime);
			} 
		}
		if(spr != null)
		{
			spr.setPosition(X, Y);
			
		}
		EndStep(); 
		PreviousX = X;
		PreviousY = Y; 
	}
	 /**
	  * 
	  * @param Other
	  */
	public void SimpleCollision(KuASMObject Other){
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.SimpleCollision(Other);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 * @param mask
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void ShapeCollisionPrecision(KuASMObject Other, float x, float y,
			float width, float height) {
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.ShapeCollisionPrecision(Other, x, y, width, height);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionRight(KuASMObject Other){
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.CollisionRight(Other);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionLeft(KuASMObject Other){
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.CollisionLeft(Other);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionBottom(KuASMObject Other){
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.CollisionBottom(Other);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionTop(KuASMObject Other){
		this.ObjOwner.CurrentShapeCollisionPrecision = this;
		this.ObjOwner.CollisionTop(Other);
		this.ObjOwner.CurrentShapeCollisionPrecision = null;
	}
	/**
	 * 
	 */
	public void PostCreation()
	{
		
	}
	
	
}
