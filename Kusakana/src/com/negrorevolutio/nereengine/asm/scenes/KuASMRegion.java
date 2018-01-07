package com.negrorevolutio.nereengine.asm.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KidBrain;
import com.negrorevolutio.nereengine.KidEar;
import com.negrorevolutio.nereengine.asm.KuIdea;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.paths.KuASMPath;
import com.negrorevolutio.nereengine.asm.paths.KuASMPathPoint;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMRegion extends KuIdea implements KidEar{

	public KuASMScene Scene = null;
	public Array<KuASMObject> RegionObjects;
	public boolean RegionEntered;
	public boolean RegionLeft;
	public Array<KuASMPath> Paths = null;
	/**
	 * 
	 * @param Scene
	 */
	public KuASMRegion(KuASMScene Scene)
	{
		super(Scene.Kid);
		this.Scene = Scene;
		this.RegionObjects = new Array<KuASMObject>();
		Name = Kind = "Region"; 
		Paths = new Array<KuASMPath>(); 
		this.Creation();
	} 
	/**
	 * 
	 */
	public void AfterCreation()
	{
		if(Paths != null && Paths.size != 0){
		for(KuASMPath Path: Paths)
			for(KuASMPathPoint PathPoint: Path.PathPoints){
				PathPoint.X += X;
				PathPoint.Y += Y;
			}
		}
	}
	/**
	 * 
	 * @param Path
	 */
	public void AddPath(KuASMPath Path){
		if(Path == null)return;
		if( Path.PathPoints.size == 0)return;
		for(KuASMPathPoint PathPoint: Path.PathPoints){
			PathPoint.X += X;
			PathPoint.Y += Y;
		}
		Paths.add(Path);
	}
	/**
	 * 
	 * @param Path
	 */
	public void RemovePath(KuASMPath Path){
		if(Path == null)return;
		Paths.removeValue(Path, false);
		Path.Dispose();
		Path = null;
	}
	/**
	 * 
	 * @param Obj
	 */
	public void AddObjectToRegion(KuASMObject Obj)
	{
		if(Obj == null)return;
		RegionObjects.removeValue(Obj, false);
		RegionObjects.add(Obj);
		Obj.ChangeRegion(this);
		this.Scene.AddObjectToScene(Obj);
	}
	/**
	 * 
	 * @param ObjName
	 * @return
	 */
	public KuASMObject GetObjectByName(String ObjName)
	{
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj.Name == ObjName)return Obj;
		}
		return null;
	}
	/**
	 * 
	 * @param ObjKind
	 */
	public Array<KuASMObject> GetObjectsKind(String ObjKind)
	{
		Array<KuASMObject> Objs = new Array<KuASMObject>();
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			 if(Obj.Kind == ObjKind)
			 {
				 Objs.add(Obj);
			 }
		}
		if(Objs.size == 0)Objs = null;
		return Objs;
	}
	/**
	 * 
	 * @param ObjKind
	 * @return
	 */
	public KuASMObject GetFirstInstanceOf(String ObjKind)
	{
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj.Kind == ObjKind)return Obj;
		}
		return null;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public KuASMObject GetObjectByIndex(int index)
	{
		if(index < 0 || index > RegionObjects.size)
		return null;
		
		return RegionObjects.get(index); 
	}
	/**
	 * 
	 */
	@Override
	public void Dispose() 
	{ 
		for(KuASMPath Path: Paths){
			Path.Dispose();
		}
		Paths.clear();
		Paths = null;
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.Dispose();
		}
		RegionObjects.clear();
		RegionObjects = null;
		Scene = null; 
		super.Dispose(); 
	}
	
	@Override
	public void Step()
	{ 
	}
	/**
	 * 
	 * @param Obj
	 */
	public void InstanceDestroyObject(KuASMObject Obj) {
		if(this.Scene == null)
			throw new NullPointerException("Scene cannot be null");
		if(Obj == null)return;
		 this.RegionObjects.removeValue(Obj, false);
		 this.Scene.InstanceDestroyObject(Obj);
	} 
	/**
	 * 
	 * @param Obj
	 * @param Kind
	 * @return
	 */
	protected abstract KuASMObject WhichObject(String Kind);
	/**
	 * 
	 * @param ObjKind
	 * @param x
	 * @param y
	 * @param depth
	 */
	public KuASMObject InstanceCreateObject(String ObjKind, float x, float y, int depth) 
	{		
		return this.InstanceCreateObject(ObjKind, ObjKind, x, y, depth);
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
	public KuASMObject InstanceCreateObject(String ObjKind, String ObjName, float x, float y, int depth) {
		KuASMObject Obj = WhichObject(ObjKind);
		Obj.X = x;
		Obj.Y = y;
		Obj.Zindex = depth;
		Obj.Name = ObjName;
		this.AddObjectToRegion(Obj);
		return Obj;
	}
	/**
	 * 
	 * @param ObjKind
	 * @return
	 */
	public KuASMObject InstanceCreateObject(String ObjKind)
	{
		KuASMObject Obj = WhichObject(ObjKind); 
		Obj.Name = Obj.Kind + "_" + StateTime;
		this.AddObjectToRegion(Obj);
		return Obj;
	}
	/**
	 * 
	 */ 
	@Override
	public void Update(float DeltaTime)
	{  
		this.DeltaTime = DeltaTime;
		this.StateTime += DeltaTime; 
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i); 
			Obj.Activated = this.Activated;  
		}
		if(!Activated)return;
		Step();  
		EndStep();  
	}
	/**
	 * 
	 */
	@Override
	public void SceneStart()
	{ 
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			Obj.SceneStart(); 
		}
	}
	/**
	 * 
	 */
	@Override
	public void SceneEnd()
	{ 
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			Obj.SceneEnd(); 
		}		
	}
	/**
	 * 
	 * @param Batch
	 */
	@Override
	public void Render(SpriteBatch Batch)
	{
		if(KidBrain.DebugPaper)
		{
			if(spr != null)
			spr.draw(Batch); 
		} 
	} 
	
	@Override
	public boolean keyDown(int keycode) {
		this.OnKeyDown(keycode);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.keyDown(keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		this.OnKeyUp(keycode);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.keyUp(keycode);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		this.OnKeyTyped(character);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.keyTyped(character);
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.OnTouchDown(screenX, screenY, pointer, button);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.OnTouchUp(screenX, screenY, pointer, button);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.OnTouchDragged(screenX, screenY, pointer);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.OnMouseMoved(screenX, screenY);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.mouseMoved(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.OnScrolled(amount);
		for(int i=0; i<RegionObjects.size; ++i)
		{
			KuASMObject Obj = RegionObjects.get(i);
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.scrolled(amount);
		}
		return false;
	}
	/**
	 * 
	 * @param obj
	 */
	public void SilentInstanceDestroyObject(KuASMObject Obj) {
		if(this.Scene == null)
			throw new NullPointerException("Scene cannot be null");
		if(Obj == null)return;
		 this.RegionObjects.removeValue(Obj, false);
		 this.Scene.SilentInstanceDestroyObject(Obj);
	}
	/**
	 * 
	 * @param Scene
	 */
	public void GotoScene(String Scene)
	{
		if(this.Scene == null)
			throw new NullPointerException("Scene cannot be null");
		this.Scene.GotoNextScene(Scene);
	}
	/**
	 * 
	 */
	public void RegionEnter() {
		for(KuASMObject Obj: RegionObjects)
		{
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			if(Obj.RegionEntered == false){
				Obj.RegionEnter();
				Obj.RegionEntered = true;
				Obj.RegionLeft = false;
			} 
		}
	}
	/**
	 * 
	 */
	public void RegionLeave() {
		for(KuASMObject Obj: RegionObjects)
		{
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			if(Obj.RegionLeft == false){
				Obj.RegionLeave();
				Obj.RegionLeft = true;
				Obj.RegionEntered = false;
			}			
		}
	}
	/**
	 * 
	 * @param Obj
	 */
	public void SendToBackground(KuASMObject Obj)
	{
		if(this.Scene == null)return;
		 this.Scene.AddObjectToBackground(Obj);
	}
	/**
	 * 
	 * @param Obj
	 */
	public void SendToForeground(KuASMObject Obj)
	{
		if(this.Scene == null)return;
		 this.Scene.AddObjectToForeground(Obj);
	}
	/**
	 * 
	 * @param Obj
	 * @param Index
	 */
	public void UnAutorizing(KuASMObject Obj, int Index)
	{
		if(Obj == null)return;
		if(Index <0)return;
		Array<KuASMObject> asmObj = this.Scene.UnAutorized.get(Index);
		asmObj.removeValue(Obj, false);
		asmObj.add(Obj);
	}
	/**
	 * 
	 * @param Obj
	 * @param Index
	 */
	public void UnAuthorizing(KuASMObject Obj, int Index)
	{
		if(Obj == null)return;
		if(Index <0)return;
		Array<KuASMObject> asmObj = this.Scene.UnAutorized.get(Index);
		asmObj.removeValue(Obj, false);
		asmObj.add(Obj);
	}
	/**
	 * 
	 * @param Obj
	 * @param Index
	 */
	public void ReAutorized(KuASMObject Obj, int Index){
		if(Obj == null)return;
		if(Index <0)return;
		Array<KuASMObject> asmObj = this.Scene.UnAutorized.get(Index);
		asmObj.removeValue(Obj, false);
	}
	/**
	 * 
	 * @param Index
	 * @return KuASMPath or null
	 */
	public KuASMPath GetPathByIndex(int Index)
	{
		if(Index >= 0 && Index < this.Paths.size)
		{
			return this.Paths.get(Index);
		}
		return null;
	}
	/**
	 * 
	 * @param PathName
	 * @return KuASMPath or null
	 */
	public KuASMPath GetPathByName(String PathName)
	{
		for(KuASMPath Path: Paths)
		{
			if(Path.Name == PathName)
			{
				return Path;
			}
		}
		return null;
	}
	/**
	 * 
	 */
	public void ScenePaused() 
	{
		for(KuASMObject Obj: RegionObjects)
		{
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.ScenePaused();
		}
	}
	/**
	 * 
	 */
	public void SceneResumed() 
	{
		for(KuASMObject Obj: RegionObjects)
		{
			if(Obj == null)continue;
			if(Obj.Destroyed)continue;
			Obj.SceneResumed();
		}
	}

}
