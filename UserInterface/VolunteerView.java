package UserInterface;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobController;
import model.Park;
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
        myJobController = new JobController(mySystem.getMyJobs());
    }
   
    
    /**
     * Returns a table of pending jobs that the Volunteer is signed up for currently
     * 
     * @return string of Jobs table to be displayed to the Volunteer
     */
    public String ViewMyJobs() {
        String result = "\n";
        //SimpleDateFormat myFormat = new SimpleDateFormat("EEE, MMM d, yy"); //example: Wed, Jul 4, '01
        Job tempJob;
        List<Integer> currentJobs = myCurrentVolunteer.getMyVolunteerJobs();  //need all the ids for the jobs the user signed up for
        System.out.println("\t\tPark\t\t\tDate\t\tDescription");
        Integer counter = 1;
        try {
            for (Integer i : currentJobs ) {
                tempJob = myJobController.getJobById(i);  //going through every job in the list, getting the job
                LocalDate tempDate = tempJob.getMyStartDate();
                //jobs will show with Name of Park, the Start Date, and the Description
                System.out.printf("%d)\t%s\t%s\t%s\n", 
                        counter,
                        tempJob.getMyPark().getMyName(),    
                        tempDate.toString(),
                        tempJob.getMyDescription());
                counter++;
            }
            
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        return result;
    }
    
    
    /**
     * Routine to allow users to sign up for jobs
     * Needs refactoring into sub-problems
     * 
     * @return string to be displayed to the user
     */
    public String SignUpForAJob() {
        String result = "";
        
        List<Job> pendingJobs = new ArrayList<Job>();
        System.out.println("\n\t\tPark\t\t\tDate\t\tDescription");
        Integer counter = 1;
        ArrayList<Job> someJobs = (ArrayList<Job>)myJobController.getMyJobsList();
        
        //this is not the best, or even a good way of doing this
        try {
            for (Job j : myJobController.getMyPendingJobs(mySystem.getMyJobs())) {
                if (j.isPending() && 
                    // compares start date to current date right now
                    //j.getMyStartDate().compareTo(j.getMyStartDate().now()) > 0 &&
                    // compares start date to date of one month from right now
                    j.getMyStartDate().compareTo(LocalDate.now().plusMonths(1)) <= 0) {
                    pendingJobs.add(j);
                    //should print to screen now
                    LocalDate tempDate = j.getMyStartDate();
                    System.out.printf("%d)\t%s\t%s\t%s\n", 
                            counter,
                            j.getMyPark().getMyName(),    
                            tempDate.toString(),
                            j.getMyDescription());
                    counter++;
                }
            }
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        result+="\n\tEnter a Job number from above >";
        System.out.printf(result);
        Job tempJob = new Job(new Park());  
        
        try {
            Integer i;
            String command = CommandLine.myScanner.nextLine();
            i = Integer.valueOf(command) - 1;
            if (i < someJobs.size() ) {
                tempJob = pendingJobs.get(i);  //need to sign up for this job. the volunteer has to be added to the job, and the job needs to be added to the volunteer
                ArrayList<Integer> tempInts = (ArrayList<Integer>) tempJob.getMyVolunteerList();
                tempInts.add(myCurrentVolunteer.getMyUserId() ); //hnow the job has a reference to the volunteer
                tempInts = (ArrayList<Integer>) myCurrentVolunteer.getMyVolunteerJobs();
                tempInts.add(myCurrentVolunteer.getMyUserId() ); //now the volunteer has a reference to the job
            } else {
                System.out.println("Not a valid job number.");
                return "";
            }
                
        } catch (Exception e){
            e.printStackTrace();
        }
        
        
        
        return "\nSuccessfully Signed Up for Job at\t" + tempJob.getMyPark().getMyName();
    }
    
    
    
    /***********************
     * Menu methods for Volunteers
     *******************/
    
    /**
     * I/O Interaction with Volunteer type users
     * 
     * Acts as a back and forth communication loop
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
     * @param theString command entered by the user that needs interpreting and executing
     * @return a string to be displayed to the user so that he can know what has happened
     */
    public String ProcessInput(String theString) {
        String[] tokens = theString.split(" ");
        String result = "";
        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            System.out.printf("\tWelcome, Volunteer %s\n",myCurrentVolunteer.getMyName());
            //result="\tWelcome, Volunteer\n";
            result="\t-------------------\n";
            result+="\t1 Search for Jobs \t\t(SCH) \n";
            result+="\t2 Volunteer for a Job\t\t(VOL) \n";
            result+="\t3 View My Current Jobs\t\t(CUR)\n";
            result+="\t4 View Past Jobs\t\t(PST) \n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
            result+="\n\n\tExample: type 3 or CUR and press Enter\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("SCH") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("VOL") ) {
            //not implemented
            result += SignUpForAJob(); 
            
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