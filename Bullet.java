
import java.awt.Rectangle;

public class Bullet {
  
  //static variables that can be used in all classes
  static Rectangle bullet;
  static final int bulletSpeed = -5;
  static boolean canShoot = true;
  
  /* Name: canShoot
   * Purpose: if there is no bullet, allow player to shoot again
   * Parameters: none
   * returns void
   */
  public static void canShoot(){
    canShoot = true; 
    bullet = null;
  }//end canShoot
  
  /* Name: offScreen
   * Purpose: if bullet offScreen, reset the bullet/make it null by calling the canShoot method
   * Parameters: none
   * returns void
   */
  public static void offScreen(){
    if (bullet!=null && bullet.y <= 0)
      canShoot(); 
  }//end OffScreen
  
  /* Name: getBullet
   * Purpose: so that I can give the bullet rectangle to the other classes (mainly the Asteroid class)
   * Parameters: none
   * returns Rectangle
   */
  public static Rectangle getBullet(){
    return bullet;
  }//end getBullet
  
  /* Name: checkCollision
   * Purpose: to check if a collision between bullet & enemy obj have occurred, if so destroy both and add to score
   * Parameters: bullet obj and enemy obj
   * returns void
   */
  public static void checkCollision(Rectangle obj1, Rectangle obj2){
    if(obj2!=null && obj1!= null && obj1.intersects(obj2)){
        Asteroids.score = Asteroids.score + Asteroids.reward;
        Bullet.canShoot();
        Enemies.enemyToTop(obj2);
    }//end if
  }//end checkCollision
  
}//end class
