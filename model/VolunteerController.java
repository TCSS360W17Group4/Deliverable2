package model;
import java.util.List;

public class VolunteerController extends AbstractController {
    Volunteer myUser;    
    
    public List<Integer> getMyJobs() {
            return myUser.getMyVolunteerJobs();
        
    }
    
    public VolunteerController(Volunteer theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            JobController theJobController) { 
        super(theUser, theVolunteers, theParkManagers, theJobController);
    }

//    public void signUpForJob(Job theJob) {
//        if( ! JobController.isJobFullForSignUp(theJob) ) {
//            //this is where the design fails
//            
//        }
//        
//    }
    
}