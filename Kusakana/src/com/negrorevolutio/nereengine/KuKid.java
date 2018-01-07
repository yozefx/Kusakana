package com.negrorevolutio.nereengine;

import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.asm.fonts.KuASMImportedFont;
/**
 * 
 * @author yozefx
 *
 */
public class KuKid 
{

	public KidBrain Brain;
	public KidHand Hands;
	public KidEye Eyes;
	public KidMouth Mouth;	
	Array<KidPart> BodyParts = new Array<KidPart>(); 
	public Array<KuASMImportedFont> Fonts = null;	
	/**
	 * 
	 */
	public KuKid()
	{
		Brain = new KidBrain(this);
		Hands = new KidHand(this);
		Eyes = new KidEye(this); 
		Mouth = new KidMouth(this); 
		Fonts = new Array<KuASMImportedFont>();	
		Brain.Fonts = Fonts;
		Hands.Fonts = Fonts; 
		
		BodyParts.add(Brain);
		BodyParts.add(Hands);
		BodyParts.add(Eyes);
		BodyParts.add(Mouth);
	}
	/**
	 * 
	 */
	public void Sleep()
	{
		for(KuASMImportedFont font: Fonts){
			font.Dispose();
		}
		Fonts.clear();
		for(KidPart Part: BodyParts){
			Part.Sleep();
		}
		BodyParts.clear();
		BodyParts = null;
		Brain	= null;
		Hands 	= null;
		Eyes 	= null; 
		Mouth 	= null; 
	}
	/**
	 * 
	 * @param global
	 */
	public void Eureka(KuAsset Asset) {
		this.Brain.Asset = Asset;
	}
	
}
