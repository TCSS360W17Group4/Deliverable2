import java.sql.Time;
import java.util.Date;

public class Job {
/**
 * 
 * 
 */
	
	private Date myCreationDate;
	private Park myPark;
	private Date myStartDate;
	private Date myEndDate;
	private Time myTime;
	private String myDescription;
	private int myLightVolunteerNumber;
	private int myMediumVolunteerNumber;
    private int myHeavyVolunteerNumber;
    private int myJobManagerId;
    private boolean isMyJobPending;
    private boolean isMyJobPast;//if enddate a job < currentdate make sure to set this to true
	
	
	
	public Job(Park thePark) {
		thePark = new Park();
	}



	public Date getMyCreationDate() {
		return myCreationDate;
	}



	public Park getMyPark() {
		return myPark;
	}



	public Date getMyStartDate() {
		return myStartDate;
	}



	public Date getMyEndDate() {
		return myEndDate;
	}



	public Time getMyTime() {
		return myTime;
	}



	public String getMyDescription() {
		return myDescription;
	}



	public int getMyLightVolunteerNumber() {
		return myLightVolunteerNumber;
	}



	public int getMyMediumVolunteerNumber() {
		return myMediumVolunteerNumber;
	}



	public int getMyHeavyVolunteerNumber() {
		return myHeavyVolunteerNumber;
	}



	public int getMyJobManagerId() {
		return myJobManagerId;
	}



	public boolean isMyJobPending() {
		return isMyJobPending;
	}



	public boolean isMyJobIsPast() {
		return isMyJobPast;
	}



	public void setMyCreationDate(Date theCreationDate) {
		this.myCreationDate = theCreationDate;
	}



	public void setMyPark(Park thePark) {
		this.myPark = thePark;
	}



	public void setMyStartDate(Date theStartDate) {
		this.myStartDate = theStartDate;
	}



	public void setMyEndDate(Date theEndDate) {
		this.myEndDate = theEndDate;
	}



	public void setMyTime(Time theTime) {
		this.myTime = theTime;
	}



	public void setMyDescription(String theDescription) {
		this.myDescription = theDescription;
	}



	public void setMyLightVolunteerNumber(int theLightVolunteerNumber) {
		this.myLightVolunteerNumber = theLightVolunteerNumber;
	}



	public void setMyMediumVolunteerNumber(int theMediumVolunteerNumber) {
		this.myMediumVolunteerNumber = theMediumVolunteerNumber;
	}



	public void setMyHeavyVolunteerNumber(int theHeavyVolunteerNumber) {
		this.myHeavyVolunteerNumber = theHeavyVolunteerNumber;
	}



	public void setMyJobManagerId(int theJobManagerId) {
		this.myJobManagerId = theJobManagerId;
	}



	public void setMyJobIsPending(boolean theJobIsPending) {
		this.isMyJobPending = theJobIsPending;
	}



	public void setMyJobIsPast(boolean theJobIsPast) {
		this.isMyJobPast = theJobIsPast;
	}
}
