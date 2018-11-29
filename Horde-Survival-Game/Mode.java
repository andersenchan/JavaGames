
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Mode{

  //which mode the user is currently selecting
  static int mode = 1;
  
  //increade the mode variable, selecting the multiplayer mode
  public void add(){
    if(mode<3)
      mode++;
  }
  
  //decrease the mode variable, selecting the single playe mode
  public void subtract(){
    if(mode >1)
      mode--;
  }
  
  public int getMode(){
    return mode;
  }
  
//draw method
  public void draw(Graphics g){
   
    Font unbolded = new Font("Times New Roman", Font.BOLD,  20);
    Font bolded = new Font("Times New Roman", Font.BOLD,  30);
    
    g.setColor(Color.YELLOW);
    
    //instructions
    g.drawString("Use 1 and 2 to scroll between modes. Press 3 to select a mode. ", 10, 50);
    
    //powerup descriptions
    g.drawString("restores health to full", 60, 140);
    g.drawString("restores ammo to full", 60, 210);
    g.drawString("gives speed", 60, 280);
    g.drawString("gives armor", 60, 350);
     
    //decide on mode
    switch(mode){
      
      //bold the survival text since it is the one that is selected, and show description for that mode
      case 1: {
        g.drawString("Description: ", 300, 90);
        g.drawString("Survive against rengars. Press WASD to move, SPACE to shoot, F to dash, can be used once, but can get the resets each time you get a kill", 300, 110);
        g.drawString("Buy items by touching them if you have enough points to but it ", 300, 130);
        g.setFont(bolded);
        g.drawString("Survival", Game.WINDOWX/2 - 30, Game.WINDOWY/2);
        g.setFont(unbolded);
        g.drawString("Multiplayer", Game.WINDOWX/2 - 30, Game.WINDOWY/2 + 50);
        break;
      }
      
      //bold the multiplayer text
      case 2:{ 
        g.drawString("Description: ", 300, 90);
        g.drawString("Destroy each other. P1: WASD and SPACE. P2: ARROW KEYS and ENTER", 300, 110);
        g.drawString("No SCORE, no ITEMS, no DASH, no SPAMMING, just SKILL ",300, 130);
        g.setFont(unbolded);
        g.drawString("Survival", Game.WINDOWX/2, Game.WINDOWY/2);
        g.setFont(bolded);
        g.drawString("Multiplayer", Game.WINDOWX/2, Game.WINDOWY/2 + 50);
        break;
      }
      
      //bold nothing
      default: {
        g.setFont(unbolded);
        g.drawString("Survival", Game.WINDOWX/2, Game.WINDOWY/2);
        g.setFont(unbolded);
        g.drawString("Multiplayer", Game.WINDOWX/2, Game.WINDOWY/2 + 50);
        break;
      }
    }
  }
    
  }//end class