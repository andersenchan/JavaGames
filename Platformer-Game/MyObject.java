/* MyObject.java
   by Andy Chan
   Date Started: December 14 2014
   Date Last Updated: January 9 2015
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class MyObject{
  
  /* declare variables */
  
  String id;
  int x, y, width, height;
  int z = 3; //for drawing the rectangles used for collision detection
  
  ImageIcon imageIcon;
  Image sprite;

  Rectangle object;

  /* Name: Game
   * Purpose: Constructor
   */
  public MyObject(int x, int y, int width, int height, String imageLocation, String id){
    
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.id = id;

    imageIcon = new ImageIcon(imageLocation);
    sprite = imageIcon.getImage();

    object = new Rectangle(x, y, width, height);
  }

  public String getID(){
    return id;
  }
  
  //returns the object rectangle, easier than having a "get x, y, width, height" methods
  public Rectangle getBounds(){
    return new Rectangle(object.x, object.y, width, height);
  } 

  /* abstract methods that show polymorphism; there will be an array of MyObjects and a for loop 
     will run through it, calling the draw and update methods of each object, 
     having a different result each time, showing polymorphism, late binding */

  public abstract void update();
  public abstract void draw(Graphics g);


  /* methods for detecting collision with another block */

  /* you dont want to hit the Y collision blocks when you're hitting the X collision blocks, 
     so you have to add the z variable so they dont intersect */

  public Rectangle getBoundsUp(){
    return new Rectangle(object.x + z, object.y, width - 2*z, 10);
  }

  public Rectangle getBoundsLeft(){
    return new Rectangle(object.x, object.y + z, 10, height - 2*z);
  }

  public Rectangle getBoundsRight(){
    return new Rectangle(object.x + width - 10, object.y + z, 10, height - 2*z);
  }

  public Rectangle getBoundsDown(){
    return new Rectangle(object.x + z, object.y + height- 10, width - 2*z, 10);
  }
  
  public Image getImage(){
    return sprite;
  }

}
