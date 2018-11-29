/* Coin.java
   by Andy Chan
   Date Started: December 14 2014
   Date Last Updated: January 9 2015
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Coin extends MyObject {

  /* declare variables */
  
  final static int WIDTH = 32;
  final static int HEIGHT = 32;
  final static String imageLocation = "";
  final static String id = "Coin";
  final static int REWARD = 100;

  private Animation coinAnimation;
  
  /* Name: Game
   * Purpose: Constructor
   */
  public Coin(int x, int y){
    
    super(x, y, WIDTH, HEIGHT, imageLocation, id);
    coinAnimation = new Animation(3, "Sprites/coinNoTransparency.png" , 10, 0, 0, 100, 100);

  }//end coin
  
  /* Name: draw
   * Purpose: draws the object
   */
  public void draw(Graphics g){
    coinAnimation.draw(g, x, y, width, height);
  }
  
  public void update(){
    coinAnimation.runAnimation();
  }
  
  public int getReward(){
    return REWARD;
  }
  
}//end Coin

  