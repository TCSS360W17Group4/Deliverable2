package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/*
 * ParksSystem to control the system data,
 * and login 
 * 
 * @author Dereje 
 *
 */
public class ParksSystem implements java.io.Serializable{

	private static AbstractUser myCurrentUser;
	private static List<Job> myJobs;
	private static List<Volunteer> myVolunteers;
	private static List<ParkManager> myParkManagers;
	private static List<UrbanParksStaff> myUrbanStaff;
	
	
	private static JobController myJobController;
	private static AbstractController myUserController;
	
	public ParksSystem(){
	    //myCurrentUser = new AbstractUser();
	    myJobs = new ArrayList<Job>();
	    myVolunteers = new ArrayList<Volunteer>();
	    myParkManagers = new ArrayList<ParkManager>();
	    myUrbanStaff = new ArrayList<UrbanParksStaff>();
	    myJobController = new JobController(myJobs);
	    ///myUserController = new AbstractController();
	}
	
	/***********************
	 * Searches for Park Managers
	 *******************/
	
	/**
	 * search for list of Volunteers for a job (past or present)
	 * with specific manager id
	 * 
	 * @param theVolunteers the volunteers to be searched
	 * @param theManagerId the id used to filter the volunteers
	 */
	public void getVolunteersByManagerId(List<Volunteer> theVolunteers, List<Job> thejobs, int theManagerId) {
		List<Job> managerJobs = getJobsByManagerId(thejobs,theManagerId);
		List<Volunteer> myVolunteers = new ArrayList<>();
		
		for(int i = 0; i < managerJobs.size(); i++) {
			
			int currentJobId = managerJobs.get(i).getMyJobId();
			
			for(int j = 0; j < theVolunteers.size(); j++) {
				//list of jobs vol volunteered
				List<Integer> currentVolJobIds = theVolunteers.get(j).getMyVolunteerJobs();
				//check if the job id belong to current manager id
				if(currentVolJobIds.contains(currentJobId)) {
					//add to volunteers under manager list
					myVolunteers.add(theVolunteers.get(j));
					
				}
			}
		}
	}
	
	/**
	 * Search for jobs by a manager id
	 * 
	 * @param theJobs all the jobs that already exist
	 * @param theManagerId the id of the manager
	 * 
	 * @return a list of jobs by specified manager id
	 */
	 //get jobs by manager Id
	 public List<Job> getJobsByManagerId(List<Job> theJobs, int theManagerId) {
		 List<Job> managerJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
				if(jobChecked.getMyJobManagerId()== theManagerId) {
						//add it to manager Jobs
						managerJobs.add(jobChecked);
					
				}
				
			}
		 
		 return managerJobs;
	 }
	 
	 /*****************************
	  * Query for volunteers
	  **************************/
	 
	 /**
	  * checks sign up for job is successful or not
	  * 
	  * @param theVolunteerId the volunteering currently signing up for the job
	  * @param theJobId the job id the volunteer choose to sign up
	  * @return true if volunteer successfully sign up for a job, otherwise false
	  */
	 
	 public boolean isSignupForJobSuccesful(int theVolunteerId, int theJobId) {
		 
		 //check user already signed up/ in case number not listed entered
		 List<Integer> currentJobsList = myVolunteers.get(theVolunteerId).getMyVolunteerJobs();
		 
		 		if (myVolunteers.get(theVolunteerId).getMyBlackballStatus() || 
		 		        volunteerHasTheJob(theVolunteerId,theJobId) || 
		 				myJobController.isSignUpDayPassed(myJobs.get(theJobId)) || 
		 				JobController.isJobFullForSignUp(myJobs.get(theJobId))) {
		 			//already volunteered or sign up date passed(BR.6C) or full or blackball(6B)
		 		    return false;
		 		    
		 		} else {
		 		
		 		    //add to the bucket
		 		    currentJobsList.add(new Integer(theJobId));
		 		    //make job list the current one
		 		    myVolunteers.get(theVolunteerId).setMyVolunteerJobs(currentJobsList);
                    	
                	//update total volunteers for the job
                	int currenTotal = myJobs.get(theJobId).getMyCurrentTotalVolunteers()+1;
    	 			myJobs.get(theJobId).setMyCurrentTotalVolunteers(currenTotal);
    	 			return true;
                    	 			
                    }
		 		
	 }
	 
	 
	 /**
	  * Search for available jobs for a volunteer to sign up for
	  * 
	  * @param theJobs theJobs the jobs to be searched
	  * @param volunteerId volunteer id the pending jobs searched for
	  * @return a list of jobs that are pending for given volunteer
	  */
	 //get pending jobs(open for sign up + is not full)
	 public List<Job> getMyPendingJobs(List<Job> theJobs, int theVolunteerId) {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
				if(!jobChecked.getMyJobIsPast()) {
					//job is not full and signing up day not passed and 
					//the volunteer does not have the job
					if(!myJobController.isSignUpDayPassed(jobChecked) &&
							!JobController.isJobFullForSignUp(jobChecked) &&
							!volunteerHasTheJob(theVolunteerId, jobChecked.getMyJobId())) {
						//add it to pending
						pendingJobs.add(jobChecked);
					}
				}
				
			}
		 
		 return pendingJobs;
	 }
	 
	 
	 
	 /**
	  * checks if the volunteer already has the job
	  * 
	  * @param theVolunteerId volunteer checked if already signed up
	  * @param theJobId the job checked 
	  * @return true if the volunteer has the job, false otherwise
	  */
	public boolean volunteerHasTheJob(int theVolunteerId, int theJobId) {
		 List<Integer> currentJobsList = myVolunteers.get(theVolunteerId).getMyVolunteerJobs();
		 return currentJobsList.contains(new Integer(theJobId));
	}
	
	/**
	 * Checks if Volunteer already has a job with the same date
	 * 
	 * @param theNewJobId the new job to be signed up for
	 * @param theVolunteerId the volunteer signing for the job
	 * @return true if volunteer has no job with that date, false otherwise
	 */
	//BR 6A
	public boolean duplicateSignUpIsPassed(int theNewJobId, int theVolunteerId) {
		
		List<Integer> currentJobsIds = myVolunteers.get(theVolunteerId).getMyVolunteerJobs();
		
		for(int i = 0; i < currentJobsIds.size(); i++) {
			
			Job prevJob = myJobs.get(currentJobsIds.get(i));
			Job newJob = myJobs.get(theNewJobId);
			
			//the volunteer has no start and end date
			if(!prevJob.getMyStartDate().equals(newJob.getMyStartDate()) &&
					!prevJob.getMyEndDate().equals(newJob.getMyEndDate())) {
				return true;
				
			}
			
		}
		
		return false;
	}
	
	
	/**
	 * search for jobs volunteered with given volunteer id
	 * 
	 * @param theVolunteerId volunteer job searched for
	 * @return jobs the volunteer signed up
	 */
	//story 7
	public List<Job> getJobsByVolunteerId(int theVolunteerId) {
		
		List<Integer> currentJobsIds = myVolunteers.get(theVolunteerId).getMyVolunteerJobs();
		List<Job> signupJobs = new ArrayList<>();
		for(int i = 0; i < currentJobsIds.size(); i++) {
			Job checkedJob = myJobs.get(currentJobsIds.get(i));
			//if not past job add it
			if(!checkedJob.getMyJobIsPast()) {
				signupJobs.add(checkedJob);
			}
		}
		
		return signupJobs;
	}
	
	
	/*************************
	 * Query for Urban Parks staff 
	 ************************/

	
	/**
	  * search for jobs that are not past yet
	  * 
	  * @param theJobs the jobs to be searched
	  * @return all jobs that are present (greater or equal to current date)
	  */
	 //get upcoming jobs for certain duration 
	//story 4
	 public List<Job> getUpcomingJobs(List<Job> theJobs, int theDuration) {
		 LocalDate currentDate = LocalDate.now();
		 LocalDate endDate = currentDate.plusDays(30);
		 List<Job> upcomingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
				if(!jobChecked.getMyJobIsPast() && jobChecked.getMyEndDate().isBefore(endDate)) {
						//add it to pending
						upcomingJobs.add(jobChecked);
					
				}
				
			}
		 
		 return upcomingJobs;
	 }
	 
	 /**
	  * sets pending jobs limit to a new number
	  * 
	  * @param theNewMax the new pending jobs limit
	  */
	 //story 5
	 public void updatePendingJobsLimit(int theNewMax) {
		 myJobController.setMyMaxNumberOfPendingJobs(theNewMax);
		 
	 }
	 
     /********************************
      * Handle user login and logouts
      ********************************/
	 
	
	 /**
	  * authenticate user login 
	  * 
	  * @param theUserName the username to be checked for login
	  */
//we need to return a user SOMEHOW otherwise when is the Controller ever going to be built?
	public AbstractController loginSuccessful(String theUserName) {
		//parse the user name
		String userType = theUserName.substring(0,3);
		//can be index 3, since username has length 4 generally
		String userId = theUserName.substring(3,theUserName.length());
		int id;
		
		if (UserType.userExist(userType) && userId.matches("[0-9]+")) {
			id = Integer.parseInt(userId);
			
		} else {
			return myUserController; //check elsewhere whether the user is instantiated or if empty
			
		}
				
				//id check to avoid nullpointer calling Contains
		if(userType.equals(UserType.Volunteer.getMyType()) 
				&& id < myVolunteers.size()
				&& myVolunteers.contains(myVolunteers.get(id)) ) {
		    myCurrentUser = myVolunteers.get(id);
			myUserController = new VolunteerController(
			        (Volunteer)myCurrentUser, 
		            myVolunteers, 
		            myParkManagers, 
		            myUrbanStaff, 
		            myJobController);
		    
		} else if(userType.equals(UserType.Manager.getMyType())
		        && id < myParkManagers.size()
				&& myParkManagers.contains(myParkManagers.get(id)) ) {
		    myCurrentUser = myParkManagers.get(id);
		    myUserController = new ParkManagerController(
		            (ParkManager)myCurrentUser, 
                    myVolunteers, 
                    myParkManagers, 
                    myUrbanStaff, 
                    myJobController);
             
		} else if (userType.equals(UserType.Staff.getMyType()) 
				&& id < myUrbanStaff.size()
				&& myUrbanStaff.contains(myUrbanStaff.get(id)) ) {
		    myCurrentUser = myUrbanStaff.get(id);
		    myUserController = new UrbanParksStaffController(
                    (UrbanParksStaff)myCurrentUser, 
                    myVolunteers, 
                    myParkManagers, 
                    myUrbanStaff, 
                    myJobController, 
                    myJobController.getMyJobsList() /*whatever*/);               
	    	
		 } else {
		     //I think we already have a 'else' block that prevents this block from ever being reached
		     return myUserController;
		 }
		
		return myUserController;
		//return isSuccessful;
		
	}
	
	
	/**
	 * checks a user id exist for a list of users
	 * 
	 * @param users the list of users
	 * @param theId the id to be checked
	 * @return true if that id exist for a user
	 */
	public <E> boolean isIdExist(List<E> users, int theId) {

		//the user id follows the index
		//user id greater than or equal the size, it doesnt exist
		return (theId>=0 && theId < users.size());
			
	}
	
	public void run(){
	    //someone needs to do this at some point, not necessary for JUnit testing
	    String userName = new String();
	    loginSuccessful(userName);
	    //Also needed after we sort out JUnit testing
	    //myUserController.run(); 
	    
	}

	public List<ParkManager> getMyParkManagers() {
		return myParkManagers;
		
	}

	public void setMyParkManagers(List<ParkManager> theParkManagers) {
		ParksSystem.myParkManagers = theParkManagers;
		
	}
	

	public  List<Volunteer> getMyVolunteers() {
		return myVolunteers;
		
	}

	public  void setMyVolunteers(List<Volunteer> theVolunteers) {
		ParksSystem.myVolunteers = theVolunteers;
		
	}

	public static List<UrbanParksStaff> getMyUrbanStaff() {
		return myUrbanStaff;
		
	}

	public void setMyUrbanStaff(List<UrbanParksStaff> theUrbanStaff) {
		ParksSystem.myUrbanStaff = theUrbanStaff;
		
	}

	public void logout() {
		//explicitly set to null for logout
	    myCurrentUser = null;
	    myUserController = null;
	    //Want to make sure these objects are not kept in memory, this might not be necessary
	    
	}
	
	/*public static AbstractUser FindVolunteer(String theUserName) {
	    for (Volunteer aVolunteer : myVolunteers) {
	        //integer.toString();
	        if (aVolunteer.getMyUserName().equals(theUserName) ) {
	            return aVolunteer;
	            
	        }
	        
	    }
	    
	    AbstractUser UserToRet = new Volunteer(); //return a null user.  Check elsewhere if the user is null to stop login routine
        return UserToRet;	    
	        
	    } */

}
