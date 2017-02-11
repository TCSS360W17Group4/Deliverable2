package UserInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//most of the interface should be done here, converter handles interpreting user commands, and making calls to the model
public class CommandLine {
    
<<<<<<< HEAD
    
    public CommandLine(Converter theConverter) {
        String command;
        InputStreamReader isr = new InputStreamReader(System.in);
        Scanner myScanner = new Scanner(System.in);
=======
    static final Scanner myScanner = new Scanner(System.in);
    
    public CommandLine(Converter theConverter) {
        String command = "";
        String result;
        
>>>>>>> f4d60e287082101ffb0d383423225672347d0b78
        //emulate a help command like above
        System.out.println(theConverter.ioCalls("HELP"));
        do
        {
<<<<<<< HEAD
            System.out.println(">");
            command = myScanner.nextLine();
            theConverter.ioCalls(command);
            System.out.println(command + "\n");
        } while ((command!="QUIT")&&(command!="Q")&&(command!="quit")&&(command!="q"));
=======
            System.out.printf(">");
            command = myScanner.nextLine();
            result = theConverter.ioCalls(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
>>>>>>> f4d60e287082101ffb0d383423225672347d0b78
        
        
        
        
        //just some cleanup
<<<<<<< HEAD
        try {
            isr.close();
            myScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
=======

        myScanner.close();

>>>>>>> f4d60e287082101ffb0d383423225672347d0b78

    }
}