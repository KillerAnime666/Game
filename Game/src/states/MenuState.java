package states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import util.KeyHandler;
import util.MouseHandler;

public class MenuState extends GameState{
	private BufferedImage imagen;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		loadImage();
	}
	
	public void loadImage() {
		try {
			imagen = ImageIO.read(getClass().getClassLoader().getResource("map/menu.png"));
		}
		catch(Exception e) {
			System.err.println("no se pudo cargar");
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(key.enter.down) {
			GameStateManager.setStates(1);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(imagen, 0, 0, null);
	}
	
}
