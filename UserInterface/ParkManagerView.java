package UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.JobController;
import model.Park;
import model.ParkManager;
import model.ParkManagerController;
import model.ParksSystem;
import model.Volunteer;

public class ParkManagerView {
	
	private ParksSystem mySystem;
	//private  Scanner myScanner;
	private JobController myJobController;
	private  List<Job>myParkSystemJobs;
	private List<Volunteer>myParksSystemVolunteer;
	private ParkManager myCurrentManager;
	

	public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myJobController = new JobController();
		myParkSystemJobs = theJobs;
		myParksSystemVolunteer = theVolunteers;
		myCurrentManager = theManager;
		//init initial page for this user
		
		initManagerHomeView(theManager);
	}
	
	public ParkManagerView(ParksSystem theSystem, ParkManagerController theParkManagerController){
	    mySystem = theSystem;
	    myCurrentManager = (ParkManager) theParkManagerController.getMyUser();
	    
	}
	
	//I don't think this does anything
	private void initManagerHomeView(ParkManager theManager){
		
		System.out.println("Welcome to Urban Parks");
		//todays date ...manager name ...logged in as Manager
		//what would you like to do? show options 
		
			if(myJobController.isNewJobAccepted(myParkSystemJobs)) {
				//print menu with submit job
			} else {
				//show menu without submit job
			}
			//read line
		//call the actions depending on the choice
		//display return option and exit
	}
	
	
	public void sumbitJobView(){
		//park name and city, job manager id
		Job newJob = null;
		//Park thePark = new Park(-1, null, null, myCurrentManager.getMyUserId());
		
		//this isn't the right place to be creating and entering jobs
		Park thePark = new Park();
		if(myJobController.isParkAdded(myCurrentManager,newJob,thePark)){
			thePark.toString();
		} else {
			System.out.println("Enter 1: for " + myCurrentManager.getMyParks().get(0)
					+ " enter 2 for " + myCurrentManager.getMyParks().get(0));
			int userChoice = CommandLine.myScanner.nextInt();
			
			thePark = myJobController.pickAPark(myCurrentManager, userChoice);
			newJob = new Job(thePark);
			newJob.setMyJobManagerId(myCurrentManager.getMyUserId());
			thePark.toString();			
			
		}
		
		//assume initial view is solid
		acceptDateView(newJob);
		acceptEndDateView(newJob);
		acceptDescriptionView(newJob);
		
		//myJobController.addStartDate(line,newJob,myParkSystemJobs);
		
	}
	
	public boolean acceptDateView(Job theJob) {

		System.out.println("Enter dates between " + LocalDate.now() + " and " + LocalDate.now().plusDays(30)+" inclusive");
		System.out.println("Enter Start date(format MM/dd/yy Eg: 2/10/17 same as Feb 10, 2017");
		for (int retries = 0;retries < 3; retries++) {
		
		    	String line = CommandLine.myScanner.nextLine();
		    	if(!myJobController.isStartDateAdded(line, theJob, myParkSystemJobs)) {
		    		System.out.println("Please enter again");
		    		continue;
		    		
		    	} else {
		    	    
		    		return true;
		    		
		    	}
		    	
		   
		}
		
		
		return false;
	}
	
	public boolean acceptEndDateView(Job theJob) {
		System.out.println("Enter job duration: 1 or 2");
		
		for (int retries = 0;retries < 3; retries++) {
			
			int jobDuration = CommandLine.myScanner.nextInt();
			
	    	if(!myJobController.isEndDateAdded(jobDuration, theJob, myParkSystemJobs)) {
	    		System.out.println("Enter again");
	    		continue;
	    		
	    	} else {
	    	    
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}

	public boolean acceptDescriptionView(Job theJob) {
		System.out.println("Enter job Description:");
		
		for (int retries = 0;retries < 3; retries++) {
			
			String jobDescription = CommandLine.myScanner.nextLine();
			
	    	if(!myJobController.isJobDescriptionAdded(theJob, jobDescription)) {
	    		System.out.println("Enter again");
	    		continue;
	    		
	    	} else {
	    	    
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}

	public boolean acceptNumOfVolunteers(Job theJob) {
		//not complete
		System.out.println("Enter max number of volunteers required: Total less than 30");
		System.out.println();
		return false;
		
	}
	
	//one routine for user story 3 As a Park Manager I want to view a numbered list of Volunteers for a job (past or present) in the parks that I manage.
	
	//one routine for user story 2 As a Park Manager I want to submit a new job
	
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
        
        
    }
    
    public String NewJobRoutine(){
        String result = "";
        //need 
        
        
        
        return result;
    }
    
    
    
    public String ProcessInput(String theString) {
        String[] tokens = theString.split(" ");
        String result = "";
        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            System.out.printf("\tWelcome, Park Manager %s\n",myCurrentManager.getMyName());
            result="\t-------------------\n";
            result+="\t1 Submit a new Job (NEW) \n";
            result+="\t2 Search Jobs\t\t(JOB) \n";
            result+="\t3 View My Upcoming Jobs\t(PND) \n";
            result+="\t4 View my Volunteers\t(VOL) \n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("NEW") ) {
            //do the user story routine
            
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("JOB") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("PND") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("4") || tokens[0].equalsIgnoreCase("VOL") ) {
            ViewMyVolunteers();
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
        
        return result;
    }
    
    
    
    
    public String ViewMyVolunteers(){
        String result = "";
        
        String command;

        result = ViewMyVolunteersHelper("HELP");
        System.out.println(result + "\n");
        do
        {
            System.out.printf("Enter a Command >");
            command = CommandLine.myScanner.nextLine();
            result = ViewMyVolunteersHelper(command);
            System.out.println(result + "\n");
            
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
        
        return result;
    }
	
    public String ViewMyVolunteersHelper(String theString){
        String result = "";
        
        String[] tokens = theString.split(" ");
        
        ArrayList<Job> tempJobs = (ArrayList<Job>)mySystem.getMyJobs();
        
        try {
            for (Job tempJob : tempJobs ) {
                if (tempJob.getMyJobManagerId() == myCurrentManager.getMyUserId())

                System.out.printf("%d)\t\t\t%s\t\t%s\n", 
                        tempJob.getMyPark().getMyName(),    
                        //myFormat.format( tempJob.getMyStartDate() ),
                        tempJob.getMyDescription());
            }
            
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        
        
        
        
        
        
        
        
        
        

        if (tokens[0].equalsIgnoreCase("HELP") || tokens[0].equalsIgnoreCase("H") ) {
            
            System.out.printf("\tWelcome, Park Manager %s\n",myCurrentManager.getMyName());
            result="\t-------------------\n";
            result+="\t1 Submit a new Job (NEW) \n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("NEW") ) {
            //do the user story routine

            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {

            result += "\tGoing Back\n";
            
        } else {
            result += "\tUnrecognized Command\n";
            
        }
        
        return result;
        
        
        
    }
    

}