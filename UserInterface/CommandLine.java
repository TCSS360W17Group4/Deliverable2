package UserInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//most of the interface should be done here, converter handles interpreting user commands, and making calls to the model
public class CommandLine {
    
    static final Scanner myScanner = new Scanner(System.in);
    
    public CommandLine(Converter theConverter) {
        String command = "";
        String result;
        
        //emulate a help command like above
        System.out.println(theConverter.ioCalls("HELP"));
        do
        {
            System.out.printf(">");
            command = myScanner.nextLine();
            result = theConverter.ioCalls(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
        
        
        
        
        //just some cleanup

        myScanner.close();


    }
}