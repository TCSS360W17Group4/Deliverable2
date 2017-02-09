
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


<<<<<<< HEAD
/**
=======
/*
>>>>>>> dereje2
 * ParksSystem to control the system data,
 * and login 
 * 
 * @author Dereje 
 *
 */
public class ParksSystem {

	private static AbstractUser myCurrentUser;
<<<<<<< HEAD
	List<Job> myJobs;
	List<Volunteer> myVolunteers;
	private static List<ParkManager> myParkManagers;
	List<UrbanStaff> myUrbanStaff;
	
	
	private static JobController jobController = new JobController();
	
	public ParksSystem(){
	    myJobs = new ArrayList<Job>();
	    myVolunteers = new ArrayList<Volunteer>();
	    ParksSystem.myParkManagers = new ArrayList<ParkManager>();
	    myUrbanStaff = new ArrayList<UrbanStaff>();
=======
	private static List<Job> myJobs;
	private static List<Volunteer> myVolunteers;
	private static List<ParkManager> myParkManagers;
	private static List<UrbanParksStaff> myUrbanStaff;
	
	
	private static JobController myJobController;
	private static AbstractController myUserController;
	
	public ParksSystem(){
	    myCurrentUser = new AbstractUser();
	    myJobs = new ArrayList<Job>();
	    myVolunteers = new ArrayList<Volunteer>();
	    myParkManagers = new ArrayList<ParkManager>();
	    myUrbanStaff = new ArrayList<UrbanParksStaff>();
	    myJobController = new JobController(myJobs);
	    myUserController = new AbstractController();
>>>>>>> dereje2
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
<<<<<<< HEAD
		 				volunteerHasTheJob(theVolunteerId,theJobId) || 
		 				jobController.isSignUpDayPassed(myJobs.get(theJobId)) || 
		 				jobController.isJobFullForSignUp(myJobs.get(theJobId))) {
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
=======
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
		 		
>>>>>>> dereje2
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
<<<<<<< HEAD
					if(!jobController.isSignUpDayPassed(jobChecked) &&
							!jobController.isJobFullForSignUp(jobChecked) &&
=======
					if(!myJobController.isSignUpDayPassed(jobChecked) &&
							!JobController.isJobFullForSignUp(jobChecked) &&
>>>>>>> dereje2
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
<<<<<<< HEAD
		 jobController.setMyMaxNumberOfPendingJobs(theNewMax);
=======
		 myJobController.setMyMaxNumberOfPendingJobs(theNewMax);
>>>>>>> dereje2
	 }
	 
     /********************************
      * Handle user login and logouts
      ********************************/
	 
	
	 /**
	  * authenticate user login 
	  * 
	  * @param theUserName the username to be checked for loggin
	  */
	public void login(String theUserName) {
		//parse the user name
		String userType = theUserName.substring(0,3);
		String userId = theUserName.substring(3,theUserName.length());
		
		//check if user type exist and user id is an int
		if(UserType.userExist(userType) && userId.matches("[0-9]+")) {
			int id = Integer.parseInt(userId);
			
<<<<<<< HEAD
			//logged in, now load the user to the system
=======
			if (userType == "vol") {
			    myUserController = new VolunteerController(
			            (Volunteer)myCurrentUser, 
			            myVolunteers, 
			            myParkManagers, 
			            myUrbanStaff, 
			            myJobController);
			}
			else if (userType == "mgr") {
	             myUserController = new ParkManagerController(
                        (ParkManager)myCurrentUser, 
                        myVolunteers, 
                        myParkManagers, 
                        myUrbanStaff, 
                        myJobController);
			} 
			else if (userType == "stf"){
			    /*
			    myUserController = new UrbanParksStaffController(
                        (UrbanParksStaff)myCurrentUser, 
                        myVolunteers, 
                        myParkManagers, 
                        myUrbanStaff, 
                        myJobController);
                        */
			}
			//logged in, now load the user to the system
			
			
>>>>>>> dereje2
			if(isUserLoaded(userType,id)) {
				
			} else {
				//load failed
			}
			
		} else {
			//user doesnt exist
		}
			
	}
	
	public void logout() {
		
	}
	
	/**
	 * loads the current user to the system
	 * 
	 * @param theUserType the type of user(enum Manager, Staff, Volunteer)
	 * @param theUserId the id of the user
	 * @return true if current user object created
	 */
	public boolean isUserLoaded(String theUserType, int theUserId) {
	
		boolean loadSuccess = false;
		if(theUserType == UserType.Manager.getMyType() && isIdExist(myParkManagers,theUserId)) {
			//check instanceof and init outside?
			myCurrentUser = myParkManagers.get(theUserId);
			
			loadSuccess = true;
			
		  
		} else if (theUserType == UserType.Staff.getMyType() && isIdExist(myParkManagers,theUserId)) {
			
			myCurrentUser = myUrbanStaff.get(theUserId);
			loadSuccess = true;
			 
		} else {
			
			myCurrentUser = myVolunteers.get(theUserId);
			loadSuccess = true;
			
		}
		
		return loadSuccess;
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
	
<<<<<<< HEAD
=======
	public void run(){
	    //someone needs to do this at some point, not necessary for JUnit testing
	    String userName = new String();
	    login(userName);
	    //Also needed after we sort out JUnit testing
	    //myUserController.run();  
	}
	
>>>>>>> dereje2
}
