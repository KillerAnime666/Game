package entity;

//import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Sprite;
import util.AABB;
import util.Vector2f;

public class Enemy extends Entity{
	
	private AABB sense; 
	
	private int r;
	
	private int health;
	private float damage;
	
	public Enemy(Sprite sprite, Vector2f orgin, int size) {
		super(sprite, orgin, size);
		
		acc = 1f; 
		maxSpeed = 2f;
		
		r = 100;
		
		sense = new AABB(new Vector2f(orgin.x + size / 2 - r / 2, orgin.y + size / 2 - r / 2), r);
		
		health = super.getHealth();
		
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setXOffset(12);
		bounds.setYOffset(40);
	}
	
	 public void update(Player player) {
		 super.update();
		 
		 move(player);
		 
		 if(hitBounds.collides(player.getBounds())) {
			 damage = (float) Math.random() * 10;
			 System.out.println(damage);
			 player.hit(damage);
			 System.out.println("me diste");
		 }
		 
		 if(!fallen) {
			 if(!tc.collisionTile(dx, 0)) {
				 sense.getPos().x += dx;
				 
				 pos.x += dx;
			 }
			 if(!tc.collisionTile(0, dy)) {
				 sense.getPos().y += dy;
				 
				 pos.y += dy;
			 }
		 }
		 else {
			 die = true;
		 }
	 }
	
	 public void hit(float damage2) {
		 if(die) {
			 return;
		 }
		 
		 health -= damage2;
		 if(health <= 0){
			 die = true;
			 System.out.println("F");
		 }
	 }
	 
	 public boolean isF() {
		 return die;
	 }
	
	 public void move(Player player) {
		 if(sense.colCircleBox(player.getBounds())) {
	        if(pos.y > player.pos.y + 1) {
	        	up = true;
	            dy -= acc;
	            if(dy < -maxSpeed) {
	                dy = -maxSpeed;
	            }
	        } 
	        else if(pos.y < player.pos.y - 1) {
	        	down = true;
	            dy += acc;
	            if(dy > maxSpeed) {
	                dy = maxSpeed;
	            }
	        } 
	        else {
	        	dy = 0;
	        	up = false;
	        	down = false;
	        } 
	        	
	        if(pos.x > player.pos.x + 1) {
	        	left = true;
	            dx -= acc;
	            if(dx < -maxSpeed) {
	                dx = -maxSpeed;
	            }
	        } 
	        else if(pos.x < player.pos.x - 1) {
	        	right = true;
	            dx += acc;
	            if(dx > maxSpeed) {
	                dx = maxSpeed;
	            }
	        }
	        else {
	        	dx = 0;
	        	left = false;
	        	right = false;
	        }
		 }
		 else {
			 up = false;
			 down = false;
			 left = false;
			 right = false;
			 dx = 0;
			 dy = 0;
		 }
	 }
	 
	@Override
	public void render(Graphics2D g) {
//		g.setColor(Color.green);
//		g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset())
//				, (int) bounds.getWidth(), (int) bounds.getHeight());
//		
//		g.setColor(Color.blue);
//		g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);
		
		g.drawImage(ani.getImage(),(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, /*ovserver*/null);
	}
}
