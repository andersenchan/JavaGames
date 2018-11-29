/* Bullet.java
 * Author: Andy Chan
 * Purpose: the bullet will be created and move continuosly in a direction that is determined based on which side of the player it was created
 * Date of last modification: 9/26/14
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Color;

public class Bullet {

  //constant variables
  private static final int SPEED = 10;
  private static final int WIDTH = 5;
  private static final int HEIGHT = 5;
  private static final int DAMAGE = 5;
  
  //location variables
  private int x;
  private int y;
  private int velX;
  private int velY;

  Rectangle bullet;

  //constructor method
  public Bullet(int x, int y){
    bullet = new Rectangle(x, y, WIDTH, HEIGHT);//creats a rectangle object called bullet, and gives it the values of x, y, WIDTH, and HEIGHT
  }
    
  //accessor methos, for the bullet recangle
  public Rectangle getBounds(){
    return bullet;
  }
  
  //makes the bullet null, Removes the bullet
  public void reset(){
    bullet = null;
  }
  
  //if collision, damage the player and reset the bullet
  public void collision(){
    
    for(int i = 0; i < Game.getNumPlayers(); i++){//goes through the array of players
      
      if (bullet!=null){//If the bullet exists, and it hits a player, add damage to the player and call the reset method to remove the bullet
        if (bullet.intersects(Game.getPlayers(i).getBounds())){
          Game.getPlayers(i).addDamage(DAMAGE);
          reset();
        }
      }
    }
  }

  //set s the direction of the 4 bullets created for each player
  public void setDirection(int direction){
    
    switch(direction){
      
      case 0: velY = -SPEED; velX = 0; break;
      case 1: velY = SPEED;  velX = 0; break;
      case 2: velX = -SPEED; velY = 0; break;
      case 3: velX = SPEED;  velY = 0; break;
      //default: velX = 0; velY = 0;
    }
  }
  
  //move the bullet
  public void move(){
      bullet.y += velY;
      bullet.x += velX;
  }
  
  //draw the bullet
  public void draw(Graphics g){
    
    if (bullet!=null){
      g.setColor(Color.CYAN);
      g.fillRect(bullet.x, bullet.y, WIDTH, HEIGHT); //draw the bullet at its location
    }
  }
  
  //update the bullet //All the methods that need constant updating
  public void update(){
    
    if (bullet!=null){
      move();
      collision();
    
    //if bullet is off bounds, 
    if (y <= 0 || y >= Game.WINDOWY || x <= 0 || x >= Game.WINDOWX);
      //reset();
    }
  }
  
}//end class
