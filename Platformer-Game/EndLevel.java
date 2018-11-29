/* EndLevel.java
   by Andy Chan
   Date Started: January 3 2015
   Date Last Updated: January 9 2015
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class EndLevel extends MyObject {
  
  /* declare variables */
  
  final static  int WIDTH = 32;
  final static int HEIGHT = 32;
  
  final static String imageLocation = "";
  final static String id = "EndLevel";
  
  /* Name: EndLevel
   * Purpose: Constructor
   */
  public EndLevel(int x, int y){
    super(x,y, WIDTH, HEIGHT, imageLocation, id);
  }
  
  /* Name: draw
   * Purpose: draws the object
   */
  public void draw(Graphics g){
    g.setColor(Color.YELLOW);
    g.fillRect(object.x, object.y, width, height);
  }
  
  public void update(){
    
  }
  
}
