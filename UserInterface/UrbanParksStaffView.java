package UserInterface;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Job;
import model.JobController;
import model.ParksSystem;
import model.UrbanParksStaff;
import model.UrbanParksStaffController;
import model.Volunteer;
import model.VolunteerController;

public class UrbanParksStaffView {

    private ParksSystem mySystem;
    private JobController myJobController;
    private UrbanParksStaff myUrbanParksStaff;
    private UrbanParksStaffController myController;

    public UrbanParksStaffView(ParksSystem theSystem, UrbanParksStaffController theUrbanParksStaffController){
        mySystem = theSystem;
        myUrbanParksStaff = (UrbanParksStaff) theUrbanParksStaffController.getMyUser();
        myController = (UrbanParksStaffController)mySystem.getMyUserController();
        myJobController = new JobController(mySystem.getMyJobs());
        //myController = new UrbanParksStaffController();
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
    
    public String GetMonthPendingJobs(){
        String result = "\n";
        ArrayList<Job> currentJobs = (ArrayList<Job>)myController.getPendingJobsForOneMonth();
        SimpleDateFormat myFormat = new SimpleDateFormat("EEE, MMM d, yy"); //example: Wed, Jul 4, '01
        
        System.out.println("\t\tPark\t\t\tDate\t\t\tDescription");
        try {
            for (Job i : currentJobs ) {
                LocalDate tempDate = i.getMyStartDate();
                //should output formatted list of jobs after full iteration  
                //jobs will show with Name of Park, the Start Date, and the Description
                System.out.printf("\t\t%s\t%s\t\t%s\n", 
                        i.getMyPark().getMyName(),    
                        tempDate.toString(),
                        i.getMyDescription());
            }
        
        } catch (NullPointerException e) {
            return "\nError: no upcoming jobs found in system\n";
        }
        
        
        return result;
    }
    
    
    
    
    public String ProcessInput(String theString) {
        String[] tokens = theString.split(" ");
        String result = "";
        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            System.out.printf("\tWelcome, Urban Parks Staff %s\n",myUrbanParksStaff.getMyName());
            //result="\tWelcome, Volunteer\n";
            result="\t-------------------\n";
            result+="\t1 View Upcoming Jobs \t(CAL) \n";
            result+="\t2 Submit a new Job \t\t(NEW) \n";
            result+="\t3 Change System Settings \t(SYS) \n";
            result+="\t4 View A User \t\t(USR) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("CAL") ) {
            //do the Calendar call
            GetMonthPendingJobs();
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("NEW") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("SYS") ) {
            //do the system tasks call
            ChangeSystemRestrictions();
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
        
        return result;
    }

    
    
    
    
    
    public String ChangeSystemRestrictions() {
        String result = "";
        String command;
        
        result = SystemChangesHelper("HELP");
        System.out.println(result + "\n");
        do
        {
            System.out.printf("Enter a Command >");
            command = CommandLine.myScanner.nextLine();
            result = SystemChangesHelper(command);
            System.out.println(result + "\n");
            
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );

        
        return result;
    }
    
    public String SystemChangesHelper(String theString) {
        String result = "";
        
        String[] tokens = theString.split(" ");

        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            System.out.printf("\tWelcome, Urban Parks Staff %s\n",myUrbanParksStaff.getMyName());
            result="\t-------------------\n";
            result+="\tWhat do you want to change?\n\n";
            result+="\t1 Maximum Total Jobs \t(MAX) <number>\n";
            result+="\t2 Maximum Volunteers \t(VOL) <number>\n";
            result+="\t3 Maximum Job Length \t\t(LNG) <number>\n";
            result+="\t4 Maximum Jobs per Day \t\t(DAY) <number>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            result+="\n\n\tExample: type 2 24 or VOL 24 and press Enter\n";
            
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("MAX") ) {
            //do the call
            Integer tempInt = Integer.parseInt(tokens[1]);
            myJobController.setMyMaxNumberOfPendingJobs(tempInt);
            result+="\tMaximum total system jobs is now " + tempInt + "\n";
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("VOL") ) {
            //not implemented
            result+="Not Implemented";
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("LNG") ) {
            //not implemented
            result+="Not Implemented";
            
        } else if (tokens[0].equalsIgnoreCase("4") || tokens[0].equalsIgnoreCase("DAY") ) {
            //not implemented
            result+="Not Implemented";
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            result += "\tGoing Back\n";
            
        } else {
            result += "\tUnrecognized Command\n";
        }
        return result;
    }
    
    
    
    
}