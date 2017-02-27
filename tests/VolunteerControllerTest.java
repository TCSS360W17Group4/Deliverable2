package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Job;
import model.JobController;
import model.Park;
import model.Volunteer;
import model.VolunteerController;

public class VolunteerControllerTest {

	private int JOB_ID_WITH_NO_CONFLICTING_DATE = 0;
	private int JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE = 1;
	
	private Job nonConflictingJob;
	private Job conflictingJob;
	private Volunteer volunteer;
	private VolunteerController controller;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	
	public void setUp() {
		volunteer = new Volunteer();
		List<Job>systemJobs = new ArrayList<>();
		
		/*set up non conflicting job that could be added with no issue*/
		nonConflictingJob = new Job(new Park());
		nonConflictingJob.setMyJobId(JOB_ID_WITH_NO_CONFLICTING_DATE);
		LocalDate firstJobStartDate = JobController.convertStringToDate("03/20/17");
		LocalDate firstJobEndDate = JobController.convertStringToDate("03/23/17");
	
		nonConflictingJob.setMyStartDate(firstJobStartDate);
		nonConflictingJob.setMyEndDate(firstJobEndDate);
		
		volunteer = new Volunteer();
		
		/*set up non conflicting job that could be added with no issue*/
		conflictingJob = new Job(new Park());
		conflictingJob.setMyJobId(JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE);
		LocalDate secondJobStartDate = JobController.convertStringToDate("03/20/17");
		LocalDate secondJobEndDate = JobController.convertStringToDate("03/23/17");
	
		conflictingJob.setMyStartDate(secondJobStartDate);
		conflictingJob.setMyEndDate(secondJobEndDate);
		systemJobs.add(nonConflictingJob);
		systemJobs.add(conflictingJob);
		
	
		/*set up controller*/
		 controller = new VolunteerController(volunteer, systemJobs, null);
	}



	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 */
	@Test
	public void hasJobViolatedMaxJobPerDayPerVolunteer_Adding_Job_With_No_Previous_Jobs() {
		
		assertFalse("Fail! with no jobs adding a job", 
				controller.hasJobViolateMaxJobPerDayPerVolunteer(nonConflictingJob.getMyJobId()));
		
		
		
	}
	

	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 */
	@Test
	public void hasJobViolatedMaxJobPerDayPerVolunteer_Expecting_IntegerId() {
		
		assertFalse("Fail! with no jobs adding a job", 
				controller.hasJobViolateMaxJobPerDayPerVolunteer(nonConflictingJob.getMyJobId()));
		
		
		
	}
	
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 *
	 */
	@Test
	public void hasJobViolatedMaxJobPerDayPerVolunteer_With_Adding_Above_Limit_PerDay(){
		//add first job
		volunteer.getMyVolunteerJobs().add(JOB_ID_WITH_NO_CONFLICTING_DATE);
		
		//try to add another one which fall same date or days as the one in volunteer bucket
		assertTrue("Fail! new Job added schedule violate max Job limit per day", 
				controller.hasJobViolateMaxJobPerDayPerVolunteer(conflictingJob.getMyJobId()));
		
		
		
	}
}
