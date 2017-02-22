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
	private ParkManager myCurrentManager;
	private Scanner myReader;

	public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myParkSystemJobs = theJobs;
		myCurrentManager = theManager;
		//init initial page for this user
		myJobController = new JobController(mySystem.getMyJobs());
		myReader = new Scanner(System.in);
		//initManagerHomeView(theManager);
	}
	
	public ParkManagerView(ParksSystem theSystem, ParkManagerController theParkManagerController){
	    mySystem = theSystem;
	    myCurrentManager = (ParkManager) theParkManagerController.getMyUser();
	    myJobController = new JobController(mySystem.getMyJobs());
		myReader = new Scanner(System.in);
		myParkSystemJobs = mySystem.getMyJobs();
	}
	
	
	/************************************
     * Methods for submitting a new job
     *********************************/
	
	//I don't think this does anything -chris
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
	
	/**
     * This needs to be documented by Dereje
     */
	public void myVolunteersMenuView() {
		StringBuilder menuOptions = new StringBuilder();
		//getVolunteersByManagerId()
		menuOptions.append("Your current volunteers list");
		
	}
	/**
	 * This needs to be documented by Dereje
	 */
	public void sumbitJobView(){
		//park name and city, job manager id
		Job newJob = null;
		Park thePark = null;
		
		if(myCurrentManager.getMyParks().size() == 1){
			thePark = myJobController.getMySinglePark(myCurrentManager);
			newJob = new Job(thePark);
			newJob.setMyPark(thePark);
			System.out.println(thePark.toString());
		
		} else {
			System.out.println("Enter 1: for " + myCurrentManager.getMyParks().get(0)
					+ " enter 2 for " + myCurrentManager.getMyParks().get(0));
			int userChoice = myReader.nextInt();
			
			thePark = myJobController.pickAPark(myCurrentManager, userChoice);
			newJob = new Job(thePark);
			newJob.setMyJobManagerId(myCurrentManager.getMyUserId());
			thePark.toString();
			
			
		}
		
		//assume initial view is solid
		acceptDateView(newJob);
		acceptEndDateView(newJob);
		acceptDescriptionView(newJob);
		acceptNumOfVolunteers(newJob);
		confirmSubmitView(newJob);
		
		
	}
    /**
     * This needs to be documented by Dereje
     */
	public boolean acceptDateView(Job theJob) {

		System.out.println("Enter dates between " + LocalDate.now() + " and " + LocalDate.now().plusDays(30)+" inclusive");
		System.out.println("Enter Start date(format MM/dd/yy Eg: 2/10/17 same as Feb 10, 2017");
		for (int retries = 0;retries < 3; retries++) {
		
		    	String line = myReader.nextLine();
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
			
			int jobDuration = myReader.nextInt();
			myReader.nextLine();//consume newline break
			
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
			
			String jobDescription = myReader.nextLine();

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
		boolean allNumAdded = false;
		System.out.println("Enter max volunteers for each category: Total less than 30");
		
		//each question has max 3 trials
		
		for (int retries = 0;retries < 3; retries++) {
			System.out.println("Light: ");
			int light = myReader.nextInt();
			if(!myJobController.isMaxLightVolNumberValid(theJob, light)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;//go to medium
			}
		}	
		
		
		for (int retries = 0;retries < 3; retries++) {
			System.out.println("Medium: ");
			int med = myReader.nextInt();
			
			if(!myJobController.isMaxMediumVolNumValid(theJob, med)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;//go to heavy
			}
		}	
		

		for (int retries = 0;retries < 3; retries++) {
			System.out.println("Heavy: ");
			int heavy = myReader.nextInt();
			myReader.nextLine();
			if(!myJobController.isMaxHeavyVolNumValid(theJob, heavy)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;
			}
		}	
			
		return allNumAdded;
	}
	
	
	public boolean confirmSubmitView(Job theJob) {
		
		System.out.println("Enter Y to submit the job or Enter N to cancel submission");
		
		String userConfirmation = myReader.nextLine();
		if(userConfirmation.equalsIgnoreCase("Y")) {
			myJobController.addJob(theJob, myParkSystemJobs);
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/*********************************************
     * Methods for Managers to view list of Volunteers
     *********************************************/

    
    /**
     * This method used to look much different. Now it is kind of silly. Needs refactoring
     * 
     * @return string of volunteers table to be displayed to the user
     */
    public String ViewMyVolunteers() {
        String result = "";

        System.out.println(result + "\n");
        result = ViewMyVolunteersHelper();
        System.out.println(result + "\n");
        
         if (!( result.equalsIgnoreCase("QUIT") || result.equalsIgnoreCase("Q") ) ) {
             //catches the case where manager does not want to continue looking for his volunteers
         }
        
        return result;
    }
	
    
    /**
     * Routine to carry out the subproblems associated with showing a manager a list of his volunteers
     * 
     * @return empty string   - probably indicates that more refactoring is needed
     */
    public String ViewMyVolunteersHelper() {
        String result = "";
        ArrayList<Job> jobsForThisManager = new ArrayList<Job>();
        
        try {
            jobsForThisManager = FindMyJobs(); //no guarantee of finding anything
        
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        Job tempJob = ChooseMyJob(jobsForThisManager);
        
        //check if previous call was successful
        if(tempJob == null){
            return "";
        }
        List<Volunteer> VolunteersListForPrinting = GetVolunteersFromJob(tempJob);        
        DisplayMyVolunteers(VolunteersListForPrinting);

        return "";
          
    }
    
    /**
     * Accepts a Job from the manager, and returns all the volunteers associated with the job
     * 
     * @param theJob a Job that was chosen previously by the manager
     * @return retVolunteers an array of Volunteers associated with this job
     */
    public List<Volunteer> GetVolunteersFromJob(Job theJob){
        List<Volunteer> retVolunteers = new ArrayList<Volunteer>();
        ArrayList<Integer> IndexList = new ArrayList<Integer>();
        
        //this list holds the IDs for the volunteers that need printing
        IndexList = (ArrayList<Integer>)theJob.getMyVolunteerList(); 
        
        List<Volunteer> VolunteersList = (ArrayList<Volunteer>)mySystem.getMyVolunteers();
        for (Integer i : IndexList) {
            retVolunteers.add(VolunteersList.get(i));
        }
        return retVolunteers;
    }
    
    /**
     * Give the manager a list of his associated jobs to choose from
     * 
     * @param theJobs a list of jobs associated with this manager
     * @return Job the job chosen by the manager 
     */
    public Job ChooseMyJob(ArrayList<Job> theJobs){
        Job aJob = new Job(new Park() );
        String result = "";
        result+="\n\tEnter a Job number from above >";
        System.out.printf(result);
         
        Integer i;
        String command = CommandLine.myScanner.nextLine();
        i = Integer.valueOf(command) - 1;
        if (i < theJobs.size() ) {
            aJob = theJobs.get(i);
            
        } else {
            System.out.println("Not a valid job number.");
            return aJob; //pass back an empty job
        }
            
        return aJob;
    }
   
    
    /**
     * Gets every job associated with the manager who is logged in
     * 
     * @return ArrayList<Job> the list of jobs associated with the View's manager
     */
    public ArrayList<Job> FindMyJobs(){
        ArrayList<Job> tempJobs = (ArrayList<Job>)mySystem.getMyJobs();
        ArrayList<Job> jobsForThisManager = new ArrayList<Job>();
        System.out.println("\n\t\tPark\t\t\tDate\t\tDescription"); //header of the table of jobs
        Integer counter = 1;
        
            for (Job tempJob : tempJobs ) {
                if (tempJob.getMyJobManagerId() == myCurrentManager.getMyUserId()) {
                    LocalDate tempDate = tempJob.getMyStartDate();
                    System.out.printf("%d)\t%s\t%s\t%s\n", 
                        counter,
                        tempJob.getMyPark().getMyName(),    
                        tempDate.toString(),
                        //myFormat.format( tempJob.getMyStartDate() ),
                        tempJob.getMyDescription());
                    counter++;
                    jobsForThisManager.add(tempJob);
                    
                }
                
            }
         return jobsForThisManager;
    }
    
    
    /**
     * Prints a table of volunteers specifically for managers to see
     * 
     * @param theVolunteersList the volunteers intended for a manager to see
     */
    public void DisplayMyVolunteers(List<Volunteer> theVolunteersList) {
        System.out.println("\tName\t\tPhone\t\tEmail");
        int counter = 1;
        for (Volunteer tempVol : theVolunteersList)
        System.out.printf("%d) %s\t%s\t%s\n",
                counter,
                tempVol.getMyName(),
                tempVol.getMyPhone(),
                tempVol.getMyEmail() );
    }
    
    
    
    /***********************
     * Menu methods for Managers
     *******************/
    
    /**
     * I/O Interaction with Park Manager type users
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
        
        
    }
    
    /**
     * 
     * @param theString command entered by the user that needs interpreting and executing
     * @return a string to be displayed to the user so that he can know what has happened
     */
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
            sumbitJobView();
        } else if (tokens[0].equalsIgnoreCase("2") || tokens[0].equalsIgnoreCase("JOB") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("3") || tokens[0].equalsIgnoreCase("PND") ) {
            //not implemented
            
        } else if (tokens[0].equalsIgnoreCase("4") || tokens[0].equalsIgnoreCase("VOL") ) {
            result += ViewMyVolunteers();
            
        } else if (tokens[0].equalsIgnoreCase("QUIT") || tokens[0].equalsIgnoreCase("Q") ) {
            mySystem.logout();
            result += "Logging Out";
            
        } else {
            result += "Unrecognized Command";
            
        }
        
        return result;
    }
    
    

}