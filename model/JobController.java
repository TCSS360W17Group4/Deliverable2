package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * JobController handles job creation,
 * 
 * @author Dereje
 * 
 *
 */
public class JobController implements Serializable{

	
	private static final int DEFAULT_MAX_NUM_PENDING_JOBS = 30;
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 30;
	private static final int MAX_DAYS_FOR_FUTURE_JOB_DATE = 30;
	private static final int MAX_JOB_LENGTH_IN_DAYS = 2;
	private static final int MAX_JOBS_PER_DAY_PER_MANAGER = 2;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	private static final int ONE_DAY_OFFSET = 1;
	private int myMaxNumberOfPendingJobs ;
	private static List<Job> myJobsList;
	
	/**
	 * 
	 */
	public JobController() {
		this.myMaxNumberOfPendingJobs = DEFAULT_MAX_NUM_PENDING_JOBS;
		myJobsList = new ArrayList<Job>();
	}
	   
	public JobController(List<Job> theJobs) {
	    this.myMaxNumberOfPendingJobs = DEFAULT_MAX_NUM_PENDING_JOBS;
	    JobController.myJobsList = theJobs;
	    
	}
	
	/**
	 * getter
	 * @return getter for max number of pending jobs
	 */
	public int getMyMaxNumberOfPendingJobs() {
		return this.myMaxNumberOfPendingJobs;
	}
	
	/**
	 * Setter for pending jobs limit
	 * 
	 * @param theNewMax setter for max number of pending jobs
	 */
	public void setMyMaxNumberOfPendingJobs(int theNewMax) {
		this.myMaxNumberOfPendingJobs = theNewMax;
	}
	
	/**
	 * Checks if new jobs can be added
	 * 
	 * @param theJobs the total jobs to be checked
	 * @return true if job if new job is allowed
	 */
	 //new jobs not accepted BR: 2A
	 public boolean isNewJobAccepted(List<Job> theJobs) {
		 List<Job> pendingJobs = getMyPendingJobs(theJobs);
		 
		 return pendingJobs.size() < myMaxNumberOfPendingJobs;
	 }
	
	 /**
	  * assign the job manager id for the job
	  * 
	  * @param theUserId the job creator id
	  * @param theJob the job to be created
	  */
	public void initJobForm(int theUserId, Job theJob) {
			theJob.setMyJobManagerId(theUserId);
			
			
	}
	
	public boolean isParkAdded(ParkManager theManager, Job theJob, Park thePark) {
		
		if(theManager.getMyParks().size() == 1) {
			thePark = theManager.getMyParks().get(0);
			theJob =  new Job(thePark);
			theJob.setMyJobManagerId(theManager.getMyUserId());
			return true;
		} else {
			return false;
		}
		
	}
	
	public Park pickAPark(ParkManager theManager, int theManagerChoice) {
		Park thePark;
		if (theManagerChoice == 1) {
			thePark = theManager.getMyParks().get(0);
		} else  {
			thePark = theManager.getMyParks().get(1);
		} 
		
		return thePark;
	}
	
	/**
	 * assign the starting date of the job
	 * 
	 * @param theDate the starting date input of the job
	 * @param theJob the job the starting assigned
	 * @param theJobs the jobs already exist
	 */
	public boolean isStartDateAdded(String theDate, Job theJob, List<Job>theJobs) {
		boolean dateAdded = false;
		//check the job is future, minus -current 3 days//assumption based on BR volunteer
		LocalDate jobStartDate = convertStringToDate(theDate);
		if(jobStartDate != null) {
			LocalDate currentDate = LocalDate.now();
			 //BR: C & E
			if (betweenDates(currentDate,jobStartDate)>= MIN_JOB_POST_DAY_LENGTH && 
					betweenDates(currentDate,jobStartDate)<= MAX_DAYS_FOR_FUTURE_JOB_DATE){
				//BR:D
				if(isDuplicateStartDatePassed(jobStartDate, theJob.getMyJobManagerId(),theJobs)) {
					theJob.setMyStartDate(jobStartDate);
					dateAdded = true;
				} 
				
			}
		} 
		
		return dateAdded;
		
	}
	
	/**
	 * assign the end date of the job with the given duration
	 * 
	 * @param theDuration the duration of the job(must be 1 or 2)
	 * @param theJob the job the duration is assigned
	 * @param theJobs theJobs the jobs already exist
	 */
	public boolean isEndDateAdded(int theDuration, Job theJob,List<Job>theJobs) {
		//accept user input duration of the job/ 2 is MAX=> 1 or 2 only option
		boolean dateAdded = false;
		if(theDuration <= 0 || theDuration > MAX_JOB_LENGTH_IN_DAYS) {
			//the LocalDate cant be added 
			dateAdded = false;
			return dateAdded;
		} 
		
		//add duration to startDate
		LocalDate endDate = theJob.getMyStartDate().plusDays(theDuration);
		//check for duplicates
		if(isDuplicateEndDatePassed(endDate, theJob.getMyJobManagerId(),theDuration,theJobs)) {
			theJob.setMyEndDate(endDate);
			dateAdded = true;
		} else {
			//cant be added
			dateAdded = false;
		}
		
		return dateAdded;

	}
	
	/**
	 * assign starting time for the job
	 * 
	 * @param theJob the job time is assigned
	 * @param theTime the starting time of the job
	 */
	public void addTime(Job theJob, String theTime) {
		LocalTime time = convertStringToTime(theTime);
		if(time!=null){
			theJob.setMyTime(time);
		}
	}
	
	/**
	 * assign string description of the job task
	 * 
	 * @param theJob the job description is assigned
	 * @param theDescription the description of the job
	 */
	public boolean isJobDescriptionAdded(Job theJob, String theDescription) {
		if (theDescription.length() >= 0) {
			theJob.setMyDescription(theDescription);
			return true;
			} else {
				return false;
			}
			
		
	}
	/**
	 * assign required number of volunteers for light work
	 * 
	 * @param theJob the job volunteer number assigned
	 * @param theNum the number of light work volunteers needed
	 * @return
	 */
	public boolean isMaxLightVolNumberValid(Job theJob, int theNum) {
		boolean numAccepted = false;
		if(theNum <=MAX_NUM_VOLUNTEERS_PER_JOB && theNum >=0) {
			theJob.setMyLightVolunteerNumber(theNum);
			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;
	}
	
	/**
	 * assign required number of volunteers for medium work
	 * 
	 * @param theJob theJob the job volunteer number assigned
	 * @param theNum the number of medium work volunteers needed
	 * @return
	 */
	public boolean isMaxMediumVolNumValid(Job theJob, int theNum) {
		boolean numAccepted = false;
		int currentTotal = theJob.getMyLightVolunteerNumber() + theNum;
		
		if(currentTotal >=0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			theJob.setMyMediumVolunteerNumber(theNum);

			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;
	}
	
	/**
	 * assign required number of volunteers for heavy work
	 * 
	 * @param theJob theJob theJob the job volunteer number assigned
	 * @param theNum the number of heavy work volunteers needed
	 */

	public boolean isMaxHeavyVolNumValid(Job theJob, int theNum) {
		boolean numAccepted = false;
		int currentTotal = theJob.getMyLightVolunteerNumber() + theJob.getMyMediumVolunteerNumber() + theNum;
		if(currentTotal > 0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			theJob.setMyHeavyVolunteerNumber(theNum);
			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;

	}
	

	/**
	 * add job id field to the job
	 * 
	 * @param theJob new job to be added
	 * @param theJobs the jobs that exist already
	 */
	//set Job ID/
	public void addJob(Job theJob, List<Job>theJobs) {
		//the size of the existing job becomes the id of the new job
		theJob.setMyJobId(theJobs.size());
		//add Job
		theJobs.add(theJob);
	}
	/**
	 * checks new job by a manager violates the maximum job per day
	 * for the start date
	 * 
	 * @param theStartDate the start date of the job to be checked
	 * @param managerId the manager that assigning the new job
	 * 
	 * @param theJobs the existing jobs to be checked
	 * @return false if manager has more than two jobs in the same day,true otherwise
	 */
	//BR:2D
	//check manager job limit per day
	 public boolean isDuplicateStartDatePassed(LocalDate theStartDate, int managerId,List<Job>theJobs) {
		//check job with the same manager exists same LocalDate more than twice
		 
		// boolean dateHasPassed = true;
			int sameStartDate = 0;
			for (int i = 0; i < theJobs.size(); i++) {
				if(theJobs.get(i).getMyJobManagerId()== managerId){
					if(theJobs.get(i).getMyStartDate() == theStartDate){
						sameStartDate += ONE_DAY_OFFSET;
					}
				}
				
				//check if count is at least 2, exit . With current job it will be 3 jobs, not allowed
				if(sameStartDate >= MAX_JOBS_PER_DAY_PER_MANAGER){
					
					//dateHasPassed = false;
					return false;
				} 
			}
			
			return true;
	 }
	 
	 /**
	  * checks new job by a manager violates the maximum job per day, i.e 2,
	  * for the second day, if job has 2 days duration
	  * 
	  * @param theEndDate  the end date of the job to be checked
	  * @param managerId the manager that assigning the new job
	  * @param theDuration the duration of the job inclusive starting date, exclusive of end date
	  * @param theJobs the existing jobs to be checked
	  * @return
	  */
		 //BR:2D
	 public boolean isDuplicateEndDatePassed(LocalDate theEndDate, int managerId, int theDuration,List<Job>theJobs) {
		 
		 boolean dateHasPassed = true;
		 if(theDuration == 1) {
			 //no need to check already passed in checking start date
			 return dateHasPassed;
		 }
			//check job with the same manager exists same LocalDate more than twice
				int sameEndDate = 0;
				for (int i = 0; i < theJobs.size(); i++) {
					if(theJobs.get(i).getMyJobManagerId()== managerId){
						if(theJobs.get(i).getMyEndDate() == theEndDate){
							sameEndDate += ONE_DAY_OFFSET;
						}
					}
					
					//check if count is at least 2, exit . With current job it will be 3 jobs, not allowed
					if(sameEndDate >= MAX_JOBS_PER_DAY_PER_MANAGER){
						
						dateHasPassed = false;
						break;//no need to look for more
					} 
				}
				
				return dateHasPassed;
		 }	


	 /**
	  * Checks a job has passed signing up date, 
	  * i.e must be more than three days from current
	  * 
	  * @param theJob the job to be checked
	  * @return true if job still available for sign up, false otherwise
	  */
	 //Volunteer BR: 6C
	 public boolean isSignUpDayPassed(Job theJob) {
		 LocalDate currentDate = LocalDate.now();
		 //CHECK NOT PAST
		 if(!isMyJobPast(theJob)){
		 //job is no longer open for sign up
			 return betweenDates(currentDate,theJob.getMyStartDate()) < MIN_JOB_POST_DAY_LENGTH;
		 }
		 
		 //no need to check exit
		 return false;
		 
	 }
	 
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
		 return (betweenDates(currentDate,theJob.getMyEndDate()) < 0);
			 
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
	 
	 
	 
	 
	 /****************************
	  * Different query for the jobs arraylist
	  ****************************/
	 /**
	  * search the jobs pending from a list of jobs
	  * 
	  * @param theJobs the jobs to be searched
	  * @return List of jobs that are pending
	  */
	 //get pending jobs(open for sign up + is not full)
	 public List<Job> getMyPendingJobs(List<Job> theJobs) {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
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
	 

	 
	/********************
	 *  Helper methods
	 *******************/
	 //format time string to Time
	 
	 /**
	  * convert the string time to LocalTime object
	  * 
	  * @param timeString the time given as string
	  * @return the formatted string date as LocalTime object
	  */
	 public static LocalTime convertStringToTime(String timeString)
	 {
		 //possible user time input formats
		String[] formats = {"Hmm", "HH:mm","HHmm","H:mm"};
		
	   LocalTime time = null;
	   for (int i = 0; i < formats.length; i++)
	   {
	     String format = formats[i];
	     Locale locale = Locale.US;
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( format ).withLocale (locale);

	     try
	     {
	       time = LocalTime.parse(timeString, formatter);
	   
	       break;
	     }
	     catch(DateTimeParseException e)
	     {
	       
	     }
	   }

	   return time;
	 }
	 
	 /**
	  * convert string date to LocalDate object 
	  * 
	  * @param theDate the date given as string
	  * @return the formated string date as LocalDate object
	  */
	//format string date to LocalDate
	public static LocalDate convertStringToDate(String theDate) {
	

		try {
			Locale locale = Locale.US;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "MM/dd/yy" ).withLocale (locale);
			LocalDate localDate = LocalDate.parse ( theDate , formatter );

			return localDate;
			} catch(Exception e) {
				//return past date so test fails, instead of exception
				return null;
			}

		
	}
	
	
	/**
	 * calcualte the difference between two days 
	 * Example,given 02/06/2017(first date) and 02/08/2017
	 *  will return 2(exclude end date), but not 3
	 * 
	 * @param firstDate the first date
	 * @param secondDate the second date, latest
	 * 
	 * @return the difference between the two days, exclusive of the second date
	 */
	//calculate difference between dates
	 public static long betweenDates(LocalDate firstDate, LocalDate secondDate) {
		
	    return  ChronoUnit.DAYS.between(firstDate,secondDate);
	 }
	 
	 public List<Job> getMyJobsList(){
	     return myJobsList;
	 }
	 
	 public Job getJobById(Integer id){
	     return myJobsList.get(id);
	     
	 }
	
}
