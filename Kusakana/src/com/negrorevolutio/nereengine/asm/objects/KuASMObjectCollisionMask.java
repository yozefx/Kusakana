package com.negrorevolutio.nereengine.asm.objects;

import com.negrorevolutio.nereengine.KuPaper;
/**
 * 
 * @author yozefx
 *
 */
public class KuASMObjectCollisionMask extends KuPaper{

	public KuASMObject Mask;
	public KuASMObject Obj;
	public boolean Left = false;
	public boolean Right = false;
	public boolean Top = false;
	public boolean Bottom = false; 
	boolean PreviousLeft = false;
	boolean PreviousRight = false;
	boolean PreviousTop = false;
	boolean PreviousBottom = false; 
	public KuASMShape Shape;
	/**
	 * 
	 * @param Obj
	 * @param Mask
	 */
	public KuASMObjectCollisionMask(KuASMObject Obj, KuASMObject Mask)
	{
		this.Obj = Obj; 
		this.Mask = Mask; 
		this.Shape = null;
	}
	/**
	 * 
	 * @param Shape
	 * @param Mask
	 */
	public KuASMObjectCollisionMask(KuASMShape Shape, KuASMObject Mask) {
		this.Obj = null; 
		this.Mask = Mask; 
		this.Shape = Shape;
	}
	/**
	 * 
	 */
	public void Dispose()
	{
		Obj = null;
		Mask = null;
		Shape = null;
		this.TearUp();
	}
	
	@Override
	public void Update(float DeltaTime)
	{
		if(Mask == null)return;
		if(Mask.Destroyed)return;
		if(!Mask.Activated)return;
		if(!Mask.Solid)return;
		 
		
		if(Obj.X + Obj.Width < Mask.X){
			Left = true; 
		}
		else{
			Left = false;
		}
		if(Obj.X > Mask.X+ Mask.Width){
			Right = true; 
		}
		else{
			Right = false;
		}
		if(Obj.Y + Obj.Height < Mask.Y){
			Top = true; 
		}
		else{
			Top = false;
		}
		if(Obj.Y > Mask.Y + Mask.Height){
			Bottom = true; 
		}
		else{
			Bottom = false;
		} 
	
		if(!Left && !Right && !Top && !Bottom)
		{
			if(PreviousLeft)
			{
				Obj.CollisionRight(Mask);
			}
			if(PreviousRight)
			{
				Obj.CollisionLeft(Mask);
			}
			if(PreviousTop)
			{
				Obj.CollisionBottom(Mask);
			}
			if(PreviousBottom)
			{
				Obj.CollisionTop(Mask);
			}
			Obj.SimpleCollision(Mask); 			 
		}		 
		
		PreviousLeft = Left;
		PreviousRight = Right;
		PreviousTop = Top;
		PreviousBottom = Bottom; 
	}
	/**
	 * 
	 * @param DeltaTime
	 */
	public void UpdateShape(float DeltaTime)
	{
		if(Mask == null)return;
		if(Mask.Destroyed)return;
		if(!Mask.Activated)return;
		if(!Mask.Solid)return;
		if(Shape == null)return;
		 
		
		if(Shape.X + Shape.Width < Mask.X){
			Left = true; 
		}
		else{
			Left = false;
		}
		if(Shape.X > Mask.X+ Mask.Width){
			Right = true; 
		}
		else{
			Right = false;
		}
		if(Shape.Y + Shape.Height < Mask.Y){
			Top = true; 
		}
		else{
			Top = false;
		}
		if(Shape.Y > Mask.Y + Mask.Height){
			Bottom = true; 
		}
		else{
			Bottom = false;
		} 
	
		if(!Left && !Right && !Top && !Bottom)
		{
			if(PreviousLeft )
			{
				Shape.CollisionRight(Mask);
			}
			if(PreviousRight  )
			{
				Shape.CollisionLeft(Mask);
			}
			if(PreviousTop  )
			{
				Shape.CollisionBottom(Mask);
			}
			if(PreviousBottom  )
			{
				Shape.CollisionTop(Mask);
			}
			try{
			Shape.SimpleCollision(Mask); 
			Shape.ShapeCollisionPrecision(Mask, Shape.X, Shape.Y, Shape.Width, Shape.Height);
			}catch(Exception e){}
		}		 
		
		PreviousLeft = Left;
		PreviousRight = Right;
		PreviousTop = Top;
		PreviousBottom = Bottom; 
	}
	/**
	 * 
	 * @return
	 */
	public String GetName()
	{
		return this.Mask != null?this.Mask.Name:"";
	}
	/**
	 * 
	 * @return
	 */
	public String GetKind() { 
		return this.Mask != null?this.Mask.Kind:"";
	}
}
