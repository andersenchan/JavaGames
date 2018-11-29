/* AI.java
 * Author: Andy Chan
 * Purpose: allow creation of an ai object that will follow the player and deal damage to it
 * Date of last modification: 9/26/14
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class AI{
  
  //constants
  final private static int REWARD = 12;
  final private static int DAMAGE = 15;
  final private static int WIDTH = 30;
  final private static int HEIGHT = 30;
  
  //speed vars
  private int velX;
  private int velY;
  private int x, y;
  private int speed = 1;
  
  //object variables
  Rectangle ai; 
  ImageIcon image;
  
  //constructor
  public AI(int x, int y){
    
    image = new ImageIcon("Sprites/RengarSquare.png");
    ai = new Rectangle(x, y, WIDTH, HEIGHT);
  }
  
  //moves the AI by adding the speed value to the x and y of the rectagle
  public void move(){
      ai.y += velY;
      ai.x += velX;
  }
  
  //setDirection, to make the ai followPlayer
  public void setVelocity(){
    for(int i = 0; i < Game.getNumPlayers(); i++){
      
      //horizontal
       if (Game.getPlayers(i).getBounds().x > ai.x)
       setVelX(+speed);
       else if(Game.getPlayers(i).getBounds().x < ai.x)
         setVelX(-speed);
       
       //vertical
       if (Game.getPlayers(i).getBounds().y > ai.y)
       setVelY(+speed);
       else if(Game.getPlayers(i).getBounds().y < ai.y)
         setVelY(-speed);
    }
    
  }

  //modifier methods
  public void setVelX(int velX){
    this.velX = velX;
  }
  public void setVelY(int velY){
    this.velY = velY;
  }
  public void setSpeed(int speed){
    this.speed = speed;//increase difficulty
  }
  
  //accessor methods
  public Rectangle getBounds(){
    return ai; //returns rectangle
  }
  public int getSpeed(){
    return speed;
  }
  public int getDamage(){
    return DAMAGE;
  }
  public int getReward(){
    return REWARD;
  }
  public void reset(){
    ai = null;//resets the ai
  }
  
  //draws the AI
  public void draw(Graphics g){
    
    if (ai!=null){
      g.drawImage(image.getImage(), ai.x, ai.y, WIDTH, HEIGHT, null);
    }
  }
  
  //updates the ai
  public void update(){
    
    if(ai!=null){
      if(Game.getNumPlayers() == 1){
        move();
        setVelocity();
      }
    }
  }
  
}
