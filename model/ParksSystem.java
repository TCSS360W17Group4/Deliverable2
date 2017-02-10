package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * ParksSystem to control the system data,
 * and login 
 * 
 * @author Dereje 
 *
 */
public class ParksSystem {

	private static AbstractUser myCurrentUser;
	List<Job> myJobs = new ArrayList<Job>();
	List<Volunteer> myVolunteers = new ArrayList<Volunteer>();
	private static List<ParkManager> myParkMangers= new ArrayList<ParkManager>();
	List<UrbanParksStaff> myUrbanParksStaff =  new ArrayList<UrbanParksStaff>();
	//added Job Controller to use the query methods
	
	private static JobController jobController = new JobController();
	
	
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
	  * Searches for volunteers
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
					if(!jobController.isSignUpDayPassed(jobChecked) &&
							!jobController.isJobFullForSignUp(jobChecked) &&
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
		 jobController.setMyMaxNumberOfPendingJobs(theNewMax);
	 }
	 
     /********************************
      * Handle user login and logouts
      ********************************/
	 
	
	public void logout() {
		
	}
	
	/**
	 * loads the current user to the system
	 * 
	 * @param theUserType the type of user(enum Manager, Staff, Volunteer)
	 * @param theUserId the id of the user
	 * @return true if current user object created
	 */
	public boolean loginSuccessful(String theUserName) {
		String userType = theUserName.substring(0,3);
		String userId = theUserName.substring(3,theUserName.length());
		boolean isSuccessful = false;
		
		if(UserType.userExist(userType) && userId.matches("[0-9]+")) {
			int id = Integer.parseInt(userId);
			
			if(userType.equals(UserType.Manager.getMyType()) ) {
				//check instanceof and init outside?
				ParkManager currentUser = myParkMangers.get(id);
				
				ParkManagerController pmController = new ParkManagerController(currentUser,myVolunteers,myJobs);
				System.out.println("manager");
				
				isSuccessful = true;
			  
			} else if (userType.equals(UserType.Staff.getMyType()) ) {
				
				UrbanParksStaff currentUser = myUrbanParksStaff.get(id);
				System.out.println("staff");
				isSuccessful = true;
			} else if (userType.equals(UserType.Volunteer.getMyType()) ){
				System.out.println("volunteerr");
				//myCurrentUser = myVolunteers.get(theUserId);
				Volunteer currentUser = myVolunteers.get(id);
				isSuccessful = true;
			} else {
				System.out.println("NONE");
				isSuccessful = false;
			}
			
			
			
		} else {
			isSuccessful = false;
		}
		
		
		return isSuccessful;
	
		
		
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
	
}
