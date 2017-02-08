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
    protected AbstractUser myUser;
    
    public AbstractController() {
        
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
                // check here whether the job is pending 
                list.add(job);       
            }
            
        }
        return list;
        
    }
    
    public List<Volunteer> getVolunteerListFromJob(int theJobID) { //to be implemented later
        return new ArrayList<Volunteer>();
    }

}