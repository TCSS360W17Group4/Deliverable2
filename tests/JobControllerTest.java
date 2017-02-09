package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Job;
import model.JobController;
import model.Park;

public class JobControllerTest {
	
	private List<Job> myJobs; 
    private JobController jobController;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		 jobController = new JobController();
		 myJobs = new ArrayList<Job>(); 
		
	}

	/**
	 * Test for business rule 2A
	 * {@link model.JobController#isNewJobAccepted(java.util.List)}
	 */
	@Test
	public void testIsNewJobAccepted() {
	
		jobController.setMyMaxNumberOfPendingJobs(2);
		
		Job job1 = new Job(new Park());
		Job job2 = new Job(new Park());
		jobController.addStartDate("02/14/17", job1, myJobs);
		jobController.addStartDate("02/15/17", job2, myJobs);
		job1.setMyHeavyVolunteerNumber(2);
		job2.setMyHeavyVolunteerNumber(2);
		jobController.addEndDate(1, job1, myJobs);
		jobController.addEndDate(1, job2, myJobs);

		
		myJobs.add(0,job1);
		assertTrue("fail new job can be added",jobController.isNewJobAccepted(myJobs));
		myJobs.add(0,job2);
		
		//jobController.isNewJobAccepted(myJobs);
		assertFalse("fail new job cannot be added",jobController.isNewJobAccepted(myJobs));
	}

	/*
	 *Business Rule 2B 
	 ************************/
	
	/**
	 * {@link model.JobController#addNumOfLightVolunteer(Job, int)(Job)}
	 */
	@Test
	public void testAddNumOfLightVolunteer() {
		Job job1 = new Job(new Park());
		jobController.addNumOfLightVolunteer(job1, 30);
		assertEquals(new Integer(job1.getMyLightVolunteerNumber()),new Integer(30));
		jobController.addNumOfLightVolunteer(job1, 31);
		//change the assert to assertTrue or false once method return boolean
		assertEquals(new Integer(job1.getMyLightVolunteerNumber()),new Integer(0));
	}
	
	
	/**
	 * Case 1 light volunteer 30, and adding medium 1
	 * 
	 * {@link model.JobController#addNumOfMediumVolunteer(Job, int)(Job)}
	 */
	@Test
	public void testAddNumOfMediumVolunteerCaseOne() {
		Job job1 = new Job(new Park());
		jobController.addNumOfLightVolunteer(job1, 30);
		jobController.addNumOfMediumVolunteer(job1, 1);
		assertEquals(new Integer(job1.getMyMediumVolunteerNumber()),new Integer(0));
		
	}
	
	/**
	 * Case 2 light volunteer <30, and adding medium 1
	 * 
	 * {@link model.JobController#addNumOfMediumVolunteer(Job, int)(Job)}
	 */
	@Test
	public void testAddNumOfMediumVolunteerCaseTwo() {
		Job job1 = new Job(new Park());
		jobController.addNumOfLightVolunteer(job1, 29);
		jobController.addNumOfMediumVolunteer(job1, 1);
		assertEquals(new Integer(job1.getMyMediumVolunteerNumber()),new Integer(1));
	
	}
	/**
	 * Case 1 light volunteer + medium = 30
	 * {@link model.JobController#addNumOfHeavyVolunteer(Job, int)(Job)}
	 */
	@Test
	public void testAddNumOfHeavyVolunteerCaseOne() {
		Job job1 = new Job(new Park());
		jobController.addNumOfLightVolunteer(job1, 29);
		jobController.addNumOfMediumVolunteer(job1, 1);
		jobController.addNumOfHeavyVolunteer(job1, 1);
		assertEquals(new Integer(job1.getMyHeavyVolunteerNumber()),new Integer(0));
	}
	
	/**
	 * case 2 light Volunteer + medium <30
	 * {@link model.JobController#addNumOfHeavyVolunteer(Job, int)(Job)}
	 */
	@Test
	public void testAddNumOfHeavyVolunteer() {
		Job job1 = new Job(new Park());
		jobController.addNumOfLightVolunteer(job1, 20);
		jobController.addNumOfMediumVolunteer(job1, 0);
		jobController.addNumOfHeavyVolunteer(job1, 1);
		assertEquals(new Integer(job1.getMyHeavyVolunteerNumber()),new Integer(1));
		
		jobController.addNumOfLightVolunteer(job1, 20);
		jobController.addNumOfMediumVolunteer(job1, 0);
		jobController.addNumOfHeavyVolunteer(job1, 11);
		//adding over 30
		assertEquals(new Integer(job1.getMyHeavyVolunteerNumber()),new Integer(0));
	}
	
	

}
