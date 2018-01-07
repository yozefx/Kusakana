package com.negrorevolutio.nereengine.asm.scenes;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.objects.KuASMObject;
/**
 * 
 * @author yozefx
 *
 */
public class KuDepth {
	public Array<KuASMObject> Objects;
	public int index = 0;
	/**
	 * 
	 */
	public KuDepth()
	{
		Objects = new Array<KuASMObject>();
	}
	/**
	 * 
	 */
	public void Dispose()
	{
		Objects.clear();
		Objects = null;
	}
}
