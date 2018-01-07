package com.negrorevolutio.nereengine;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.KuResource;
/**
 * 
 * @author yozefx
 *
 */
public class KidMouth implements KidPart{

	public Array<KuResource> Playlist;
	public float Volume;
	KuKid Kid;
	/**
	 * 
	 */
	public KidMouth(KuKid Kid)
	{ 
		this.Kid = Kid;
		Playlist = new Array<KuResource>();
	}
	/**
	 * 
	 * @param Index
	 * @param Volume
	 */
	public void Volume(int Index, float Volume){
		this.Volume = Volume;
		if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.setVolume(Volume);
		} 
	}
	/**
	 * 
	 * @param Index
	 * @param isLooping
	 */
	public void Looping(int Index, boolean isLooping){
		if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.setLooping(isLooping);
		}
	}
	/**
	 * 
	 * @param Index
	 * @return
	 */
	public boolean isLooping(int Index){
		if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.isLooping();
			return Playlist.get(Index)._Music.isLooping();
		}
		return false;
	}
	/**
	 * 
	 * @param Index
	 * @return
	 */
	public boolean isPlaying(int Index){
		if(Playlist.get(Index).Type == "music"){			
			return Playlist.get(Index)._Music.isPlaying();
		}
		return false;
	}
	/**
	 * 
	 * @param index
	 */
	public void Pause(int index){
		if(Playlist.get(index).Type == "music"){
			if(this.isPlaying(index))Playlist.get(index)._Music.pause();
		}
	}
	/**
	 * 
	 * @param Resource
	 */
	public void Load(KuResource Resource){
		Playlist.add(Resource);
	}
	/**
	 * 
	 * @param Index
	 */
	public void Stop(int Index){
		if(Playlist.get(Index).Type == "sound"){
			Playlist.get(Index)._Sound.stop();
		}
		else if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.stop();
		}
	}
	/**
	 * 
	 * @param index
	 * @param Volume
	 * @param Pitch
	 * @param Pan
	 */
	public void Play(int Index, float Volume, float Pitch, float Pan){
		if(Playlist.get(Index).Type == "sound"){
			Playlist.get(Index)._Sound.play(Volume, Pitch, Pan);
		}
		else if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.play();
		}
	}
	/**
	 * 
	 * @param Index
	 * @param Volume
	 * @param Pitch
	 */
	public void Play(int Index, float Volume, float Pitch){
		if(Playlist.get(Index).Type == "sound"){
			Playlist.get(Index)._Sound.play(Volume, Pitch, 0);
		}
		else if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.play();
		}
	}
	/**
	 * 
	 * @param Index
	 * @param Volume
	 */
	public void Play(int Index, float Volume){
		if(Playlist.get(Index).Type == "sound"){
			Playlist.get(Index)._Sound.play(Volume, 1, 0);
		}
		else if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.play();
		}
	}
	/**
	 * 
	 * @param Index
	 */
	public void Play(int Index){
		if(Playlist.get(Index).Type == "sound"){
			Playlist.get(Index)._Sound.play();
		}
		else if(Playlist.get(Index).Type == "music"){
			Playlist.get(Index)._Music.play();
		}
	}
	@Override
	public void Sleep() {
		// TODO Auto-generated method stub
		
	}

}
