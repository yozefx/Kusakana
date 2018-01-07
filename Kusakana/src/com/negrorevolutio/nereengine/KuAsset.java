package com.negrorevolutio.nereengine;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.KuResource;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuAsset { 
	
	protected Array<KuResource> Resources;  	
	/**
	 * 
	 */
	public KuAsset(){
		Resources = new Array<KuResource>(); 
	} 
	/**
	 * 
	 * @param Resource
	 * @param Type
	 * @param Name
	 * @param Path
	 * @return
	 */
	public KuResource InstanceCreate(KuResource Resource, String Type, String Name, String Path){
		if(Resource != null){
			Resource.Name = Name;
			Resource.Type = Type;
			Resource.init(Path);
			Resource.Index = Resources.size;
			Resources.add(Resource); 
		}
		return Resource;
	}
	/**
	 * 
	 * @param Resource
	 * @param Type
	 * @param Name
	 * @param Path
	 * @param L
	 * @param R
	 * @return
	 */
	public KuResource InstanceCreate(KuResource Resource, String Type, String Name, String Path, int L, int R){
		if(Resource != null){
			Resource.Name = Name;
			Resource.Type = Type;
			Resource.init(Path, L, R);
			Resource.Index = Resources.size;
			Resources.add(Resource);
		}
		return Resource;
	}
	/**
	 * 
	 * @param Name
	 * @return
	 */
	public boolean InstanceDestroy(String Name)
	{		  
		if(Resources == null)return false;
		KuResource Res = null;  
		for(int i=0; i<Resources.size; ++i)
		{
			KuResource Resource = Resources.get(i);
			if(Resource.Disposed)continue;
			if(Resource.Name == Name)
			{
				Res = Resource; 
				break;
			}
		}
		if(Res != null)
		{ 
			Res.Dispose(); 
			Res = null;
			return true;
		} 
		return false;
	}
	
	public boolean InstanceDestroy(int index)
	{
		if(Resources == null)return false;
		KuResource Res = null; 
		for(KuResource Resource: Resources)
		{ 
			if(Resource.Disposed)continue;
			if(Resource.Index == index)
			{
				Res = Resource; 
				break;
			}
		}
		if(Res != null)
		{
			 
			Res.Dispose(); 
			Res = null;
			return true;
		} 
		return false;
	}
	/**
	 * 
	 */
	public void Dispose() { 
		if(Resources != null)
		{
			for(KuResource r: Resources)
			{
				if(!r.Disposed)
				r.Dispose();  
			}
			Resources.clear();  
		}
		Resources = null;
	}

}
