import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author Dereje
 * 
 *
 */
public class JobController {

    
    private static final int MAX_NUM_PENDING_JOBS = 30;
    private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 30;
    private static final int MAX_DAYS_FOR_FUTURE_JOB_DATE = 30;
    private static final int MAX_JOB_LENGTH_IN_DAYS = 2;
    private static final int MAX_JOBS_PER_DAY_PER_MANAGER = 2;
    private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
    private static final int ONE_DAY_OFFSET = 1;
    
    private List<Job> myJobs = new ArrayList<Job>(); 
    

    
    public JobController(List<Job> theJobs) {
        myJobs = theJobs;
    }

     //new jobs not accepted BR: 2A
     public boolean isNewJobAccepted(List<Job> theJobs) {
         List<Job> pendingJobs = getMyPendingJobs(theJobs);
         
         return pendingJobs.size() < MAX_NUM_PENDING_JOBS;
     }
    
    public void initJobForm(int theUserId, Job theJob) {
            theJob.setMyJobManagerId(theUserId);
            
    }
    
    
    public void addJobParkName(String theParkName, Job theJob){
        theJob.getMyPark().setMyName(theParkName);
    }
    
    public void addJobCity(String theCity, Job theJob) {
        theJob.getMyPark().setMyCity(theCity);
    }
    
    public void addStartDate(String theDate, Job theJob) {
        
        //check the job is future, minus -current 3 days//assumption based on BR volunteer
        LocalDate jobStartDate = convertStringToDate(theDate);
        LocalDate currentDate = LocalDate.now();
         //BR: C & E
        if (betweenDates(currentDate,jobStartDate)>= MIN_JOB_POST_DAY_LENGTH && 
                betweenDates(currentDate,jobStartDate)<= MAX_DAYS_FOR_FUTURE_JOB_DATE){
            //BR:D
            if(isDuplicateStartDatePassed(jobStartDate, theJob.getMyJobManagerId())) {
                theJob.setMyStartDate(jobStartDate);
            }
        } else {
            //return with error
        }
        
    }
    
    public void addEndDate(int theDuration, Job theJob) {
        //accept user input duration of the job/ 2 is MAX=> 1 or 2 only option
        
        if(theDuration <= 0 || theDuration > MAX_JOB_LENGTH_IN_DAYS) {
            //the LocalDate cant be added 
            
        } 
        
        //add duration to startDate
        LocalDate endDate = theJob.getMyStartDate().plusDays(theDuration);
        //check for duplicates
        if(isDuplicateEndDatePassed(endDate, theJob.getMyJobManagerId(),theDuration)) {
            theJob.setMyEndDate(endDate);
        } else {
            //cant be added
        }
        

    }
    
    public void addTime(Job theJob, String theTime) {
        LocalTime time = convertStringToTime(theTime);
        if(time!=null){
            theJob.setMyTime(time);
        }
    }
    
    public void addJobDescription(Job theJob, String theDescription) {
        theJob.setMyDescription(theDescription);
        
    }
    public void addNumOfLightVolunteer(Job theJob, int theNum) {
        if(theNum <=30 && theNum >=0) {
            theJob.setMyLightVolunteerNumber(theNum);
        }
    }
    
    public void addNumOfMediumVolunteer(Job theJob, int theNum) {
        int currentTotal = theJob.getMyLightVolunteerNumber() + theNum;
        
        if(currentTotal >=0 && currentTotal <=30 ) {
            theJob.setMyMediumVolunteerNumber(theNum);
        } else {
            //error cant be added
        }
    }
    public void addNumOfHeavyVolunteer(Job theJob, int theNum) {
        int currentTotal = theJob.getMyLightVolunteerNumber() + theJob.getMyMediumVolunteerNumber() + theNum;
        if(currentTotal > 0 && currentTotal <=30 ) {
            theJob.setMyHeavyVolunteerNumber(theNum);
        } else {
            //error 
        }
    }
    

    //set Job ID
    public void addJob(Job theJob, List<Job>theJobs){
        theJob.setMyJobId(theJobs.size());
        //add Job
        theJobs.add(theJob);
    }
    //BR:2D
    //check manager job limit per day
     public boolean isDuplicateStartDatePassed(LocalDate theStartDate, int managerId) {
        //check job with the same manager exists same LocalDate more than twice
         
        // boolean dateHasPassed = true;
            int sameStartDate = 0;
            for (int i = 0; i < myJobs.size(); i++) {
                if(myJobs.get(i).getMyJobManagerId()== managerId){
                    if(myJobs.get(i).getMyStartDate() == theStartDate){
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
         //BR:2D
     public boolean isDuplicateEndDatePassed(LocalDate theEndDate, int managerId, int theDuration) {
         
         boolean dateHasPassed = true;
         if(theDuration == 1) {
             //no need to check already passed in checking start date
             return dateHasPassed;
         }
            //check job with the same manager exists same LocalDate more than twice
                int sameEndDate = 0;
                for (int i = 0; i < myJobs.size(); i++) {
                    if(myJobs.get(i).getMyJobManagerId()== managerId){
                        if(myJobs.get(i).getMyEndDate() == theEndDate){
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
         LocalDate currentDate = LocalDate.now();
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
     
     //get pending jobs(open for sign up + is not full)
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
     
     
     //get upcoming jobs(job not in the past)
     public List<Job> getUpcomingJobs(List<Job> theJobs) {
         List<Job> upcomingJobs = new ArrayList<Job>(); 
         
         for (int i = 0; i < theJobs.size(); i++) {
                Job jobChecked = theJobs.get(i);
                if(!jobChecked.isMyJobIsPast()) {
                        //add it to pending
                        upcomingJobs.add(jobChecked);
                    
                }
                
            }
         
         return upcomingJobs;
     }
    
     
    /**Helper methods **/
     //format time string to Time
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
     
    //format string date to LocalDate
    public static LocalDate convertStringToDate(String theDate) {
    
        Locale locale = Locale.US;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "MM/dd/yy" ).withLocale (locale);
        LocalDate localDate = LocalDate.parse ( theDate , formatter );

        return localDate;
        
    }
    
    
    
    //calculate difference between dates
     public static long betweenDates(LocalDate firstDate, LocalDate secondDate) {
        
        return  ChronoUnit.DAYS.between(firstDate,secondDate);
    }
     
    
}
