/* Player.java
 * Author: Andy Chan
 * Purpose: creates a player class that the user can control
 * Date of last modification:9/26/14
 */

import java.awt.Graphics;
import java.awt.Color;
//import java.awt.Font;
//import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Player {

  //constant variables
  final private static int WIDTH = 40;
  final private static int HEIGHT = 40;
  
  //health variables
  private int totalDamage = 0; 
  private int maxHealth = WIDTH;
  private int currentHealth;
  private int armor = 0;
  
  //movement variables
  private int velX = 0;
  private int velY = 0;
  private int speed = 5;
  private int dash = 70;
  private int directionX = 0;
  private int directionY = 0;
  private boolean canDash = true;
  
  //object variables
  ImageIcon image;
  Rectangle player;

  //constructor method
  public Player(int x, int y){
    image = new ImageIcon("Sprites/lucian.png");
    player = new Rectangle(x ,y , WIDTH, HEIGHT);
  }

  public void moveLeft(){//moves player left
    if(player.x <= 0 + WIDTH){
      velX = 0;
      player. x = 0 + WIDTH;
    }
    else
      velX = -speed;
  }
  
  public void moveRight(){//moves player right
    if(player.x >= Game.WINDOWX - WIDTH*2){
      velX = 0;
      player.x = Game.WINDOWX - WIDTH*2;
    }
    else
      velX = speed;
  }
  
  public void moveUp(){//moves player up
    if(player.y <= 0 + WIDTH){
      velY = 0;
      player.y = 0 + WIDTH;
    }
    else
      velY = -speed;
  }
  
  public void moveDown(){//moves player down
    if(player.y >= Game.WINDOWY - HEIGHT*2){
      velY = 0;
      player.y = Game.WINDOWY - HEIGHT*2;
    }
    else
      velY = speed;
  }
  

 //accessor methods{
  
  public int getSpeed(){
    return speed;
  }
  
  public Rectangle getBounds(){//Gets the dimentions of each player
    return player;
  }
  
  public int getArmor(){
    return armor;
  }
  
  //}//end accessor methods
  
  //mutator methods{
  
  public void addDamage(int damage){//Adds damage when bullet intersects player
    totalDamage += damage;
  }
  
  public void addArmor(int armor){
    this.armor = armor;
  }
  
  public void addSpeed(int speed){//Sets the player speed
    this.speed += speed;
  }
  
  public void setVelX(int velX){
    this.velX = velX;
  }
  
  public void setVelY(int velY){
    this.velY = velY;
  }
  
  public void setXDirection(int directionX){
    this.directionX = directionX;
  }
  
  public void setYDirection(int directionY){
    this.directionY = directionY;
  }
  
  public void canDash(){
    canDash = true;
  }
  
  //}end mutator methods

  //checks for death, and resets game if one player dies
  public void reset(){
    if (totalDamage >= maxHealth){
      Game.over();
    }
  }
  
  //sets damage to 0, or resets to max health
  public void resetHealth(){
    totalDamage = 0;
  }

   //used so that the player can move quickly and get out of a tough spot, the player coordinates will jump the distance of the flash value
   public void dash(){
     
     //if you can used flash and you would like to use it
     if(canDash){
       
    // same thing as  x = x + (distance * direction);
       player.x += dash*directionX;  //the direction the player will flash in depends on the directionX value, whether it is negative(left) or positive(right)
       player.y += dash*directionY;  //same with the direction X
       canDash = false;
     }
   }

  public void update(){//All things that need to be constantly updated (speed and check reset)
    
    //moves the player
    player.x += velX;
    player.y += velY;
    
    //checks for death
    reset();
  }
  
  //Draws the player object
  public void draw(Graphics g, int playerNum){
    
    g.drawImage(image.getImage(),player.x, player.y, WIDTH, HEIGHT, null);
    
    //drawing the players's health bar based on current healthg
    g.setColor(Color.CYAN);
    currentHealth = maxHealth - totalDamage;
    g.fillRect(player.x, player.y - 20, currentHealth, 5);
    g.drawString("Player" + playerNum, player.x, player.y-30);
  }
}

