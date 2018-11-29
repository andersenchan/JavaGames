
import java.awt.Rectangle;

public class Enemies {
 
  /* Name: enemyCreate
   * Purpose: creates a new enemy at (randX, 0) when the requirements are met at a specific time in the game, making the game harder as it progresses
   *          the requirement is the amount of asteroids already created
   * if the enemy has already been created, the returned rectangle is itself
   * Each enemy created as its own object for more variety, and just recreated so that there will always be that many amount of asteroids on the screen at once
   * Parameters: the enemy that is going to be created, the requirement, and the size of the enemy object
   * Returns: the enemy
   */
  public static Rectangle enemyCreate(Rectangle enemy, int requirement, int size, int asteroidsCreated){
    if (enemy==null && asteroidsCreated == requirement)
      return new Rectangle((int)(Math.random()*Asteroids.windowSizeX), 0, size, size);
    else if(enemy!=null)
      return enemy;
    else
      return null;
  }//end enemyCreate
  
  /* Name: enemyToTop
   * Purpose: when they reach the bottom of the screen, move them ABVOVE the screen (0-enemy.height), which is how we create "new" enemies  
   * no asteroidsCreated as parameter so that whats altered is the real var instead of the "copy" of the variable
   * Parameters: Rectangle enemy
   * returns void
   */

  public static void enemyToTop(Rectangle enemy){
    enemy.x = (int)(Math.random()*Asteroids.windowSizeX);
    enemy.y = 0 - enemy.height;
    Asteroids.asteroidsCreated++;
    //System.out.println("new enemy");
  }//end enemyToTop
    
  /* Name: enemyOffScreen
   * Purpose: check if an enemy is off screen, and if so, recreate them at the top of the screen
   * Parameters: enemy, windowSizeY
   * returns void
   */
  public static void offScreen(Rectangle r){
    if (r!=null && r.y >= Asteroids.windowSizeY)
      enemyToTop(r);
  }//end offSreen
  
  /* Name: checkCollision
   * Purpose: to check if a collision between object and enemy have occurred, if so, destroy enemy and add to totalDamage
   * Parameters: obj1, obj2, aka the enemy and the userObject
   * returns void
   */
  public static void checkCollision(Rectangle obj1, Rectangle obj2){
    if(obj2!=null && obj1!= null && obj1.intersects(obj2)){
        enemyToTop(obj2);
        Asteroids.totalDamage = Asteroids.totalDamage + Asteroids.asteroidDamage;
    }//end if
  }//end checkCollision
    
}//end class
