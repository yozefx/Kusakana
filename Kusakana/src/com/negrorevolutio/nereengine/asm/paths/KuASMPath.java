package com.negrorevolutio.nereengine.asm.paths;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KuKid;
import com.negrorevolutio.nereengine.asm.KuIdea;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject.PathAnimation;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMPath extends KuIdea{

	int reversed_step;
	
	public Array<KuASMPathPoint> PathPoints;
	public KuASMRegion Region = null;

	public KuASMPath(KuKid Kid) {
		super(Kid);
		Creation();
	}
	 
	
	@Override
	public void Creation()
	{  
		this.PathPoints = new Array<KuASMPathPoint>();
	}
	/**
	 * 
	 * @param PathPoint
	 */
	public void AddPoint(KuASMPathPoint PathPoint){
		PathPoints.add(PathPoint);
		reversed_step = PathPoints.size - 1;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void AddPoint(int x, int y){
		KuASMPathPoint PathPoint = new KuASMPathPoint(Kid);
		PathPoint.X = x;
		PathPoint.Y = y;
		AddPoint(PathPoint);
	}
	/**
	 * 
	 * @param PathPoint
	 * @return
	 */
	public boolean RemovePoint(KuASMPathPoint PathPoint)
	{
		return PathPoints.removeValue(PathPoint, false);
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void RemovePoint(int x, int y)
	{		
		KuASMPathPoint PathPoint = null;
		for(KuASMPathPoint Pt: PathPoints)
		{
			if(Pt.X == x && Pt.Y == y){
				PathPoint = Pt;
				break;
			}
		}
		if(PathPoint != null)
		RemovePoint(PathPoint);
		reversed_step = PathPoints.size - 1;
	} 
	/**
	 * 
	 * @param Obj
	 */
	public void Step(KuASMObject Obj) 
	{
		if(Obj == null)return;
		if(Obj.FirstTime)
		{
			Obj.reversed_step = reversed_step;
 
		}
		if(Obj.pathAnimation == PathAnimation.LOOP 
				|| Obj.pathAnimation == PathAnimation.NORMAL)
		{ 
			this.ToRight(Obj);
		}
		else if(Obj.pathAnimation == PathAnimation.REVERSED_LOOP 
				|| Obj.pathAnimation == PathAnimation.REVERSED)
		{ 
			//this.ToLeft(Obj);
		} 
	} 
	
	void ToLeft(KuASMObject Obj)
	{  
	}
	 
	void ToRight(KuASMObject Obj)
	{
		KuASMPathPoint CurrentPoint = null;
		if(Obj.path_step >=this.PathPoints.size)
		{
			return;
		}
		CurrentPoint = this.PathPoints.get(Obj.path_step);
		if(Obj.path_step == 0 && Obj.FirstTime)
		{
			Obj.StepOnPathStarts();
			Obj.FirstTime = false;
			if(!Obj.PointByPoint)
			{
				Obj.X = CurrentPoint.X;
				Obj.Y = CurrentPoint.Y; 
			}
		} 
		
		if(Obj.PointByPoint)
		{
			Obj.X = CurrentPoint.X + Obj.OriginX;
			Obj.Y = CurrentPoint.Y + Obj.OriginY;
			Obj.pathStateTime += (Obj.PathSpeed * Obj.DeltaTime);
			Obj.path_step = (int)Obj.pathStateTime; 
		}
		else
		{				
			if((Obj.X + Obj.OriginX) < CurrentPoint.X)
			{
				Obj.X += Obj.PathSpeed;
			}
			else if((Obj.X + Obj.OriginX) > CurrentPoint.X)
			{
				Obj.X -= Obj.PathSpeed;
			}
			if((Obj.Y + Obj.OriginY) < CurrentPoint.Y)
			{
				Obj.Y += Obj.PathSpeed;
			}
			else if((Obj.Y + Obj.OriginY) > CurrentPoint.Y)
			{
				Obj.Y -= Obj.PathSpeed;
			} 
			 
			Rectangle A = null, B = null;
			A = new Rectangle(Obj.X + Obj.OriginX, Obj.Y + Obj.OriginY, 5, 5);
			B = new Rectangle(CurrentPoint.X, CurrentPoint.Y, 5, 5);
			if(A.overlaps(B)||A.contains(B))
			{
				Obj.path_step ++;
			} 
		}
		Obj.StepOnPath();
		if(Obj.path_step >= PathPoints.size)
		{
			if(Obj.pathAnimation == PathAnimation.LOOP)
			{
				Obj.pathStateTime = 0;
				Obj.path_step = 0;
				if(!Obj.PointByPoint)
				{
					CurrentPoint = this.PathPoints.get(Obj.path_step);
					Obj.X = CurrentPoint.X;
					Obj.Y = CurrentPoint.Y;
				}
			}
			Obj.StepOnPathEnds();
		}	
	}
	
	@Override
	public void Dispose(){
		super.Dispose();
		for(KuASMPathPoint PathPoint: PathPoints){
			PathPoint.Dispose();
		}
		PathPoints.clear();
		PathPoints = null;
	}
	
}
