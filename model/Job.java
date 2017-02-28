package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * 
 * 
 */
public class Job implements Serializable {

	private int myJobId;
	private LocalDate myCreationDate;
	private Park myPark;
    
    //private Calendar myCreationDate;
    //private Calendar myStartDate;
	//private Calendar myEndDate;
    //private Calendar myTime;
    
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
	
	private List<Integer> myVolunteerList;
	
    
    public Job(Park thePark) {
		myPark = thePark;
	}
    

     /*
    public Job(Park thePark, ) {
        // sets creation date for time and date right now
        myCreationDate = new GregorianCalendar();
    }*/
    
    
	public List<Integer> getMyVolunteerList() {
        return myVolunteerList;
    }



    public void setMyVolunteerList(List<Integer> theVolunteerList) {
        this.myVolunteerList = theVolunteerList;
    }


    
    
    /*public Calendar getMyCreationDate() {
		return this.myCreationDate;
	}*/
	//public Date getMyCreationDate() {
    //}







	public LocalDate getMyCreationDate() {

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


    /*public Calendar getMyStartDate() {
		return myStartDate;
	}*/
	public LocalDate getMyStartDate() {
		return myStartDate;
	}


    /*public Calendar getMyEndDate() {
		return myEndDate;
	}*/
	public LocalDate getMyEndDate() {
		return myEndDate;
	}


    /*public Calendar getMyTime() {
		return myTime;
	}*/
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

	public void setMyCreationDate(LocalDate theCreationDate) {
		this.myCreationDate = theCreationDate;
	}



	public void setMyPark(Park thePark) {
		this.myPark = thePark;
	}


    /*public void setMyStartDate(Calendar theStartDate) {
		this.myStartDate = theStartDate;
	}*/
	public void setMyStartDate(LocalDate theStartDate) {
	    this.myStartDate = theStartDate;
	}

	/*public void setMyEndDate(Calendar theEndDate) {
		this.myEndDate = theEndDate;
	}*/
	public void setMyEndDate(LocalDate theEndDate) {
		this.myEndDate = theEndDate;
	}


	/*public void setMyTime(Calendar theTime) {
		this.myTime = theTime;
	}*/
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
    
    
    /*
     * @author Tony Richardson
     * date 2/10/2017
     */
     public boolean isPending() {
        return myStartDate.compareTo(LocalDate.now()) >= 0;
     }
}
