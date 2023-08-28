 package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Sprite;
import util.AABB;
import util.TileCollision;
import util.Vector2f;

public abstract class Entity {
    protected int RIGHT = 0;
    protected int LEFT = 1;
    protected int DOWN = 2;
    protected int UP = 3;
    protected int FALLEN = 4;
    protected int ATTACK_RIGHT = 5;
    protected int ATTACK_LEFT = 6;
    protected int ATTACK_DOWN = 7;
    protected int ATTACK_UP = 8;
    
	protected int currentAnimation;
	protected int size;	
	
	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected AABB hitBounds;
	protected AABB bounds;
	protected TileCollision tc;
	
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean attack;
	protected boolean fallen;
	
	protected int attackSpeed;
	protected int attackDuration;
	
	protected float dx;
	protected float dy;
	
	protected float maxSpeed = 3f;
	protected float acc = 2f;;
	protected float deacc = 1f;
	
	public boolean xCol = false;
	public boolean yCol = false;
	
	protected int life = 3;
    protected int health = 100;
    protected float damage = 10;
    protected boolean die = false;
	
	public Entity(Sprite sprite, Vector2f orgin, int size) {
		this.sprite = sprite;
		this.size = size;
		
		pos = orgin;
		
		bounds = new AABB(orgin, size, size);
		hitBounds = new AABB(orgin, size, size);
		hitBounds.setXOffset(size / 2); 
		
		ani = new Animation();
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
		
		tc = new TileCollision(this);
	}
	
	 public boolean getDeath() { return die; }
	 public int getHealth() { return health; }
	 public float getDamage() { return damage; }
	 public int getLife() { return life; }
	 
	public void setAnimation(int i, BufferedImage[] frames, float delay) {
		currentAnimation = i;
		
		ani.setFrames(frames);
		ani.setDelay(delay);
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
    
	public void update() {        
        animate();
        setHitBoxDirection();
        ani.update();
    }
	
	public void animate() { 
		if(up) {
	        if ((currentAnimation != UP || ani.getDelay() == -1)) {
	        	setAnimation(UP, sprite.getSpriteArray(UP), /*delay*/5);
	        }    
	    } 
	    else if (down) {
	        if ((currentAnimation != DOWN || ani.getDelay() == -1)) {
	        	setAnimation(DOWN, sprite.getSpriteArray(DOWN), /*delay*/5);
	        }
	    } 
	    else if (left) {
	    	if ((currentAnimation != LEFT || ani.getDelay() == -1)) {
	    		setAnimation(LEFT, sprite.getSpriteArray(LEFT), /*delay*/5);
	        }    
	    } 
	    else if (right) {
	    	if ((currentAnimation != RIGHT || ani.getDelay() == -1)) {
	    		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), /*delay*/5);
	        }
	    }
	    else if (fallen) {
	    	if (currentAnimation != FALLEN || ani.getDelay() == -1) {
	    		setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), /*delay*/10);
	        }
	    }
		else if(attack) {
			if(currentAnimation == UP) {
				if (currentAnimation != ATTACK_UP || ani.getDelay() == -1) {
		    		setAnimation(ATTACK_UP, sprite.getSpriteArray(ATTACK_UP), /*delay*/4);
		        }
			}
			else if(currentAnimation == DOWN) {
				if (currentAnimation != ATTACK_DOWN || ani.getDelay() == -1) {
		    		setAnimation(ATTACK_DOWN, sprite.getSpriteArray(ATTACK_DOWN), /*delay*/4);
		        }
			}
			else if(currentAnimation == LEFT) {
				if (currentAnimation != ATTACK_LEFT || ani.getDelay() == -1) {
		    		setAnimation(ATTACK_LEFT, sprite.getSpriteArray(ATTACK_LEFT), /*delay*/4);
		        }
			}
			else if(currentAnimation == RIGHT) {
				if (currentAnimation != ATTACK_RIGHT || ani.getDelay() == -1) {
		    		setAnimation(ATTACK_RIGHT, sprite.getSpriteArray(ATTACK_RIGHT), /*delay*/4);
		        }
			}
		}
		else {
			if(!attack) {
				if(currentAnimation == ATTACK_UP) {
					currentAnimation = UP;
					setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
				}
				else if(currentAnimation == ATTACK_DOWN) {
					currentAnimation = DOWN;
					setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
				}
				else if(currentAnimation == ATTACK_LEFT) {
					currentAnimation = LEFT;
					setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
				}
				else if(currentAnimation == ATTACK_RIGHT) {
					currentAnimation = RIGHT;
					setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
				}
			}
			setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
	    }
	}
	
	private void setHitBoxDirection() {
        if (up) {
        	hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0);
        } 
        else if (down) {
        	hitBounds.setYOffset(size / 2);
        	hitBounds.setXOffset(0);
        }
        else if (left) {
        	hitBounds.setXOffset(-size / 2); 
            hitBounds.setYOffset(0);
        } 
        else if (right) {
        	hitBounds.setXOffset(size / 2);
        	hitBounds.setYOffset(0);
            
        }
    }
	
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	
	public void setFallen(boolean b) {fallen = b;}
	
	public void setSize(int i) {size = i;}
	
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	
	public int getSize() {return size;}
	
	public Animation getAnimation() {return ani;}
	
	public float getAcc() {return acc;}
	public float getDeacc() {return deacc;}
	public float getMaxSpeed() {return maxSpeed;}
	
	public float getDx() {return dx;}
	public float getDy() {return dy;}
	
	public AABB getBounds() {return bounds;}
	
	public abstract void render(Graphics2D g);
}
