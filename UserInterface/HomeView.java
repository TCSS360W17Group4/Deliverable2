package UserInterface;

import model.ParkManager;
import model.ParkManagerController;
import model.ParksSystem;
import model.UrbanParksStaff;
import model.UrbanParksStaffController;
import model.Volunteer;
import model.VolunteerController;

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
            System.out.printf("Enter a Command >");
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
            result+="\t1 Login Volunteer \t\t(Vol) <username>\n";
            result+="\t2 Login Manager\t\t\t(Mgr) <username>\n";
            result+="\t3 Login Urban Parks Staff\t(USt) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            result+="\n";
            result+="\tExample input: 2 MyUserName, or VOL MyUserName\n";
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("VOL") ) {
            VolunteerController myVolunteerController = (VolunteerController)mySystem.loginSuccessful(tokens[1]);
            if (myVolunteerController == null || myVolunteerController.getMyUser() == null){
                return "Login Failed, User not Found";
                
            }
            
            VolunteerView myVolunteerView = new VolunteerView(mySystem, myVolunteerController);
           myVolunteerView.run();
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("MGR") ) {

            ParkManagerController myParkManagerController = (ParkManagerController)mySystem.loginSuccessful(tokens[1]);
            if (myParkManagerController == null || myParkManagerController.getMyUser() == null){
                return "Login Failed, User not Found";
                
            }
            
            ParkManagerView myParkManagerView = new ParkManagerView(mySystem, myParkManagerController);
            myParkManagerView.run();
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("UST") ) {
            UrbanParksStaffController myUrbanParksStaffController = (UrbanParksStaffController)mySystem.loginSuccessful(tokens[1]);
            if (myUrbanParksStaffController == null || myUrbanParksStaffController.getMyUser() == null) {
                return "Login Failed, User not Found";
                
            }
            UrbanParksStaffView myUrbanParksStaffView = new UrbanParksStaffView(mySystem, myUrbanParksStaffController);
            //UrbanParksStaffView.run();
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
 
        
        
        
        return result;
        
    }

}