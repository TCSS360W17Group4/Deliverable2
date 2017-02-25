package UserInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.Volunteer;
import model.VolunteerController;

public class VolunteerView extends HomeView {

	private Volunteer myVolunteer;
	private VolunteerController myController;
	private Scanner myReader;
	public VolunteerView(VolunteerController theController, Volunteer theUser){
	
		super();
		this.myController = theController;
		this.myVolunteer = theUser;
	
		HomeHeaderTitle();
	}
	
	private void HomeHeaderTitle() {
		System.out.println("Today, " + myController.convertLocalDatetToReadableString(LocalDate.now())+
				" Welcome to UParks " + myVolunteer.getMyName());
	   
	}
	
	
	public void initHome(Scanner theScanner){
		myReader = theScanner;
		
		StringBuilder volunteerMenu = new StringBuilder();
		volunteerMenu.append("Choose the command option, and press enter:\n");
		if(myController.isNewJobAccepted()) {
			volunteerMenu.append("s Sign up for a job\n");
			volunteerMenu.append("v View my jobs\n");
			volunteerMenu.append("l logout");
		} else {
			volunteerMenu.append("You current do not have any jobs\n");
			volunteerMenu.append("v View my Jobs\n");
			//show the jobs already created and 1 option
			volunteerMenu.append("l logout");
		}
		System.out.println(volunteerMenu);
		String userChoice = myReader.nextLine();
		
	
		showChoosenCommand(userChoice);
	}

	public void showChoosenCommand(String userInput) {
		//System.out.println(userInput.equalsIgnoreCase(userInput));
		if(userInput.equalsIgnoreCase("s")) {
		
			exitOrReturnView();
	
		}  else if(userInput.equalsIgnoreCase("l")) {
			myController.logout();
		} else if (userInput.equalsIgnoreCase("v")) {
			
			viewMyJobs();
			exitOrReturnView();
		}
	}

	public void exitOrReturnView() {
		
		StringBuilder exitString = new StringBuilder();
		exitString.append("What would you like to do?\n");
		exitString.append("r Return to prior menu\n");
		exitString.append("l Logout");
		
		System.out.println(exitString);
		String userInput = myReader.nextLine();
		if (userInput.equalsIgnoreCase("l")) {
			myController.logout();
		} else if(userInput.equalsIgnoreCase("r")) {
			initHome(myReader);
		}
		
		
	}

    public String viewMyJobs() {
        String result = "\n";
        //SimpleDateFormat myFormat = new SimpleDateFormat("EEE, MMM d, yy"); //example: Wed, Jul 4, '01
        Job tempJob;
        List<Integer> currentJobs = myVolunteer.getMyVolunteerJobs();  //need all the ids for the jobs the user signed up for
        System.out.println("\t\tPark\t\t\tDate\t\tDescription");
        Integer counter = 1;
        try {
            for (Integer i : currentJobs ) {
                tempJob = myController.getSingleJobByGivenId(i);  //going through every job in the list, getting the job
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
     * Routine to allow users to sign up for jobs.
     * Should be called directly after the relevant choice is entered by the user at the UI screen
     * 
     * @return "signup successful" or "not successful" string to be displayed to the user
     */ 
//    public String SignUpForAJob() {
//        System.out.println("\n\t\tPark\t\t\tDate\t\tDescription");
//
//        ArrayList<Job> someJobs = (ArrayList<Job>)myJobController.getMyJobsList();
//        List<Job> pendingJobs = new ArrayList<Job>();
//
//        try {
//            pendingJobs = GetPendingJobsForThisMonth();
//        } catch (NullPointerException e) {
//            return "\nError: no jobs found for you\n";
//        }
//
//        PrintChoicesToVolunteer(pendingJobs);
//        System.out.printf("\n\tEnter a Job number from above >");
//        
//        Job aJob = GetTheChosenJob(pendingJobs);
//        SignMeUp(aJob); //this method probably needs to exist inside the volunteerController instead
//        return "\nSuccessfully Signed Up for Job at\t" + aJob.getMyPark().getMyName();
//    }
}