package com.negrorevolutio.nereengine;

import com.badlogic.gdx.Game;
import com.negrorevolutio.nereengine.asm.scenes.KuASMScene;
/**
 * 
 * @author yozefx
 *
 */
public abstract class Kusakana extends Game implements KuLand{

	protected KuAsset Asset = null; 
	public KuASMScene MainScene = null;
	
	public KuKid Kid;
	
	@Override
	public void create() 
	{
		Kid = new KuKid();
		PrepareKusakana();
		StartKusakana();
	}
	/**
	 * 
	 */
	public void PrepareKusakana()
	{ 
		Assets();
		Misc();
		Eureka(); 
		InitMainScene();
		if(MainScene != null)
		{
			MainScene.ReinitDepth(MainScene.Depthi);
		}
	}
	/**
	 * 
	 */
	private void Eureka()
	{ 
		if(Asset == null)
			return;
		 Kid.Eureka(Asset);   
	}
	
	@Override
	public void dispose()
	{
		if( Kid.Hands.LastScene != null)
		{
			 Kid.Hands.LastScene.dispose();
			 Kid.Hands.LastScene = null;
		}
		if(this.getScreen() != null)
			this.getScreen().dispose(); 
		Kid.Sleep();
		if(Asset != null)
			Asset.Dispose();
		Asset = null;
		MainScene = null;
		Kid = null;
	}
	/**
	 * 
	 */
	public abstract void InitMainScene();
	/**
	 * 
	 */
	public abstract void Assets();
	/**
	 * 
	 */
	@Override
	public abstract void Misc();
	/**
	 * 
	 */
	@Override
	public void StartKusakana()
	{
		if(MainScene == null)return;
		this.setScreen(MainScene);
	}
	 
}
