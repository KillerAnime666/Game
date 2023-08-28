package states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Enemy;
import entity.Player;
import game.GamePanel;
import graphics.Font;
import graphics.Sprite;
import tiles.TileManager;
import util.KeyHandler;
import util.MouseHandler;
import util.Vector2f;

public class PlayState extends GameState{
	private Font font;
	private Player player;
	private ArrayList<Enemy>enemy;
	private TileManager tm;
	public static Vector2f map; 
	
	private BufferedImage imagen;
	
	private int score = 0;
	
	public PlayState(GameStateManager gsm) {
		super(gsm); 
		
		map = new Vector2f();
		Vector2f.setWorldVar(map.x, map.y);
		
		//genera la sombra
		loadImage();
		
		//genera el mapa
		tm = new TileManager("map/tilemap2.xml");
		
		//genera la fuene del juego
		font = new Font("fonts/font.png",/*w*/10,/*h*/10);
		
		// genera al jugador
		player = new Player(new Sprite("sprites/linkFormatted.png"), 
				new Vector2f(/*x*/0 + (GamePanel.width / 2) - 32, /*y*/0 + (GamePanel.height / 2) - 32), /*size*/64);
		
		// genera los enemigos
		numEnemys();
	}
	
	public void loadImage() {
		try {
			imagen = ImageIO.read(getClass().getClassLoader().getResource("map/sombra.png"));
		}
		catch(Exception e) {
			System.err.println("no se pudo cargar");
		}
	}
	
	public void numEnemys() {
		enemy = new ArrayList<Enemy>();
		
		Enemy mago;
		
		//genera las posiciones de los enemigos
		Vector2f[] position = new Vector2f[] {
				new Vector2f(/*x*/0 + (GamePanel.width / 2) - 32 + 300, /*y*/0 + (GamePanel.height / 2) - 32 + 150),
				new Vector2f(/*x*/0 + (GamePanel.width / 2) - 32 + 500, /*y*/0 + (GamePanel.height / 2) - 32 + 150),
				new Vector2f(/*x*/0 + (GamePanel.width / 2) - 32 + 700, /*y*/0 + (GamePanel.height / 2) - 32 + 150),
		};
		
		// otorga las posiciones dentro del array de enemigos
		for(int i = 0; i < position.length; i++) {
			mago = new Enemy(new Sprite("sprites/wizardPlayer.png", 64, 64), position[i], 64);
			enemy.add(mago);
		}
	}
	
	@Override
	public void update() {
		// actualiza el mapa
		Vector2f.setWorldVar(map.x, map.y);
		
		// actualiza el peronaje
		if(player.isF() == false) {
			player.update(enemy);
		}
		else {
			//al morir manda al game over
			score = 0;
			GameStateManager.setStates(2);
		}
		
		// actualiza los enemigos
		for(int i = 0; i < enemy.size(); i++) {
			Enemy e = enemy.get(i);
			e.update(player);
			
			// verifica si los enemigos estan muertos
			if(e.isF()) {
				enemy.remove(i);
				score += 10;
				i--;
			}				
		}
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// asigna la entrada de teclado y mouse al personaje
		player.input(mouse, key);
	}

	@Override
	public void render(Graphics2D g) {		
		// dibuja el mapa
		tm.render(g);
		
		// dibuja a los enemigos
		for(int i = 0; i < enemy.size(); i++) {
			Enemy e = enemy.get(i);
			e.render(g);
		}
		
		// dibuja la sombra
		g.drawImage(imagen, 0, 0, null);
		
		// dibuja los FPS
		Sprite.drawArray(g, font,  /*word*/GamePanel.oldFrameCounter + " FPS",
				new Vector2f(/*x*/GamePanel.width - 145,/*y*/30),/*size*/25,/*xOffset*/20);
		
		// dibuja las vidas
		Sprite.drawArray(g, font, Player.life + " Vidas", new Vector2f(GamePanel.width - 1070, 30), 25, 18);
		
		// dibuja la salud
		Sprite.drawArray(g, font,"Salud: " + Player.health, new Vector2f(GamePanel.width - 1070, 55), 25, 18);
		
		// dibuja el puntaje
		Sprite.drawArray(g, font,"Score: " + score, new Vector2f(GamePanel.width - 1070, 80), 25, 18);
		
		//dibuja al pers onaje
		player.render(g);
	}
}
