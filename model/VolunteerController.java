package model;
import java.util.List;

public class VolunteerController extends AbstractController {
        
    /*
    public List<Integer> getMyJobs() {
        return myVolunteer.getMyVolunteerJobs();
        
    }*/
    public VolunteerController(Volunteer theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            List<UrbanParksStaff> theUrbanParksStaff,
            JobController theJobController) { 
        super(theUser, theVolunteers, theParkManagers, theUrbanParksStaff, theJobController);
    }

    public void signUpForJob(Job theJob) {
        if( ! JobController.isJobFullForSignUp(theJob) ) {
            //this is where the design fails
            
        }
        
    }
    
}