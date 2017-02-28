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
	private Scanner myReader;

	public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myParkSystemJobs = theJobs;
		myParksSystemVolunteer = theVolunteers;
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
	
	//I don't think this does anything -chris
	/*private void initManagerHomeView(ParkManager theManager){
		
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
	
	
	public void myVolunteersMenuView() {
		StringBuilder menuOptions = new StringBuilder();
		//getVolunteersByManagerId()
		menuOptions.append("Your current volunteers list");
		
	}*/
	
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
    
    
    
    //heavily modified version of other UI loops
    public String ViewMyVolunteers(){
        String result = "";
        String command = "";

        //result = ViewMyVolunteersHelper("HELP");
        System.out.println(result + "\n");
        result = ViewMyVolunteersHelper(command);
        System.out.println(result + "\n");
        
         if (!( command.equalsIgnoreCase("QUIT") || command.equalsIgnoreCase("Q") ) ) {
             //we have a number from before, get the volunteers based on this number
         }
        
        return result;
    }
	
    
    //This is the second most awful thing I have had to do in this entire project
    public String ViewMyVolunteersHelper(String theString) {
        String result = "";
        
        String[] tokens = theString.split(" ");
        
        ArrayList<Job> tempJobs = (ArrayList<Job>)mySystem.getMyJobs();
        ArrayList<Job> jobsForThisManager = new ArrayList<Job>();
        System.out.println("\n\t\tPark\t\t\tDate\t\tDescription");
        Integer counter = 1;
        try {
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
            
        } catch (NullPointerException e) {
            return "\nError: no jobs found for you\n";
        }
        
        result+="\n\tEnter a Job number from above >";
        System.out.printf(result);
        
      //this is the most awful thing I have had to do in this entire project. This line right here.
        Job tempJob = new Job(new Park());  
        try {
            Integer i;
            String command = CommandLine.myScanner.nextLine();
            i = Integer.valueOf(command) - 1;
            if (i < jobsForThisManager.size() ) {
                tempJob = jobsForThisManager.get(i);
                
            } else {
                System.out.println("Not a valid job number.");
                return "";
            }
                
        } catch (Exception e){
            e.printStackTrace();
        }
        
        ArrayList<Integer> IndexList = new ArrayList<Integer>();
        IndexList = (ArrayList<Integer>)tempJob.getMyVolunteerList();
        List<Volunteer> VolunteersList = (ArrayList<Volunteer>)mySystem.getMyVolunteers();
        System.out.println("\tName\t\tPhone\t\tEmail");
        Volunteer tempVol;
        counter = 1;
        for (Integer i : IndexList) {
            tempVol = VolunteersList.get(i);
            System.out.printf("%d) %s\t%s\t%s\n",
                    counter,
                    tempVol.getMyName(),
                    tempVol.getMyPhone(),
                    tempVol.getMyEmail() );
            counter++;
        }

        return "";
        
        
        
    }
    

}