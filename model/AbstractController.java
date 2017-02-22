package model;

 
 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* 
 * @authors Christopher Hall, Dereje Bireda, Tony Richardson, Brian Hess
 * @Winter Quarter 2017
 * 
 */


public abstract class AbstractController implements Serializable {
    protected final AbstractUser myUser;
    public AbstractUser getMyUser() {
        return myUser;
    }

    public List<Volunteer> getMyVolunteers() {
        return myVolunteers;
    }

    public List<ParkManager> getMyParkManagers() {
        return myParkManagers;
    }


    public JobController getMyJobController() {
        return myJobController;
    }

    protected final List<Volunteer> myVolunteers;
    protected final List<ParkManager> myParkManagers;
    protected final JobController myJobController;
    
    //should not get called explicitly, this exists to prevent compile errors
    /*
    public AbstractController() {  
        //myUser = new AbstractUser();
        myVolunteers = new ArrayList<Volunteer>();
        myParkManagers = new ArrayList<ParkManager>();
        myUrbanParksStaff = new ArrayList<UrbanParksStaff>();
        myJobController = new JobController();
    }
    */
    
    public AbstractController(AbstractUser theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,           
            JobController theJobController) {
        myUser = theUser;
        myVolunteers = theVolunteers;
        myParkManagers = theParkManagers;
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