package com.negrorevolutio.nereengine.asm.paths;

import com.negrorevolutio.nereengine.KuKid;
import com.negrorevolutio.nereengine.asm.KuIdea;
/**
 * 
 * @author yozefx
 *
 */
public class KuASMPathPoint extends KuIdea
{ 
	/**
	 * 
	 * @param Kid
	 */
	public KuASMPathPoint(KuKid Kid) {
		super(Kid); 
	}
	
	@Override
	public void Creation()
	{ 
		Width = Height = 5;
	}
}
