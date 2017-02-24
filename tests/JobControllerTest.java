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
	
//	private List<Job> myJobs; 
//    private JobController jobController;
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		
//		 jobController = new JobController();
//		 myJobs = new ArrayList<Job>(); 
//		
//	}
//
//	/**
//	 * Test for business rule 2A
//	 * {@link model.JobController#isNewJobAccepted(java.util.List)}
//	 */
//	@Test
//	public void testIsNewJobAccepted() {
//	
//		jobController.setMyMaxNumberOfPendingJobs(2);
//		
//		Job job1 = new Job(new Park());
//		Job job2 = new Job(new Park());
//		jobController.isStartDateAdded("02/14/17", job1, myJobs);
//		jobController.isStartDateAdded("02/15/17", job2, myJobs);
//		job1.setMyHeavyVolunteerNumber(2);
//		job2.setMyHeavyVolunteerNumber(2);
//		jobController.isEndDateAdded(1, job1, myJobs);
//		jobController.isEndDateAdded(1, job2, myJobs);
//
//		
//		myJobs.add(0,job1);
//		assertTrue("fail new job can be added",jobController.isNewJobAccepted(myJobs));
//		myJobs.add(0,job2);
//		
//		//jobController.isNewJobAccepted(myJobs);
//		assertFalse("fail new job cannot be added",jobController.isNewJobAccepted(myJobs));
//	}
//
//	/*
//	 *Business Rule 2B 
//	 ************************/
//	
//	/**
//	 * {@link model.JobController#isMaxLightVolNumberValid(Job, int)}
//	 */
//	@Test
//	public void testAddNumOfLightVolunteerOverLimitAndUnderLimit() {
//		Job job1 = new Job(new Park());
//		jobController.isMaxLightVolNumberValid(job1, 30);
//		assertTrue("light volunteer max below 30 failed",jobController.isMaxLightVolNumberValid(job1, 30));
//		jobController.isMaxLightVolNumberValid(job1, 31);
//		assertFalse("light volunteer max below 30 failed",jobController.isMaxLightVolNumberValid(job1, 31));
//	}
//	
//	
//	/**
//	 * Case 1 light volunteer 30, and adding medium 1
//	 * 
//	 * {@link model.JobController#isMaxMediumVolNumValid(Job, int)}
//	 */
//	@Test
//	public void testIsMaxMediumVolNumValidWithMaxLightVol() {
//		Job job1 = new Job(new Park());
//		assertTrue("light volunteer has valid number failed",jobController.isMaxLightVolNumberValid(job1, 30));
//		assertFalse("sum of light + medium is <=30 failed",jobController.isMaxMediumVolNumValid(job1, 1));
//		
//	}
//	
//	/**
//	 * Case 2 light volunteer <30, and adding medium 1
//	 * 
//	 * {@link model.JobController#isMaxMediumVolNumValid(Job, int)}
//	 */
//	@Test
//	public void testIsMaxMediumVolNumValidrWithBelowMaxLightVol() {
//		Job job1 = new Job(new Park());
//		assertTrue("light volunteer has valid number failed",jobController.isMaxLightVolNumberValid(job1, 29));
//		assertTrue("sum of light + medium is <=30 failed",jobController.isMaxMediumVolNumValid(job1, 1));
//
//	
//	}
//	/**
//	 * Case 1 light volunteer + medium = 30
//	 * {@link model.JobController#isMaxHeavyVolNumValid(Job, int)}
//	 */
//	@Test
//	public void testAddNumOfHeavyVolunteerWithMedAndLightMax() {
//		Job job1 = new Job(new Park());
//
//		assertTrue("light volunteer has valid number fail",jobController.isMaxLightVolNumberValid(job1, 29));
//		assertTrue("sum of light + medium is <=30 fail",jobController.isMaxMediumVolNumValid(job1, 1));
//		assertFalse("sum of light + medium + heavy is <=30 fail",jobController.isMaxHeavyVolNumValid(job1, 1));
//	
//	}
//	
//	/**
//	 * case 2 light Volunteer + medium <30
//	 * {@link model.JobController#isMaxHeavyVolNumValid(Job, int)}
//	 */
//	@Test
//	public void testIsMaxHeavyVolNumValidBelow30WithMedAndLightBelowMax() {
//		Job job1 = new Job(new Park());
//
//		assertTrue("light volunteer has valid number fail",jobController.isMaxLightVolNumberValid(job1, 20));
//		assertTrue("sum of light + medium is <=30 fail",jobController.isMaxMediumVolNumValid(job1, 0));
//		assertTrue("sum of light + medium + heavy is <=30 fail",jobController.isMaxHeavyVolNumValid(job1, 1));
//		
//		
//		assertTrue("light volunteer has valid number fail",jobController.isMaxLightVolNumberValid(job1, 20));
//		assertTrue("sum of light + medium is <=30 fail",jobController.isMaxMediumVolNumValid(job1, 0));
//		assertFalse("sum of light + medium + heavy is <=30 fail",jobController.isMaxHeavyVolNumValid(job1, 11));
//	
//	}
//	
//	
	

}
