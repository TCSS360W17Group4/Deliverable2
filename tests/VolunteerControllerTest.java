package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Job;
import model.Park;
import model.Volunteer;
import model.VolunteerController;

public class VolunteerControllerTest { 
	private static final int JOB_ID_WITH_NO_CONFLICTING_DATE = 0;
	private static final  int JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE = 1;
	
	private static final int VOLUNTEER_ID = 0;
	private static final int INVALID_JOB_ID_NEGATIVE_VALUE = -1;
	private static final int NOT_USED_JOB_ID = 2;
	private static final int MIN_NUM_DAYS_FOR_JOB_SIGNUP = 2;
	private static final int ONE_DAY_OFFSET = 1;
	
	private Job nonConflictingJob;
	private Job conflictingJob;
	private Volunteer volunteer;
	private VolunteerController controller;
	private List<Job>emptyJobList;
	private List<Job>JobWithTwoPendingJobs;
	private LocalDate myCurrentDate;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	
	public void setUp() {
		volunteer = new Volunteer();
		JobWithTwoPendingJobs = new ArrayList<>();
		emptyJobList = new ArrayList<>();
		
		//makes date testing future 
		myCurrentDate = LocalDate.now();
		
		/*set up non conflicting job that could be added with no issue*/
		nonConflictingJob = new Job(new Park());
		nonConflictingJob.setMyJobId(JOB_ID_WITH_NO_CONFLICTING_DATE);
		LocalDate firstJobStartDate = myCurrentDate.plusDays(3);
		LocalDate firstJobEndDate = myCurrentDate.plusDays(5);
	
		nonConflictingJob.setMyStartDate(firstJobStartDate);
		nonConflictingJob.setMyEndDate(firstJobEndDate);
		//number of volunteers needed
		nonConflictingJob.setMyLightVolunteerNumber(1);
		
		volunteer = new Volunteer();
		volunteer.setMyUserId(VOLUNTEER_ID);
		/*set up non conflicting job that could be added with no issue*/
		conflictingJob = new Job(new Park());
		conflictingJob.setMyJobId(JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE);
		LocalDate secondJobStartDate = myCurrentDate.plusDays(3);
		LocalDate secondJobEndDate =  myCurrentDate.plusDays(5);
	
		conflictingJob.setMyStartDate(secondJobStartDate);
		conflictingJob.setMyEndDate(secondJobEndDate);
		//number of volunteers needed
		conflictingJob.setMyLightVolunteerNumber(1);
		JobWithTwoPendingJobs.add(nonConflictingJob);
		JobWithTwoPendingJobs.add(conflictingJob);
		
	
		/*set up controller*/
		 controller = new VolunteerController(volunteer, JobWithTwoPendingJobs, null);
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
	
	

	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 */
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void hasJobViolatedMaxJobPerDayPerVolunteer_Expecting_Positive_IntegerId() {
		
				controller.hasJobViolateMaxJobPerDayPerVolunteer(INVALID_JOB_ID_NEGATIVE_VALUE);
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void hasJobViolatedMaxJobPerDayPerVolunteer_Adding_NOT_USED_JobId() {
		volunteer.getMyVolunteerJobs().add(JOB_ID_WITH_NO_CONFLICTING_DATE);
			controller.hasJobViolateMaxJobPerDayPerVolunteer(NOT_USED_JOB_ID);
	}
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_Signup_When_Job_IsPast() {
		Job pastJob = new Job(new Park());
		
		pastJob.setMyStartDate(myCurrentDate.minusDays(ONE_DAY_OFFSET));
		pastJob.setMyEndDate(myCurrentDate.plusDays(ONE_DAY_OFFSET));
		assertTrue("Fail! signed up for a past job", controller.hasMinSignupDaysBeforeJobStartPassed(pastJob));
		
		
	}
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_Job_Has_At_Least_Min_Days_For_Signup() {
		Job futureJobStillOpen = new Job(new Park());
	
		futureJobStillOpen.setMyStartDate(myCurrentDate.plusDays(MIN_NUM_DAYS_FOR_JOB_SIGNUP));
		futureJobStillOpen.setMyEndDate(myCurrentDate.plusDays(ONE_DAY_OFFSET));
		
		
		assertFalse("Fail! sign up for still open job", controller.hasMinSignupDaysBeforeJobStartPassed(futureJobStillOpen));
		
		
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_Job_Start_Date_Below_Min_Days_For_Signup() {
		Job futureJobClosed = new Job(new Park());

	
		futureJobClosed.setMyStartDate(myCurrentDate.plusDays(MIN_NUM_DAYS_FOR_JOB_SIGNUP - ONE_DAY_OFFSET ));
		futureJobClosed.setMyEndDate(myCurrentDate);
		
		
		assertTrue("Fail! sign up for future job, but sign up date passed",
				controller.hasMinSignupDaysBeforeJobStartPassed(futureJobClosed));
		
		
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobFullForSignUp(Job)}
	 */
	@Test
	public void isJobFullForSignUp_Job_Already_Has_Max_Volunteer_Allowed() {
		Job fullJob = new Job(new Park());
		int numberOfVolunteersRequested = 2;
		int numberOfCurrentVolunteers = 2;
		fullJob.setMyLightVolunteerNumber(numberOfVolunteersRequested);
		fullJob.setMyMediumVolunteerNumber(0);
		fullJob.setMyHeavyVolunteerNumber(0);
		fullJob.setMyCurrentTotalVolunteers(numberOfCurrentVolunteers);

		assertTrue("Fail!Job already has max number of volunteer requested",
					controller.isJobFullForSignUp(fullJob));
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobFullForSignUp(Job)}
	 */
	@Test
	public void isJobFullForSignUp_Job_Has_One_Spot_Tofill() {
		Job fullJob = new Job(new Park());
		//volunteers number with two cases
		int numberOfVolunteersRequested = 2;
		int numberOfCurrentVolunteers = 1;
		
		fullJob.setMyLightVolunteerNumber(numberOfVolunteersRequested);
		fullJob.setMyMediumVolunteerNumber(0);
		fullJob.setMyHeavyVolunteerNumber(0);
		fullJob.setMyCurrentTotalVolunteers(numberOfCurrentVolunteers);

		assertFalse("Fail!Job has one spot left",
					controller.isJobFullForSignUp(fullJob));
	}
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobFullForSignUp(Job)}
	 */
	@Test(expected = NullPointerException.class)
	public void isJobFullForSignUp_Null_Passed_As_Parameter() {

		assertFalse("Fail!Job is Null",
					controller.isJobFullForSignUp(null));
	}
	

	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#volunteerHasTheJob(Job)}
	 */
	@Test
	public void volunteerHasTheJob_Job_Is_Previously_Signedup() {
		
		int jobId = 1;
		Job volunteerJob = new Job(new Park());
		volunteerJob.setMyJobId(jobId);
		//add volunteer to job object
		volunteer.setMyUserId(VOLUNTEER_ID);
		volunteerJob.getMyVolunteerList().add(VOLUNTEER_ID);
		//add job to volunteer
		volunteer.getMyVolunteerJobs().add(jobId);
		assertTrue("Fail!volunteer already signed up for the job",
					controller.volunteerHasTheJob(jobId));
	}
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#volunteerHasTheJob(Job)}
	 */
	@Test
	public void volunteerHasTheJob_Volunteer_First_Time_Signup_For_TheJob() {
		
		int jobId = 1;
		Job newJob = new Job(new Park());
		newJob.setMyJobId(jobId);
		//add volunteer to job object
		volunteer.setMyUserId(VOLUNTEER_ID);
	
		assertFalse("Fail!Volunteer does not have the job",controller.volunteerHasTheJob(jobId));
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobAvailableToSignUp()}
	 */
	@Test
	public void isJobAvailableToSignUp_When_Job_List_Is_Empty() {
		//reset controller with empty job list 
		controller = new VolunteerController(volunteer,emptyJobList,null);
		assertFalse("Fail! no jobs available",controller.isJobAvailableToSignUp());
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobAvailableToSignUp()}
	 */
	@Test
	public void isJobAvailableToSignUp_When_There_Is_A_Job_For_Sign_Up() {
		//reset controller with empty job list 
		emptyJobList.add(nonConflictingJob);
		controller = new VolunteerController(volunteer,emptyJobList,null);
		assertTrue("Fail! There is one Job available",controller.isJobAvailableToSignUp());
	}
	

}
