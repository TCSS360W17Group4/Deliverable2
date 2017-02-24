package model;
import java.util.ArrayList;
import java.util.List;

public class VolunteerController extends AbstractController {
	
    private Volunteer myUser;    
    private List<Job>myJobs;
    private List<Volunteer>myVolunteers;
    
   
    
    public VolunteerController(Volunteer theUser, 
           List<Job> theJobs, List<Volunteer> theVolunteers) { 
    	
        super(theJobs,theUser);
        
        this.myUser = theUser;
        this.myJobs = theJobs;
        this.myVolunteers = theVolunteers;
        
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
	 
	 public boolean isSignupForJobSuccesful(int theJobId) {
		 
		 //check user already signed up/ in case number not listed entered
		 List<Integer> currentJobsList = myVolunteers.get(myUser.getMyUserId()).getMyVolunteerJobs();
		 
		 		if (myVolunteers.get(myUser.getMyUserId()).getMyBlackballStatus() || 
		 		        volunteerHasTheJob(myUser.getMyUserId(),theJobId) || 
		 				isSignUpDayPassed(myJobs.get(theJobId)) || 
		 				isJobFullForSignUp(myJobs.get(theJobId))) {
		 			//already volunteered or sign up date passed(BR.6C) or full or blackball(6B)
		 		    return false;
		 		    
		 		} else {
		 		
		 		    //add to the bucket
		 		    currentJobsList.add(new Integer(theJobId));
		 		    //make job list the current one
		 		    myVolunteers.get(myUser.getMyUserId()).setMyVolunteerJobs(currentJobsList);
                   	
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
					if(!isSignUpDayPassed(jobChecked) &&
							!isJobFullForSignUp(jobChecked) &&
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
	
	
    //D:not sure why we need this
    public List<Integer> getMyJobs() {
        return myUser.getMyVolunteerJobs();
    
}


}