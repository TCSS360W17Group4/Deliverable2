import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobController {

	
	private static final int MAX_NUM_PENDING_JOBS = 30;
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 30;
	private static final int MAX_DAYS_FOR_FUTURE_JOB_DATE = 30;
	private static final int MAX_JOB_LENGTH_IN_DAYS = 2;
	private static final int MAX_JOBS_PER_DAY_PER_MANAGER = 2;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	
	private List<Job> myJobs = new ArrayList<Job>(); 
	
	private Job myJob = new Job(new Park());
	
	
	public static void main(String[] args) {
		Date d1 = convertStringToDate("02/8/2017");
		Date d2 = convertStringToDate("02/04/2017");
		Date d3 = Calendar.getInstance().getTime();
		System.out.println(betweenDates(d2,d1));
		System.out.println(d3);
		//start date and end date difference 1 or 2?? how could you tell 
	}
	
	public int createJob(int theUserId) {
		//theJob = new Job(new Park());
		if (myJobs.isEmpty()) {
			return 0;//job is full
		} else {
			//form with Manager Id
			myJob.setMyJobManagerId(theUserId);
		}
	
		
		return 0;
	}
	
	
	public void addJobParkName(String theParkName, Job theJob){
		theJob.getMyPark().setMyName(theParkName);
	}
	
	public void addJobCity(String theCity, Job theJob) {
		theJob.getMyPark().setMyCity(theCity);
	}
	
	public void addStartDate(String theDate, Job theJob) {
		//convert String to date
		Date jobStartDate = convertStringToDate(theDate);
		//check the job is future, minus -current 3 days//assumption based on BR volunteer
		Date currentDate = Calendar.getInstance().getTime();
		 //BR: C & E
		if (betweenDates(jobStartDate,currentDate)>= MIN_JOB_POST_DAY_LENGTH && 
				betweenDates(jobStartDate,currentDate)<= MAX_DAYS_FOR_FUTURE_JOB_DATE ){
			//BR:D
			if(isDuplicateStartDatePassed(jobStartDate, theJob.getMyJobManagerId())) {
				theJob.setMyStartDate(jobStartDate);
			}
		} else {
			//return with error
		}
		
	}
	
	public void addEndDate(String theDate, Job theJob) {
		//convert String to date
		Date jobEndDate = convertStringToDate(theDate);
		//check the job is future, minus -current 3 days//assumption based on BR volunteer
		Date currentDate = Calendar.getInstance().getTime();
	    if(betweenDates(theJob.getMyStartDate(),jobEndDate) >= 0 && 
	    		betweenDates(theJob.getMyStartDate(),jobEndDate)<=2){
	    	
	    }
	}
	public void addTime(String theTime) {
		
	}
	public void addNumOfLightVolunteer(int theNum) {
		
	}
	
	public void addNumOfMediumVolunteer(int theNum) {
		
	}
	public void addNumOfHeavyVolunteer(int theNum) {
		
	}
	
	public boolean isJobAdded(boolean theValue) {
		
		
		return true;//if job is added
	}
	
	
	//check manager has add limited job per day
	 public boolean isDuplicateStartDatePassed(Date theStartDate, int managerId) {
		//check job with the same manager exists same date more than twice
		 
		 
		 boolean dateHasPassed = true;
			int sameStartDate = 0;
			for (int i = 0; i < myJobs.size(); i++) {
				if(myJobs.get(i).getMyJobManagerId()== managerId){
					if(myJobs.get(i).getMyStartDate() == theStartDate ||
							myJobs.get(i).getMyEndDate() == theStartDate){
						sameStartDate += 1;
					}
				}
				
				//check if count is at least 2, exit . With current job it will be 3 jobs, not allowed
				if(sameStartDate >= MAX_JOBS_PER_DAY_PER_MANAGER){
					
					dateHasPassed = false;
				} 
			}
			
			return dateHasPassed;
	 }
		 
	 public boolean isDuplicateEndDatePassed(Date theEndDate, int managerId) {
		 
		 boolean dateHasPassed = true;
			//check job with the same manager exists same date more than twice
				int sameEndDate = 0;
				for (int i = 0; i < myJobs.size(); i++) {
					if(myJobs.get(i).getMyJobManagerId()== managerId){
						if(myJobs.get(i).getMyStartDate() == theEndDate ||
								myJobs.get(i).getMyEndDate() == theEndDate){
							sameEndDate += 1;
						}
					}
					
					//check if count is at least 2, exit . With current job it will be 3 jobs, not allowed
					if(sameEndDate >= MAX_JOBS_PER_DAY_PER_MANAGER){
						
						dateHasPassed = false;
					} 
				}
				
				return dateHasPassed;
		 }	

	 public boolean isMyJobOpen(){
		 return false;
	 }
	 
	 //Volunteer BR: 6C
	 public boolean isSignUpDayPassed(Job theJob) {
		 //CHECK NOT PAST
		 if(!isMyJobPast(theJob)){
		 //job is no longer open for sign up
			 return betweenDates(theJob.getMyStartDate(),theJob.getMyStartDate()) < MIN_JOB_POST_DAY_LENGTH;
		 }
		 
		 return false;
		 
	 }
	 
	 //job is full for sign up BR: 2B
	 public boolean isJobFullForSignUp(Job theJob) {
		 
		 int currentTotal = totalVolunteersPerJob(theJob);
		 return currentTotal == MAX_NUM_VOLUNTEERS_PER_JOB;
	 }
	 
	 
	 //total volunteers per job
	 public int totalVolunteersPerJob(Job theJob) {
		 int currentTotalVolunteers = 0;
		 
		 currentTotalVolunteers = theJob.getMyLightVolunteerNumber() + 
				                 theJob.getMyMediumVolunteerNumber() + 
				                  theJob.getMyHeavyVolunteerNumber();
		 return currentTotalVolunteers;
		 
	 }
	 
	 //check if job is past
	 public boolean isMyJobPast(Job theJob){
		 Date currentDate = Calendar.getInstance().getTime();
		 //-ve means past,true
		 return (betweenDates(currentDate,theJob.getMyEndDate()) < 0);
			 
	
	 }
	 
	 public void updateJobPastStatus(Job theJob){
		 if(isMyJobPast(theJob)) {
			 theJob.setMyJobIsPast(true);
			 //past no longer pending too
			 theJob.setMyJobIsPending(false);
		 }
	 }
	 
	 //get pending jobs
	 public List<Job> getMyPendingJobs(List<Job> theJobs) {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
				if(!jobChecked.isMyJobIsPast()) {
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
	 
	 //new jobs not accepted BR: 2A
	 public boolean isNewJobAccepted(List<Job> theJobs) {
		 List<Job> pendingJobs = getMyPendingJobs(theJobs);
		 
		 return pendingJobs.size() < MAX_NUM_PENDING_JOBS;
	 }
	 
     public List<Job> getJobsByManagerId(List<Job> theJobs, int theManagerId) {
    	 List<Job> myJobs = new ArrayList<Job>(); 
    	
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	
				
			}
		 
		 return myJobs;
     }
	 
	/**Helper methods **/
	//format string 
	public static Date convertStringToDate(String theDate) {
		String expectedPattern = "MM/dd/yyyy";
		try {
		SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
		Date date = dateFormater.parse(theDate);
		return date;
		} catch (ParseException e) {
			return null;
		}
		
	}
	
	public void convertDateToString(Date theDate) {
		String expectedPattern = "MM/dd/yyyy";
		
		SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
		String date = dateFormater.format(theDate);
		
		
		
	}
	
	//calculate difference between dates
	
	 public static long betweenDates(Date firstDate, Date secondDate) {
	    return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
	}
	 
   
}
