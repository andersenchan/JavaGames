/* Character.java
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

public class Character extends MyObject {

  /* declare variables */

  Rectangle character;
  
  int boundaryXR;//right horizontal boundary 
  int boundaryXL;//left horizxontal boundary

  int velX;
  int velY;
  int speed;

  int z = 3;//width of the empty square made by the collision blocks
            //larger z = more chance the collision blocks will be missed
            //lower z = more chance youll hit multiple collision blocks, the X and Y collision blocks
  int c = 10; //width of the collision blocks
  
  /* Name: Game
   * Purpose: Constructor
   */
  public Character(int x, int y, int width, int height, String imageLocation, int speed, String id){
    super(x, y, width, height, imageLocation, id);
    this.speed = speed;
    character = object;
  }

  /* Name: setBoundaries
   * Purpose: sets the movement boundaries, mainly for the enemy object
   */
  public void setBoundaries(int leftBoundary, int rightBoundary){
    boundaryXL = leftBoundary; 
    boundaryXR = rightBoundary; 
  }

  public void update(){
    character.x += velX;
  }

  /* Name: draw
   * Purpose: draws the object
   */
  public void draw(Graphics g){

    //draw the sprite where the character is
    //g.drawImage(sprite, x, y, null);
    g.setColor(Color.BLUE);
    g.drawRect(character.x, character.y, width, height);

    //this is just drawing collision blocks for testing whether the collision blocks are accurate
    g.setColor(Color.RED);
    g.drawRect(getBoundsUp().x, getBoundsUp().y, getBoundsUp().width, getBoundsUp().height);
    g.drawRect(getBoundsLeft().x, getBoundsLeft().y, getBoundsLeft().width, getBoundsLeft().height);
    g.drawRect(getBoundsRight().x, getBoundsRight().y, getBoundsRight().width, getBoundsRight().height);
    g.drawRect(getBoundsDown().x, getBoundsDown().y, getBoundsDown().width, getBoundsDown().height);
  }

}
