
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


/**
 * 
 * 
 */
public class Job {

	private int myJobId;
	private Date myCreationDate;
	private Park myPark;
	private LocalDate myStartDate;
	private LocalDate myEndDate;
	private LocalTime myTime;
	private String myDescription;
	private int myLightVolunteerNumber;
	private int myMediumVolunteerNumber;
    private int myHeavyVolunteerNumber;
    private int myJobManagerId;
    private boolean myJobIsPending;
    private boolean myJobIsPast;//if enddate a job < currentdate make sure to set this to true
    private int myCurrentTotalVolunteers;
	
	
	
	public Job(Park thePark) {
		thePark = new Park();
	}



	public Date getMyCreationDate() {
		return this.myCreationDate;
	}

	public int getMyJobId(){
		return this.myJobId;
	}

	public void setMyJobPending(boolean theJobPending) {
		this.myJobIsPending = theJobPending;
	}



	public void setMyJobPast(boolean theJobPast) {
		this.myJobIsPast = theJobPast;
	}



	public Park getMyPark() {
		return myPark;
	}



	public LocalDate getMyStartDate() {
		return myStartDate;
	}



	public LocalDate getMyEndDate() {
		return myEndDate;
	}



	public LocalTime getMyTime() {
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



	public boolean getMyJobIsPending() {
		return myJobIsPending;
	}



	public boolean getMyJobIsPast() {
		return myJobIsPast;
	}

	public void setMyJobId(int theJobId) {
		this.myJobId = theJobId;
	}

	public void setMyCreationDate(Date theCreationDate) {
		this.myCreationDate = theCreationDate;
	}



	public void setMyPark(Park thePark) {
		this.myPark = thePark;
	}



	public void setMyStartDate(LocalDate theStartDate) {
		this.myStartDate = theStartDate;
	}



	public void setMyEndDate(LocalDate theEndDate) {
		this.myEndDate = theEndDate;
	}



	public void setMyTime(LocalTime theTime) {
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
		this.myJobIsPending = theJobIsPending;
	}



	public void setMyJobIsPast(boolean theJobIsPast) {
		this.myJobIsPast = theJobIsPast;
	}



	public int getMyCurrentTotalVolunteers() {
		return myCurrentTotalVolunteers;
	}



	public void setMyCurrentTotalVolunteers(int theCurrentTotalVolunteers) {
		this.myCurrentTotalVolunteers = theCurrentTotalVolunteers;
	}
}
