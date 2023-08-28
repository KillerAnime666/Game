package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import states.GameStateManager;
import util.KeyHandler;
import util.MouseHandler;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static int oldFrameCounter;
	
	public static int width;
	public static int height;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage img;
	private Graphics2D g;
	
	private MouseHandler mouse;
	private KeyHandler key;
	
	private GameStateManager gsm;
	
	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}
	
	public void update() {
		gsm.update();
	}
	
	public void render(Graphics2D g) {
		if(g != null) {
			g.setColor(Color.darkGray);
			g.fillRect(/*x*/0, /*y*/0, width, height);
			
			gsm.render(g);
		}
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		gsm.input(mouse, key);
	}
	
	public void draw() {
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(img, /*x*/0,/*y*/0, width, height,/*observer*/null);
		g2.dispose();
	}
	
	public void init() {
		running = true;
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		
		mouse = new MouseHandler(this);
		key = new KeyHandler(this);
		
		gsm = new GameStateManager();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		if(thread == null) {
			thread = new Thread(/*thread*/this,/*name*/"GameThread");
			thread.start();
		}
	}
	
	@Override
	public void run() {
		init();
		
		final double GAME_HERTZ = 60.0;
		//Time Before Update - tiempo antes de la actualizacion
		final double TBU = 1000000000 / GAME_HERTZ; 
		
		//Must Update Before Render - debe actualizarce antes de renderizar
		final int MUBR = 5; 
		
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime;
		
		final double TARGET_FPS = 60;
		//Total Time Before Render - tiempo total antes de renderizar
		final double TTBR = 1000000000 / TARGET_FPS;
		
		int frameCounter = 0;
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		oldFrameCounter = 0;
		
		while(running) {
			double now = System.nanoTime();
			int updateCount = 0;
			while(((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
				update();
				input(mouse, key);
				
				lastUpdateTime +=  TBU;
				updateCount++;
			}
			
			if((now - lastUpdateTime) > TBU) {
				 lastUpdateTime = now - TBU;
			}			
			
			input(mouse, key);
			render(g);
			draw();
			
			lastRenderTime = now;
			frameCounter++;
			
			int thisSeconds = (int) (lastUpdateTime / 1000000000);
			if(thisSeconds > lastSecondTime) {
				if(frameCounter != oldFrameCounter) {
					System.out.println("NEW SECOND " + thisSeconds + " " + frameCounter);
					oldFrameCounter = frameCounter;
				}
				frameCounter = 0;
				lastSecondTime = thisSeconds;
			}
			
			while(((now - lastRenderTime) < TTBR) && ((now - lastUpdateTime) < TBU)) {
				Thread.yield();
				 
				try {
					Thread.sleep(1);
				}
				catch (Exception e) {
					System.err.println("ERROR: no se puede acceder al thread");
				}
				
				now = System.nanoTime();
			}
		}
	}
}
