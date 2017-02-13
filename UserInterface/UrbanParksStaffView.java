package UserInterface;

import model.JobController;
import model.ParksSystem;
import model.UrbanParksStaff;
import model.UrbanParksStaffController;
import model.Volunteer;
import model.VolunteerController;

public class UrbanParksStaffView {

    private ParksSystem mySystem;
    private JobController myJobController;
    private Volunteer myCurrentVolunteer;
    

    public UrbanParksStaffView(ParksSystem theSystem, UrbanParksStaffController theUrbanParksStaffController){
        mySystem = theSystem;
        myCurrentVolunteer = (Volunteer) theUrbanParksStaffController.getMyUser();
        
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
            result="\tWelcome, Urban Parks Staff\n";
            result+="\t-------------------\n";
            result+="\t1 Submit a new Job (NEW) <username>\n";
            result+="\t2 View My Current Jobs(JOBS) <username>\n";
            result+="\t3 View My Volunteer List\t(LST) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("NEW") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("JOBS") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("LST") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
        
        return result;
    }

}