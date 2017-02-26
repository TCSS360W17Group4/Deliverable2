package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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

	

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAX_NUM_PENDING_JOBS = 20;
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 10;
	private static final int MAX_ALLOWED_DATE_INTO_FUTURE = 75;
	private static final int MAX_JOB_LENGTH_IN_DAYS = 3;
	private static final int SYSTEM_MAX_JOBS_IN_ANY_GIVEN_DAY = 4;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	private static final int ONE_DAY_OFFSET = 1;
	private int myMaxNumberOfPendingJobs ;

	/* new added fields*/
	private Job myJob;
	private List<Job>myJobs;
	private ParkManager myJobCreator;
	
	

	public JobController(Job theJob, List<Job>theJobs, ParkManager theCreator) {
		this.myJob = theJob;
		this.myJobs = theJobs;
		this.myJobCreator = theCreator;
		
		//from old constructor 
		this.myMaxNumberOfPendingJobs = DEFAULT_MAX_NUM_PENDING_JOBS;
	}
	


	
	public boolean isParkAdded() {
		
		if(myJobCreator.getMyParks().size() == 1) {
			Park thePark = myJobCreator.getMyParks().get(0);
			myJob =  new Job(thePark);
			myJob.setMyJobManagerId(myJobCreator.getMyUserId());
			return true;
		} else {
			return false;
		}
		
	}
	
	public void pickAPark(int theManagerChoice) {
		Park thePark;
		if (theManagerChoice == 1) {
			thePark = myJobCreator.getMyParks().get(0);
		} else  {
			thePark = myJobCreator.getMyParks().get(1);
		} 
		
		
		myJob =  new Job(thePark);
		myJob.setMyJobManagerId(myJobCreator.getMyUserId());
	}
	
	//this is our assumption-job need to be well in the future to get volunteers--min MIN_JOB_POST_DAY_LENGTH
	public boolean hasJobStartDateAllowVolunteerSignUp(LocalDate theCurrentDate, LocalDate theJobStartDate) {
		return numOfDaysBetweenTwoDays(theCurrentDate,theJobStartDate)>= MIN_JOB_POST_DAY_LENGTH;
	}
	/**
	 * assign the starting date of the job
	 * 
	 * @param theDate the starting date input of the job
	 * @param theJob the job the starting assigned
	 * @param theJobs the jobs already exist
	 */
	public boolean isStartDateAdded(String theDate) {
		boolean dateAdded = false;
		
		LocalDate jobStartDate = convertStringToDate(theDate);
	
		if(jobStartDate != null) {
			LocalDate currentDate = LocalDate.now();
			 //MIN_JOB_POST_DAY_LENGTH at least more than MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE
		
			if (hasJobStartDateAllowVolunteerSignUp(currentDate, jobStartDate) && 
					numOfDaysBetweenTwoDays(currentDate,jobStartDate)<= MAX_ALLOWED_DATE_INTO_FUTURE){
				//dont have max jobs on start date
				if(hasMaxJobsNotOnJobsDate(jobStartDate)) {
					myJob.setMyStartDate(jobStartDate);
					dateAdded = true;
				} 
				
			}
		} else {
			System.out.println("Wrong date");
		}
		
		return dateAdded;
		
	}
	
	public boolean isJobDurationNumWithinAllowedRange(int theDuration) {
		
		
		return (theDuration <= MAX_JOB_LENGTH_IN_DAYS && theDuration >= 1);
		
	}
	
	//new docmentation needed
	public boolean isEndDateAdded(int theDuration) {
		
		boolean dateAdded = false;
		if(!isJobDurationNumWithinAllowedRange(theDuration)) {
			
			dateAdded = false;
			return dateAdded;
		} 
		
		//add duration to startDate
		LocalDate endDate = myJob.getMyStartDate().plusDays(theDuration);
		//check for duplicates
		if(hasDurationDayshasNoMaxJobs(endDate,theDuration)) {
			myJob.setMyEndDate(endDate);
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
	public void addTime(String theTime) {
		LocalTime time = convertStringToTime(theTime);
		if(time!=null){
			myJob.setMyTime(time);
		}
	}
	
	/**
	 * assign string description of the job task
	 * 
	 * @param theJob the job description is assigned
	 * @param theDescription the description of the job
	 */
	public boolean isJobDescriptionAdded(String theDescription) {
		if (theDescription.length() >= 0) {
			myJob.setMyDescription(theDescription);
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
	public boolean isMaxLightVolNumberValid(int theNum) {
		boolean numAccepted = false;
		if(theNum <=MAX_NUM_VOLUNTEERS_PER_JOB && theNum >=0) {
			myJob.setMyLightVolunteerNumber(theNum);
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
	public boolean isMaxMediumVolNumValid( int theNum) {
		boolean numAccepted = false;
		int currentTotal = myJob.getMyLightVolunteerNumber() + theNum;
		
		if(currentTotal >=0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			myJob.setMyMediumVolunteerNumber(theNum);

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

	public boolean isMaxHeavyVolNumValid(int theNum) {
		boolean numAccepted = false;
		int currentTotal = myJob.getMyLightVolunteerNumber() + myJob.getMyMediumVolunteerNumber() + theNum;
		if(currentTotal > 0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			myJob.setMyHeavyVolunteerNumber(theNum);
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
	public void addJob() {
		//the size of the existing job becomes the id of the new job
		myJob.setMyJobId(myJobs.size());
		//add Job
		myJobs.add(myJob);
	}
	
		

		//check system job limit per day for start day passed
	//isDuplicateStartDatePassed is refactored
		 public boolean hasMaxJobsNotOnJobsDate(LocalDate theDate) {
			
			 
			  	boolean dateHasPassed = true;
				int countSameStartDayJobs = 0;
				for (int i = 0; i < myJobs.size(); i++) {
					if(myJobs.get(i).getMyStartDate().equals(theDate)){
						
							countSameStartDayJobs += ONE_DAY_OFFSET;
							if(countSameStartDayJobs == SYSTEM_MAX_JOBS_IN_ANY_GIVEN_DAY) {
								
								dateHasPassed = false;
								break;
							}
						
					}
					
				}
				
				return dateHasPassed;
		 }

		 //system job limit passed for the duration of the days--check each day in the duration
	 public boolean hasDurationDayshasNoMaxJobs(LocalDate theStartDate,int theDuration) {
		 
		 boolean dateHasPassed = true;
		 if(theDuration == 1) {
			 //no need to check already passed in checking start date
			 return dateHasPassed;
		 }
			//loop and call hasMaxJobsNotOnJobsDate the duration time and check each date
		 for(int i = 0; i < theDuration; i++) {
			LocalDate checkedDate = theStartDate.plusDays(i);
			//if it doesnt pass ,exit, the end date not accepted
			 if(!hasMaxJobsNotOnJobsDate(checkedDate)){
				 dateHasPassed = false;
				 break;
			 }
		 }
		 	
		
				
				return dateHasPassed;
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
		 return (numOfDaysBetweenTwoDays(currentDate,theJob.getMyEndDate()) < 0);
			
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
	 public static long numOfDaysBetweenTwoDays(LocalDate firstDate, LocalDate secondDate) {
		
	    return  ChronoUnit.DAYS.between(firstDate,secondDate);
	 }
	 
	//this simplify testing but our application users--volunteer and manager cant use it
	 public void setMyMaxNumberOfPendingJobs(int theMaxPendingJobsAllowed) {
			this.myMaxNumberOfPendingJobs = theMaxPendingJobsAllowed;
	 }
	
}
