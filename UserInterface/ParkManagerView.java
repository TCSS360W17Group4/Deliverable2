package UserInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.JobController;
import model.ParkManager;
import model.ParkManagerController;



public class ParkManagerView extends HomeView {
	
	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static final String V_FOR_VIEW_COMMAND = "v";
	private static final String S_FOR_SUBMIT_OR_SINGUP_COMMAND = "s";
	private static final String L_FOR_LOGOUT_USER_VIEW_COMMAND = "L";
	private static final String R_FOR_RETURN_PREV_VIEW_FOR_USERS = "r";
	private  ParkManager myManager;
	private  JobController jobValidator;
	private  ParkManagerController myController;
	private Scanner myReader;
	
	
	public ParkManagerView(ParkManagerController theController, JobController theJobValidator, ParkManager theUser){
		super();
		this.myManager = theUser;
		this.jobValidator = theJobValidator;
		this.myController = theController;
	
		HomeHeaderTitle();
	}
	
	private void HomeHeaderTitle() {
		System.out.println("Today, " + myController.convertLocalDatetToReadableString(LocalDate.now())+
				" Welcome to UParks " + myManager.getMyName());
	   
	}
	public void initHome(Scanner theScanner){

		myReader = theScanner;
		StringBuilder managerMenu = new StringBuilder();

		managerMenu.append("Choose the command option, and press enter:\n");
		if(myController.isNewJobAccepted()) {
			managerMenu.append(S_FOR_SUBMIT_OR_SINGUP_COMMAND +" Submit a job\n");
			managerMenu.append(V_FOR_VIEW_COMMAND + " View my Jobs\n");
			managerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " logout");
		} else {
			managerMenu.append("We are not accepting new job currently\n");
			managerMenu.append(V_FOR_VIEW_COMMAND+ " View my Jobs\n");
			//show the jobs already created and 1 option
			managerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " logout");
		}
		System.out.println(managerMenu);
		String userChoice = myReader.nextLine();
		
	
		showChoosenCommand(userChoice);
	}

	public void showChoosenCommand(String userInput) {
		
		if(userInput.equalsIgnoreCase(S_FOR_SUBMIT_OR_SINGUP_COMMAND)) {
			sumbitJobView();
			
			exitOrReturnView();
	
		}  else if(userInput.equalsIgnoreCase(L_FOR_LOGOUT_USER_VIEW_COMMAND)) {
			myController.logout();
		} else if (userInput.equalsIgnoreCase(V_FOR_VIEW_COMMAND)) {
			
			viewManagerJobs();
			exitOrReturnView();
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
	
	public void sumbitJobView(){
		//park name and city, job manager id
		
		if(!jobValidator.isParkAdded()){
			System.out.println("Enter 1: for " + myManager.getMyParks().get(0)
					+ " enter 2 for " + myManager.getMyParks().get(0));
			int userChoice = myReader.nextInt();
			
			jobValidator.pickAPark(userChoice);
		
		} 
		
		//assume initial view is solid
		acceptDateView();
		acceptEndDateView();
		acceptDescriptionView();
		acceptNumOfVolunteers();
		confirmSubmitView();
		
	
	}
	
	public boolean acceptDateView() {

		System.out.println("Enter dates between " + LocalDate.now() + " and " + LocalDate.now().plusDays(30)+" inclusive");
		System.out.println("Enter Start date(format MM/dd/yy Eg: 2/10/17 same as Feb 10, 2017");
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
		
		    	String date = myReader.nextLine();
		    	if(!jobValidator.isStartDateAdded(date)) {
		    		System.out.println("Please enter again");
		    		continue;
		    	} else {
		    		return true;
		    	}
		    	
		   
		}
		
		
		return false;
	}
	
	public boolean acceptEndDateView() {
		System.out.println("Enter job duration: 1 2 or 3");
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			
			int jobDuration = myReader.nextInt();
			myReader.nextLine();//consume newline break
			
	    	if(!jobValidator.isEndDateAdded(jobDuration)) {
	    		System.out.println("Enter again");
	    		continue;
	    	} else {
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}

	public boolean acceptDescriptionView() {
		System.out.println("Enter job Description:");
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			
			String jobDescription = myReader.nextLine();

	    	if(!jobValidator.isJobDescriptionAdded(jobDescription)) {
	    		System.out.println("Enter again");
	    		continue;
	    	} else {
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}
	
	/**
	 * Checks business rule: Not more than the maximum number of volunteers for any job (MAX_NUM_VOLUNTEERS_PER_JOB)
	 * Does this by calling a validator method for light, medium, and heavy volunteer types
	 * 
	 * @return 
	 */
	public boolean acceptNumOfVolunteers() {
		//not complete
		boolean allNumAdded = false;
		System.out.println("Enter max volunteers for each category: Total less than 30");
		
		//each question has max 3 trials
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			System.out.println("Light: ");
			int light = myReader.nextInt();
			if(!jobValidator.isMaxLightVolNumberValid(light)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;//go to medium
			}
		}	
		
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			System.out.println("Medium: ");
			int med = myReader.nextInt();
			
			if(!jobValidator.isMaxMediumVolNumValid(med)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;//go to heavy
			}
		}	
		

		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			System.out.println("Heavy: ");
			int heavy = myReader.nextInt();
			myReader.nextLine();
			if(!jobValidator.isMaxHeavyVolNumValid(heavy)) {
				System.out.println("Enter valid number again");
	    		continue;
			} else {
				allNumAdded = true;
				break;
			}
		}	
			
		return allNumAdded;
	}
	
	
	public boolean confirmSubmitView() {
		boolean jobSubSuccess = false;
		System.out.println("Enter y/n to accept or reject the job submission");
		
		String userConfirmation = myReader.nextLine();
		if(userConfirmation.equalsIgnoreCase("Y")) {
			jobValidator.addJob();
			
			jobSubSuccess = true;
		} else {
			jobSubSuccess = false;
		}
		//clean it for new job Validation
		jobValidator = new JobController(new Job(null), myController.getJobs(),myManager);
		return jobSubSuccess;
	}
	
	public void viewManagerJobs(){
		List<Job>managerJobs = myController.getJobsByManagerId();
		StringBuilder managerJobsString = new StringBuilder();
		managerJobsString.append("Job Id-----Job Description-------Start Date-------End Date-----Current Volunters # \n");
		
		String shortDescription = "";
		for(int i = 0; i < managerJobs.size(); i++) {
			
			shortDescription = myController.truncateJobDescriptionForDisplay(managerJobs.get(i));
			
			managerJobsString.append(managerJobs.get(i).getMyJobId() +" ------------ " + shortDescription + "------" +
		         managerJobs.get(i).getMyStartDate()+ " ------- "+ managerJobs.get(i).getMyEndDate()+" ----- "
					+ managerJobs.get(i).getMyCurrentTotalVolunteers() + " \n");
			
		}
		System.out.println(managerJobsString);
		
	}
}