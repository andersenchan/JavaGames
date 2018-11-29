/* Powerup.java
 * Author: Andy Chan
 * Purpose: creates powerups
 * Date of last modification: 9/26/14
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Powerup{
  
  //constant variables
  final static int WIDTH = 30;
  final static int HEIGHT = 30;
  final static int X = 30;
  final static int[] Y = {120, 190, 260, 330};
  final static int[] COST = {35, 120, 150, 300};
  
  //effects variables
  final static int ARMOR = 7; //damagetaken = ai.damage - player.armor
  final static int BOOST = 5;

  int rate = 1;//%chance of creating all the powerups
  int percent = 100; //the total as in: theres a 1/100 cvhance per cycle of it being created
  
  int numPowerups = 4;
  Rectangle[] powerups;
  String[] images;
  
  //constructor
  public Powerup(){
    
    powerups = new Rectangle[numPowerups];
    images = new String[numPowerups];
    images[0] = "Sprites/healthPot.jpg";
    images[1] = "Sprites/Chalice.jpg";
    images[2] = "Sprites/BootsofSpeed.png";
    images[3] = "Sprites/cloth.jpg";
  }
  
  //give image location, get image
  public Image getImage(String img){
    ImageIcon image = new ImageIcon(img);
    return image.getImage();   
  }

  
  //creates the powerups at a random time
  public void create(int i){
    
    //same code as controller, generate the powerup at a random time
    int randNum = (int)(Math.random()*percent);
    if (randNum < rate && powerups[i] == null) //%chance of creating
      powerups[i] = new Rectangle(X, Y[i], WIDTH, HEIGHT);
    
  }
  
  //checks for plyer ollision
  public void playerCollision(int i){
    
    //if player intersects powerup,
    for(int x = 0; x < Game.getNumPlayers(); x++){
      
      if(Game.getScore().getScore() > COST[i]){ //if the player has more score than it costs, aka if they can buy it       
        if(Game.getPlayers(x).getBounds() != null && powerups[i] != null){
          if(Game.getPlayers(x).getBounds().intersects(powerups[i])){
            
            //what happens if they collide
            powerups[i] = null;
            reactions(i, x);
            
          }
        }
      }
    }
  }
    
  //what happens if they collide
  public void reactions(int powerup, int player){
    
    switch(powerup){
      
      case 0: {//healthpoet
        Game.getPlayers(player).resetHealth();
        Game.getScore().subtract(COST[powerup]);
        break;
      }
      case 1: {//ammo
        Game.refillAmmo(player);
        Game.getScore().subtract(COST[powerup]);
        break;
      }
      case 2: {//boots
        Game.getPlayers(player).addSpeed(BOOST);
        Game.getScore().subtract(COST[powerup]);
        break;
      }
      case 3: { //armor
        Game.getPlayers(player).addArmor(ARMOR);
        Game.getScore().subtract(COST[powerup]);
        break;
      }
    }
  }
    
  //draw all the powerups
  public void draw(Graphics g){
    
    for(int i = 0; i < numPowerups; i++){
      if(powerups[i] != null){
        g.drawImage(getImage(images[i]), powerups[i].x, powerups[i].y, WIDTH, HEIGHT, null);
        
        //draw the price of the powerup
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial Bold", Font.BOLD, 15));
        g.drawString(COST[i] + "g", powerups[i].x, powerups[i].y - 10);
      }
    }
  }
  
  public void update(){
    
    //always be attempting to create it and checking for collision
    for(int i = 0; i < numPowerups; i++){
      create(i);
      playerCollision(i);
    }
  }
}
