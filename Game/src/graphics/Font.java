package graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Font {
	private BufferedImage FONTSHEET = null;
	private BufferedImage[][] fontArray;
	private final int TITLE_SIZE = 32;
	
	public int w;
	public int h; 
	
	private int wLetter;
	private int hLetter;
	
	public Font(String file) {
		w = TITLE_SIZE;
		h = TITLE_SIZE;
		
		System.out.println("Loading: " + file + "...");
		FONTSHEET = loadFont(file);
		
		wLetter = FONTSHEET.getWidth() / w;
		hLetter = FONTSHEET.getHeight() / h;
		
		loadFontArray();
	}	
	
	public Font(String file, int w, int h) {
		this.w = w;
		this.h = h;
	
		System.out.println("Loading: " + file + "...");
		FONTSHEET = loadFont(file);
		
		wLetter = FONTSHEET.getWidth() / w;
		hLetter = FONTSHEET.getHeight() / h;
		
		loadFontArray();
	}
	
	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	
	public void setWidth(int i) {
		w = i;
		
		wLetter = FONTSHEET.getWidth() / w;
	}
	public void setHeight(int i) {
		h = i;
		
		hLetter = FONTSHEET.getHeight() / h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	private BufferedImage loadFont(String file) {
		BufferedImage sprite = null; 
		
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
		}
		catch(Exception e){
			System.err.println("ERROR: no se puede cargar el archivo: " + file);
		}
		return 	sprite;
	}	
	
	public void loadFontArray() {
		fontArray = new BufferedImage[wLetter][hLetter];
		
		for(int x = 0; x < wLetter; x++) {
			for(int y = 0; y < hLetter; y++) {
				fontArray[x][y] = getLetter(x, y);
			}
		}
	}
	
	public BufferedImage getFontSheet() { return FONTSHEET;	}
	
	public BufferedImage getLetter(int x, int y) { return FONTSHEET.getSubimage(/*x*/x * w, /*y*/y * h, w, h); }
	
	public BufferedImage getFont(char letter) {
		int value = letter;
		
		int x = value % wLetter;
		int y = value / wLetter;
		
		return getLetter(x, y);
	}
}
