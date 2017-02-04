import java.util.Date;

public class Job {
    
    //private Park myPark;
    private Date myCreationDate;
    private Date myStartDate;
    private Date myEndDate;
    //-myTime
    //-myDescription
    //-myLightVolunteerNumber
    //-myMediumVolunteerNumber
    //-myHeavyVolunteerNumber
    //-myJobManagerId
    private boolean myJobIsFull;
    private boolean myJobIsPast;
       
    
    private int jobID;
    
    public int getJobID() {
        return jobID;
        
    }
    
    public void setJobID(int theInt) {
        jobID = theInt;
        
    }
    
}