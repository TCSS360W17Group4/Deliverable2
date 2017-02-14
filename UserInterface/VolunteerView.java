package UserInterface;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import model.Job;
import model.JobController;
import model.ParkManagerController;
import model.ParksSystem;
import model.Volunteer;
import model.VolunteerController;

public class VolunteerView {

    private ParksSystem mySystem;
    private JobController myJobController;
    private Volunteer myCurrentVolunteer;
    

    public VolunteerView(ParksSystem theSystem, VolunteerController theVolunteerController){
        mySystem = theSystem;
        myCurrentVolunteer = (Volunteer) theVolunteerController.getMyUser();
        
    }
    
    //user stories go here
    //As a Volunteer I want to volunteer for a job.
    //As a Volunteer I want to view a listing of the jobs I am signed up for.
    public String ViewMyJobs() {
        String result = "\n";
        SimpleDateFormat myFormat = new SimpleDateFormat("EEE, MMM d, yy"); //example: Wed, Jul 4, '01
        Job tempJob;
        List<Integer> currentJobs = myCurrentVolunteer.getMyVolunteerJobs();  //need all the ids for the jobs the user signed up for
        System.out.println("Park,           Date            Description");
        try {
            for (Integer i : currentJobs ) {
                tempJob = myJobController.getJobById(i);  //going through every job in the list, getting the job
           
                //should output formatted list of jobs after full iteration  
                //jobs will show with Name of Park, the Start Date, and the Description
                System.out.printf("%d)\t\t\t%s\t\t%s\n", 
                        tempJob.getMyPark().getMyName(),    
                        myFormat.format( tempJob.getMyStartDate() ),
                        tempJob.getMyDescription());
            }
            
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        return result;
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
            System.out.printf("\tWelcome, Volunteer %s\n",myCurrentVolunteer.getMyName());
            //result="\tWelcome, Volunteer\n";
            result="\t-------------------\n";
            result+="\t1 Search for Jobs \t\t(SCH) <username>\n";
            result+="\t2 Volunteer for a Job\t\t(VOL) <username>\n";
            result+="\t3 View My Current Jobs\t\t(CUR)\n";
            result+="\t4 View Past Jobs\t\t(PST) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("SCH") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("VOL") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("CUR") ) {
            result += ViewMyJobs();
            
        } else if (tokens[0].equalsIgnoreCase("4") || tokens[0].equalsIgnoreCase("PST") ) {
            //not a required user story
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
        
        return result;
    }

}