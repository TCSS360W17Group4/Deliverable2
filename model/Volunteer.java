package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Volunteer extends AbstractUser {
	
	 private boolean isUserBlackBalled = false;
	 private List<Integer> myVolunteerJobs = new ArrayList<>();
	
	 public boolean getMyBlackballStatus() {
		return isUserBlackBalled;
	}
	public void setMyBlackballStatus(boolean theBlackballStatus) {
		this.isUserBlackBalled = theBlackballStatus;
	}
	public List<Integer> getMyVolunteerJobs() {
		return myVolunteerJobs;
	}
	public void setMyVolunteerJobs(List<Integer> theVolunteerJobs) {
		this.myVolunteerJobs = theVolunteerJobs;
	}


}
