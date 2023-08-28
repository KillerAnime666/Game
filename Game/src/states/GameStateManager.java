package states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.Vector2f;

public class GameStateManager {
	private static ArrayList<GameState> states = new ArrayList<GameState>();
	
	public static Vector2f map;
	
	public static final int	MENU = 0;
	public static final int PLAY = 1;
	public static final int GAMEOVER = 2;
	
	static GameStateManager gsm = new GameStateManager();
	
	public GameStateManager() {
		map = new Vector2f(GamePanel.width, GamePanel.height);
		Vector2f.setWorldVar(map.x, map.y);
		
		setStates(0);
	}
	
	public void update(){
		Vector2f.setWorldVar(map.x, map.y);
		
		for(int i = 0; i < states.size(); i++) {
			states.get(i).update();
		}
	}
	
	public static void setStates(int state) {
		 states.clear();
		 
		 switch (state) {
		 	case PLAY: states.add(new PlayState(gsm));
		 		break;
		 	case MENU: states.add(new MenuState(gsm));
	 			break;
		 	case GAMEOVER: states.add(new GameOverState(gsm));
	 			break;
		}
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		key.escape.tick();
		
		for(int i = 0; i < states.size(); i++) {
			states.get(i).input(mouse, key);
		}
		
		if(key.escape.clicked) {
			System.exit(0);
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).render(g);
		}
	}
    
    public void add(int state) {
        if (state == PLAY) {
        	states.add(new PlayState(/*gsm*/ this));
        }
        else if (state == MENU) {
        	states.add(new MenuState(/*gsm*/ this));
        }
        else if (state == GAMEOVER) {
        	states.add(new GameOverState(/*gsm*/ this));
        }
    }
}
