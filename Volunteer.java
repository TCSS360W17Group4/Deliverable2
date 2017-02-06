public class Volunteer extends AbstractUser {
    private int myVolunteerJobs[];
    private boolean myBlackballStatus = false;
    
    public int[] getMyVolunteerJobs() {
        return myVolunteerJobs;
        
    }
    
    public void setMyVolunteerJobs(int theJobs[]){
        myVolunteerJobs = theJobs;  ///we accept a reference to a list of jobs, this is our new list
        return;
    }
}