package UserInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.JobController;
import model.Park;
import model.ParkManager;
import model.Volunteer;

public class ParkManagerView {
	
	
	//private  Scanner myScanner;
	private JobController myJobController;
	private  List<Job>myParkSystemJobs;
	private List<Volunteer>myParksSystemVolunteer;
	private ParkManager myCurrentManager;
	
	//can't do this as we don't have access to these things
	/*public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myReader = new Scanner(System.in);
		myJobController = new JobController();
		myParkSystemJobs = theJobs;
		myParksSystemVolunteer = theVolunteers;
		myCurrentManager = theManager;
		//init initial page for this user
		
		initManagerHomeView(theManager);
	}*/
	
	
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
            System.out.printf(">");
            command = CommandLine.myScanner.nextLine();
            result = ProcessInput(command);
            System.out.println(result + "\n");
        } while (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) );
        
        //if user quit, do a ParksSystem.logout() call
        
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
            result="\tWelcome Park Manager\n";
            result+="\t-------------------\n";
            result+="\t1 Submit a new Job (NEW) <username>\n";
            result+="\t2 View Current Jobs(JOBS) <username>\n";
            result+="\t3 View Volunteer List\t(LST) <username>\n";
            result+="\tHELP(H)\n";
            result+="\tQUIT(Q)\n";
        
        } else if (tokens[0].equalsIgnoreCase("1") || tokens[0].equalsIgnoreCase("NEW") ) {
            //do the user story routine
            result += NewJobRoutine();
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("JOBS") ) {
            //not a required user story
            result += "Hi class, this isn't implemented!";
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("LST") ) {
            //do the user story routine
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            result += "Logging Out";
        } else {
            result += "Unrecognized Command";
        }
        
        return result;
    }
    
    
}