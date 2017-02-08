public class Volunteer extends AbstractUser {

    private int myVolunteerJobs[];
    private boolean myBlackballStatus = false;
    
    public boolean getMyBlackballStatus() {
        return myBlackballStatus;
        
    }
    
    public void setMyBlackballStatus(boolean theBlackballStatus) {
        this.myBlackballStatus = theBlackballStatus;
        
    }
    
    public int[] getMyVolunteerJobs() {
        return this.myVolunteerJobs;
        
    }
    
    public void setMyVolunteerJobs(int[] theJobs){
        myVolunteerJobs = theJobs;  ///we accept a reference to a list of jobs, this is our new list
    }
}