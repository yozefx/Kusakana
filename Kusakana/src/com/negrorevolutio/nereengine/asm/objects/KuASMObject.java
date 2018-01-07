package com.negrorevolutio.nereengine.asm.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KidBrain;
import com.negrorevolutio.nereengine.asm.KuIdea;
import com.negrorevolutio.nereengine.asm.paths.KuASMPath;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
import com.negrorevolutio.nereengine.asm.scenes.KuASMScene;
import com.negrorevolutio.nereengine.asm.scenes.KuDepth;
import com.negrorevolutio.nereengine.asm.sprites.KuASMAnimation;
import com.negrorevolutio.nereengine.asm.timeline.KuASMTimeline;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMObject extends KuIdea{

	public KuASMRegion Region 	= null; 
	public float PreviousX, PreviousY; 
	public Array<KuASMObjectCollisionMask> Masks;
	public boolean Solid = true; 
	public float OriginX, OriginY;
	public float ScaleX, ScaleY;
	public boolean CheckingCollision = false;
	public KuASMObject Other; 
	public enum Direction
	{
		RIGHT, LEFT
	}
	public Direction _Direction = Direction.RIGHT;
	public int Zindex = 0;
	public KuASMAnimation Animation = null;
	public boolean Visible = true;
	public Array<KuASMShape> Shapes = null;
	protected int ShapeMax = 0;
	public boolean CheckShapeCollision = true;
	public boolean CheckBoundaryCollision = true; 
	public float Xprevious = 0;
	public float Yprevious = 0;
	public boolean AdventureMode = false;
	public boolean Persistent = false; 
	public boolean BlackListed = false;
	public float VelocityX = 0;
	public float VelocityY = 0; 
	public int DepthLoopTime = 0;
	public float Alpha	= 1;
	public float Red = 1;
	public float Blue = 1;
	public float Green = 1; 
	protected KuASMScene Scene;
	protected boolean RemoveFromGameObject = false;  
	public boolean ResizeByFrame = true;
	public boolean RegionLeft = false;
	public boolean RegionEntered = false;
	public KuASMPath Path = null;
	public boolean ReadPathEnabled = false;
	public boolean StaticFindingPath = true;
	public int path_step = 0;
	public float pathStateTime = 0;
	public int reversed_step = 0;
	public float PathSpeed = 1;
	public boolean FirstTime = true;
	public enum PathAnimation{
		NORMAL, LOOP, REVERSED, REVERSED_LOOP
	}
	public PathAnimation pathAnimation = PathAnimation.LOOP;
	public boolean PointByPoint = true;
	public boolean UsingTimeline = false;
	public KuASMTimeline StopMotion = null;  
	public int Id;
	public KuASMShape CurrentShapeCollisionPrecision = null;
	/**
	 * 
	 */
	public KuASMObject(KuASMRegion Region)
	{
		super(Region.Kid);
		this.Region = Region;
		Kind = "Object";  
		PreviousX 	= 0;
		PreviousY 	= 0;
		Masks 		= new Array<KuASMObjectCollisionMask>();
		FlipX 		= false;
		FlipY 		= true;
		OriginX 	= 0;
		OriginY 	= 0;
		ScaleX 		= 1;
		ScaleY 		= 1; 
		Other 		= null;
		Zindex 		= 0;
		Visible 	= true;
		Shapes 		= new Array<KuASMShape>(); 
		this.Scene 	= null; 
		this.Creation();  
	} 
	/**
	 * 
	 */
	public void StopMotionCreate()
	{
		
	}	
	/**
	 * 
	 * @param ObjName
	 * @param x
	 * @param y
	 * @param depth
	 */
	public KuASMObject InstanceCreate(String ObjKind, float x, float y, int depth)
	{
		if(this.Region == null)
			throw new NullPointerException("Region cannot be null"); 
		return Region.InstanceCreateObject(ObjKind, x, y, depth); 
	}
	/**
	 * 
	 * @param ObjKind
	 * @param ObjName
	 * @param x
	 * @param y
	 * @param depth
	 * @return
	 */
	public KuASMObject InstanceCreate(String ObjKind, String ObjName, float x, float y, int depth)
	{
		if(this.Region == null)
			throw new NullPointerException("Region cannot be null"); 
		return Region.InstanceCreateObject(ObjKind, ObjName, x, y, depth); 
	}
	/**
	 * 
	 * @param ObjKind
	 * @return
	 */
	public KuASMObject InstanceCreate(String ObjKind)
	{
		if(this.Region == null)
			throw new NullPointerException("Region cannot be null"); 
		return Region.InstanceCreateObject(ObjKind);
	}
	/**
	 * 
	 */
	public void InstanceDestroy()
	{
		this.InstanceDestroy(this);
	}
	/**
	 * 
	 * @param Obj
	 */
	public void InstanceDestroy(KuASMObject Obj)
	{
		if(this.Region == null)
			throw new NullPointerException("Region cannot be null");
		if(Obj == null)return;
		this.Region.InstanceDestroyObject(Obj);
	}
	/**
	 * 
	 */
	public void SilentInstanceDestroy()
	{
		SilentInstanceDestroy(this);
	}
	
	public void SilentInstanceDestroy(KuASMObject Obj)
	{
		if(this.Region == null)
			throw new NullPointerException("Region cannot be null");
		if(Obj == null)return;
		this.Region.SilentInstanceDestroyObject(Obj);
	}
	/**
	 * 
	 * @param Max
	 */
	public void SetShapeMax(int Max)
	{
		this.ShapeMax = Max;
	}
	/**
	 * 
	 * @return
	 */
	public int GetShapeMax()
	{
		return this.ShapeMax;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param setappereance
	 */
	public void AddShape(float x, float y, float w, float h, boolean setappereance)
	{
		if(Shapes.size == ShapeMax && Shapes.size != 0)return;
		KuASMShape Shape = new KuASMShape(this);
		Shape.StartX = x;
		Shape.StartY = y;
		Shape.Width = w;
		Shape.Height = h;
		if(setappereance)
		Shape.SetAppearance();
		Shapes.add(Shape); 
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public KuASMShape GetShapeByIndex(int index)
	{
		if(index >= 0 && index < Shapes.size)
		{
			return Shapes.get(index);
		}
		return null;
	}
	/**
	 * 
	 * @param Region
	 */
	public void ChangeRegion(KuASMRegion Region)
	{
		this.Region = Region;
	}
	/**
	 * 
	 * @param Scene
	 */
	public void ChangeRegion(KuASMScene Scene) {
		this.RemoveFromGameObject  = true;
		try{
			if(Region != null)
			Region.RegionObjects.removeValue(this, false);
		}catch(Exception e){}
		this.Region = null;
		this.Scene = Scene;
	}
	/**
	 * 
	 * @return
	 */
	public boolean IsRemovedFromGameObjects()
	{
		return RemoveFromGameObject;
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
		for(KuASMShape Shape: Shapes)
		{
			Shape.AddCollisionMask(Obj);
		}
	}
	 
	/**
	 * 
	 * @param MaskName
	 * @return
	 */
	public KuASMObjectCollisionMask GetCollisionMaskByName(String MaskName)
	{
		if(Masks == null)return null;
		for(KuASMObjectCollisionMask Mask: Masks)
		{ 
			if(Mask.GetName() == MaskName)return Mask;
		}
		return null;
	}
	/**
	 * 
	 * @param MaskName
	 * @return
	 */
	public KuASMObjectCollisionMask GetCollisionMaskByObjKind(String ObjKind)
	{
		if(Masks == null)return null;
		for(int i=0; i<Masks.size; ++i){
			KuASMObjectCollisionMask Mask = Masks.get(i);
			if(Mask == null)continue;
			if(Mask.GetKind() == ObjKind)return Mask;
		} 
		return null;
	}
	
	@Override
	public void Dispose()
	{ 
		if(Destroyed)return; 
		if(Masks != null)
		{
			for(int i=0; i<Masks.size; ++i){
				KuASMObjectCollisionMask Mask = Masks.get(i);
				if(Mask == null)continue;
				Mask.Dispose();
			} 
			Masks.clear();
		}
		Masks = null; 
		Other = null;
		if(Animation != null)
			Animation.Dispose();
		Animation = null;
		if(Shapes != null)
		{
			for(int i=0; i<Shapes.size; ++i){
				KuASMShape Shape = Shapes.get(i);
				Shape.Dispose();
			} 
			Shapes.clear();
		}
		Shapes = null;
		Region = null;
		Scene = null;
		super.Dispose(); 
	}
	
	 @Override
	 public void Render(SpriteBatch Batch)
	 { 	
			if(Kind == "~@Off")return;	 
		if(KidBrain.DebugPaper)
		{
			if(spr != null)
			spr.draw(Batch);   
		} 
		 if(Visible){
			if(SpriteIndex != null){
				Color color = Batch.getColor();
				float PreviousRed = color.r;
				float PreviousGreen = color.g;
				float PreviousBlue = color.b;
				float PreviousAlpha = color.a;
				color.r = Red;
				color.b = Blue;
				color.g = Green;
				color.a = Alpha;
				Batch.setColor(color);
				if(!ResizeByFrame){
					Batch.draw(
							SpriteIndex.CroppedImg.getTexture(), X, Y, OriginX, OriginX,
							(int)SpriteIndex.Width, (int)SpriteIndex.Height, 
							ScaleX, ScaleY, Angle, 
							(int)SpriteIndex.X, (int)SpriteIndex.Y, 
							(int)SpriteIndex.Width2, (int)SpriteIndex.Height2, 
							FlipX, FlipY); 
				}
				else
				{ 
					Batch.draw(
							SpriteIndex.CroppedImg.getTexture(), X, Y, OriginX, OriginX, 
							Width, Height, ScaleX, ScaleY, Angle, 
							(int)SpriteIndex.X, (int)SpriteIndex.Y, 
							(int)SpriteIndex.Width2, (int)SpriteIndex.Height2, 
							FlipX, FlipY); 
				}
				color.r = PreviousRed;		
				color.g = PreviousGreen;		
				color.b = PreviousBlue;		
				color.a = PreviousAlpha;	
				
				Batch.setColor(color);
			}
		}
		if(Shapes != null && !this.BlackListed){
			if(Shapes != null){
				for(int i=0; i<Shapes.size; ++i){
					KuASMShape Shape = Shapes.get(i);
					Shape.Render(Batch);
				} 
			}		 
		}
		Draw();
	 }
	 
	 @Override
	 public void Update(float DeltaTime)
	{  
		 if(Kind == "~@Off")return;	
		 if(this.Destroyed)return; 
		 
		 this.DeltaTime = DeltaTime;
		 this.StateTime += DeltaTime;
		 
			 if(this.CheckBoundaryCollision || this.CheckShapeCollision)
			 {
				 CheckingCollision = true;
			 }
			 if(this.CheckingCollision)
			 {
				 if(this.CheckShapeCollision)
				 {
					 if(Shapes != null && !this.BlackListed){ 
						for(int i=0; i<Shapes.size; ++i){
							KuASMShape Shape = Shapes.get(i);
							Shape.Update(DeltaTime);
						}
					 }
				 }
				 if(this.CheckBoundaryCollision)
				 {	 
					 if(Masks != null && !this.BlackListed){ 
						for(int i=0; i<Masks.size; ++i){
							KuASMObjectCollisionMask Mask = Masks.get(i);
							if(Mask == null)continue;
							 Mask.Update(DeltaTime);
						} 
					 } 
				 } 
			 }
	
			if(Animation != null){
				Animation.Play(DeltaTime);
				if(Animation.CurrentFrame != null){
					SpriteIndex = Animation.CurrentFrame;  
				}
				
			} 
			float speedX = Math.abs(VelocityX); 
			if(this._Direction == Direction.LEFT)
			{
				if(speedX>=0)
				speedX *= -1; 
			}
			else{
				if(speedX<0)
					speedX *= -1; 
			}
			X += speedX; 
			Step(); 
			 
			 if(this.CheckingCollision)
			 {
				 if(this.CheckShapeCollision)
				 {
					 if(Shapes != null && !this.BlackListed){
						 for(int i=0; i<Shapes.size; ++i){
								KuASMShape Shape = Shapes.get(i);
								Shape.Update(DeltaTime);
							}
					 }
				 }
				 if(this.CheckBoundaryCollision)
				 { 
					 if(Masks != null && !this.BlackListed){
						 for(int i=0; i<Masks.size; ++i){
								KuASMObjectCollisionMask Mask = Masks.get(i);
								if(Mask == null)continue;
								 Mask.Update(DeltaTime);
							}
					 } 
				 } 
			 }
			
			if(spr != null)
			{
				spr.setPosition(X, Y); 
			}
			if(Other != null)
			Collision();
			EndStep(); 
			PreviousX = X;
			PreviousY = Y; 
			ReadPath(); 
			this.ReadPersonalTimeline(this.DeltaTime);
	}
	/**
	 * 
	 * @param deltaTime
	 */
	void ReadPersonalTimeline(float DeltaTime) {
		
		if(this.UsingTimeline && this.StopMotion != null)
		{ 
			StopMotion.Update(DeltaTime);
		}
	}
	/**
	  * 
	  */
	 void ReadPath()
	 { 
		 if(this.ReadPathEnabled && Path != null)
		 {
			 Path.Step(this);
		 }
	 } 
	 /**
	  * 
	  * @param Other
	  */
	public void SimpleCollision(KuASMObject Other){}
	/**
	 * 
	 * @param Other
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void ShapeCollisionPrecision(KuASMObject Other, float x, float y,
			float width, float height) { }
	/**
	 * 
	 * @param Other
	 */
	public void CollisionRight(KuASMObject Other){}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionLeft(KuASMObject Other){}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionBottom(KuASMObject Other){}
	/**
	 * 
	 * @param Other
	 */
	public void CollisionTop(KuASMObject Other){}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */ 
	public void FollowTowardsObjectAxis(float x, float y, float speed) {
		if (speed == 0) return ;
		if (X == x && Y == y) return ;  
		float _dx = x - X,
			_dy = y - Y,
			_dist = _dx * _dx + _dy * _dy;
		if (!(_dist < speed * speed)) { 
			_dist = (float)Math.sqrt(_dist);
			X += (int)(_dx * speed / _dist);
			Y += (int)(_dy * speed / _dist);
			X = (int)X;
			Y = (int)Y;
		}			
	}
	/**
	 * 
	 * @param Obj
	 * @param speed
	 */
	public void FollowTowardsObjectAxis(KuASMObject Obj, float speed) {
		if(Obj == null)return;
		if (speed == 0) return ;   
		if (X == Obj.X && Y == Obj.Y) return ;  
		float _dx = Obj.X - X,
			_dy = Obj.Y - Y,
			_dist = _dx * _dx + _dy * _dy;
		if (!(_dist < speed * speed)) { 
			_dist = (float)Math.sqrt(_dist);
			X += (int)(_dx * speed / _dist);
			Y += (int)(_dy * speed / _dist);
			X = (int)X;
			Y = (int)Y;
		}		 
	}	 
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public float PointDirection(float x1, float y1, float x2, float y2) {
		return (float)(Math.atan2(y2-y1,x2-x1) * (180.0f/Math.PI));
		//return Math.floorMod((int)(Math.atan2(y2-y1,x2-x1) * (180.0f/Math.PI))+360, 360); 		
	}
	/**
	 * 
	 * @param Obj
	 * @return
	 */
	public float PointDirection(KuASMObject Obj){
		if(Obj == null)return 0;
		return PointDirection(X, Y, Obj.X, Obj.Y);
	}
	/**
	 * 
	 * @param Instance
	 * @param x
	 * @param y
	 * @return
	 */
	public Array<KuASMObject> PlaceMeeting(String Instance)
	{
		KuASMScene Scene =  Kid.Hands.CurrentScene;
		KuASMObject Obj = this;
		Array<KuASMObject> SameKind = null;
		for(int j = 0; j<Scene.Depths.size; ++j){
			KuDepth Depth = Scene.Depths.get(j);
			if(Depth == null)continue;
			for(int i = 0; i<Depth.Objects.size; ++i)
			{
				if(!Obj.CheckingCollision)break;
				KuASMObject O = Depth.Objects.get(i);
				if(O  == null)continue;
				if(O .equals(Obj))continue;
				if(O .Destroyed)continue;
				if(!O .Activated)continue;
				if(!O .Solid)continue;
				if(O.Kind != Instance)continue;
				
				Rectangle Other = new Rectangle(O.X, O.Y, O.Width, O.Height);
				Rectangle _this = new Rectangle(Obj.X, Obj.Y, Obj.Width, Obj.Height);
				
				if(Other.overlaps(_this) || Other.contains(_this))
				{
					if(SameKind == null)
					{
						SameKind = new Array<KuASMObject>();
					}
					SameKind.add(O);
				} 
			}
		}
		return SameKind;
	}
	/**
	 * 
	 * @param Obj
	 */
	public void AddObject(KuASMObject Obj)
	{
		if(Obj == null)return; 
		if(Region == null)return;
		if(equals(Obj))return;
		this.Region.AddObjectToRegion(Obj);
	}
	/**
	 * 
	 * @param Instance
	 * @return
	 */
	public int InstanceCount(String Instance)
	{
		KuASMScene Scene =  Kid.Hands.CurrentScene; 
		Array<KuASMObject> SameKind = null;
		for(int j = 0; j<Scene.Depths.size; ++j){
			KuDepth Depth = Scene.Depths.get(j);
			if(Depth == null)continue;
			for(int i = 0; i<Depth.Objects.size; ++i)
			{ 
				KuASMObject O = Depth.Objects.get(i);
				if(O  == null)continue; 
				if(O .Destroyed)continue; 
				if(O.Kind != Instance)continue;
				
				if(SameKind == null)
				{
					SameKind = new Array<KuASMObject>();
				}
				SameKind.add(O);
			}
		}
		int Count = 0;
		if(SameKind != null)
		{
			Count =  SameKind.size;
		}
		return Count;
	}
	/**
	 * 
	 * @param Instance
	 * @return
	 */
	public KuASMObject DetectFirstInstance(String Instance)
	{
		for(int i=0; i<Region.Scene.GameObjects.size;++i)
		{
			KuASMObject Obj = Region.Scene.GameObjects.get(i);
			if(Obj.equals(this))continue;
			if(Obj.Kind.contains( Instance) || Obj.Kind == Instance)
			{
				return Obj;
			}
		}
		return null;
	}
	/**
	 * 
	 * @param Instance
	 * @return
	 */
	public Array<KuASMObject> DetectAllInstances(String Instance)
	{
		Array<KuASMObject> Objs = new Array<KuASMObject>();
		for(int i=0; i<Region.Scene.GameObjects.size;++i)
		{
			KuASMObject Obj = Region.Scene.GameObjects.get(i);
			if(Obj.equals(this))continue;
			if(Obj.Kind.contains( Instance) || Obj.Kind == Instance)
			{
				Objs.add(Obj);
			}
		}
		if(Objs.size == 0)
			return null;
		return Objs;
	}
	/**
	 * 
	 * @param Scene
	 */
	public void GotoScene(String Scene)
	{
		if(Region == null)return;
		Region.GotoScene(Scene);
	}
	/**
	 * 
	 */
	public void RegionEnter() {
		 
	}
	/**
	 * 
	 */
	public void RegionLeave() {
		 
	}
	/**
	 * 
	 * @param Alpha
	 */
	public void ChangeAlphaValue(float Alpha)
	{
		this.Alpha = Alpha;
	} 
	/**
	 * 
	 * @param Red
	 */
	public void ChangeRedValue(float Red)
	{
		this.Red = Red;
	} 
	/**
	 * 
	 * @param Green
	 */
	public void ChangeGreenValue(float Green)
	{
		this.Green = Green;
	} 
	/**
	 * 
	 * @param Blue
	 */
	public void ChangeBlueValue(float Blue)
	{
		this.Blue = Blue;
	} 
	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void ColorModification(float r, float g, float b, float a)
	{
		this.ChangeAlphaValue(a);
		this.ChangeBlueValue(b);
		this.ChangeGreenValue(g);
		this.ChangeRedValue(r);
	} 
	/**
	 * 
	 * @param x
	 * @return
	 */
	public float MoveX(float x)
	{
		X+=x;
		return X;
	}
	/**
	 * 
	 * @param y
	 * @return
	 */
	public float MoveY(float y)
	{
		Y+=y;
		return Y;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public float[] Move(float x, float y)
	{
		float[] d = new float[2];
		d[0] = MoveX(x);
		d[1] = MoveY(y);
		return d;
	}
	/**
	 * 
	 */
	public void StepOnPathStarts() {
		 
	}
	/**
	 * 
	 */
	public void StepOnPath() {
		 
	}
	/**
	 * 
	 */
	public void StepOnPathEnds() {
		 
	}
	/**
	 * 
	 */
	public void ReversedStepOnPathStarts() {
		 
	}
	/**
	 * 
	 */
	public void ReversedStepOnPath() {
		 
	}
	/**
	 * 
	 */
	public void ReversedStepOnPathEnds() {
		 
	}
	/**
	 * 
	 * @param Range
	 * @return
	 */
	public float Random(float Range)
	{
		return MathUtils.random(Range);
	}
	/**
	 * 
	 * @param Start
	 * @param End
	 * @return
	 */
	public float Random(float Start, float End)
	{
		return MathUtils.random(Start, End);
	} 
	/**
	 * 
	 */
	public void OutOfMargin(){}
	/**
	 * 
	 */
	public void RestartAnimation()
	{
		if(this.Animation != null)
		{
			this.Animation.Loop();
		}
	}
	/**
	 * 
	 * @param ImageIndex
	 */
	public void RestartAnimationAt(int ImageIndex)
	{
		if(this.Animation != null)
		{
			if(this.Animation.ImageIndex == ImageIndex)
			{
				RestartAnimation();
			}
		}
	}
	/**
	 * 
	 */
	public void ScenePaused() { }
	/**
	 * 
	 */
	public void SceneResumed() { }
	 
}
