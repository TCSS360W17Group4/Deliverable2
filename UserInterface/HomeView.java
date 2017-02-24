
package UserInterface;


import model.AbstractController;
import model.ParkManagerController;
import model.ParksSystem;
import model.VolunteerController;

public class HomeView {
	
	private static ParksSystem mySystem;

	public HomeView(ParksSystem theSystem) {
	    mySystem = theSystem;
    }
	/**
	 * I/O Interaction with user before one of the three Views take over
	 * 
	 * Displays the initial Urban Parks prompt to the user for login
	 * 
	 * Tries to pass control over to one of the "views" once user is logged in
	 */
    public void run() {
        String result = "";
        String command;

		result = ProcessInput("HELP");
        System.out.println(result + "\n");
        do
        {
            System.out.printf("Enter a Command >");
            command = CommandLine.myScanner.nextLine();
            result = ProcessInput(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
		
		//if user quit, do a ParksSystem.logout() call
		
	}
	
    /**
     * Executes the command received by the user, taking the action relevant to the command
     * 
     * @param theString the command entered by the user that needs interpreting and executing
     * @return a string to be displayed to the user so that he can know what has happened
     */
    public String ProcessInput(String theString) {
        String[] tokens = theString.split(" ");
        String result = "";
        
        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            result="\tWelcome to Urban Parks\n";
            result+="\t-------------------\n";
            result+="\t1 Login\t\t(LOG) <email>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            result+="\n";
            result+="\tExample input: 1 myname@myemail.com, or LOG arealemail@gmail.com\n";
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("LOG") ) {
            AbstractController myController = mySystem.loginSuccessful(tokens[1]);
            if (myController == null || myController.getMyUser() == null){
                return "Login Failed, User not Found";
            }
            if (myController instanceof VolunteerController){
                VolunteerView myVolunteerView = new VolunteerView(mySystem, (VolunteerController)myController);
                myVolunteerView.run(); 
                
            }
            if (myController instanceof ParkManagerController){
                ParkManagerView myParkManagerView = new ParkManagerView(mySystem, (ParkManagerController)myController);
                myParkManagerView.run(); 
                
            }

            
        }  else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
        }
 
        return result;
        
    }

}