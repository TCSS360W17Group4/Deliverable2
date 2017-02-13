package model;
import java.io.Serializable;
import java.util.List;



public class ParkManagerController extends AbstractController implements Serializable{
    
    public ParkManagerController(ParkManager theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            List<UrbanParksStaff> theUrbanParksStaff,
            JobController theJobController) { 
        super(theUser, theVolunteers, theParkManagers, theUrbanParksStaff, theJobController);
    }
	
}
