package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.GamePanel;
import graphics.Sprite;
import states.PlayState;
import util.KeyHandler;
import util.MouseHandler;
import util.Vector2f;

public class Player extends Entity{
	
	public static int life; 
	public static int health;
	protected float damage;
	
	public Player(Sprite sprite, Vector2f orgin, int size) {
		super(sprite, orgin, size);
		
		acc = 2f;
		maxSpeed = 3f;
		deacc = 1f;
		
		life = super.getLife();
		health = 200;
		damage = super.getDamage();
		
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setXOffset(12);
		bounds.setYOffset(40);
	} 
	
	public void setLife(int x) { life -= x; }
	
	public void update(ArrayList<Enemy> enemy) {
        super.update();
        for(int i = 0; i < enemy.size(); i++) {
        	Enemy enemigo = enemy.get(i);
        	
	        if(hitBounds.collides(enemigo.getBounds()) && attack) {
	        	enemigo.hit(damage);
	        	System.out.println("te di");
	        }
        }
	        
        if(!fallen) {
        	move();
        	
	        if(!tc.collisionTile(dx, 0)) {
	        	PlayState.map.x += dx;
	        	pos.x += dx;
	        }        
	        
	        if(!tc.collisionTile(0, dy)) {
	        	PlayState.map.y += dy;
	        	pos.y += dy;
	        }
        }
        else {
        	if(ani.hasPlayedOnce()) {
        		if(life > 1) {
        			resetPosition();

            		fallen = false;
            		
            		setLife(1);
        			System.out.println(life);
        		}
        		else {
        			System.out.println("me mori");
        			this.die = true;
        		}
        	}
        }
   	}
	
	 public void hit(float damage2) {	
		 if(die) {
			 return;
		 }
		 		 
		 health -= damage2;
		 
		 if(health <= 0 && life > 0) {
			 setLife(1);
			 System.out.println("ay");
			 health = 200;
			 resetPosition();
		 }
		 
		 if(health < 0 && life == 0){
			 die = true;
			 System.out.println("F... en el chat");
		 }
	 }
	 
	 public boolean isF() {
		 return die;
	 }
	
	@Override
	public void render(Graphics2D g) {
//		g.setColor(Color.blue);
//		g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), 
//				(int) bounds.getWidth(), (int) bounds.getHeight());
//		
//		if(attack) {
//			g.setColor(Color.red);
//			g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), 
//					(int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), 
//					(int) hitBounds.getHeight());
//		}
			
		g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size,/*observer*/null);
	}
	
	private void resetPosition() {
		System.out.println("Restaurando pensonaje... ");
		pos.x = GamePanel.width / 2 - 32;
		PlayState.map.x = 0;
		
		pos.y = GamePanel.height / 2 - 32;
		PlayState.map.y = 0;
		
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), /*delay*/10);
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		if(mouse.getButton() == 1) {
			System.out.println("Player: " + pos.x + "  " + pos.y);
		}
		
		if(!fallen) {
	        if(key.up.down) {
	            up = true;
	        } 
	        else {
	            up = false;
	        }
	        if(key.down.down) {
	            down = true;
	        } 
	        else {
	            down = false;
	        }
	        if(key.left.down) {
	            left = true;
	        } 
	        else {
	            left = false;
	        }
	        if(key.right.down) {
	            right = true;
	        } 
	        else {
	            right = false;
	        }
	        
	        if(key.attack.down) {
	            attack = true;
	        } 
	        else {
	            attack = false;
	        }
		}
		else {
			up = false;
			down = false;
			left = false;
			right = false;
			attack = false;
		}
    }
	
	public void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } 
        else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }

        if(down) {
            dy += acc;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } 
        else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }

        if(left) {
            dx -= acc;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } 
        else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        if(right) {
            dx += acc;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } 
        else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }
}
