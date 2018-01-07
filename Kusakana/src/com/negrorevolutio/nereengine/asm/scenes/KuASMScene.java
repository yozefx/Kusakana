package com.negrorevolutio.nereengine.asm.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KidEar;
import com.negrorevolutio.nereengine.KuKid;
import com.negrorevolutio.nereengine.Kusakana;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMScene implements Screen, KidEar{

	public Array<KuASMRegion> RegionMap;
	public Array<KuASMRegion> CurrentRegions;
	public Array<KuASMObject> GameObjects;
	public Array<KuDepth> Depths;
	public Array<KuASMObject> BackgroundDepths;
	public Array<KuASMObject> ForegroundDepths;
	protected Kusakana Game;
	public float Red = 0, Green = 1, Blue = 0;
	public String Name;
	public int Depthi =	2;
	public boolean Conversion = false; 
	public float LeftBorder;
	public float RightBorder;
	public float TopBorder;
	public float BottomBorder;
	public float DeltaTime;	 
	public boolean Paused = false;
	public KuKid Kid;
	public Array<Array<KuASMObject>> UnAutorized; 
	public KuASMScene DeprecatedScene = null;
	/**
	 * 
	 */
	public KuASMScene(Kusakana Game)
	{
		this.Game = Game;
		this.Kid = this.Game.Kid;
		RegionMap = new Array<KuASMRegion>();
		CurrentRegions = new Array<KuASMRegion>();
		GameObjects = new Array<KuASMObject>();
		Depths = new Array<KuDepth>();
		BackgroundDepths = new Array<KuASMObject>();
		ForegroundDepths = new Array<KuASMObject>();
		this.UnAutorized = new Array<Array<KuASMObject>>();
		this.Name = "Undefined";
		InitDepth();
		 Kid.Eyes.CurrentScene = this;
		 Kid.Brain.CurrentScene = this;
		 Kid.Hands.CurrentScene = this;
		Gdx.input.setInputProcessor(this); 
		Assets(); 
	}
	/**
	 * 
	 */
	public void UseDefault()
	{
		this.LeftBorder = 0;
		this.RightBorder = 800;
		this.TopBorder = 0;
		this.BottomBorder = 480;
	} 
	/**
	 * 
	 * @param Region
	 */
	public void SetCurrentRegion(KuASMRegion Region){ 
		if(Region != null){
			 CurrentRegions.removeValue(Region, false);
			 CurrentRegions.add(Region); 
		}
	} 
	/**
	 * 
	 */
	public void Dispose()
	{  
		if(UnAutorized != null){
			for(Array<KuASMObject> asmObj: UnAutorized){
				asmObj.clear();
			}
			UnAutorized.clear(); 
		}
		
		if(BackgroundDepths != null)
		{
			for(KuASMObject Depth: BackgroundDepths){
				Depth.Dispose();
			}
			BackgroundDepths.clear();
		} 
		
		if(Depths != null)
		{
			for(KuDepth Depth: Depths){
				Depth.Dispose();
			}
			Depths.clear();
		} 
		
		if(ForegroundDepths != null)
		{
			for(KuASMObject Depth: ForegroundDepths){
				Depth.Dispose();
			}
			ForegroundDepths.clear();
		}
		 
		if(CurrentRegions != null)
		{
			CurrentRegions.clear();
			CurrentRegions = null;
		}
		
		if(RegionMap != null)
		{
			for(int i = 0; i<RegionMap.size; ++i)
			{
				KuASMRegion Region = RegionMap.get(i);
				Region.Dispose();
			}
			RegionMap.clear();
		}
		 
		Game = null; 
	}
	/**
	 * 
	 * @param Region
	 */
	public void AddRegionToScene(KuASMRegion Region)
	{
		if(Region == null)
			return;
		RegionMap.add(Region);
	}
	/**
	 * 
	 * @param RegionName
	 * @return
	 */
	public KuASMRegion GetRegionByName(String RegionName)
	{
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region.Name == RegionName)return Region;
		}
		return null;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public KuASMRegion GetRegionByIndex(int index)
	{
		if(index < 0 || index > RegionMap.size)
		return null;
		
		return RegionMap.get(index); 
	}
	/**
	 * 
	 * @param Obj
	 */
	public void AddObjectToScene(KuASMObject Obj)
	{
		if(Obj == null)
			return;
		GameObjects.removeValue(Obj, false);
		GameObjects.add(Obj);
	}
	/**
	 * 
	 * @param Obj
	 */
	public void AddObjectToBackground(KuASMObject Obj)
	{
		if(Obj == null)
			return;
		if(Obj.Destroyed)return;
		Obj.Activated = true;
		Obj.ChangeRegion(this);
		this.BackgroundDepths.add(Obj);
	} 
	/**
	 * 
	 * @param Obj
	 */
	public void AddObjectToForeground(KuASMObject Obj)
	{
		if(Obj == null)
			return;
		if(Obj.Destroyed)return;
		Obj.Activated = true;
		Obj.ChangeRegion(this);
		this.ForegroundDepths.add(Obj);
	}
	/**
	 * 
	 * @param ObjName
	 * @return
	 */
	public KuASMObject GetObjectByName(String ObjName)
	{
		for(KuASMObject Obj: GameObjects){
			if(Obj.Name == ObjName)return Obj;
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
		if(index < 0 || index > GameObjects.size)
		return null;
		
		return GameObjects.get(index); 
	}
	/**
	 * 
	 */
	 void InitDepth()
	 {
		if(Depthi <= 0)Depthi = 1;
		for(KuDepth Depth: Depths)
		{
			Depth.Dispose();
		}
		Depths.clear();
		for(int i = 0; i<Depthi; ++i)
		{
			KuDepth Depth = new KuDepth();
			Depth.index = i;
			Depths.add(Depth); 
		}
	}
	 /**
	  * 
	  * @param Depthi
	  */
	 public void ReinitDepth(int Depthi)
	 {
		  this.Depthi = Depthi;
		  this.InitDepth();
	 }
	  /**
	   * 
	   */
	 public void UpdateUnAuthorized(){
		 for(Array<KuASMObject> asmObj: this.UnAutorized){
			 for(int i = 1; i<asmObj.size; ++i){
					KuASMObject Obj = asmObj.get(i); 
					this.SilentInstanceDestroyObject(Obj);
			 } 
			 asmObj.clear();
		 } 
	 }
	 /**
	  * 
	  * @param Amount
	  */
	 public void DeclareUnAuthorizedAmount(int Amount){
		for(int i = 0; i<Amount; ++i){
			Array<KuASMObject> asmObj = new Array<KuASMObject>();
			UnAutorized.add(asmObj);
		}
	 }

	@Override
	public void show() {
		 Kid.Eyes.Update();
		OnSceneStart();
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			 if(Region.Activated)
			 Region.SceneStart();
		 }
	}

	@Override
	public void render(float delta) {
		if(this.DeprecatedScene != null)
		{ 
			DeprecatedScene.Dispose();
			DeprecatedScene = null;
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(Red, Green, Blue, 1);
		this.DeltaTime = delta;
		if(!Paused){
			 Kid.Brain.RenderBackground();
		     Kid.Eyes.Update(); 
			 Kid.Hands.Update(delta);
			 Kid.Hands.UpdateDepth(); 
			 Kid.Brain.Render(); 
			 Kid.Brain.RenderForeground(); 
		}
	}
	/**
	 * 
	 * @param SceneToGo
	 */
	public void SceneSwitchTo(KuASMScene SceneToGo) {
		if(SceneToGo == null)return;
		OnSceneEnd();
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;
			if(Region.Activated)
			Region.SceneEnd();
		}
		if( Kid.Hands.LastScene != null)
		{
			 Kid.Hands.LastScene.dispose();
			 Kid.Hands.LastScene = null;
		}
		 Kid.Hands.LastScene = this;
		this.Game.setScreen(SceneToGo);
	}
	/**
	 * 
	 * @param Scene
	 */
	public abstract void GotoNextScene(String Scene);
	/**
	 * 
	 */
	public void OnSceneStart()
	{
		
	}
	/**
	 * 
	 */
	public void OnSceneEnd()
	{
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		Paused = true;
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			 if(Region.Activated)
			 Region.ScenePaused();
		 }
	}

	@Override
	public void resume() {
		Paused = false; 
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			 if(Region.Activated)
			 Region.SceneResumed();
		 }
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	public abstract void Assets();
	public abstract void DisposeAssets();

	@Override
	public void dispose() {
		Dispose();
		DisposeAssets();
	}
	@Override
	public void Sleep() {
		 
	}
	public void OnKeyDown(int keycode)
	{
		
	}
	public void OnKeyUp(int keycode)
	{
		
	}
	public void OnKeyTyped(char character)
	{
		
	}
	public void OnTouchDown(int screenX, int screenY, int pointer, int button)
	{
		
	}
	public void OnTouchUp(int screenX, int screenY, int pointer, int button)
	{
		
	}
	public void OnTouchDragged(int screenX, int screenY, int pointer)
	{
		
	}
	public void OnMouseMoved(int screenX, int screenY)
	{
		
	}
	public void OnScrolled(int amount)
	{
		
	}
	@Override
	public boolean keyDown(int keycode) {
		this.OnKeyDown(keycode);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.keyDown(keycode);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue; 
			if(Region.Activated)
			Region.keyDown(keycode);
		}
		for(KuASMObject Obj:  BackgroundDepths)
		{
			 Obj.keyDown(keycode);
		}
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		OnKeyUp(keycode);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.keyUp(keycode);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;
			if(Region.Activated)
			Region.keyUp(keycode);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.keyUp(keycode);
		}
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		this.OnKeyTyped(character);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.keyTyped(character);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue; 
			if(Region.Activated)
			Region.keyTyped(character);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.keyTyped(character);
		}
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		 Kid.Hands.GetCoordinates(screenX, screenY);
		OnTouchDown((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer, button);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.touchDown(screenX, screenY, pointer, button);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;
			if(Region.Activated)
			Region.touchDown((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer, button);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		 Kid.Hands.GetCoordinates(screenX, screenY);
		OnTouchUp((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer, button);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.touchUp(screenX, screenY, pointer, button);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue; 
			if(Region.Activated)
			Region.touchUp((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer, button);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		 Kid.Hands.GetCoordinates(screenX, screenY);
		OnTouchDragged((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.touchDragged(screenX, screenY, pointer);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;
			if(Region.Activated)
			Region.touchDragged((int) Kid.Hands.Fingers.x, (int) Kid.Hands.Fingers.y, pointer);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.OnMouseMoved(screenX, screenY);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.mouseMoved(screenX, screenY);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;  
			if(Region.Activated)
			Region.mouseMoved(screenX, screenY);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.mouseMoved(screenX, screenY);
		}
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		OnScrolled(amount);
		for(KuASMObject Obj: ForegroundDepths)
		{
			 Obj.scrolled(amount);
		}
		for(int i = 0; i<RegionMap.size; ++i)
		{
			KuASMRegion Region = RegionMap.get(i);
			if(Region == null)continue;
			if(Region.Activated)
			Region.scrolled(amount);
		}
		for(KuASMObject Obj: BackgroundDepths)
		{
			 Obj.scrolled(amount);
		}
		return false;
	}
	/**
	 * 
	 * @param Obj
	 */
	public void InstanceDestroyObject(KuASMObject Obj) {
		if(Obj == null)return;
		Kid.Brain.DestroyAfter(Obj);
	}
	/**
	 * 
	 * @param Obj
	 */
	public void SilentInstanceDestroyObject(KuASMObject Obj) {
		if(Obj == null)return;
		Kid.Brain.SilentDestroyAfter(Obj);
	}
	
}
