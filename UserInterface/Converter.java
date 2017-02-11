package UserInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.ParksSystem;

public class Converter {
    
    private ParksSystem mySystem;

    
    public String ioCalls(String theString) {
        
        //user will input commands and arguments, all separated by whitespace
        String[] tokens = theString.split(" ");
        String result = "";
        
        //we need to define all of the useful commands: for the demonstration, there should be 7 commands, one for each user story
         
        if (tokens[0].equalsIgnoreCase("HELP")|| tokens[0].equalsIgnoreCase("H") ) {
            result="\tCommands:\n";
            result+="\t-------------------\n";
            result+="\tSAVE(S) <filename>\n";
            result+="\tLOAD(L) <filename>\n";
            result+="\tSTARTSIM(SS)\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            //mySystem.logout();
        } else if (tokens[0].equalsIgnoreCase("STARTSIM") || tokens[0].equalsIgnoreCase("SS") ) {
            HomeView myHomeView = new HomeView(mySystem);
            myHomeView.run();
            //LoginView myLoginView = new LoginView();
            
        } else if (tokens[0].equalsIgnoreCase("SAVE") || tokens[0].equalsIgnoreCase("S") ) {
        //do a save call  

            try {
                FileOutputStream f_out=new FileOutputStream(tokens[1]);
                ObjectOutputStream oos=new ObjectOutputStream(f_out);
                oos.writeObject(mySystem);
                oos.close();
            } catch (FileNotFoundException e) { 
                e.printStackTrace();
                
            } catch (IOException e) {
                e.printStackTrace();
            } 
            
            
        } else if (tokens[0].equalsIgnoreCase("LOAD") || tokens[0].equalsIgnoreCase("L") ) {

            try {
             // Read from disk using FileInputStream
                FileInputStream f_in = new 
                    FileInputStream(tokens[1]);

                // Read object using ObjectInputStream
                ObjectInputStream obj_in = 
                    new ObjectInputStream (f_in);

                // Read an object
                Object obj = obj_in.readObject();
                if (obj instanceof ParksSystem) {
                    mySystem = (ParksSystem)obj;
                }
                obj_in.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } 
        }
        
        
        return result;
        
    }
    
}