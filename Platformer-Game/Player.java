/* Player.java
   by Andy Chan
   Date Started: December 14 2014
   Date Last Updated: January 9 2015
   Purpose:
   Status: good enough
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Player extends Character {

  /* declare variables */
  
  final static int WIDTH = 32;
  final static int HEIGHT = 32;
  final static int SPEED = 10;
  final static int MAXSPEED = 6;
  
  final static String imageLocation = "Sprites/background0.png";
  final static String spritesheet = "Sprites/marioSpriteSheetWorks.png";
  final static String id = "Player";
  
  int velX = 0;
  double velY = 0;
  
  /* jumping and falling variables */
  
  int jumpSpeed = 10;
  double gravity = 0.5;

  boolean jumping = false;
  boolean falling = true;
  boolean flappyBird = false;//if true, you can jump midair, if not, you must hit theground again before you canjump
  
  /* object variables */
  
  Score score;
  Animation idleAnimation;
  Animation walkRightAnimation;
  Animation walkLeftAnimation;
  
  
  /* Name: Game
   * Purpose: Constructor
   */
  public Player(int x, int y){
    
    super(x, y, WIDTH, HEIGHT, imageLocation, SPEED, id);
    score = new Score(-width/2,10);//have a score for each player, the parameters are the distance from the player that the score will be drawn
    
    //initialize all animations
    idleAnimation = new Animation(5, spritesheet, 6, 17, 19, 27, 37);
    walkRightAnimation = new Animation(5, spritesheet, 8, 11, 149, 33, 36);
    walkLeftAnimation = new Animation(5, spritesheet, 8, 11, 193, 33, 36);
  }
  
  /* Name: draw
   * Purpose: will continuosly draw the player
   */
  public void draw(Graphics g){
    
    score.draw(g, this);
    
    if(velX == 0)
      idleAnimation.draw(g, character.x, character.y, width, height);
    if(velX > 0)
      walkRightAnimation.draw(g, character.x, character.y, width + 2, height);//the walking animations are a bit smaller
    if(velX < 0)
      walkLeftAnimation.draw(g, character.x, character.y, width + 2, height);
    
    //this is just for drawing the collision bounds
    //super.draw(g);

  }
  
  /* Name: update
   * Purpose: everything in this method will be continuously called in the game class
   */
  public void update(ArrayList<MyObject> myObjects, Background background){
    
    idleAnimation.runAnimation();
    walkRightAnimation.runAnimation();
    walkLeftAnimation.runAnimation();
    
    //checking if the player object is falling out of the map 
    if(character.y > Game.windowY) Game.over();
    
    /* Movement */
    
    character.x += velX;
    character.y += velY;
    
     /* Gravity */
    
    if(jumping || falling){ //gravity will only have an effect on you if youre falling or jumping
      
      velY += gravity;//add to it first and then check whether it passes the max speed
      
      if(velY >= MAXSPEED)
        velY = MAXSPEED;
    }
    
    /* Collision */
    
    //collision checks are placed in the player class because the player objects are included in the arrayList of objects, 
    //making it harder to check collision in the game class
    
    //for loop is scrolling through all of the objects in the game
    for(int i = 0; i < myObjects.size(); i++){
      
      MyObject currObj = myObjects.get(i);
      
      /* Block Collision */
      
      if(currObj.getID() == "Block"){
        
        Block currBlock = (Block)currObj;
        
        /* checks top collision of the player */
        if(currBlock.getBoundsDown().intersects(getBoundsUp())){
          
          //stop moving upwards and let gravity fall the player
          character.y = currBlock.getBounds().y + currBlock.getBounds().height;
          velY = 0;
          jumping = false;
        }  
        
        /* checks bottom collision */
        if(currBlock.getBoundsUp().intersects(getBoundsDown())){
          
          /* land */
          character.y = currBlock.getBoundsUp().y - height;//+1 so it intersects the block so it doesnt fall continuously
          velY = 0;
          falling = false;
          jumping = false;
        }
        else
          falling = true;
        
        /* checks left collision */
        if(currBlock.getBounds().intersects(getBoundsLeft())){
          
          //constantly set the player's x to the right of the block, so it appears as if the player isnt moving
          character.x = currBlock.getBoundsRight().x + currBlock.getBoundsRight().width;

        }
        
        /* checks right collision */
        if(currBlock.getBounds().intersects(getBoundsRight())){
          
          //same thing as left collision
          character.x = currBlock.getBounds().x - width;
        }  
        
      }//end Block collision
      
      /* Enemy Collision */
      
      if(currObj.getID() == "Enemy"){
        
        //player kills the enemy
        if(((Enemy)currObj).getBoundsUp().intersects(getBoundsDown())){
          myObjects.remove(currObj);
          Sound sound = new Sound("Sound Files/smw_stomp2.wav");
          sound.play();
        }
        //if they intersect
        else if(currObj.getBounds().intersects(getBounds())){
          if (!Game.safe){
            Game.over();
          }
        }
        
      }//end Enemy collision
      
      if(currObj.getID() == "Coin"){
        
        if(currObj.getBounds().intersects(getBounds())){
          myObjects.remove(currObj);//removes it from the list so it stops updating and drwing so basically its gone from the game
          score.add(((Coin)currObj).getReward());
          //Sound.playCoin();
          //Sound.simonsSound();
          Sound sound = new Sound("Sound Files/Coin.wav");
          sound.play();
        }
        
      }//end Coin Section
      
      if(currObj.getID() == "EndLevel"){
        
        //if they intersect
        if(currObj.getBounds().intersects(getBounds())){

          myObjects.clear();//deleting the level
          
          break;//you dont have to update the objects anymore because they dont exist
        }
        
      }//end EndLevel Section
      
    }//end for loop of myObjects
    
   }//end update method

   /* accessor and mutator methods (some are actually unnecessary) */
   
   public int getSpeed(){
     return SPEED;
   }
   public Score getScore(){
     return score;
   }
   public boolean getFlappyBird(){
     return flappyBird;
   }
   public void setVelY(int velY){
     this.velY = velY;
   } 
   public void setVelX(int velX){
     this.velX = velX;
   }
   public void setX(int x){
     character.x = x;
   }
   public void setY(int y){
     character.y = y;
   }
   public boolean isJumping(){ 
     return jumping;
   }
   public void setJumping(boolean jumping){
     this.jumping = jumping ;
   }
   public void setFalling(boolean falling){
    this.falling = falling;
  }
   public void setFlappyBird(boolean flappyBird){
    this.flappyBird = flappyBird;
  }   
   
  /* Name: jump
   * Purpose: the player jumps when this method is called
   */
   public void jump(){
     if(!flappyBird){
       //only jump if youre not jumping already
       if(!jumping){
         jumping = true;
         velY =  -jumpSpeed;
         Sound sound = new Sound("Sound Files/smw_jump.wav");
         sound.play();
       }
     }
     else{
       //infinite jumping
       jumping = true;
       velY =  -jumpSpeed;
       Sound sound = new Sound("Sound Files/smw_jump.wav");
       sound.play();
     }
   }

}//end player