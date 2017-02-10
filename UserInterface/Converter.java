package UserInterface;

import model.ParksSystem;

public class Converter {
    
    private ParksSystem mySystem;

    
    public String placeHolderName(String theString) {
        
        //user will input commands with following arguments, all separated by whitespace
        String[] tokens = theString.split(" ");
        String result = "";
        
        //we need to define all of the useful commands: for the demonstration, there should be 7 commands, one for each user story
        if (  tokens[0].toUpperCase() == "1"  ) {
            //do a call
            //example: '1' could come from user trying to log in as a volunteer, call login method for volunteer
            //the string at index = 1 could be the userName that they are trying to log in with
            mySystem.login(tokens[1]);
            
        } 
        else if (tokens[0].toUpperCase() == "HELP" || tokens[0].toUpperCase() == "H") {
            result="Commands:\n";
            result+="-------------------\n";
            result+="\tSAVE(S) <filename>\n";
            result+="\tLOAD(L) <filename>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            //mySystem.logout();
        }
        
        
        
        
        
        
        return result;
        //problem: the intention is to pass control from parkssystem over to a controller: that's not going to be the case anymore.
    }
    
}