<<<<<<< HEAD


public class ParkManagerController {

	
=======
import java.util.List;



public class ParkManagerController extends AbstractController{
    
    public ParkManagerController(ParkManager theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            List<UrbanParksStaff> theUrbanParksStaff,
            JobController theJobController) { 
        super(theUser, theVolunteers, theParkManagers, theUrbanParksStaff, theJobController);
    }
>>>>>>> dereje2
	
}
