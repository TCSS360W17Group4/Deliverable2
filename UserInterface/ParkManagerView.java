<<<<<<< HEAD
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
	
	
	private  Scanner myReader;
	private JobController myJobController;
	private  List<Job>myParkSystemJobs;
	private List<Volunteer>myParksSystemVolunteer;
	private ParkManager myCurrentManager;
	
	public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myReader = new Scanner(System.in);
		myJobController = new JobController();
		myParkSystemJobs = theJobs;
		myParksSystemVolunteer = theVolunteers;
		myCurrentManager = theManager;
		//init initial page for this user
		
		initManagerHomeView(theManager);
	}
	
	
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
		
		//myJobController.addStartDate(line,newJob,myParkSystemJobs);
		
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
		System.out.println("Enter max number of volunteers required: Total less than 30");
		System.out.println();
		return false;
	}
}
=======
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
	
	
	private  Scanner myReader;
	private JobController myJobController;
	private  List<Job>myParkSystemJobs;
	private List<Volunteer>myParksSystemVolunteer;
	private ParkManager myCurrentManager;
	
	public ParkManagerView(ParkManager theManager, List<Volunteer>theVolunteers, List<Job> theJobs) {
		myReader = new Scanner(System.in);
		myJobController = new JobController();
		myParkSystemJobs = theJobs;
		myParksSystemVolunteer = theVolunteers;
		myCurrentManager = theManager;
		//init initial page for this user
		
		initManagerHomeView(theManager);
	}
	
	
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
		
		//myJobController.addStartDate(line,newJob,myParkSystemJobs);
		
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
		System.out.println("Enter max number of volunteers required: Total less than 30");
		System.out.println();
		return false;
	}
}
>>>>>>> f4d60e287082101ffb0d383423225672347d0b78
