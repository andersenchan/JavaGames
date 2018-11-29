/* Music.java
   by Andy Chan
   Modification Started: January 6 2015
   Date Last Updated: January 9 2015
   Purpose:To play music within the program
*/


import java.io.*;
import javax.sound.midi.*;
   
public class Music {
  
   private static Sequencer midiPlayer;
   
   //initializes, takes in the filename and how many loops
   public static void start(String fileName, int loop) {
     
      try {
        
        //create the file
         File file = new File(fileName);
         
         Sequence song = MidiSystem.getSequence(file);
         
         midiPlayer = MidiSystem.getSequencer();
         midiPlayer.open(); 
         midiPlayer.setSequence(song); //set the song in the parameters as the sequence
         midiPlayer.setLoopCount(loop); // repeat 0 times (play once)
         midiPlayer.start(); //start the sequence
         
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
         
      } catch (InvalidMidiDataException e) {
         e.printStackTrace();
         
      } catch (IOException e) {
         e.printStackTrace();
         
      }
   }
   
   //stops the sopng by closing the sequence
   public static void stop(){
     midiPlayer.close();
   }
}