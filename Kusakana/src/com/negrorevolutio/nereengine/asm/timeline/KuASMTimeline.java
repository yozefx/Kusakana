package com.negrorevolutio.nereengine.asm.timeline;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KuKid;
import com.negrorevolutio.nereengine.asm.KuIdea;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMTimeline extends KuIdea
{ 
	public boolean Looping = true;	 
	public int FramesMax 	= 100;
	public int FramesMin 	= 0;
	public int CurrentFrame = 0;
	public enum METHOD
	{
		ADD, REPLACE
	}
	public METHOD Method = METHOD.REPLACE; 
	public KuASMObject ObjParent;
	public boolean Playing = false;
	public Array<KuASMObject> Keyframes = null;
	/**
	 * 
	 * @param Kid
	 */
	public KuASMTimeline(KuKid Kid) {
		super(Kid); 
	}
	/**
	 * 
	 * @param ObjParent
	 */
	public KuASMTimeline(KuASMObject ObjParent) 
	{
		super(ObjParent.Kid); 
		this.ObjParent = ObjParent;  
		Keyframes = new Array<KuASMObject>();
	}
	
	/**
	 * 
	 */
	public void Play()
	{
		Playing = true;
		CurrentFrame = FramesMin;
	}
	/**
	 * 
	 */
	public void Stop()
	{
		Playing = false;
		CurrentFrame = FramesMin;
	}
	/**
	 * 
	 */
	public void Pause()
	{
		Playing = false;
	} 
	
	public void Step()
	{
		if(!Playing)return;
		if(this.CurrentFrame <= FramesMax)
		{
			++CurrentFrame;
		}
		else
		{
			if(Looping)
			CurrentFrame = FramesMin;
		}
		for(KuASMObject KeyFrame: Keyframes)
		{
			if(KeyFrame.Id == CurrentFrame)
			{
				KeyFrame.Step(); 
			}
		} 
	}
	/**
	 * 
	 * @param ObjState
	 * @param At
	 */
	public void AddKeyFrame(KuASMObject ObjState)
	{
		this.Keyframes.add(ObjState); 
	}
	
}
