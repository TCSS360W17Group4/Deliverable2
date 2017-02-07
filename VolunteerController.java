import java.util.ArrayList;
import java.util.List;

public class VolunteerController extends JobController {
   
    private Volunteer myVolunteer;
    
    public int[] getMyJobs() {
        return myVolunteer.getMyVolunteerJobs();
        
    }
    
    public void signUpForJob(Job theJob) {
        if( ! isJobFullForSignUp(theJob) ) {
            //this is where the design fails
        }
    }
    
}