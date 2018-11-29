
/* Game.java
 * Author: Andy Chan
 * Purpose:
 * Date of creation: 9/15/14
 * Date of last modification: 9/26/14
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

public class Game extends JFrame implements ActionListener{

  //title of the game
  final String TITLE = "Game";
  
  //window screen size
  final static int WINDOWX = 1200;
  final static int WINDOWY = 700;  //change from a constant to a variable so each game instance can have its own screen resolution, involves finding and replacing window x and y

  //for double buffering
  static Image doubleBufferImage;
  static Graphics doubleBufferGraphics;
  
  //Game objects

  //static object vasriables, private so that only the game can change them, b=ut can be accessed through accessor methods or getter methods
  private static int numPlayers = -1;
  private static Player[] players;
  private static Bullet[][] bullet; //Bullet[numPlayers][totalAmmo]
  private static Score score;
  private static int[] ammoUsed;
  private static Powerup powerups;
  
  Mode mode;
  Controller controller;
  Timer timer;

   //ammo variables
  int maxAmmo = 100;//multiples of 4
  int[] ammoLeft;
  
  boolean hasStarted = false;
  static boolean gameOver = false;
  
  //extends KeyAdapter to create the KeyEvent variable/listener
  public class ActionListener extends KeyAdapter{

  /* Name: keyPressed
   * Purpose: listens for the event and calls the appropriate method when a key is pressed
   * @params KeyEvent variable e
   * @returns void
   */
    public void keyPressed(KeyEvent e){
      
      int key = e.getKeyCode();
      
      //choosing the mode 
      if(key == e.VK_1)
        mode.subtract();
      if(key == e.VK_2)
        mode.add();
      
      //selecting the mode
      if(key == e.VK_3){//user wants to select this mode
        if(numPlayers == -1 && hasStarted == false){//the default value, meaning the user has not chose a mode yet
          start();
        }
      }
      
      //Player 2 Controls (Arrow Keys + Enter)
      if (numPlayers > 1){ //only allow input for player 2 if there are 2 or more players
        if(key == e.VK_LEFT){
          players[1].moveLeft();
        }
        if(key == e.VK_RIGHT)
          players[1].moveRight();
        
        if(key == e.VK_UP){         
          players[1].moveUp();
        }
        if(key == e.VK_DOWN){
          players[1].moveDown();
        }
        if(key == e.VK_ENTER){

          //if Player at 2 still has ammo left, fire bullets
          if(ammoUsed[1] < maxAmmo)
            shootBullets(1);
        }
      }
      
      //Player 1 Controls (WASD + Space)
      if(numPlayers>-1){
      if(key == e.VK_A){
        players[0].moveLeft();
        players[0].setXDirection(-1); //setDirection is to determine the direction of the dash, onbly relevant for first player
      }
      if(key == e.VK_D){
        players[0].moveRight();
        players[0].setXDirection(1);//set direction to right
      }
      if(key == e.VK_W){
        players[0].moveUp();
        players[0].setYDirection(-1);//set direction to up
      }
      if(key == e.VK_S){
        players[0].moveDown();
        players[0].setYDirection(1);//set direction to down
      }
      if(key == e.VK_SPACE){
        if(ammoUsed[0] < maxAmmo)
          shootBullets(0);
      }
      if(key == e.VK_F){
        if(numPlayers == 1)
        players[0].dash(); //because dash is OP when used against real players
      }
      if (key == e.VK_ESCAPE){//If user preses escape, game closes
        System.exit(0);
      }
      }
    }//end keyPressed Class
    
    /* Name: keyReleased
     * Purpose: This is the EVENT HANDLER that responds when the user releases a key
     *          change thevelocity and direcvtion to 0 when the user lets go of a key
     * @params: KeyEvent e
     * @returns: void
     */
    public void keyReleased(KeyEvent e){
      
      int key = e.getKeyCode();
      
      //Player 2
      if (numPlayers > 1){
      if(key == e.VK_UP)
        players[1].setVelY(0);
      
      if(key == e.VK_DOWN)
        players[1].setVelY(0);
      
      if(key == e.VK_LEFT)
        players[1].setVelX(0);
      
      if(key == e.VK_RIGHT)
        players[1].setVelX(0);
      }
      
      //Player1
      if(numPlayers>-1){
      if(key == e.VK_W){
        players[0].setVelY(0);
        players[0].setYDirection(0);
      }
      if(key == e.VK_S){
        players[0].setVelY(0);
        players[0].setYDirection(0);
      }
      if(key == e.VK_A){
        players[0].setVelX(0);
        players[0].setXDirection(0);
      }
      if(key == e.VK_D){
        players[0].setVelX(0);
        players[0].setXDirection(0);
      }
      }
      
    }//end keyReleased
    
  }//end ActionListener Class
  
  /* Name: Game
   * Purpose: Constructor
   */
  public Game(){

    
    //creating all our objects
    score = new Score();
    controller = new Controller();
    powerups = new Powerup();
    mode = new Mode();
    
    //create the timer (which goes off every 10 miliseconds and in this class)
    timer = new Timer(10, this);
    timer.start();

    addKeyListener(new ActionListener());//Adds the key event listener

    //setting properties of the JFrame/the game window
    setTitle(TITLE);
    setSize(WINDOWX, WINDOWY);
    setResizable(false);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    setBackground(Color.BLACK);
    
  }//end constructor
  
  /* Name: paint
   * Purpose: draws game
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
    
    if(hasStarted){
    //draw the background first, order matters
  // Background.draw(g);
   
    //drw players
    for(int i = 0; i < numPlayers; i++)
       players[i].draw(g, i);
    
    //draw bullets
    for(int i = 0; i < maxAmmo; i++){
      for(int y = 0; y < numPlayers; y++){
      if (bullet[y][i] != null)
       bullet[y][i].draw(g);
    }
    }

     //draw the #ammo left, i  =playerNum
    g.setColor(Color.YELLOW);
     for(int i = 0; i < numPlayers; i++){//Know how much ammo left
      g.drawString("P" + i + " Mana Left: " + ammoLeft[i] , WINDOWX - 150, WINDOWY*7/8 + i*10);
     }
     
     //draaw all the ais
     if(numPlayers == 1)
       controller.draw(g);
     
     //draw the powerups
     g.drawString("shop", 30, 70);
     score.draw(g);
     powerups.draw(g);
     
     //draw the game Over screen
     if(gameOver){
     g.setFont(new Font("Arial Bold", Font.BOLD, 200));
     g.drawString("Game Over", 100, WINDOWY/2);
     }
  }
    
    //draw items in the pre game menu
    else{
      mode.draw(g);
      powerups.draw(g);
    }
    
    repaint(); 
    
  }//end paint method
  
  /* Name: actionPerformed
   * Purpose: game loop that causes all other required updates to occur
   * @params ActionEvent e
   * @returns void
   */
  public void actionPerformed(ActionEvent e){

    //the actual game
    if(hasStarted){
      
    //update player
    for(int i = 0; i < numPlayers; i++){
      players[i].update();
      ammoLeft[i] = maxAmmo - ammoUsed[i];
    } 
    
    //update bullets
    for(int i = 0; i < maxAmmo; i++){
      for(int y = 0; y < numPlayers; y++){
        if (bullet[y][i] != null)
          bullet[y][i].update();
      }
    }
    
    //refill the ammo for 2 player mode
    if(numPlayers > 1){
    for(int i = 0; i < numPlayers; i++){
      if(ammoUsed[i] > 0)
      ammoUsed[i]--;
    }
    }
    
    //update the ais
    if(numPlayers == 1)
      controller.update();

    }
    
    //updates in the pre game menu as well
    //update the powerups;
     powerups.update();
  }//end actionPerformed Method
  
  //starts the game
  public void start(){
    
    numPlayers = mode.getMode();
    hasStarted = true;
    
    //creates objects now that numPlayers has been initialized
    players = new Player[numPlayers];
    ammoUsed = new int[numPlayers];
    ammoLeft = new int[numPlayers];
    bullet = new Bullet[numPlayers][maxAmmo];
    
    //creates the players if they dont exist
    for(int i = 0; i < numPlayers; i++){
        players[i] = new Player(WINDOWX/4 + (i*WINDOWX/2), WINDOWY/2);//Populates the players array, params is the Start Location
        ammoUsed[i] = 0;//Sets full ammo for each player
    }
  }
  
  //mutator method
  public static void over(){
     gameOver = true;
  }
  
  //all of the accessor methods
  public static int getNumPlayers(){
    return numPlayers;
  }
  public static Player getPlayers(int i){
    return players[i];
  }
  public static Bullet getBullet(int i, int y){
    return bullet[i][y];
  }
  public static Score getScore(){
    return score;
  }
  public static int getAmmoUsed(int i){
    return ammoUsed[i];
  }
  public static void refillAmmo(int i){//not an accessor method
    ammoUsed[i] = 0;
  }
  //end accessormethods
  
    //move the object to asn empty slot in the array instead of the latest spot to save space
//  public int getEmptyPos(){
//    for(int i = 0; i < ai.length; i++){
//      if (ai[i] == null)
//        return i; 
//    }
//    return -1;
//  }
  
  /* Name: shootBullets
   * Purpose: fire bullets from player object in all 4 directions
   * @params int playerNum
   * @returns void
   */
  public void shootBullets(int playerNum){
    
    //original code
//       bullet[1][ammoUsed[1] + 0] = new Bullet(players[1].getBounds().x + players[1].getBounds().width/2, players[1].getBounds().y - players[1].getBounds().height + 20);//bullet up
//       bullet[1][ammoUsed[1] + 1] = new Bullet(players[1].getBounds().x + players[1].getBounds().width/2, players[1].getBounds().y + players[1].getBounds().height + 20);//bullet down
//       bullet[1][ammoUsed[1] + 2] = new Bullet(players[1].getBounds().x - players[1].getBounds().width/2, players[1].getBounds().y + players[1].getBounds().height/2);//bullet left
//       bullet[1][ammoUsed[1] + 3] = new Bullet(players[1].getBounds().x + players[1].getBounds().width + 20, players[1].getBounds().y + players[1].getBounds().height/2);//bullet right
//       ammoUsed[1] += 4;
    
    //vertical bullets
    for(int i = 0; i < 2; i++){
      bullet[playerNum][ammoUsed[playerNum]] = new Bullet(players[playerNum].getBounds().x + players[playerNum].getBounds().width/2, players[playerNum].getBounds().y - players[playerNum].getBounds().height + 20 + (i*(2*players[playerNum].getBounds().height)));
      bullet[playerNum][ammoUsed[playerNum]].setDirection(i); //set the direction, since each bullet has itsown direction
      ammoUsed[playerNum]++;
    }
    
    //horizontal bullets
    for(int i = 0; i < 2; i++){
      bullet[playerNum][ammoUsed[playerNum]] = new Bullet(players[playerNum].getBounds().x - players[playerNum].getBounds().width/2 + (i*(2*players[playerNum].getBounds().width)), players[playerNum].getBounds().y + players[playerNum].getBounds().height/2);
      bullet[playerNum][ammoUsed[playerNum]].setDirection(i+2);
      ammoUsed[playerNum]++;
    }
  }
  
  /* Name: reset
   * Purpose: to reset the game
   */
  public static void reset(){

    //int playAgain = Integer.parseInt(JOptionPane.showInputDialog("GameOver, PlayAgain?"));
    //JOptionPane.showMessageDialog(null, "GameOver, Goodbye");
//    if (playAgain == 0){
//      for (int i = 0; i < numPlayers; i++)
//        players[i].resetHealth(); 
//    }
//    else 
      //System.exit(0);
  }
  

  /* Name: main
   * Purpose: main method/to run the game
   * @params String[] args
   * @returns void
   */
  public static void main(String[] args){

   //windowX = Integer.parseInt(JOptionPane.showInputDialog("screen resolution for X?"));
    
//   numPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players? Press 1 for solo survival mode, Press 2 for competitive mode"));
//   
//   while (numPlayers>2)
//     numPlayers = Integer.parseInt(JOptionPane.showInputDialog(null, "Sorry, only max 2 players are allowed. Try Again"));
//   
//   if(numPlayers == 1){
//   JOptionPane.showMessageDialog(null, "Survive against rengars. WASD to move, SPACE to shoot, F to dash, can be used once, but can get the resets each time you get a kill/n Buy items by touching them if you have enough pointsto but it ");
//   }
//   
//   if (numPlayers == 2){
//   JOptionPane.showMessageDialog(null, "Destroy each other. P1: WASD and SPACE. P2: ARROW KEYS and ENTER \n No SCORE, no ITEMS, no DASH, no SPAMMING, just SKILL");
//   }
     
    new Game();    
    
  }//end main
  
}//end JavaGame class
