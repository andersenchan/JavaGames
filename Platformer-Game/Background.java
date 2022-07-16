/* MyObject.java
   by Andy Chan
   Date Started: December 14 2014
   Date Last Updated: December 14 2014
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Background extends MyObject{
  
  final int SPEED = 8;
  int velX = 0;
  
  final static String imageLocation = "Other Things/background0.png";
  Rectangle b1;
  Rectangle b2;
  
  //allow the main class to determine the size of the background
  //the size,  width and height, is the windowSize
  public Background(int width, int height){

    super(0, 0, width, height, imageLocation, "Background");

    b1 = new Rectangle(0, 0, width, height);
    b2 = new Rectangle(-width , 0, width, height);
  }

  public void draw(Graphics g){
    g.drawImage(sprite, b1.x, b1.y, width, height, null);
    g.drawImage(sprite, b2.x, b2.y, width, height, null);
  }
  
  //non continuous scrolling, isnt more diffucult, only call the method when a condition(keyPressed) occurrs
  public void scrollHorizontalRight(){

    velX = SPEED;

    if(b1.x >= width)
      b1.x = -width;
    if(b2.x >= width)
      b2.x = -width;
  }
  
  public void scrollHorizontalLeft(){

    velX = -SPEED;

    if(b2.x <= -width)
      b2.x = width;
    if(b1.x <= -width)
      b1.x = width;
    
  }
  
  public void setVelX(int velX){
    this.velX = velX;
  }
  
  public void update(){
    b1.x += velX;
    b2.x += velX;
    
  }
}
