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
    
    /***************************************
     * Job Signup methods for Volunteers
     ****************************************/
    
    
    /**
     * Routine to allow users to sign up for jobs.
     * Should be called directly after the relevant choice is entered by the user at the UI screen
     * 
     * @return "signup successful" or "not successful" string to be displayed to the user
     */ 
    public String SignUpForAJob() {
        System.out.println("\n\t\tPark\t\t\tDate\t\tDescription");

        ArrayList<Job> someJobs = (ArrayList<Job>)myJobController.getMyJobsList();
        List<Job> pendingJobs = new ArrayList<Job>();

        try {
            pendingJobs = GetPendingJobsForThisMonth();
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }

        PrintChoicesToVolunteer(pendingJobs);
        System.out.printf("\n\tEnter a Job number from above >");
        
        Job aJob = GetTheChosenJob(pendingJobs);
        SignMeUp(aJob); //this method probably needs to exist inside the volunteerController instead
        return "\nSuccessfully Signed Up for Job at\t" + aJob.getMyPark().getMyName();
    }
    
    /**
     * Adds references to the user inside of the job, as well as references to the job, inside the user.
     * Both references need to exist for a signup to be complete.
     * 
     * @param theJob that the user wants to sign up for
     */
    public void SignMeUp(Job theJob){
        ArrayList<Integer> tempInts = (ArrayList<Integer>) theJob.getMyVolunteerList();
        tempInts.add(myCurrentVolunteer.getMyUserId() ); //now the job has a reference to the volunteer
        tempInts = (ArrayList<Integer>) myCurrentVolunteer.getMyVolunteerJobs();
        tempInts.add(theJob.getMyJobId() ); //now the volunteer has a reference to the job
    }
    
    
    
    /**
     * Grabs the specific job that the volunteer is choosing
     * 
     * @param theJobs the pending jobs that were presented to the user
     * @return the one that the user chooses
     */
    public Job GetTheChosenJob(List<Job> theJobs){
        Job aJob = new Job(new Park());
        Integer i;
        String command = CommandLine.myScanner.nextLine();
        i = Integer.valueOf(command) - 1;
        if (i < theJobs.size() ) {
            aJob = theJobs.get(i);  //need to sign up for this job. the volunteer has to be added to the job, and the job needs to be added to the volunteer
            
        } else {
            System.out.println("Not a valid job number.");
        }
        return aJob;
    }
    
    /**
     * Takes a list of pending jobs and prints them out for a volunteer
     * 
     * @param theJobs a list of jobs for a volunteer to choose. This method assumes the jobs are relevant to the user for volunteering
     */
    public void PrintChoicesToVolunteer(List<Job> theJobs){
        Integer counter = 1;
        for (Job j : theJobs) {
            
            LocalDate tempDate = j.getMyStartDate();
            
            System.out.printf("%d)\t%s\t%s\t%s\n", 
                    counter,
                    j.getMyPark().getMyName(),    
                    tempDate.toString(),
                    j.getMyDescription());
            counter++;
        }
    }
    
    /**
     * Be sure to handle a NullPointerException, in the case where no jobs are found
     * 
     * @return pendingJobs a list of jobs that are both pending, and starting within one month from now
     * 
     */
    public List<Job> GetPendingJobsForThisMonth(){
        List<Job> pendingJobs = new ArrayList<Job>();
        for (Job j : myJobController.getMyPendingJobs()) {
            if (j.isPending() && 
                // compares start date to date of one month from right now
                j.getMyStartDate().compareTo(LocalDate.now().plusMonths(1)) <= 0) {
                pendingJobs.add(j);

            }
        }
        return pendingJobs;
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