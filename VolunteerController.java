import java.util.ArrayList;
import java.util.List;

public class VolunteerController extends AbstractController {
    
    private Volunteer myVolunteer;
    
    public VolunteerController(Volunteer theVolunteer) {
        myVolunteer = theVolunteer;
        
    }
    
    public int[] getMyJobs() {
        return myVolunteer.getMyVolunteerJobs();
        
    }
    
    public void signUpForJob(Job theJob) {
        if( ! JobController.isJobFullForSignUp(theJob) ) {
            //this is where the design fails
        }
    }
    
}