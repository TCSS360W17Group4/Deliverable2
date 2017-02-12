package UserInterface;

import model.ParksSystem;

public class HomeView {
	
	private static ParksSystem mySystem;

	public HomeView(ParksSystem theSystem) {
	    mySystem = theSystem;
    }

    public void run() {
        String result = "";
        String command;

		result = ProcessInput("HELP");
        System.out.println(result + "\n");
        do
        {
            System.out.printf(">");
            command = CommandLine.myScanner.nextLine();
            result = ProcessInput(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
		
		//if user quit, do a ParksSystem.logout() call
		
	}
	
    
    public String ProcessInput(String theString) {
        String[] tokens = theString.split(" ");
        String result = "";
        
        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            result="\tWelcome to Urban Parks\n";
            result+="\t-------------------\n";
            result+="\t1 Login User (LOG) <username>\n";
            result+="\t2 Login Manager\t\t\t(Mgr) <username>\n";
            result+="\t3 Login Urban Parks Staff\t(USt) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("VOL") ) {
            //do a volunteer ParksSystem.login call with username as tokens[1]
            mySystem.loginSuccessful(tokens[1]);
            //pass to the VolunteerView
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("MGR") ) {
            //do a manager ParksSystem.login call with username as tokens[1]
            mySystem.loginSuccessful(tokens[1]);
            ParkManagerView myParkManagerView = new ParkManagerView();
            myParkManagerView.run();
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("UST") ) {
            //do a Urban Parks Staff ParksSystem.login call with username as tokens[1]
            mySystem.loginSuccessful(tokens[1]);
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            result += "Logging Out";
        } else {
            result += "Unrecognized Command";
        }
 
        
        
        
        return result;
        
    }
}