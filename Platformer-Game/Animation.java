/* Animations.java
 * Author: Andy Chan
 * Purpose: displays the animations 
 * Date of creation: 12/20/14
 * Date of last modification: 1/13/15
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;

public class Animation{
  
  private int speed; //speed of the animation
  private int index = 0; //
  private int count = 0; //the image position that you are currently on
  
  private BufferedImage[] images; //the images that it will be running through
  private BufferedImage currImage; //the current image

  private BufferedImage spritesheet;
  
  /* Name: Animation
   * Purpose: Constructor
   */
  public Animation(int speed, String imageLocation, int numFrames, int colStart, int rowStart, int width, int height){
    
    this.speed = speed;
    
    /* load the spritesheet image */
    
    BufferedImageLoader loader = new BufferedImageLoader();
    
    try{
      spritesheet = loader.loadImage(imageLocation);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    
    images = new BufferedImage[numFrames];
    
    //grabbing each subimage from the spritesheet
    for(int i = 0; i < images.length; i++){
      int x = i*width + colStart;//these are just assumptions, so make sure in the spritesheet its correct
      int row  = 0;//since rowStart is a thing now
      int y = row*height + rowStart;
      images[i] = spritesheet.getSubimage(x, y, width, height);
    }
    
    currImage = images[0];//initialize currImage
  }
  
  /* Name: runAnimation
   * Purpose: changes the current frame depending on the speed
   */
  public void runAnimation(){
    index++;
    if(index > speed){
      index = 0;
      nextFrame();
    }
  }
  
  /* Name: nextFrame
   * Purpose: changes the current frame to the next one
   */
  public void nextFrame(){

    //change the current image and then increase the count
    currImage = images[count];
    count++;
    
    //repeat the animation once its gone through all the images
    if(count >= images.length)
      count = 0;
  }
  
  /* Name: draw
   * Purpose: draws the animation
   */
  public void draw(Graphics g, int x, int y, int width, int height){
    g.drawImage(currImage, x,y,width, height, null);
  }
  
}