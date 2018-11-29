/* BufferedImageLoader.java
   by Andy Chan
   Date Started: December 26 2014
   Date Last Updated: December 26 2014
   Purpose:
*/

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader{

  public BufferedImage loadImage (String path) throws java.io.IOException {
    
    Image image;
    BufferedImage buffered = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);;
    
    //converting the image into a buffered image
    try{
      image = ImageIO.read(new File(path));
      buffered = (BufferedImage) image;      
    }
    catch(IOException e){
      e.printStackTrace();
    }
    
    return buffered;
    
  }

}