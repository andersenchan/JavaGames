/* Score.java
 * by Andy Chan
 * Date Created: December 16 2014
 * Date of last modification: January 9 2015
 * Purpose: keep track of the player's score
 */

import java.awt.Graphics;
import java.awt.Color;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Score {
  
  /* declare variables */
  
  private static int score = 0;
  public int x,y;
  public String name;
  
  /* Name: Game
   * Purpose: Constructor
   * allows the main class to decide where to display the score on the screen
   */
  public Score(int x, int y){
    this.x = x;
    this.y = y;
  } 
  
  /* mutator and accessor methods */
  
  public static void add(int reward){
    score += reward;
  }
  
  public void subtract(int reward){
    score -= reward;
  }
  
  public void reset(){
    score = 0;
  }
  
  public int getScore(){
    return score;
  }
  
  
  /* Name: draw
   * Purpose: draws the score above the player
   */
  public void draw(Graphics g, Player player){
    g.setColor(Color.BLACK);
    g.drawString("" + score, player.getBounds().x - x, player.getBounds().y - y);
  }

  /* Name: sort
   * Purpose: recieves a string of scores and sorts them in ascending order
   */
  public int[] sort(ArrayList<String> scoreList){
    
    //convert arrayList into array called score
    String[] score = new String[scoreList.size()];
    for(int i = 0; i < scoreList.size(); i++){
      score[i] = scoreList.get(i);
    }
    
    //convert the strrings into ints before you can sort them
    //create a new int[] called scores which stores the integer value of the scores
    int [] scores = token(score);
    
    //sort the int[] of scores using insertion sort
    int i, j, newValue; 
    for (i = 1; i < scores.length; i++) { 
      newValue = scores[i]; 
      j = i; 
      while (j > 0 && scores[j - 1] > newValue) { 
        scores[j] = scores[j - 1]; 
        j--; 
      } 
      scores[j] = newValue; 
    }
    
    //return the sorted array of integers
    return scores;
  }

  /* Name: token
   * Purpose: recieves a string of scores and converts them into integers (using String Tokenizer) so they can be sorted
   */
  public int[] token(String[] scores){
    
    int [] toked = new int[scores.length];
    
    for(int i = 0; i < scores.length; i++){
      
      //create a tokenuizer for each element of the array
      StringTokenizer toke = new StringTokenizer(scores[i]);

      //skips the first token, siunce that is the name, and we want the score
      toke.nextToken();
      
      //convert the score into an int
      toked[i] = Integer.parseInt(toke.nextToken());
    }
    
    //return array of ints
    return toked;
  }
  
  /* Name: search
   * Purpose: goes through every element of the array and returns the position of the score
   */
  public int search(int[] scores, int score){

    //find the position of your score
    for(int i = 0; i < scores.length; i++){
      if(score == scores[i]){
        return i;//return the first instance
      }
    }

    return -1;//if it doesnt exist
  }
  
  /* Name: displayHS
   * Purpose: shows to the user their name and score
   */
  public void displayHS(){
    
    //ask for their name using Joptionpane
    name = JOptionPane.showInputDialog("Game Over\nPlease enter in your name");
    
    JOptionPane.showMessageDialog(null, "Congratulations " +name+ ", with a score of "+ score);
    
    try{
      fileIO(name, score);//see below method
    }
    catch (Exception ex){
      //unnecessary to do anything
      System.out.println("score.txt file could not be found");
    }

  }
  
  public void fileIO(String name, int score) throws FileNotFoundException{
    
    /* read the text file containing all the scores using scanner */
    
    Scanner input = new Scanner (new FileReader("score.txt"));
    
    ArrayList<String>scores = new ArrayList<String>();

    while (input.hasNextLine()){
      scores.add(input.nextLine());
    }
    
    //Add the current Score and Name to arraylist containing the scores
    scores.add(name + " " + score);
    
    /* Writes all the name and score to a text file using PrintWriter */
    PrintWriter out = new PrintWriter("score.txt");
    try{
      for(int i = 0; i < scores.size(); i++){
        out.println(scores.get(i));
      }
    }
    catch(Exception ex) { 
      System.out.println(ex.getMessage());
    } 
    out.close();

    
    //sort the arrayList 
    int[] scoresList = sort(scores);
    
    //search for which position you came in and store it in a variable
    int pos = search(scoresList, score);

    //printing out the high score table to the console
    for(int i = 0; i < scoresList.length; i++){
      System.out.println(scoresList[i]);
    }
    
    JOptionPane.showMessageDialog(null, "See score table below");
//  if(pos > -1)
//    JOptionPane.showMessageDialog(null, "You came in position " + pos+1);
  }
}