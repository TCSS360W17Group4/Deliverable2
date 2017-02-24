package model;
/*
 * Stub for AbstractController
 * everyone feel free to use, fix, enhance, whatever 
 * 
 */ 
 

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* 
 * @authors Christopher Hall, Dereje Bireda, Tony Richardson, Brian Hess
 * @Winter Quarter 2017
 * 
 */


public abstract class AbstractController implements Serializable {
  

	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 

	private static final int MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE = 2; 
	private static final int DEFAULT_MAX_NUM_PENDING_JOBS = 20;
	
	

    private  List<Job> myJobs;
    private  AbstractUser myUser;
    
    private boolean pageRedirected;
    
    public AbstractController(List<Job>theJobs, AbstractUser theUser) {
    	myJobs = theJobs;
    	myUser = theUser;
    	
    	//
    	pageRedirected = false;
    	runUpdate(myJobs);
    }
    

    public void runUpdate(List<Job>theJobs) {
    	for(int i = 0 ; i < theJobs.size(); i++) {
    		Job job = theJobs.get(i);
    		 if(isMyJobPast(job)) {
    			 job.setMyJobIsPast(true);
    			 //past no longer pending too
    			 job.setMyJobIsPending(false);
    		 }
    	}
    }
    
    /**
	 * Checks if new jobs can be added
	 * 
	 * @param theJobs the total jobs to be checked
	 * @return true if job if new job is allowed
	 */

	 public boolean isNewJobAccepted() {
		 List<Job> pendingJobs = getMyPendingJobs();
		 
		 return pendingJobs.size() < DEFAULT_MAX_NUM_PENDING_JOBS;
	 }
	 
	 
	 //get pending jobs(open for sign up + is not full)
	 public List<Job> getMyPendingJobs() {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < myJobs.size(); i++) {
			 	Job jobChecked = myJobs.get(i);
				if(!jobChecked.getMyJobIsPast()) {
					//job is not full and signing up day not passed
					if(!isSignUpDayPassed(jobChecked) &&
							!isJobFullForSignUp(jobChecked)) {
						//add it to pending
						pendingJobs.add(jobChecked);
					}
				}
				
			}
		 
		 return pendingJobs;
	 }
    
    public List<Job> getJobsById(int theJobID) {
        List<Job> list = new ArrayList<Job>();
        Iterator<Job> iterator = list.iterator();
        Job job;
        while (iterator.hasNext()) {
            job = iterator.next();
            if (job.getMyJobId() == theJobID) {
                // check whether the job is pending 
                list.add(job);       
            }
            
        }
        return list;
        
    }
    
 
    
    /**
	  * Checks a job has passed signing up date, 
	  * i.e must be more than three days from current
	  * 
	  * @param theJob the job to be checked
	  * @return true if job still available for sign up, false otherwise
	  */
	//signup day passed = job is past or sign up date less than 2 days to job start time
	 public boolean isSignUpDayPassed(Job theJob) {
		 LocalDate currentDate = LocalDate.now();
		 //CHECK NOT PAST
		 if(!isMyJobPast(theJob)){
		 //job is no longer open for sign up

			 //make it different method
			 return ChronoUnit.DAYS.between(currentDate,theJob.getMyStartDate()) < MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE;
		 }
		 
		 //no need to check exit
		 return false;
		 
	 }
	 
	 //???This can go to volunteers 
	 /**
	  * checks if job reached full capacity for volunteering
	  * 
	  * @param theJob the job to be checked 
	  * @return true if job reached maximum volunteer limit, false otherwise
	  */
	 //job is full for sign up BR: 2B
	 public static boolean isJobFullForSignUp(Job theJob) {
		 
		 int totalVolunteersNeeded = totalVolunteersPerJob(theJob);
		 int currentTotal = theJob.getMyCurrentTotalVolunteers();
		 return totalVolunteersNeeded == currentTotal;
	 }
	 
	 //this can go to volunteers
	 /**
	  * calculate the total number of volunteers for a job
	  * 
	  * @param theJob the job to be checked
	  * @return the total number of volunteers(light,medium,heavy)
	  */
	 //total volunteers per job
	 public static int totalVolunteersPerJob(Job theJob) {
		 int currentTotalVolunteers = 0;
		 
		 currentTotalVolunteers = theJob.getMyLightVolunteerNumber() + 
				                 theJob.getMyMediumVolunteerNumber() + 
				                  theJob.getMyHeavyVolunteerNumber();
		 return currentTotalVolunteers;
		 
	 } 
	 
	 //???this can go to abstract controller
	 /**
	  * checks if the job is older than current date
	  * 
	  * @param theJob the job to be checked
	  * @return true if job is older than the current date
	  */
	 //check if job is past
	 public boolean isMyJobPast(Job theJob){
		 LocalDate currentDate = LocalDate.now();
		 //-ve means past,true
		 return ChronoUnit.DAYS.between(currentDate,theJob.getMyEndDate()) < 0;
		// return (betweenDates(currentDate,theJob.getMyEndDate()) < 0);
			 
	 }
	 /**
	  * update job status(past status and pending status)
	  * 
	  * @param theJob the job to be updated
	  */
	 
	 public void updateJobPastStatus(Job theJob){
		 if(isMyJobPast(theJob)) {
			 theJob.setMyJobIsPast(true);
			 //past no longer pending too
			 theJob.setMyJobIsPending(false);
		 }

	 }
	 

		public AbstractUser getMyUser() {
		
			return this.myUser;
		}
		public List<Job> getJobs() {
			return myJobs;
		}
	 public void logout() {
		 this.pageRedirected = true;
	 }
	 
	 public boolean getIsPageRedicrected() {
		 
		 return this.pageRedirected;
	 }
}