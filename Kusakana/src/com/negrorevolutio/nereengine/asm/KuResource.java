package com.negrorevolutio.nereengine.asm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * 
 * @author yozefx
 *
 */
public class KuResource 
{	
	public Texture _Texture = null;
	public TextureRegion Region = null;
	public String Type = "texture";
	public Sound _Sound = null;
	public Music _Music = null;
	public String Name = "Undefined";
	public int Index;
	public boolean Disposed = false;
	public FileHandle File = null;
	/**
	 * 
	 */
	public KuResource(){ }
	/**
	 * 
	 * @param path
	 */
	public void init(String path){
		if(Type == "texture")
		{
			_Texture = new Texture(Gdx.files.internal(path));
			_Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			Region = new TextureRegion(_Texture);
			Region.flip(false, true); 
		}
		else if(Type == "sound")
		{
			_Sound  =  Gdx.audio.newSound(Gdx.files.internal(path));
		}
		else if(Type == "music")
		{
			_Music  =  Gdx.audio.newMusic(Gdx.files.internal(path));
		}
		else if(Type == "file")
		{
			File = Gdx.files.internal(path);
		}
	}
	/**
	 * 
	 * @param path
	 * @param L
	 * @param R
	 */
	public void init(String path, int L, int R){
		if(Type == "texture")
		{
			_Texture = new Texture(Gdx.files.internal(path));
			if(L == 0 && R == 0)
			_Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			else if(L == 0 && R == 1)
			_Texture.setFilter(TextureFilter.Linear, TextureFilter.Nearest);
			else if(L == 1 && R == 1)
			_Texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			else if(L == 1 && R == 0)
			_Texture.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
			Region = new TextureRegion(_Texture);
			Region.flip(false, true);
		}
		else if(Type == "sound")
		{
			_Sound  =  Gdx.audio.newSound(Gdx.files.internal(path));
		}
		else if(Type == "music")
		{
			_Music  =  Gdx.audio.newMusic(Gdx.files.internal(path));
		}
		else if(Type == "file")
		{
			File = Gdx.files.internal(path);
		}
	}
	/**
	 * 
	 */
	public void Dispose() {
		if(Type == "texture"){
			Region = null;
			if(_Texture != null)
				_Texture.dispose();
		}
		else if(Type == "sound"){
			if(_Sound == null)
				_Sound.dispose();
		}
		else if(Type == "music"){
			if(_Music==null)
				_Music.dispose();
		} 
		_Texture = null;
		_Music = null;
		_Sound = null; 
		File = null;
		Disposed = true;
	}
}
