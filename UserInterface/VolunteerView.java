package UserInterface;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.Volunteer;
import model.VolunteerController;

public class VolunteerView extends HomeView {

	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static final int OFFSET = 1;
	private static final String V_FOR_VIEW_COMMAND = "V";
	private static final String S_FOR_SUBMIT_OR_SINGUP_COMMAND = "S";
	private static final String L_FOR_LOGOUT_USER_VIEW_COMMAND = "L";
	private static final String R_FOR_RETURN_PREV_VIEW_FOR_USERS = "R";
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
		if(myController.isJobAvailableToSignUp()) {
			volunteerMenu.append(S_FOR_SUBMIT_OR_SINGUP_COMMAND + " Sign up for a job\n");
			if (!myVolunteer.getMyVolunteerJobs().isEmpty()) {
				volunteerMenu.append(V_FOR_VIEW_COMMAND + " View my jobs\n");
			}
			volunteerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " logout");
		} else {
			volunteerMenu.append("No jobs available for sign up\n");
			volunteerMenu.append(V_FOR_VIEW_COMMAND + " View my Jobs\n");
			//show the jobs already created and 1 option
			volunteerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " logout");
		}
		System.out.println(volunteerMenu);
		String userChoice = myReader.nextLine();
		
	
		showChoosenCommand(userChoice);
	}

	public void showChoosenCommand(String userInput) {
		
		if(userInput.equalsIgnoreCase(S_FOR_SUBMIT_OR_SINGUP_COMMAND)) {
			signUpForJobView();
			exitOrReturnView();
	
		}  else if(userInput.equalsIgnoreCase(L_FOR_LOGOUT_USER_VIEW_COMMAND)) {
			myController.logout();
		} else if (userInput.equalsIgnoreCase(V_FOR_VIEW_COMMAND)) {
			
			viewMyJobs();
			exitOrReturnView();
		} else {
			//return home
			initHome(myReader);
		}
	}

	public void exitOrReturnView() {
		
		StringBuilder exitString = new StringBuilder();
		exitString.append("What would you like to do?\n");
		exitString.append(R_FOR_RETURN_PREV_VIEW_FOR_USERS + " Return to prior menu\n");
		exitString.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " Logout");
		
		System.out.println(exitString);
		String userInput = myReader.nextLine();
		if (userInput.equalsIgnoreCase(L_FOR_LOGOUT_USER_VIEW_COMMAND)) {
			myController.logout();
		} else if(userInput.equalsIgnoreCase(R_FOR_RETURN_PREV_VIEW_FOR_USERS)) {
			initHome(myReader);
		}
		
		
	}

    public void viewMyJobs() {
       // String result = "\n";
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
           System.out.println("\nError: no jobs found for you\n");
        }
        
       // return result;
    }
    
   
 
    public void signUpForJobView(){
    	
    	System.out.println("Please choose number to sign up for a job\n");
    	
    	showAvailableJobs();
    	
   
    	
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			
		
			try {
				
			 	String userChoice = myReader.nextLine();
		    	int theId = Integer.parseInt(userChoice);
		    	
					if ( !myController.volunteerHasTheJob(theId) &&
							!myController.hasMinSignupDaysBeforeJobStartPassed(myController.getSingleJobByGivenId(theId)) &&
							!myController.isJobFullForSignUp(myController.getSingleJobByGivenId(theId)) &&
							!myController.hasJobViolateMaxJobPerDayPerVolunteer(theId)
							
							) { 
					myVolunteer.getMyVolunteerJobs().add(new Integer(userChoice));
					
					//update current total for the job
					int currenTotal = (myController.getSingleJobByGivenId(new Integer(theId)).getMyCurrentTotalVolunteers())+1;
					Job jobAdded = myController.getSingleJobByGivenId(new Integer(theId));
					jobAdded.setMyCurrentTotalVolunteers(currenTotal);
					
					//add volunteer to Job arrayList of volunteers
					jobAdded.getMyVolunteerList().add(myVolunteer.getMyUserId());
					System.out.println("You have successfully signed up for the job");
					break;
			
				}  else {
					
					if ( retries == MAX_USER_INPUT_TRIAL - OFFSET) {
						
						System.out.println("Failed max trial ...");
						
						break;
					} else {

						
						System.out.println("Signup failed. Reasons:You may have the job already, \n"
								+ "sign up date passed for the job,\n "
								+ "you have the another job on the same date\n"
								+ "or the job could be full");
						System.out.println("Try again, add job from available list only");
						continue;
						
					}
					
					
				}
			} catch (IndexOutOfBoundsException |InputMismatchException | NumberFormatException exception) {
				
	
				if ( retries == MAX_USER_INPUT_TRIAL - OFFSET) {
					
					System.out.println("Failed max trial redirecting to main menu...");
					
					initHome(myReader);
					break;
				} else {
					System.out.println("Job Id doesnt exist. Enter id from job list!");
					continue;
				}
				
			}
			   
		}
    }
    
    /**
     * 
     * @return true jobs displayed false otherwise 
     */
    
    public boolean showAvailableJobs() {
    	
    	boolean jobsAvailable = true;
    	
    	StringBuilder jobSignUpString = new StringBuilder();
    	StringBuilder output = new StringBuilder();
    	
    	jobSignUpString.append("Job Id-----current total Volunteers-------Job Description-------"
    			+ "-----------Start Date------EndDate-------City----Park\n");
    	
    	System.out.println(jobSignUpString);
    	List<Job> pendingJobs = myController.getMyPendingJobsForVolunteer();
    	String shortDescription = "";
    	
    	if(pendingJobs.isEmpty()) {
    		System.out.println("There are no jobs available currently");
    		jobsAvailable = false;
    		return jobsAvailable;
    	}
    	
    	
    	//loop if pending jobs has 1 job at least?? check that
    	for(int i = 0; i < pendingJobs.size(); i++) {
    		shortDescription = myController.truncateJobDescriptionForDisplay(pendingJobs.get(i));
    		
    				output.append(pendingJobs.get(i).getMyJobId() + "---"+ 
    				 pendingJobs.get(i).getMyCurrentTotalVolunteers()+"-------" + shortDescription
    				        + " --- " + pendingJobs.get(i).getMyStartDate() + " to " + 
    				pendingJobs.get(i).getMyEndDate() + " --- "+ 
    				            pendingJobs.get(i).getMyPark().getMyCity() + "------- " + 
    				pendingJobs.get(i).getMyPark().getMyName() + " \n");
    		
    		
    	}
    	//output final list of jobs
    	System.out.println(output);
    	
    	return jobsAvailable;
    }
}