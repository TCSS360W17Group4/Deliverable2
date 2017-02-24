package UserInterface;

import java.util.Scanner;

//most of the interface should be done here, converter handles interpreting user commands, and making calls to the model
public class CommandLine {
    
    public static final Scanner myScanner = new Scanner(System.in);
    
    public CommandLine() {
        
    }
    
    /**
     * I/O Interaction with user before one of the three Views take over
     * 
     * Displays the initial Urban Parks prompt to the user for login
     * 
     * Tries to pass control over to one of the "views" once user is logged in
     */
    public void run(Converter theConverter){
        String command = "";
        String result;
        
        //emulate a help command
        System.out.println(theConverter.ioCalls("HELP"));
        do
        {
            System.out.printf(">");
            command = myScanner.nextLine();
            result = theConverter.ioCalls(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );

        myScanner.close();
    }
}