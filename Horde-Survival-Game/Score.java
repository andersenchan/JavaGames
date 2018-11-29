/* Score.java
 * Author: Andy Chan
 * Purpose: keep track of the player's score
 * Date of last modification: 9/26/14
 */

import java.awt.Graphics;
import java.awt.Color;

public class Score {
  
  //class variables
  private int x = Game.WINDOWX - 100;
  private int y = 100;
  
  private static int highest = 0;
  private static int score = 0;
  
  //mutator methods{
  
  public static void add(int reward){
    score += reward;
  }
  public void subtract(int reward){
    score -= reward;
  }
  public void reset(){
    score = 0;
  }
  
  //}end mutato rmethods
  
  //accessor methods{
  
  public int getScore(){
    return score;
  }
  
//  public int getMax(){
//    if (highest <= score)
//      return highest = score;
//    else
//      return highest = highest;
//  }
  
  //}end accessor methods

  //draw the score
  public void draw(Graphics g){
    g.setColor(Color.YELLOW);
    g.drawString("" + score, x, y);
  }
}