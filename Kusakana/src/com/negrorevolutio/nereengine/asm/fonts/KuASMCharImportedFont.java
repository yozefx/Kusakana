package com.negrorevolutio.nereengine.asm.fonts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.negrorevolutio.nereengine.KuPaper;
/**
 * 
 * @author yozefx
 *
 */
public class KuASMCharImportedFont extends KuPaper 
{
	public TextureRegion Region;
	public Sprite Char;
	public KuASMImportedFont ImportedFont;
	public int C;
	/**
	 * 
	 * @param ImportedFont
	 */
	public KuASMCharImportedFont(KuASMImportedFont ImportedFont)
	{
		super();
		Region = null;
		Char = null;
		this.ImportedFont = ImportedFont;
	}
	/**
	 * 
	 * @param X
	 * @param Y
	 * @param Width
	 * @param Height
	 */
	public void Create(int C, int X, int Y, int Width, int Height)
	{
		this.Region = new TextureRegion(ImportedFont.BmpFont._Texture, X, Y, Width, Height);
		this.Char = new Sprite(this.Region);
		this.C = C; 
		this.Width = Width;
		this.Height = Height;
		Char.flip(false, true);
	}
	
	@Override
	public void Render(SpriteBatch Mind)
	{
		super.Render(Mind);
		if(Char == null)return;
		this.Char.setPosition(X, Y);
		Char.draw(Mind);
	}
	
	@Override
	public void Update(float DeltaTime)
	{
		if(Char == null)return;
		this.Char.setSize(Width, Height);
		this.Char.setOrigin(Width/2, Height/2); 
	}
	/**
	 * 
	 */
	public void Dispose()
	{
		super.TearUp();
		Region = null;
		Char = null;
		ImportedFont = null;
	}
	
}
