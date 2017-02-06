
public class Volunteer extends AbstractUser {
	
	 private int myBlackballStatus;
	 private int myVolunteerJobs[];
	
	 public int getMyBlackballStatus() {
		return myBlackballStatus;
	}
	public void setMyBlackballStatus(int theBlackballStatus) {
		this.myBlackballStatus = theBlackballStatus;
	}
	public int[] getMyVolunteerJobs() {
		return myVolunteerJobs;
	}
	public void setMyVolunteerJobs(int[] theVolunteerJobs) {
		this.myVolunteerJobs = theVolunteerJobs;
	}


}
