package UserInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//most of the interface should be done here, converter handles interpreting user commands, and making calls to the model
public class CommandLine {
    
    
    public CommandLine(Converter theConverter) {
        String command;
        InputStreamReader isr = new InputStreamReader(System.in);
        Scanner myScanner = new Scanner(System.in);
        //emulate a help command like above
        System.out.println(theConverter.ioCalls("HELP"));
        do
        {
            System.out.println(">");
            command = myScanner.nextLine();
            theConverter.ioCalls(command);
            System.out.println(command + "\n");
        } while ((command!="QUIT")&&(command!="Q")&&(command!="quit")&&(command!="q"));
        
        
        
        
        //just some cleanup
        try {
            isr.close();
            myScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}