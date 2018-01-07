package com.negrorevolutio.nereengine.asm.puppets;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
import com.negrorevolutio.nereengine.asm.scenes.KuASMRegion;
/**
 * 
 * @author yozefx
 *
 */
public abstract class KuASMPuppet extends KuASMObject
{
	public Array<KuASMObject> BodyParts = null;
	public Array<KuASMObject> Groups = null;
	/**
	 * 
	 * @param Region
	 */
	public KuASMPuppet(KuASMRegion Region) {
		super(Region);
		this.Kind = "Puppet";
		this.BodyParts = new Array<KuASMObject>();
		this.Groups = new Array<KuASMObject>();
	}
	
	@Override
	public void Dispose()
	{
		for(KuASMObject Obj: BodyParts){
			Obj.Dispose();
		}
		BodyParts.clear();
		BodyParts = null;
		for(KuASMObject Obj: Groups){
			Obj.Dispose();
		}
		Groups.clear();
		Groups = null;
		super.Dispose();
	}
	/**
	 * 
	 * @param GroupName
	 */
	public void AddGroup(String GroupName)
	{
		
	}
	/**
	 * 
	 * @param Index
	 */
	public KuASMObject GetGroupByIndex(int Index)
	{
		return null;
	}
	/**
	 * 
	 * @param Name
	 */
	public KuASMObject GetGroupByName(String Name)
	{
		return null;
	}
	/**
	 * 
	 * @param Index
	 */
	public void DestroyGroupByIndex(int Index)
	{
		
	}
	/**
	 * 
	 * @param Name
	 */
	public void DestroyGroupByName(String Name)
	{
		
	}
	/**
	 * 
	 */
	public void DestroyGroup()
	{
		
	}
	/**
	 * 
	 */
	public void DestroyLastGroupAdded()
	{
		
	}
	/**
	 * 
	 * @param Instance
	 * @param Name
	 */
	public void AddInstanceAtGroupByName(KuASMObject Instance, String Name)
	{
		
	}
	/**
	 * 
	 * @param Instance
	 * @param Index
	 */
	public void AddInstanceAtGroupByIndex(KuASMObject Instance, int Index)
	{
		
	}
	/**
	 * 
	 * @param Instance
	 * @param Name
	 * @return
	 */
	public KuASMObject GetInstanceByNameAtGroupByName(String Instance, String Name)
	{
		return null;
	}
	/**
	 * 
	 * @param Instance
	 * @param Index
	 * @return
	 */
	public KuASMObject GetInstanceByNameAtGroupByIndex(String Instance, int Index)
	{
		return null;
	}
	/**
	 * 
	 * @param InstanceIndex
	 * @param Name
	 * @return
	 */
	public KuASMObject GetInstanceByNameAtGroupByName(int InstanceIndex, String Name)
	{
		return null;
	}
	/**
	 * 
	 * @param InstanceIndex
	 * @param Index
	 * @return
	 */
	public KuASMObject GetInstanceByNameAtGroupByName(int InstanceIndex, int Index)
	{
		return null;
	}

}
