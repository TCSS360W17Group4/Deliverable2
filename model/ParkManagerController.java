package model;


import java.util.ArrayList;

import java.io.Serializable;

import java.util.List;






public class ParkManagerController extends AbstractController implements Serializable{

    
    private final List<Job> myJobs;
    
    public ParkManagerController(ParkManager theUser, 
            List<Volunteer> theVolunteers, List<ParkManager> theParkManagers,
            List<UrbanParksStaff> theUrbanParksStaff,
            JobController theJobController,
            List<Job> theJobs) { 
        super(theUser, theVolunteers, theParkManagers, theUrbanParksStaff, theJobController);
        myJobs = theJobs;
        
    }
    
    
    /**
     * Assume job was created correctly and all job fields are valid.
     * 
     * 
     * @author Tony Richardson
     * Date 2017/02/10
     * 
     * User story 2: As a Park Manager I want to submit a new job.
     * 
     * @return 1 if job submitted successfully, 0 if job not submitted
     */
    public int submitNewJob(Job theJob) {
        // User Story 2 BR a) Not more than the maximum number of pending jobs at a time.
        // User Story 2 BR d) There can be no more than two jobs on any given day.
        // User Story 2 BR e) A job cannot be scheduled more than one month in the future.
        if(myJobController.canAddJob(theJob, myJobs)) {
            myJobController.addJob(theJob, myJobs);
            return 1;
        }
        return 0;
    }
    
    
    /**
     * 
     * @author Tony Richardson
     * Date 2017/02/10
     * 
     * User story 3: 
     * As a Park Manager I want to view a numbered list of Volunteers for a job (past or present) in the parks that I manage.
     * 
     * @param theJob It contains a list of volunteers signed up for it. The volunteers will be returned.
     * 
     * @returns A list of volunteers for the given job.
     */
    public List<Volunteer> viewVolunteers(Job theJob) {
        List<Integer> volunteerId = theJob.getMyVolunteerList();
        List<Volunteer> volunteerList = new ArrayList<Volunteer>();
        for(int i = 0; i < myVolunteers.size(); i++) {
            // checks to see if the volunteerId in myVolunteers list is in
            // the list of volunteer ids in the job
            if(volunteerId.contains(myVolunteers.get(i).getMyUserId())) {
                volunteerList.add(myVolunteers.get(i));
            }
        }
        return volunteerList;
    }
	
}
