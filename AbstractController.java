/*
 * Stub for AbstractController
 * everyone feel free to use, fix, enhance, whatever 
 * 
 */ 
 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* 
 * @author Christopher Hall
 * @Winter Quarter 2017
 * 
 */


public class AbstractController {
    public String viewMyJobs(String userName) {
        return "foo";
    }
    
    public List<Job> FindMyJobs(int theJobID) {
        List<Job> list = new ArrayList<Job>();
        Iterator<Job> iterator = list.iterator();
        Job job;
        while (iterator.hasNext()) {
            job = iterator.next();
            if (job.getJobID() == theJobID) {
                list.add(job);       
            }
        }
        return list;
    }

}