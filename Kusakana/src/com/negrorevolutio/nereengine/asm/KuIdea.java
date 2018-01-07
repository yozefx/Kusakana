package com.negrorevolutio.nereengine.asm;

import com.badlogic.gdx.Gdx;
import com.negrorevolutio.nereengine.KidEar;
import com.negrorevolutio.nereengine.KuKid;
import com.negrorevolutio.nereengine.KuPaper;
/**
 * 
 * @author yozefx
 *
 */
public class KuIdea extends KuPaper implements KidEar{

	public String Name;
	public KuKid Kid;
	public boolean Destroyed;
	public boolean Activated;
	public String Kind = "Undefined";
	private int WasPressed;
	public float StateTime;
	public float DeltaTime;
	/**
	 * 
	 */
	public KuIdea(KuKid Kid){
		super();
		Name = "Undefined";
		this.Kid = Kid;
		PreCreation();
		Destroyed = false;
		Activated = true;
	}
	/**
	 * @param DeltaTime
	 */
	public void Update(float DeltaTime)
	{
		super.Update(DeltaTime);
		this.DeltaTime = DeltaTime;
		this.StateTime += DeltaTime;
		Step();
		EndStep();
	}
	/**
	 * 
	 */
	public void Creation()
	{
		
	}
	/**
	 * 
	 */
	public void PreCreation()
	{
		
	}
	/**
	 * 
	 */
	public void Destroy()
	{
		
	}
	/**
	 * 
	 */
	public void Step()
	{
		
	}
	/**
	 * 
	 */
	public void EndStep()
	{
		
	}
	/**
	 * 
	 */
	public void SceneStart()
	{
		
	}
	/**
	 * 
	 */
	public void SceneEnd()
	{
		
	}
	/**
	 * 
	 */
	public void AnimationEnd() 
	{
		 
	}
	/**
	 * 
	 */
	public void Collision()
	{
		
	}
	/**
	 * 
	 */
	public void Draw()
	{
		
	}
	/**
	 * 
	 */
	public void Dispose()
	{
		this.TearUp();
		Kid = null;
		Destroyed = true;
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
	/**
	 * 
	 * @param Key
	 * @return
	 */
	public boolean KeyboardCheck(int Key){ 
		return Gdx.input.isKeyPressed(Key);
	}
	/**
	 * 
	 * @param Key
	 * @return
	 */
	public boolean KeyboardCheckPressed(int Key){
		return Gdx.input.isKeyJustPressed(Key);
	}
	/**
	 * 
	 * @param Key
	 * @return
	 */
	public boolean KeyboardCheckReleased(int Key){
		boolean k = Key == this.getWasPressed();
		setWasPressed(-1); 
		return k;
	}

	@Override
	public boolean keyDown(int keycode) {
		this.OnKeyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		setWasPressed(keycode);
		this.OnKeyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) { 
		this.OnKeyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.OnTouchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.OnTouchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.OnTouchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.OnMouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.OnScrolled(amount);
		return false;
	}

	@Override
	public void Sleep() {
		 
	}
	/**
	 * @return the wasPressed
	 */
	public int getWasPressed() {
		return WasPressed;
	}
	/**
	 * @param wasPressed the wasPressed to set
	 */
	public void setWasPressed(int wasPressed) {
		WasPressed = wasPressed;
	}
	
}
