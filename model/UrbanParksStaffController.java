package model;
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


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class UrbanParksStaffController extends AbstractController{
    
    private final List<Job> myJobs;
    private final List<Volunteer> myVolunteers;
    private final List<ParkManager> myParkManagers;
    private final List<UrbanParksStaff> myUrbanParksStaff;
    private JobController myJobController;
    
    public UrbanParksStaffController(List<Job> theJobs, 
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
    
    
    // User story 5: As an Urban Parks staff member I want to change the maximum number of pending jobs that are allowed in the system.
    public void changeMaxJobs(int newMaxJobs) {
        // uses the setter method in JobController class
        myJobController.setMyMaxNumberOfPendingJobs(newMaxJobs);
    }  
    
    
    // User story 4: As an Urban Parks staff member I want to view a one-month calendar of all upcoming jobs.
    // view one month calendar
    // returns a list of upcoming jobs
    public List<Job> viewCalendar() {
        List<Job> pendingJobs = new ArrayList<Job>();
        for (Job j : myJobs) {
            if (j.getMyJobIsPending()) {
                pendingJobs.add(j);
            }
        }
        return pendingJobs;
    }
    
    
    
    
    /*
    private Job[] viewCalendar(int startDate, int endDate) {
        // sorts job array if not already sorted
        
    } 
    
    
    private Job[] viewCalendar(int startDate, int endDate) {
        // sorts job array if not already sorted
        sortJobsByStartDate(jobs);
        // get indicies in array jobs
        
        int s = 0;
        int e = jobs.length();
        int check = (s + e)/2;
        while(jobs[check].myStartDate != startDate ||
              e - s > 1) {
            if(jobs[check].myStartDate > s) {
                s = check;
            }
            if(jobs[check].myStartDate < e) {
                e = check;
            }
        }
        // check = index
        
        
        //find index 
        int d = jobs.length()/2;
        int len = jobs.length()/4;
        while(d != startdate && len != 0) {
            if(d > startdate) {
                d -= len;
            } else if(d < startdate) {
                d += len;
            }
            len /= 2;
        }
       
        
        int s = binarySearch(jobs, startDate);
        int e = binarySearch(jobs, endDate);
        
        return Arrays.copyOfRange(jobs, s, e);
    } 
    
    private int binarySearch(Job[] arr, int date) {
        
        int s = 0;
        int e = arr.length();
        int check = 0;
        while(arr[check].) {
            
        }
    }
    */
}