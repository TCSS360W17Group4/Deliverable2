package UserInterface;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.JobController;
import model.ParkManager;
import model.ParkManagerController;



public class ParkManagerView extends HomeView {
	
	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static final int OFFSET = 1;
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 10;
	private static final String V_FOR_VIEW_COMMAND = "V";
	private static final String S_FOR_SUBMIT_OR_SINGUP_COMMAND = "S";
	private static final String L_FOR_LOGOUT_USER_VIEW_COMMAND = "L";
	private static final String R_FOR_RETURN_PREV_VIEW_FOR_USERS = "R";
	private static final int MAX_ALLOWED_DATE_INTO_FUTURE = 75;
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
			managerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " Logout");
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
//		if(acceptDateView()){
//		
//			if(acceptEndDateView()) {
//				if(acceptDescriptionView()) {
//					if(acceptNumOfVolunteers()) {
//						
//						if(confirmSubmitView()){
//							
//						}
//					}
//				}
//			}
//			
//		}
	
		if(acceptDateView()  && acceptEndDateView() 
				&& acceptDescriptionView() && acceptNumOfVolunteers() 
				&& confirmSubmitView()) {
			
			System.out.println("Job added successfully");
		}
		
	
	}
	
	public boolean acceptDateView() {

		System.out.println("Enter dates between " + LocalDate.now() + " and " +
		LocalDate.now().plusDays(MAX_ALLOWED_DATE_INTO_FUTURE)+" inclusive");
		System.out.println("Enter Start date(format MM/dd/yy");
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
		
		    	String date = myReader.nextLine();
		    	if(!jobValidator.isStartDateAdded(date)) {
		    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
		    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			
		    		} else {
		    		System.out.println("Please enter correct date");
		    		}
		    		continue;
		    	} else {
		    		return true;
		    		
		    	}
		    	
		   
		}
		
		
		return false;
	}
	
	public boolean acceptEndDateView() {
		System.out.println("Enter job duration: 1 2 or 3");
		boolean numAccepted = false;
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			String jobDuration = myReader.nextLine();
			
			int jobDurationIntValue = 0;
			
			try {
			
				jobDurationIntValue = Integer.parseInt(jobDuration);
			} catch(InputMismatchException | NumberFormatException ex){
				
				numAccepted = false;
				System.out.println("Unexpected input exiting..");
				break;
			}
		
		
			
			//we have number read this part
	    	if(jobDurationIntValue < 0 || !jobValidator.isEndDateAdded(jobDurationIntValue)) {
	    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
	    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			numAccepted = false;
	    			break;
	    		} else {
	    			System.out.println("Please enter valid duration");
	    		}
	    		continue;
	    	} else {
	    		numAccepted = true;
	    		break;
	    	}
	    	
	   
		}
	
	
		return numAccepted;
	}

	public boolean acceptDescriptionView() {
		
		System.out.println("Enter job Description:");
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			
			String jobDescription = myReader.nextLine();

	    	if(!jobValidator.isJobDescriptionAdded(jobDescription)) {
	    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
	    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			break;
	    			
	    		} else {
	    			System.out.println("Please enter valid description");
	    		}
	    		continue;
	    	} else {
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}

	public boolean acceptNumOfVolunteers() {
	
		boolean numAccepted = false;
		System.out.println("Enter Volunteers number required: light + medium + heavy <= " + MAX_NUM_VOLUNTEERS_PER_JOB);
		
		//each question has max 3 trials
		
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			System.out.println("Light: ");
			int lightIntValue = 0;
			try {
				String light = myReader.nextLine();
				lightIntValue = Integer.parseInt(light);
			} catch(InputMismatchException | NumberFormatException ex){
				System.out.println("Failed: Unexpected input exiting..");
				numAccepted = false;
				break;
			}
			
			if(!jobValidator.isMaxLightVolNumberValid(lightIntValue)) {
				
				if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
					System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			numAccepted = false;
	    			break;
	    		} else {

					System.out.println("Enter valid max light volunteer number");
	    		}
	    	
	    		continue;
			} else {
				numAccepted = true;
				break;//go to medium
			}
		}	
		
		//start getting medium volunteer number
		if (numAccepted) {//previous category accepted we check this
			for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
				System.out.println("Medium: ");
				int medIntValue =0;
				try {
					String med = myReader.nextLine();
					medIntValue = Integer.parseInt(med);
				} catch(InputMismatchException ex){
					
					numAccepted = false;
					System.out.println("Failed: Unexpected input exiting..");
					break;
				}
				if(!jobValidator.isMaxMediumVolNumValid(medIntValue)) {
					if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
						System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			numAccepted = false;
		    		} else {
		    			System.out.println("Enter valid max medium volunteer number");
		    		}
		    	
				
		    		continue;
				} else {
					numAccepted = true;
					break;//go to heavy
				}
			}	
		}

		//start getting medium volunteer number
		if(numAccepted) {//previous categeory accepted we check this
			for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
				System.out.println("Heavy: ");
				int heavyIntValue = 0;
				
				try {
					String heavy = myReader.nextLine();
					heavyIntValue = Integer.parseInt(heavy);
				} catch(InputMismatchException ex){
					
					numAccepted = false;
					System.out.println("Failed: Unexpected input exiting..");
					break;
				}
				
				
				if(!jobValidator.isMaxHeavyVolNumValid(heavyIntValue)) {
					if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
						System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			numAccepted = false;
		    		
		    		} else {
		    			System.out.println("Enter valid max heavy number of volunteer");
		    		}
		    		continue;
				} else {
					numAccepted = true;
					break;
				}
			}	
				
		
		}
		
		return numAccepted;
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