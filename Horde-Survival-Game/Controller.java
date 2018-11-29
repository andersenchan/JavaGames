/* Controller.java
 * Author: Andy Chan
 * Purpose: controlls all the ais that will be created during the game
 * Date of last modification: 9/26/14
 */

import java.awt.Graphics;
import javax.swing.JOptionPane;

public class Controller{
  
  //the array of AIs that the controller will be controlling
  AI[] ai;

  //class variables
  static int maxAI = 10;//max # of AI possible
  static int aiCreated = 0;
  static int round = 1;
  
  int rate = 5;//the rate at which the ai will be sdpawning per second ex. 5%
  
  //constructor
  public Controller(){
    
    ai = new AI[maxAI];
  }

  //method of creating AI
  public void createAI(){
    
    //generate a random number to see if they will be created or not
    int randNum = (int)(Math.random()*100);
    if (randNum < rate){ //%chance of creating an AI
      
      //if there is still enough space in the room for more ai
      if(maxAI>aiCreated){
        ai[aiCreated] =  new AI(randX(),randY());//create the ai in a random location
        ai[aiCreated].setSpeed(ai[aiCreated].getSpeed()+(2*round)); //create the ai with an increasnig speed, the speed will depend on the round lvl, making the game more difficult
        aiCreated++;
      }
//      else if(getEmptyPos() != -1){
//        ai[getEmptyPos()] = new AI(60,60);
//        //aiCreated++;
//      }
    }
  }
  
  //generate random coordinates fot the ai to spawn at
  public int randX(){
   return (int)(Math.random()*Game.WINDOWX);
  }
  public int randY(){
    return (int)(Math.random()*Game.WINDOWY);
  }
  

  //Purpose: if AI hits player, player takes damage
  public void checkAIPlayerCollision(){
    
    //if collision
    for(int i = 0; i < Game.getNumPlayers(); i++){
      for(int y = 0; y < ai.length; y++){
        if(ai[y]!=null && ai[y].getBounds()!=null){
          if(Game.getPlayers(i).getBounds().intersects(ai[y].getBounds())){
            
            //do this
            ai[y].reset();
            Game.getPlayers(i).addDamage(ai[y].getDamage() - Game.getPlayers(i).getArmor());;
          }
        }
      }
    }
  }
  

  // Purpose: if bullet hits AI, destroy AI and add points
  public void checkAIBulletCollision(){
    
    //if collision
    for(int i = 0; i<Game.getNumPlayers(); i++){
      for(int y = 0; y < Game.getAmmoUsed(i); y++){
        for(int z = 0; z < ai.length; z++){
          if(ai[z]!=null && ai[z].getBounds()!=null && Game.getBullet(i, y) != null && Game.getBullet(i, y).getBounds() != null)
            if(Game.getBullet(i, y).getBounds().intersects(ai[z].getBounds())){
            
            //do this
            Game.getScore().add(ai[z].getReward());;   
            ai[z].reset();
            Game.getBullet(i, y).reset();
            Game.getPlayers(i).canDash();
          }
        }
      }
    }
  }
  
  //check if all the ais have been destroyed,if so, move on to the next round
  public void nextRound(){
    if(aiCreated == maxAI){
      if(checkNull() == -1){//since initialized to -1
        
        //set up variables for the next round
        aiCreated = 0;
        round++;
        rate++;
        maxAI++;
        ai = new AI[maxAI];
        
        //let the player know its the next round
        //JOptionPane.showMessageDialog(null, "Round " + round);
      }
    }
  }
  
  //checks if the arrsay of ais is null,if the player has destroyed all of them
  public int checkNull(){
    int x = -1;
    for(int i = 0; i<ai.length; i++){
      if(ai[i].getBounds() != null){
        //the element exists inside the array at position i
        x = i;
      }
    }
    return x;
  }
  
  //reset all the ais
  public void reset(){
      for(int i = 0; i < ai.length; i++){
      ai[i] = null;
    }
  }
  
  //draw the ais 
  public void draw(Graphics g){
    for(int i = 0; i < ai.length; i++){
      if (ai[i]!=null)
        ai[i].draw(g);
    }
    
    //draw the round number
    g.drawString("Round " + round, 300, 100);
  }
  
  //update ais
  public void update(){

    for(int i = 0; i < ai.length; i++){
      if(ai[i]!=null)
        ai[i].update();
    }
    
    //constantly create AIs and check for collisions
      createAI();
      checkAIPlayerCollision();
      checkAIBulletCollision();
      nextRound();
  }
  
}//end class
  
