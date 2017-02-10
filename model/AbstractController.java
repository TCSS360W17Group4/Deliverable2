package model;
/*
 * Stub for AbstractController
 * everyone feel free to use, fix, enhance, whatever 
 * 
 */ 
 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* 
 * @authors Christopher Hall, Dereje Bireda, Tony Richardson, Brian Hess
 * @Winter Quarter 2017
 * 
 */


public class AbstractController {
    protected final AbstractUser myUser;
    protected final List<Volunteer> myVolunteers;
    protected final List<ParkManager> myParkManagers;
    protected final List<UrbanParksStaff> myUrbanParksStaff;
    protected final JobController myJobController;
    
    //should not get called explicitly, this exists to prevent compile errors
    public AbstractController() {  
        myUser = new AbstractUser();
        myVolunteers = new ArrayList<Volunteer>();
        myParkManagers = new ArrayList<ParkManager>();
        myUrbanParksStaff = new ArrayList<UrbanParksStaff>();
        myJobController = new JobController();
    }
    
    public AbstractController(AbstractUser theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            List<UrbanParksStaff> theUrbanParksStaff,
            JobController theJobController) {
        myUser = theUser;
        myVolunteers = theVolunteers;
        myParkManagers = theParkManagers;
        myUrbanParksStaff = theUrbanParksStaff;
        myJobController = theJobController;
    }
    
    public String viewMyJobs(String userName) {
        return "foo";
        
    }
    
    
    public List<Job> getJobsById(int theJobID) {
        List<Job> list = new ArrayList<Job>();
        Iterator<Job> iterator = list.iterator();
        Job job;
        while (iterator.hasNext()) {
            job = iterator.next();
            if (job.getMyJobId() == theJobID) {
                // check whether the job is pending 
                list.add(job);       
            }
            
        }
        return list;
        
    }
    
    public List<Volunteer> getVolunteerListFromJob(int theJobID) { //to be implemented later
        return new ArrayList<Volunteer>();
    }

}