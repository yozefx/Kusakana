package com.negrorevolutio.nereengine.asm.fonts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.negrorevolutio.nereengine.KuPaper;
import com.negrorevolutio.nereengine.asm.KuResource;
/**
 * 
 * @author yozefx
 *
 */
public class KuASMImportedFont extends KuPaper
{
	public String Name;
	public Array<KuASMCharImportedFont> Characters = null; 
	public Array<KuASMCharImportedFont> Temp = null; 
	public KuResource BmpFont = null;
	public KuResource FontTxt = null;
	public String Text;
	public int OffsetX = 0, OffsetY = 0;
	/**
	 * 
	 * @param Region
	 */
	public KuASMImportedFont( )
	{
		super(); 
		Characters = new Array<KuASMCharImportedFont>();
		Temp = new Array<KuASMCharImportedFont>();
		Name = "Undefined";
		Text = "";
	}
	/**
	 * 
	 * @param BmpFont
	 * @param FontTxt
	 */
	public void LoadBmpFont(KuResource BmpFont, KuResource FontTxt)
	{
		if(BmpFont == null)return;
		if(FontTxt == null)return;
		this.BmpFont = BmpFont;
		this.FontTxt = FontTxt;
	}
	/**
	 * 
	 */
	public void Dispose()
	{
		for(KuASMCharImportedFont C : Characters)
		{
			 C.Dispose();
		}
		Characters.clear();
		Characters = null; 
		if(BmpFont == null)BmpFont.Dispose();
		if(FontTxt == null)FontTxt.Dispose();
		BmpFont = null;
		FontTxt = null;
	}
	/**
	 * 
	 */
	public void Setup()
	{
		if(BmpFont == null)return;
		if(FontTxt == null)return;
		String[] Content = FontTxt.File.readString().split("\n"); 
		for(String Line: Content)
		{ 
			KuASMCharImportedFont CharImportedFont = new KuASMCharImportedFont(this);
			String[] Properties = Line.split(",");
			int C = Integer.parseUnsignedInt( Properties[0]);
			int x = Integer.parseUnsignedInt( Properties[1]);
			int y = Integer.parseUnsignedInt( Properties[2]);
			int w = Integer.parseUnsignedInt( Properties[3]);
			int h = Integer.parseUnsignedInt( Properties[4]); 
			CharImportedFont.Create(C, x, y, w, h);
			Characters.add(CharImportedFont);
		} 
	}
	/**
	 * 
	 * @param txt
	 */
	public void DrawText(String txt)
	{
		Text = txt;
	}
	/**
	 * 
	 * @param txt
	 * @param X
	 * @param Y
	 */
	public void DrawText(String txt, float X, float Y)
	{
		Text = txt;
		this.X = X;
		this.Y = Y;
	}
	
	@Override
	public void Render(SpriteBatch Mind)
	{
		super.Render(Mind);  
		if(Temp != null)
		{ 
			for(KuASMCharImportedFont font: Temp)
			{ 
				font.Render(Mind);  
			}
		}
	}
	 
	
	@Override
	public void Update(float DeltaTime)
	{
		this.Reset();
		if(Temp != null){
			if(Temp.size == 0)
			{  
				for(int c=0; c<Text.length(); ++c)
				{
					if(Characters != null){
						for(KuASMCharImportedFont t: Characters)
						{
							char Ca = Text.charAt(c); 
							 int Val = (int)Ca; 
							if(t.C == Val)
							{
								KuASMCharImportedFont tt = new KuASMCharImportedFont(this);
								t.Update(DeltaTime);
								tt.C = t.C;
								tt.X = t.X;
								tt.Y = t.Y;
								tt.Width = t.Width;
								tt.Height = t.Height;
								tt.Char = t.Char;
								Temp.add(tt);  
							}
						} 
					}
				} 
				KuASMCharImportedFont previous = null;
				for(KuASMCharImportedFont font: Temp)
				{ 
					 font.Y = Y;
					 float y = Y + Height;
					 float yy = font.Y + font.Height;
					 float y2 = Math.abs(y-yy);
					 font.Y = y2 + Y;
					 if(previous != null)
					 {
						 font.X = previous.X + previous.Width;
					 }
					 else
					 {
						 font.X = X;
					 }
					 previous = font;
				}  
			}
		}
	}
	
	public void Reset()
	{
		if(Temp != null){
			for(KuASMCharImportedFont font: Temp)
			{
				font.Char = null;
				font.Region = null;
			}
			Temp.clear();
		}
	}
	
}
