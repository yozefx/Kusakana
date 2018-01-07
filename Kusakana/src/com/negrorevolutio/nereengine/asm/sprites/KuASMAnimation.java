package com.negrorevolutio.nereengine.asm.sprites;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KuAsset;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;

public abstract class KuASMAnimation {

	public Array<KuASMSprite> Frames; 
	public KuASMSprite CurrentFrame; 
	public float[] WaitFrames;
	public int ImageIndex = 0;
	public int Count = 0;
	public enum AnimationType{
		NORMAL, LOOP, REVERSED, REVERSED_LOOP, PINGPONG, PINGPONG_LOOP
	}
	public AnimationType AnimType = AnimationType.LOOP;
	boolean normalDirection = true;

	public boolean Paused;
	public boolean Stopped;
	public boolean Destroyed;
	protected KuASMObject Parent;
	protected boolean StoppedOnFrame;
	private boolean Equalized;
	public KuAsset Asset;
	private boolean Once = true;
	/**
	 * 
	 * @param Parent
	 */
	public KuASMAnimation(KuASMObject Parent){
		Frames 			= new Array<KuASMSprite>();
		WaitFrames 		= null;
		CurrentFrame 	= null;
		Paused 			= false;
		Stopped 		= true;
		Destroyed 		= false;
		StoppedOnFrame 	= false;
		this.Parent 	= Parent;
		Asset			= this.Parent.Kid.Brain.Asset;  
	}
	/**
	 * 
	 * @param duration
	 */
	public void Equalize(float duration){
		if(!Equalized){
			for(KuASMSprite Frame: Frames){
				Frame.Wait = duration;
			}
			Equalized = true;
		}
	}
	
	/**
	 * Freeze frame at specified index
	 * @param index
	 */
	public void FreezeFrame(int index){
		if(index < 0 && index >= Frames.size)return;
		if(!StoppedOnFrame){
			for(KuASMSprite Frame: Frames){
				Frame.Wait = -1.0f;
			}
			StoppedOnFrame = true;
		}
		Frames.get(index).Wait = WaitFrames[index];
	}
	/**
	 * Release all freezes frames
	 */
	public void ReleaseFrames(){
		StoppedOnFrame = false;
		Equalized = false;
		for(int i = 0; i<Frames.size; ++i){
			Frames.get(i).Wait = WaitFrames[i];
		}
	}	
	/**
	 * 
	 * @param Speed
	 */
	public void IncreaseSpeed(float Speed){
		for(int i = 0; i<Frames.size; ++i){
			if( Frames.get(i).Wait >= 0.010000015f)
			Frames.get(i).Wait -= Speed; 
		} 
	}
	/**
	 * 
	 * @param Speed
	 */
	public void DecreaseSpeed(float Speed){
		for(int i = 0; i<Frames.size; ++i){
			Frames.get(i).Wait += Speed;
		}
	}
	/**
	 * Creation
	 */
	public abstract void Creation();
	/**
	 * Play
	 * @param delta
	 */
	public void Play(float DeltaTime){
		Stopped = false; 
		if(AnimType == AnimationType.LOOP || AnimType == AnimationType.NORMAL){
			normalDirection = true;
		}
		else if(AnimType == AnimationType.REVERSED_LOOP || AnimType == AnimationType.REVERSED){
			normalDirection = false;
		} 
		
		if(((AnimType == AnimationType.LOOP || AnimType == AnimationType.NORMAL) && normalDirection)
			|| 
			((AnimType == AnimationType.PINGPONG || AnimType == AnimationType.PINGPONG_LOOP)&& normalDirection)){
			for(ImageIndex = 0; ImageIndex<Frames.size; ++ImageIndex)
			{
				KuASMSprite Frame = Frames.get(ImageIndex);
				if(Paused || Frame == null)continue;
				if(Frame.Update(DeltaTime)){
					CurrentFrame = Frame;  
					return;
				}
			}
		}
		else if(((AnimType == AnimationType.REVERSED_LOOP || AnimType == AnimationType.REVERSED) && !normalDirection)
				|| 
				((AnimType == AnimationType.PINGPONG || AnimType == AnimationType.PINGPONG_LOOP)&& !normalDirection)){
			for(ImageIndex = Frames.size-1; ImageIndex>=0; --ImageIndex)
			{
				KuASMSprite Frame = Frames.get(ImageIndex);
				if(Paused || Frame == null)continue;
				if(Frame.Update(DeltaTime)){
					CurrentFrame = Frame;  
					return;
				}
			}
		}
		
		this.Parent.AnimationEnd();
		
		if((AnimType == AnimationType.PINGPONG || AnimType == AnimationType.PINGPONG_LOOP)){
			this.normalDirection = !normalDirection; 
			if(AnimType == AnimationType.PINGPONG_LOOP)
			{
				Loop();
			}
			else if(AnimType == AnimationType.PINGPONG)
			{
				if(this.Once)
				{
					Loop();
					this.Once = false;
				}
				else
				Pause();
			}
			return;
		}
		
		if(AnimType == AnimationType.REVERSED_LOOP 
				|| AnimType == AnimationType.LOOP ){
			Loop();
		}
		else if(AnimType == AnimationType.NORMAL 
				|| AnimType == AnimationType.REVERSED ){
			Pause();
		}
	}
	/**
	 * Pause
	 */
	public void Pause(){
		Paused = true;
	}
	/**
	 * Resume
	 */
	public void Resume(){
		Paused = true;
	}
	/**
	 * Loop 
	 */
	public void Loop( ){
		if(Frames == null)return;
		for(KuASMSprite Frame: Frames){
			if(Frame == null)continue;
			Frame.StateTime = 0.0f;
		} 
	} 
	/**
	 * Stop
	 */
	public void Stop(){
		Stopped = true;
	}
	/**
	 * Destroy
	 */
	public void Destroy(){
		Stopped = true;
		Destroyed = true;
	}	
	/**
	 * 
	 */
	public void Dispose(){ 
		this.CurrentFrame = null;
		for(KuASMSprite Frame: Frames){
			Frame.Dispose(); 
		}
		Frames.clear();
		Frames 		= null;
		WaitFrames 	= null;
		Parent		= null; 
	}
}
