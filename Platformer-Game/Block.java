/* Block.java
   by Andy Chan, Riley Kemp
   Date Started: December 14 2014
   Date Last Updated: January 5 2015
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Block extends MyObject {
  
  /* declare variables */
  
  final static  int WIDTH = 32;
  final static int HEIGHT = 32;
  
  final static String imageLocation = "Sprites/block.jpg";
  final static String id = "Block";
  
  /* Name: Block
   * Purpose: Constructor
   */
  public Block(int x, int y){
    super(x,y, WIDTH, HEIGHT, imageLocation, id);
  }
  
  /* Name: draw
   * Purpose: draws the object
   */
  public void draw(Graphics g){
    g.setColor(Color.BLACK);
    g.drawImage(sprite, object.x, object.y, width, height, null);
  }
  
  public void update(){
    
  }
  
}//end Block

  