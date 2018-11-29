import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.ArrayList;


public class Game extends JFrame implements ActionListener{
  
  /* declare variables */
  
  //title of the game
  final String TITLE = "Cool Game";
  
  //window screen size
  static int windowX = 1200;
  static int windowY = 900;
  
  //location of the levels
  final String[] levels = {"Sprites/TestLevel2.png",
                           "Sprites/TestLevel4.png",
                           "Sprites/TestLevel3.png"};
  
  //Music file locations
  final String endLevelMusic = "Sound Files/musicLevelEnd.midi";
  final String[] levelMusic = {"Sound Files/music0.midi", "Sound Files/music1.midi", "Sound Files/music2.midi"};
  
  int currLevel = -1;
  
  int levelWidth;
  
  Image doubleBufferImage;
  Graphics doubleBufferGraphics;
  
  Timer timer;
  Camera camera;
  BufferedImageLoader loader;
  Background background;
  Score score;
  
  ArrayList<MyObject> myObjects = new ArrayList<MyObject>();
  
  //int[][] strings = new int[1][2];//x and y locations of strings that we want to display in the game such as instructions
  
  
  
  
  static boolean safe = true;
  
  static boolean gameover = false;

  
  /**
   * Constructor of the Game class.
   * @param None
   * @return None
   */
  public Game(){
    
    camera = new Camera(0, 0);
    loader = new BufferedImageLoader();
    background = new Background(windowX, windowY);
    
    //initialize the level
    nextLevel();
    
    //create the timer (which goes off every 10 miliseconds and in this class
    timer = new Timer(10, this);
    timer.start();
    
    //Adds the key event listener
    addKeyListener(new ActionListener());
    
    //setting properties of the JFrame/the game window
    setTitle(TITLE);
    setSize(windowX, windowY);
    setResizable(false);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    setBackground(Color.WHITE);
    
  }//end constructor
  
  
  public class ActionListener extends KeyAdapter{
    
    /**
     * Listens for a key pressed event and calls the appropriate method
     * @param KeyEvent variable e
     * @returns void
     */
    public void keyPressed(KeyEvent e){
      
      int key = e.getKeyCode();
      
      //scroll through myObjects to find a player object to move if a key is Pressed
      for(int i = 0; i < myObjects.size(); i++){
        
        if(myObjects.get(i).getID() == "Player"){
          
          //cast object into a player so to access the player methods
          Player player = (Player)myObjects.get(i);
          
          //left
          if(key == e.VK_A){ 
            player.setVelX(-player.getSpeed());
          }
          
          //right
          if(key == e.VK_D) { 
            player.setVelX(player.getSpeed());
          }
          
          //f key
          if(key == e.VK_F){
            if(player.getFlappyBird()) player.setFlappyBird(false);
            else player.setFlappyBird(true);
          }
          
          //space
          if(key==e.VK_SPACE) player.jump();
        }
      }
    }//end keyPressed Class
    
    /**
     * The event handler that responds when the user releases a key
     * @params: KeyEvent e
     * @returns: void
     */
    public void keyReleased(KeyEvent e){
      
      int key = e.getKeyCode();
      
      //scroll through myObjects to find a player object to move if a key is Pressed
      for(int i = 0; i < myObjects.size(); i++){
        
        if(myObjects.get(i).getID() == "Player"){
          
          Player player = (Player)myObjects.get(i);
          
          if(key==e.VK_A){ 
            player.setVelX(0);
          }
          if(key==e.VK_D){
            player.setVelX(0);
          }
        }
      }
    }//end keyReleased
    
  }//end ActionListener Class
  
  
  /**
   * Recieves an image and then goes through every pixel in it, creating a different object depending on the pixel colour
   * @params: BufferedImage image
   * @returns: void
   */
  private void loadImageLevel(BufferedImage image){
    
    int width = image.getWidth();
    int height = image.getHeight();
    
    levelWidth = width*32;//each pixel = 32 pixels
    
    int red, blue, green;
    
    //loop through every pixel in our image
    for(int xx = 0; xx < width; xx++){
      for(int yy = 0; yy < height; yy++){
        int pixel = image.getRGB(xx,yy);
        
        //get RGB values
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
        
        //System.out.println("RGB: " + red + " " + green + " " + blue);
        
        // if the currPixel is blue, add a player
        if(red == 0 && green == 0 && blue == 255)
          myObjects.add(new Player(xx*32, yy*32));
        
        // if the currPixel is black, add a block
        if(red == 0 && green == 0 && blue == 0)
          myObjects.add(new Block(xx*32, yy*32));
        
        // if the currPixel is red, add a enemy
        if(red == 255 && green == 0 && blue == 0)
          myObjects.add(new Enemy(xx*32, yy*32, xx*32 - Enemy.boundsLength*32, xx*32 + Enemy.boundsLength*32));
        
        // if the currPixel is yellow, add a coin
        if(red == 255 && green == 255 && blue == 0)
          myObjects.add(new Coin(xx*32, yy*32));
        
        // if the currPixel is green, add an endLevel object
        if(red == 0 && green == 255 && blue == 0)
          myObjects.add(new EndLevel(xx*32, yy*32));
        
        /*
        // if the currPixel is Orange, add a String
        if(red == 255 && green == 127 && blue == 39){
          strings[0][0] = xx*32;
          strings[0][1] = yy*32;
        }
        */
      }
    }
  } 
  
  /**
   * Purpose: double buffering, also calls paintComponent, which draws the game
   * @params Graphics g
   * @returns void
   */
  public void paint(Graphics g){//For the double buffering
    
    doubleBufferImage = createImage(getWidth(), getHeight());
    doubleBufferGraphics = doubleBufferImage.getGraphics();
    paintComponent(doubleBufferGraphics);
    g.drawImage(doubleBufferImage, 0, 0, this);
    
  }//end paint method
  
  /* Name: paintComponent
   * Purpose: seperate method for drawing specific items such as the player objects
   * @params Graphics g
   * @returns void
   */
  public void paintComponent(Graphics g){//Choses what you are able to draw
    
    Graphics2D g2D = (Graphics2D)g;
    g2D.translate(camera.getX(), camera.getY());//begin camera
    
    //translate everything that is in between
    ////////////////////////////////////////////////
    
    // Draw the background before anything else.
    // Draw the background at -width, and keep on drawing backgrounds until you reach the levelWidth
    int width = background.getBounds().width;
    for(int i = -width; i < levelWidth; i+= width){
      g.drawImage(background.getImage(), i, 0, width, windowY, null);
    }
    
    //polymorphism, drawing all of our objects
    for(int i = 0; i < myObjects.size(); i++){
      myObjects.get(i).draw(g);
    }
    
    g.setColor(Color.BLACK);
    
    /* this is dumb, we want differenty levels
    for(int i = 0; i < strings.length; i++){
      g.drawString("Press F for Flappy Bird", strings[i][0], strings[i][1]);
    }
    */
    
    //////////////////////////////////////////////
    
    g2D.translate(-camera.getX(), -camera.getY());//end camera
    
    repaint(); 
    
  }//end paint method
  
  /* Name: actionPerformed
   * Purpose: the pulse of the game, loops through everything in this method
   * @params ActionEvent ae
   * @returns void
   */
  public void actionPerformed(ActionEvent ae){
    
    background.update();
    
    //if the current level is over, switch to the next level
    if(myObjects.size() == 0) 
      nextLevel();

    //get the score from the player and set it as your own score
    for(int i = 0; i < myObjects.size(); i++){
      if (myObjects.get(i).getID() == "Player")
        score = ((Player)(myObjects.get(i))).getScore();
    }

    //checks if gameover, an executes the appropriate code
    if(gameover){
      for(int i = 0; i < myObjects.size(); i++){
        myObjects.remove(myObjects.get(i));
      }
      score.displayHS();
      System.exit(0);
    }
    
    //polymorphism
    for(int i = 0; i < myObjects.size(); i++){
      
      //update all objects
      myObjects.get(i).update();
      
      //if the current object is a player
      if(myObjects.get(i).getID() == "Player"){
        camera.update((Player)myObjects.get(i));
        ((Player)myObjects.get(i)).update(myObjects, background);//the player is special, since it has parameters in its update method
      }
    }
  }
  
  //acts as kind of like a mutator method, seting gameOver as false
  public static void over(){
    gameover = true;
  }

  /* Name: nectLevel
   * Purpose: moves on to the next level
   * @params n/a
   * @returns void
   */
  public void nextLevel(){

    //dont start and stop music if youre on the first level
    if(currLevel > -1){
      Music.stop();
      Music.start(endLevelMusic, 0);
    }
    
    currLevel++;
    
    //if there are more levels
    if(currLevel < levels.length){
      
      //try to load the image, and catch if the image could not be found
      try{ 
        //get the image
        BufferedImage levelImage = loader.loadImage(levels[currLevel]); 
        //load the level
        loadImageLevel(levelImage);
      }
      catch(IOException e){
        System.out.println("couls not find a new level");
        e.printStackTrace();
      }
    }
    else
      System.exit(0);
    
    //delay the game, so the end level music can finish playing before the next level starts
    if(currLevel > -1){
      try {   
        //Sets music delay to prevent overlap
        Thread.sleep(6500);//1000 milliseconds is one second.
      } 
      catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }

    //play a random music out of the three available background musics
    int randMusic = (int)(Math.random()*3+1);
    switch (randMusic){
      case 1: Music.start(levelMusic[0], 2);
      break;
      case 2: Music.start(levelMusic[1], 2);
      break;
      case 3: Music.start(levelMusic[2], 2);
      break;
    }
    
  }//end nextLevel
  
  /* Name: main
   * Purpose: the main method is called first when the program starts
   * @params String[] args
   * @returns void
   */
  public static void main(String[] args){
    new Game(); 
  }//end main
  
}//end game




