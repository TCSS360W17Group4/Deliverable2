/* 
   extending system would make accessing data in system easy
   but extending to share data makes the object too bound to system
*/

/**
 * 
 * 
 * @author Tony Richardson
 * 
 */

package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class UrbanParksStaffController extends AbstractController{
    
    
    private final List<Job> myJobs;
	//private final List<Volunteer> myVolunteers;
	//private final List<ParkManager> myParkManagers;
    //private final List<UrbanParksStaff> myUrbanParksStaff;
    //private JobController myJobController;
    
    
    /**
     * Constructor for UrbanParksStaffController
     * 
     * 
     * @param theJobs The list of jobs for the park system.
     * @param theVolunteers The list of Volunteers for the park system.
     * @param theParkManagers The list of Park Managers for the park system.
     * @param theUrbanParksStaff The list of Urban Parks Staff for the park system.
     * @param theJobController The job controller from the parks system.
     * 
     */
     /*
    public UrbanParksStaffController(
    List<Job> theJobs, 
    List<Volunteer> theVolunteers, 
    List<ParkManager> theParkManagers, 
    List<UrbanParksStaff> theUrbanParksStaff, 
    JobController theJobController) {
        myJobs = theJobs;
        myVolunteers = theVolunteers;
        myParkManagers = theParkManagers;
        myUrbanParksStaff = theUrbanParksStaff;
        myJobController = theJobController;
    }
    */
    public UrbanParksStaffController(AbstractUser theUser, 
        List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
        List<UrbanParksStaff> theUrbanParksStaff,
        JobController theJobController,
        List<Job> theJobs) {
        
        super(theUser, theVolunteers, theParkManagers, theUrbanParksStaff, theJobController);
        myJobs = theJobs;
    }
    
    
    // User story 5: As an Urban Parks staff member I want to change the maximum number of pending jobs that are allowed in the system.
    /**
     * Changes the maximum number of pending jobs for the park system.
     * 
     * @param newMaxJobs The new maximum number of jobs for the park system.
     */
    public void changeMaxJobs(int newMaxJobs) {
        // uses the setter method in JobController class
        myJobController.setMyMaxNumberOfPendingJobs(newMaxJobs);
    }
    
    
    
    // User story 4: As an Urban Parks staff member I want to view a one-month calendar of all upcoming jobs.
    // view one month calendar
    // returns a list of upcoming jobs
    /**
     * Returns a list of jobs for the next month.
     * 
     * 
     * @return A list of pending jobs with start dates from now to one month from now.
     */
    public List<Job> getPendingJobsForOneMonth() {
        List<Job> pendingJobs = new ArrayList<Job>();
        for (Job j : myJobs) {
            if (j.isPending() && 
                // compares start date to current date right now
                //j.getMyStartDate().compareTo(j.getMyStartDate().now()) > 0 &&
                // compares start date to date of one month from right now
                j.getMyStartDate().compareTo(LocalDate.now().plusMonths(1)) <= 0) {
                pendingJobs.add(j);
            }
        }
        return pendingJobs;
    }
    
    // local date will not do what we need it to do
    
    /*
    public List<Job> getPendingJobsInRange(LocalDate start, LocalDate end) {
        List<Job> jobsRange = new ArrayList<Job>();
        for (Job j : myJobs) {
            if (j.getMyJobIsPending() && 
                // compares start date to current date right now
                j.getMyStartDate().compareTo(j.getMyStartDate().now()) > 0 &&
                // compares start date to date of one month from right now
                j.getMyStartDate().compareTo(j.getMyStartDate().now().plusMonths(1)) < 0) {
                pendingJobs.add(j);
            }
        } 
    }
    */
    

}
