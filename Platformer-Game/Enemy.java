import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Enemy extends Character {
  
  final static int WIDTH = 32;
  final static int HEIGHT = 32;
  final static int SPEED = 5;
  final static String imageLocation = "";
  final static String id = "Enemy";
  
  //this is defenitely a problem, this isnt how i wanted it to work
  final static int boundsLength = 0;//the length that the enemy can travel before they have to switch direction
  
  int velX = 0;
  int velY = 0;
  
  Animation enemyAnimation;

  public Enemy(int x, int y, int leftBoundary, int rightBoundary){
    
    super(x, y, WIDTH, HEIGHT, imageLocation, SPEED, id);
    
    enemyAnimation = new Animation(5, SpriteSheetLocations.ENEMY, 2, 0, 2, 18, 11);
    
    setBoundaries(leftBoundary, rightBoundary);
    
    moveLeft();//move left initially when it spawns
  }

  public void switchDirection(){
    if(character.x <= boundaryXL)
      moveRight();
    else if((character.x + width) >= boundaryXR)
      moveLeft();
  }

  public void moveRight(){
    if((character.x - width) < boundaryXR)
      velX = SPEED;
  }

  public void moveLeft(){
    if(character.x > boundaryXL)
      velX = -SPEED;
  }
  
  public void update(){
    character.x += velX;
    switchDirection();
    
    enemyAnimation.runAnimation();
  }
  
  public void draw(Graphics g){
    enemyAnimation.draw(g, character.x, character.y, width, height);
  }
  
}