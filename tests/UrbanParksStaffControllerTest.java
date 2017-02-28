package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.AbstractUser;
import model.Job;
import model.JobController;
import model.Park;
import model.UrbanParksStaffController;
public class UrbanParksStaffControllerTest {

	 private JobController jobController;
	 private List<Job> myJobs; 
	 private UrbanParksStaffController urbanParks;
	 private AbstractUser myUser;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		 jobController = new JobController();
		 jobController.setMyMaxNumberOfPendingJobs(2);
		 
		 myJobs = new ArrayList<Job>(); 	
			Job job1 = new Job(new Park());
			Job job2 = new Job(new Park());
			//make the job full
			job1.setMyMediumVolunteerNumber(2);
			job1.setMyCurrentTotalVolunteers(2);
			
			jobController.isStartDateAdded("02/14/17", job1, myJobs);
			jobController.isStartDateAdded("02/15/17", job2, myJobs);
		
			urbanParks = new UrbanParksStaffController(myUser, null, null, null, jobController, myJobs);
	}


}
