
import java.awt.Rectangle;

public class PowerUp {
  
  //static variables that can be used in all classes
  static boolean canCreate;
  static int randInt;
  static int scoreReward = 100;
  
  /* Name: createPowerUp
   * Purpose: creating powerup, same logic as creating enemies and bullet, except with the addition of the canCreate variable, calculated in canCreate 
   * Parameters: Rectangle r - the powerUp object from the Asteroids class
   * returns a Rectangle - the powerUp Rectangle
   */
  public static Rectangle createPowerUp(Rectangle r){
    if(r == null){
      if (canCreate){
        canCreate = false;
        System.out.println("powerUp created");
        return new Rectangle((int)(Math.random()*Asteroids.windowSizeX), 0, 50, 50);       
      }//end if
      else
        return null;
    }//end if
    else //if (r!=null)
      return r;  
  }//end createPowerUp
  
  
  public static String test(Rectangle r){
    if (r!=null)
      return "y";
    else
      return "n";
  }//end test
  
  /* Name: canCreate
   * Purpose: determine whether you can create the powerUp object, will determine how rare the powerUp is
   * Parameters: 
   * returns 
   */
  public static void canCreate(){
    
    //generate randInt from 0-1000 to determine wheteher a powerUp will be created
    randInt = (int)(Math.random()*1001);   
    
    //.1% chance (1/1000) of a powerUp being created, since timer set to 10 miliseconds(0.01 seonds), should create powerUp every 10 seconds
    if (randInt <= 1)
      canCreate = true;
  }//end canCreate
  
  /* Name: checkCollision
   * Purpose: if collision with object(which is a good thing), add to score and destroy powerUp object
   * Parameters: 2 rectengles - the user controlled obj and the powerUp 
   * returns void
   */
  public static void checkCollision(Rectangle obj1, Rectangle obj2){
    if(obj2!=null && obj1!= null && obj1.intersects(obj2)){
      Asteroids.score = Asteroids.score + scoreReward;
      Asteroids.powerUp = null;
    }//end if
  }//end checkCollision
  
  /* Name: OffScreen
   * Purpose: if object is off screen, return null so that a new object can be created
   * Parameters: Rectangle - the powerUp rectangle
   * returns Rectangle - the powerUp rectangle
   */
  public static Rectangle offScreen(Rectangle r){
    if (r!=null && r.y >= Asteroids.windowSizeY)
      return null;
    else
      return r;
  }//end offSreen
  
}//end class
