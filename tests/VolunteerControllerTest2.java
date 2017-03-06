/**
 * 
 * 
 * @author Tony Richardson
 * 2017-02-27
 * 
 * The following methods will test the isJobAvailableToSighUp method
 * from the VolunteerController class
 * in the model package.
 * 
 * I am unsure what the isJobAvailableToSignUp method actually does.
 *  
 * 
 */

package tests;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import model.Volunteer;
import model.Job;
import model.VolunteerController;
import model.Park;


public class VolunteerControllerTest2 {
    
    private Volunteer volunteerUser;
    private List<Job> emptyJobs;
    private List<Job> oneJob;
    private List<Job> oneLessThanFullJobs;
    private List<Job> fullJobs;
    private List<Job> overFullJobs;
    private List<Volunteer> emptyVolunteers;
    private List<Volunteer> oneVolunteer;
    private List<Volunteer> oneLessThanFullVolunteers;
    private List<Volunteer> fullVolunteers;
    private List<Volunteer> overFullVolunteers;
    private Integer jobId1;
    private Job job1;
    private Job job2;
    private Job job3;
    private Volunteer volunteer1;
    private Volunteer volunteer2;
    private Volunteer volunteer3;
    private List<Integer> volunteerJobListOneJob;
    private Park park1;
    
    private VolunteerController emptyJobsAndVolunteers;
    private VolunteerController oneJobAndOneVolunteer;
    private VolunteerController fullJobsAndVolunteers;
    


    @Before public void setUp() {
        //=== start setup volunteerUser with one job
        jobId1 = 1;
        
        // setup job1 with jobId1
        park1 = new Park();
        job1 = new Job(park1);
        job1.setMyStartDate(LocalDate.now().plusDays(1));
        job1.setMyEndDate(LocalDate.now().plusDays(2));
        job1.setMyJobIsPending(true);
        job1.setMyJobIsPast(false);
        job1.setMyJobId(jobId1);
        
        // setup the volunteerUser so it is signed up for job1
        volunteerJobListOneJob = new ArrayList<Integer>();
        volunteerJobListOneJob.add(jobId1);
        volunteerUser = new Volunteer();
        volunteerUser.setMyVolunteerJobs(volunteerJobListOneJob);
        //=== end setup volunteerUser with one job
        
        

        
        volunteer1 = new Volunteer();
        
        
        emptyJobs = new ArrayList<Job>();
        oneJob = new ArrayList<Job>();
        oneLessThanFullJobs = new ArrayList<Job>();
        fullJobs = new ArrayList<Job>();
        overFullJobs = new ArrayList<Job>();
        
        oneJob.add(job1);
        
        emptyVolunteers = new ArrayList<Volunteer>();
        oneVolunteer = new ArrayList<Volunteer>();
        oneLessThanFullVolunteers = new ArrayList<Volunteer>();
        fullVolunteers = new ArrayList<Volunteer>();
        overFullVolunteers = new ArrayList<Volunteer>();
        
        oneVolunteer.add(volunteer1);
        
        emptyJobsAndVolunteers = new VolunteerController(volunteerUser, emptyJobs, emptyVolunteers);
        oneJobAndOneVolunteer = new VolunteerController(volunteerUser, oneJob, oneVolunteer);
        //fullJobsAndVolunteers = new VolunteerController(volunteerUser, fullJobs, fullVolunteers);
        
    }
    
    
    @Test 
    public void isJobAvailableToSignUp_emptyJobs_returnFalse() {
        assertFalse(emptyJobsAndVolunteers.isJobAvailableToSignUp());
    }
    
    
    @Test 
    public void isJobAvailableToSignUp_oneJob_returnTrue() {
        assertTrue(oneJobAndOneVolunteer.isJobAvailableToSignUp());
    }
    
    
    
}
