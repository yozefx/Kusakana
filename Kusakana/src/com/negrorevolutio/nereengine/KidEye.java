package com.negrorevolutio.nereengine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
import com.negrorevolutio.nereengine.asm.scenes.KuASMScene;
/**
 * 
 * @author yozefx
 *
 */
public class KidEye implements KidPart{
	public KuASMScene CurrentScene = null;

	public OrthographicCamera Perception;
	public OrthographicCamera Background;
	public OrthographicCamera Foreground;	
	public float ViewportWidth = 800, 
				ViewportHeight = 480;
	public KuASMObject Target;	
	public float LeftBoundary = 0;
	public float RightBoundary = 800;
	public float TopBoundary = 0;
	public float BottomBoundary = 480;
	public Rectangle Field; 
	public Pixmap pix;
	public Texture tex;
	public Sprite spr;
	public float MarginX = 0, 
				MarginY = 0;
	public float MarginWidth = 0, 
				MarginHeight = 0;
	public boolean BoundariesLess = false;

	public static boolean UsingSceneRulers = false;
	public KuKid Kid = null;

	public boolean WithDelay = false;
	public float Speed = 1;
	public float PreviousX, PreviousY;
	/**
	 * 
	 */
	public KidEye(KuKid Kid)
	{
		this.Kid = Kid;
		this.Blink(ViewportWidth, ViewportHeight);
		Target = null;
		Field = new Rectangle();
	}
	/**
	 * 
	 * @param ViewportWidth
	 * @param ViewortHeight
	 */
	public void Blink(float ViewportWidth, float ViewortHeight)
	{
		this.ViewportWidth		= Math.abs(ViewportWidth); 
		this.ViewportHeight		= Math.abs(ViewortHeight);
		Perception 				= new OrthographicCamera(this.ViewportWidth, this.ViewportHeight);
		Perception.position.set(0, 0, 0);
		Perception.setToOrtho(true, this.ViewportWidth, this.ViewportHeight);
		Background 				= new OrthographicCamera(this.ViewportWidth, this.ViewportHeight);
		Background.position.set(0, 0, 0);
		Background.setToOrtho(true, this.ViewportWidth, this.ViewportHeight);
		Foreground 				= new OrthographicCamera(this.ViewportWidth, this.ViewportHeight);
		Foreground.position.set(0, 0, 0);
		Foreground.setToOrtho(true, this.ViewportWidth, this.ViewportHeight);
		Perception.position.x 	=  ViewportWidth/2; 
		Perception.position.y 	=  ViewportHeight/2;
		Foreground.position.x 	=  ViewportWidth/2; 
		Foreground.position.y 	=  ViewportHeight/2;
		Background.position.x 	=  ViewportWidth/2; 
		Background.position.y 	=  ViewportHeight/2;
		Perception.update(); 
		Background.update(); 
		Foreground.update(); 
		PreviousX 				=  ViewportWidth/2;  
		PreviousY 				=  ViewportHeight/2;  
	}
	/**
	 * 
	 */
	public void SetAppearance(){
		if(KidBrain.DebugPaper){
			if(pix != null)pix.dispose();
			if(tex != null)tex.dispose();
			pix = new Pixmap(2, 2, Format.RGBA8888);
			//pix.setColor(1, 1, 0, 0.25f);
			//pix.drawRectangle(0, 0, 2, 2);
			pix.setColor(1, 1, 0, 0.25f);
			pix.fillRectangle(0, 0, 2, 2);
			tex = new Texture(pix);
			spr = new Sprite(tex);
			spr.setOrigin(ViewportWidth/2, ViewportHeight/2);
			spr.setSize(ViewportWidth - MarginX, ViewportHeight - MarginY);
			spr.setScale(1, 1);
			spr.setPosition(Perception.position.x + (MarginX/2), Perception.position.y + (MarginY/2));
		}
	}
	
	@Override
	public void Sleep() {
		CurrentScene = null;
		Perception = null;
		Target = null;
		if(KidBrain.DebugPaper)
		{
			if(pix != null)pix.dispose();
			if(tex != null)tex.dispose();
			spr = null;
		}  
	}
	/**
	 * 
	 * @param Width
	 * @param Height
	 */
	public void Reframe(int Width, int Height)
	{ 
		Field.set(0, 0, Width, Height);
		if(spr != null)
		spr.setSize( Width, Height);
	}
	
	/**
	 * 
	 */
	public void Update() {
		 Kid.Brain.Mind.setProjectionMatrix(Perception.combined);
		 Perception.update();
		 if(UsingSceneRulers)
		 { 
			 if(CurrentScene != null)
			 {
				 LeftBoundary = CurrentScene.LeftBorder;
				 RightBoundary = CurrentScene.RightBorder;
				 TopBoundary = CurrentScene.TopBorder;
				 BottomBoundary = CurrentScene.BottomBorder;
			 } 
		 }	
		 if(Target != null)
		 {   
			 if(this.WithDelay)
			 {
				 
			 }
			 else
			 {
				 Perception.position.x = Target.X + Target.OriginX; 
				 Perception.position.y = Target.Y + Target.OriginY;  
			 }
			 if(Perception.position.x - (ViewportWidth/2) < LeftBoundary)
			 {
				 Perception.position.x = ViewportWidth/2 + MarginX; 
			 }
			 if(Perception.position.y - (ViewportHeight/2) < TopBoundary)
			 {
				 Perception.position.y = ViewportHeight/2 + MarginY; 
			 }
			 if(Perception.position.x + (ViewportWidth/2) > RightBoundary)
			 {
				 Perception.position.x = RightBoundary - (ViewportWidth/2) + MarginX; 
			 }
			 if(Perception.position.y + (ViewportHeight/2) > BottomBoundary)
			 {
				 Perception.position.y = BottomBoundary - (ViewportHeight/2) + MarginY;  
			 } 
		 } 
		 
		 Field.x = Perception.position.x - ViewportWidth/2; 
		 Field.y = Perception.position.y - ViewportHeight/2;
		 
		 if(KidBrain.DebugPaper)
		 {  
			 if(spr != null)
					spr.setPosition(Field.x, Field.y);
		 }
		 Rake();
	}
	/**
	 * 
	 */
	public void Rake() {
		if(CurrentScene == null)return;  	
		for(KuASMRegion Region: CurrentScene.RegionMap){ 
			Rectangle Bounds = new Rectangle(Region.X, Region.Y, Region.Width, Region.Height);
			if(Field.overlaps(Bounds) || Field.contains(Bounds)){
				Region.Activated = true;
				if(Region.RegionEntered == false){
					Region.RegionEnter();
					CurrentScene.SetCurrentRegion(Region);
					Region.RegionEntered = true;
					Region.RegionLeft = false;
				}
			}
			else{
				Region.Activated = false; 
				if(Region.RegionLeft == false){
					Region.RegionLeave();
					Region.RegionEntered = false;
					Region.RegionLeft = true;
					CurrentScene.CurrentRegions.removeValue(Region, false);
				} 
			}
		}
	}
	/**
	 * 
	 * @param Batch
	 */
	public void Render(SpriteBatch Batch)
	{
		if(KidBrain.DebugPaper){ 
			if(spr != null)
			spr.draw(Batch);
		 }
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	public void FollowTowardsObjectAxis(float x, float y, float speed) {
		if (speed == 0) return ;
		if (Perception.position.x == x && Perception.position.y == y) return ;  
		float _dx = x - Perception.position.x,
			_dy = y - Perception.position.y,
			_dist = _dx * _dx + _dy * _dy;
		if (!(_dist < speed * speed)) { 
			_dist = (float)Math.sqrt(_dist);
			Perception.position.x += (int)(_dx * speed / _dist);
			Perception.position.y += (int)(_dy * speed / _dist);
			Perception.position.x = (int)Perception.position.x;
			Perception.position.y = (int)Perception.position.y;
		}			
	}
}
