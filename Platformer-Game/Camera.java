/* Camera.java
   by Andy Chan
   Date Started: December 26 2014
   Date Last Updated: December 26 2014
   Purpose:
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Camera{
  
  /* declare variables */
  
  private int x;
  private int y;
  
  /* Name: Game
   * Purpose: Constructor
   */
  public Camera(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public void setX(int x){
    this.x = x;
  }
  
  //constantly set the camera's x to centre around the player
  public void update(Player player){
    x = -player.getBounds().x + (Game.windowX/2);
  }
  
}