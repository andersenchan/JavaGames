
/* Program Name: Asteroids
 * Author: Andy Chan
 * Date: 1/9/ 2014
 * Comments: 
 */

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Asteroids extends JFrame implements ActionListener{

  //DECLARING & INITIALIZING VARIABLES
  
  //create the timer variable for our game loop
  //fires an action event after a delay(which you specify); it's used to repeatedly perfrom a task
  Timer timer;
  
  //our window screen size
  final static int windowSizeX = 700;
  final static int windowSizeY = 600;
  
  //game variables
  static int highScore = 0;
  static int score = 0, reward = 20;
  static int asteroidsCreated = 0;
  static int totalDamage = 0, asteroidDamage = 20, maxHealth = 100, currentHealth;
  boolean gameOver = false;
  int playAgain;
  boolean upPressed = false;
  final int sBackgroundSpeed = 8;
  
  final int objectWidth = 50;
  final int objectHeight = 49;
  final int objectY = windowSizeY - (objectHeight + 50);
  final int objectX = windowSizeX/2;
  
  //creating our objects (of the rectangle class) using the NEW operator
  //creating a rectangle which we will use as our objects
  //Rectangle x = new Rectangle(x, y, width, height);
  Rectangle sBackground1 = new Rectangle(0,0,windowSizeX, windowSizeY);
  Rectangle sBackground2 = new Rectangle(0,-windowSizeY,windowSizeX, windowSizeY);
  
  Rectangle object = new Rectangle(objectX, objectY, objectWidth, objectHeight);  
  int objectSpeedX = 20, objectSpeedY = 10;
  
  Rectangle bullet;
  Rectangle enemies[] = new Rectangle[8];
  final int[] enemySpeed = {13, 12, 11, 10, 9, 8, 7, 6};
  final int[] enemySize = {30, 40, 50, 60, 70, 77, 85, 90};
  
  static Rectangle powerUp = new Rectangle((int)(Math.random()*windowSizeX), 0, 50, 50);
  
  //create my images/sprites variables; store images in these variables
  Image background;
  Image spaceShip;
  Image asteroid;
  Image powerup;
    
  //for double buffering
  Image doubleBufferImage;
  Graphics doubleBufferGraphics;
  
  
  //extends KeyAdapter to create the KeyEvent variable/listener
  public class ActionListener extends KeyAdapter{
    
    /* Name: keyPressed
     * Purpose: This is the EVENT HANDLER that responds when the user presses a key
     * parameters: KeyEvent e - stores the key the user pressed
     * returns: void
     */
    public void keyPressed(KeyEvent e){
      
      //stores the value of button they press indide this int
      int key = e.getKeyCode();
      
      /* MOVING OBJECT USING ARROW KEYS + COLLISION DETECTIONS
       * if the button the user pressed is the left arrow key button, 
       * AND if the object doesnt touch the borders(x>0)
       * move the object left by subtracting an integer from the x coordinate of the object
       * if the x = 0, keep it at 0(dont let it move any further)
       */
      if(key == e.VK_LEFT){
        if(object.x <= 0)
          object.x = 0;
        else
          object.x = object.x - objectSpeedX; 
      }
      
      if(key == e.VK_RIGHT){
        if(object.x >= windowSizeX - objectWidth)
          object.x = windowSizeX - objectWidth;
        else
          object.x = object.x + objectSpeedX;
      }
      
      if(key == e.VK_UP){
        upPressed = true;
        if(object.y <= 0)
          object.y = 0+objectHeight;
        else
          object.y = object.y - objectSpeedY;
      }
      
      if(key == e.VK_DOWN){
        if(object.y >= windowSizeY - objectHeight)
          object.y = windowSizeY - objectHeight;
        else
          object.y = object.y + objectSpeedY;
      }
      
      //if user presses space, creates a bullet object
      if(key == e.VK_SPACE){
        if (Bullet.canShoot = true){
          Bullet.canShoot = false;
          Bullet.bullet = new Rectangle(object.x + object.width/2, object.y - object.height, 4, 12);
        }//end if
      }
      
    }//end keyPressed Class
    
    /* Name: keyReleased
     * Purpose: This is the EVENT HANDLER that responds when the user releases a key
     * parameters: KeyEvent e
     * returns: void
     */
    public void keyReleased(KeyEvent e){
      
      int key = e.getKeyCode();
      
      //allows for a sprite change when user presses up
      if(key == e.VK_UP)
        upPressed = false;
      
    }//end keyReleased
    
  }//end ActionListener Class
  
  /* Name: JavaGame
   * Purpose: Constructor Method
   */
  public Asteroids(){
    
    //load images
    
    ImageIcon i = new ImageIcon("E:/Compture Science/JavaGame V2/Sprites/AsteroidsAnimate.jpg");
    background = i.getImage();
    ImageIcon ship = new ImageIcon("E:/Compture Science/JavaGame V2/Sprites/ship.gif");
    spaceShip = ship.getImage();
    ImageIcon kaboom = new ImageIcon("E:/Compture Science/JavaGame V2/Sprites/Asteroid (2).png");
    asteroid = kaboom.getImage();
    ImageIcon pickup = new ImageIcon("E:/Compture Science/JavaGame V2/Sprites/ship.gif");
    powerup = pickup.getImage();
    
    //create the timer object (which goes off every 10 miliseconds and in this class)
    timer = new Timer(10, this);
    //start the timer so that from now on it starts going off every 10 miliseconds
    timer.start();
    
    //creates our event handler
    addKeyListener(new ActionListener());
    
    //setting properties of the JFrame/the game window
    setTitle("Asteroids");
    setSize(windowSizeX, windowSizeY);
    setResizable(false);//the user cant resize the window
    setVisible(true);//the user can see the window
    
    //this methood changes what happens when you press the close button
    //exits the application when the user presses the close button
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
  }//end constructor
  
  /* Name: paint
   * Purpose: Double Buffering
   * Parameters: Graphics g
   * Returns: void
   */
  public void paint(Graphics g){
    
    //creates an image of the current screen and saves it into the image var
    doubleBufferImage = createImage(getWidth(), getHeight());
    //gets the graphics of the image and stores it into dbg
    doubleBufferGraphics = doubleBufferImage.getGraphics();
    //call paintComponent method to paint the graphics/paint the creen
    paintComponent(doubleBufferGraphics);
    //draws the dbImage at (0,0)
    g.drawImage(doubleBufferImage, 0, 0, this);
    
  }//end paint method
  
  /* Name: paintComponent
   * Purpose: to draw/paint grphics on the screen, much like the gameMaker draw tab
   * Parameters: Graphics g
   * Returns: void
   */
  public void paintComponent(Graphics g){
    
    //this is the proper syntax:
    //g.drawImage(image, x, y, width, height, observer);
    //g.drawString("string", x, y);
    //g.fillRect(x, y, length, height);
    
    //start off by drawing the layer that will be behind everything else, the background
    //drawing two screen-sized images that will act as the "scrolling background"
    g.setColor(Color.BLACK);
    g.drawImage(background, sBackground1.x, sBackground1.y, sBackground1.width, sBackground1.height, this);
    g.setColor(Color.BLUE);
    g.drawImage(background, sBackground2.x, sBackground2.y, sBackground1.width, sBackground1.height, this);
    
    //drawing the health bar based on current healthg
    g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
    g.setColor(Color.RED);
    g.drawString("Health Bar", 40, 50);
    currentHealth = maxHealth - totalDamage;
    g.fillRect(40, 60, currentHealth, 10);
    
    //display the score
    g.drawString("" + score, windowSizeX - 40, 50);
    
    //drawing/filling the rectangles that were created:
    
    //change the sprite of the object when the user presses the up key
    if (upPressed == false){
      g.setColor(Color.RED);
      g.drawImage(spaceShip, object.x, object.y, this);
    }//end if
    else if(upPressed){
      g.setColor(Color.YELLOW);
      g.drawImage(spaceShip, object.x, object.y, this);
    }//end else
    
    //draw the bullet only if it exists
    if (bullet!=null){
      g.setColor(Color.YELLOW);
      g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
    }//end if
    
    if (powerUp!=null)
      g.drawImage(powerup, powerUp.x, powerUp.y, powerUp.width, powerUp.height, this);
    
    //drawing the enemies only if it exists
    if (enemies[0]!=null)
      g.drawImage(asteroid, enemies[0].x, enemies[0].y, enemySize[0], enemySize[0], this);
    if (enemies[1]!=null)
      g.drawImage(asteroid, enemies[1].x, enemies[1].y, enemySize[1], enemySize[1], this);
    if (enemies[2]!=null)
      g.drawImage(asteroid, enemies[2].x, enemies[2].y, enemySize[2], enemySize[2], this);
    if (enemies[3]!=null)
      g.drawImage(asteroid, enemies[3].x, enemies[3].y, enemySize[3], enemySize[3], this);
    if (enemies[4]!=null)
      g.drawImage(asteroid, enemies[4].x, enemies[4].y, enemySize[4], enemySize[4], this);
    if (enemies[5]!=null)
      g.drawImage(asteroid, enemies[5].x, enemies[5].y, enemySize[5], enemySize[5], this);
    if (enemies[6]!=null)
      g.drawImage(asteroid, enemies[6].x, enemies[6].y, enemySize[6], enemySize[6], this);
    if (enemies[7]!=null)
      g.drawImage(asteroid, enemies[7].x, enemies[7].y, enemySize[7], enemySize[7], this);
    
    //if game over, cover up the whole screen with the black game over screen
    if (gameOver){
      g.setColor(Color.BLACK);
      g.fillRect(0,0,windowSizeX, windowSizeY);
      g.setColor(Color.GREEN);
      g.drawString("Game Over", windowSizeX/3, windowSizeY/2);
      g.drawString("High Score:\t" + highScore, windowSizeX/3, windowSizeY/2 + 30);
    }//end if
    
    //repaints, recalls this method for us so that everything is constantly being painted only at their current position
    repaint();
    
  }//end paint method
  
  
  /* Name: scrollingBackground 
   * Purpose: the two images(scrolling backgrounds) will move down constantly, until they reach the bottom of the screen, 
   *          at which point they will reappear at the top of the screen, creating a scrolling effect
   * Parameters: none
   * Returns: void
   */
  public void scrollingBackground(){
    sBackground1.y = sBackground1.y + sBackgroundSpeed;
    sBackground2.y = sBackground2.y + sBackgroundSpeed;
    if(sBackground1.y >= windowSizeY)
      sBackground1.y = -windowSizeY;
    if(sBackground2.y >= windowSizeY)
      sBackground2.y = -windowSizeY;
  }//end scrollingBackground
  
  
  /*Name: moveObject
   * Purpose: move any object
   * Parameters: whichever object you are moving , and the speed of that particular objecty
   * returns void
   */
  public void moveObject(Rectangle r, int rSpeed){
    if (r!=null)
      r.y = r.y + rSpeed;
  }//end moveObject
  
  /* Name: 
   * Purpose: Constantly executes everything in this method
   * this action listener is listening for when the timer goes off and executes everything in the mwehod
   * Parameters:
   * Returns:
   */
  public void actionPerformed(ActionEvent e){
    
    //continuously call the scrolling background method to scroll the background
    scrollingBackground();
    
    //constantly check if you have died
    death(enemies);
    
    //moving the player object down to make them feel more like theyre in space
    //this will also encourage the player to move up so that it will be more dangerous/exciting to the player
    if (object.y <= windowSizeY - objectHeight)
      object.y = object.y+1; 
    
    //create a bullet/enemies object in this class (so we can use them) that we get from the other classes
    bullet = Bullet.getBullet();
    
    //create more enemies as the game progresses by calling the enemyCreate method from the enemies class
    enemies[0] = Enemies.enemyCreate(enemies[0],  0, enemySize[0], asteroidsCreated);
    enemies[1] = Enemies.enemyCreate(enemies[1],  2, enemySize[1], asteroidsCreated);
    enemies[2] = Enemies.enemyCreate(enemies[2],  4, enemySize[2], asteroidsCreated);
    enemies[3] = Enemies.enemyCreate(enemies[3],  8, enemySize[3], asteroidsCreated);
    enemies[4] = Enemies.enemyCreate(enemies[4], 15, enemySize[4], asteroidsCreated);
    enemies[5] = Enemies.enemyCreate(enemies[5], 20, enemySize[5], asteroidsCreated);
    enemies[6] = Enemies.enemyCreate(enemies[6], 25, enemySize[6], asteroidsCreated);
    enemies[7] = Enemies.enemyCreate(enemies[7], 30, enemySize[7], asteroidsCreated);
    
    //move enemies (and bullets) constantly using the moveObject method
    moveObject(bullet, Bullet.bulletSpeed);
    moveObject(powerUp, 5);
    moveObject(enemies[0], enemySpeed[0]);
    moveObject(enemies[1], enemySpeed[1]);
    moveObject(enemies[2], enemySpeed[2]);    
    moveObject(enemies[3], enemySpeed[3]); 
    moveObject(enemies[4], enemySpeed[4]);    
    moveObject(enemies[5], enemySpeed[5]); 
    moveObject(enemies[6], enemySpeed[6]);    
    moveObject(enemies[7], enemySpeed[7]);
    
    //creating and destroying thee powerUp object
    //FIRST check if you can create the powerUp, THEN create if if (canCreate)
    PowerUp.canCreate();
    powerUp = PowerUp.createPowerUp(powerUp);
    powerUp = PowerUp.offScreen(powerUp);
    //String test = PowerUp.test(powerUp);
    //System.out.println("" + test);
    
    //checking if our objects are off screen
    Enemies.offScreen(enemies[0]);
    Enemies.offScreen(enemies[1]);
    Enemies.offScreen(enemies[2]);
    Enemies.offScreen(enemies[3]);
    Enemies.offScreen(enemies[4]);
    Enemies.offScreen(enemies[5]);
    Enemies.offScreen(enemies[6]);
    Enemies.offScreen(enemies[7]);
    Bullet.offScreen();
    
    //constantly check for collision between object and enemies
    Enemies.checkCollision(object, enemies[0]);
    Enemies.checkCollision(object, enemies[1]);
    Enemies.checkCollision(object, enemies[2]);
    Enemies.checkCollision(object, enemies[3]);
    Enemies.checkCollision(object, enemies[4]);
    Enemies.checkCollision(object, enemies[5]);
    Enemies.checkCollision(object, enemies[6]);
    Enemies.checkCollision(object, enemies[7]);
    
    PowerUp.checkCollision(object, powerUp);
    
    //constantly check for collision between bullet and enemies
    Bullet.checkCollision(bullet, enemies[0]);
    Bullet.checkCollision(bullet, enemies[1]);
    Bullet.checkCollision(bullet, enemies[2]);
    Bullet.checkCollision(bullet, enemies[3]);
    Bullet.checkCollision(bullet, enemies[4]);
    Bullet.checkCollision(bullet, enemies[5]);
    Bullet.checkCollision(bullet, enemies[6]);
    Bullet.checkCollision(bullet, enemies[7]);
    
  }//end actionPerformed Method
  
  /* Name: death
   * Purpose: check if death occurs, if it has, reset everything by making everthing = 0 + call playAgain method
   * Parameters: the enmies recangles so that we can make them null, resetting the game
   * Returns:void
   */
  public void death(Rectangle[] enemies){
    if (totalDamage == maxHealth){
      System.out.println("death has occured");
      for (int i = 0; i < enemies.length; i++){
        enemies[i] = null;
      }
      asteroidsCreated = 0;
      totalDamage = 0;
      highScore();
      gameOver = true;
      playAgain();
      score = 0;
      
    }//end if
  }//end death
  
  /* Name: playAgain
   * Purpose: checks if the user wants to playAgain; I used a do while loop so that will only loop if user presses the invalid response
   * Parameters: none
   * Returns:void
   */
  public void playAgain(){
    playAgain = Integer.parseInt(
                                 JOptionPane.showInputDialog("You have died with a score of " + score + ". Would you like to play again? 1 = yes 0 = no"));
    do{  
      if (playAgain == 1){//wants to play again
        gameOver = false;
        break;
      }
      else if(playAgain == 0)//doesnt want
        System.exit(0);
      else //invalid response
        playAgain = Integer.parseInt(
                                     JOptionPane.showInputDialog("Invalid Response. Please try again. 1 = yes 0 = no"));
    }while (playAgain != 1 || playAgain != 0);    
    
  }//end playAgain

  /* Name: highScore
   * Purpose: calculate the highScore and update it if the current score is higer than the highScore
   * Parameters: none
   * Returns:void
   */
  public void highScore(){
    if (highScore <= score)
      highScore = score;
  }//end highscore
  
  /* Name: main
   * Purpose: main method
   * Parameters: String[] args
   * Returns:void
   */
  public static void main(String[] args){
    
    JOptionPane.showMessageDialog(null, "Welcome to Asteroids, a game where you have to shoot and destroy oncoming asteroids to make your way through space. \nYou will use your arrow keys to move the ship, and the space bar to shoot lasers to destroy the asteroids. \nYou will obtain points by destroying the asteroids.");
    new Asteroids();    
    
  }//end main
  
}//end JavaGame class
