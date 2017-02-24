package UserInterface;

import java.util.Scanner;

//most of the interface should be done here, converter handles interpreting user commands, and making calls to the model
public class CommandLine {
    
    public static final Scanner myScanner = new Scanner(System.in);
    
    public CommandLine() {
        
    }
    

    public void run(Converter theConverter){

        theConverter.ioCalls("LOAD uparksdata.bin");
        theConverter.ioCalls("SS");
        theConverter.ioCalls("SAVE uparksdata.bin");
        myScanner.close();
    }
}