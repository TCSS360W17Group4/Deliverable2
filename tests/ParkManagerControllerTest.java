/**
 * 
 */
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
import model.ParkManagerController;


public class ParkManagerControllerTest {
    private static int DEFAULT_MAX_NUM_PENDING_JOBS = 20;
    private List<Job> myJobs = new ArrayList<Job>();
    private static List<Job> myManyJobs = new ArrayList<Job>();
    private static ParkManagerController myFullController;
    private ParkManagerController myEmptyController = new ParkManagerController(myJobs, null, null);
    
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        for (int i = 0; i < DEFAULT_MAX_NUM_PENDING_JOBS; i++){
            Job aJob = new Job(new Park());
            LocalDate aDate = LocalDate.now();
            aDate = aDate.plusDays(4);
            aJob.setMyStartDate(aDate);
            myManyJobs.add(aJob);
        }
        myFullController = new ParkManagerController(myManyJobs, null, null);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * {@link model.JobController#isNewJobAccepted(java.util.List)}
     */
//    @Test
//    public void testIsNewJobAccepted() {
//
//
//    }
//    
    /**
     * @author Chris
     *
     */
    @Test
    public void isNewJobAccepted_SystemJobLimitReached(){
        assertFalse("Allowed to a job to a full system", myFullController.isNewJobAccepted() );
        
    }
    /**
     * @author Chris
     *
     */
    @Test
    public void isNewJobAccepted_JobLimitNotReached(){
        assertTrue("Cannot add a job to an empty system", myFullController.isNewJobAccepted() );
    }
    /**
     * @author Chris
     *
     */
    @Test
    public void hasMinSignupDaysBeforeJobStartPassed_NewJobIsTooSoon(){
        Job aJob = new Job(new Park());
        LocalDate aDate = LocalDate.now();
        aDate = aDate.plusDays(0);
        aJob.setMyStartDate(aDate);
        
        assertTrue("Failed to stop a bad job", myEmptyController.hasMinSignupDaysBeforeJobStartPassed(aJob));
    }
    /**
     * @author Chris
     *
     */
    @Test
    public void hasMinSignupDaysBeforeJobStartPassed_NewJobIsPast(){
        Job aJob = new Job(new Park());
        LocalDate aDate = LocalDate.now();
        aDate = aDate.plusDays(-1);
        aJob.setMyStartDate(aDate);
        
        assertTrue("Failed to stop a bad job", myEmptyController.hasMinSignupDaysBeforeJobStartPassed(aJob));
    }
    /**
     * @author Chris
     *
     */
    @Test
    public void hasMinSignupDaysBeforeJobStartPassed_NewJobIsFine(){
        Job aJob = new Job(new Park());
        LocalDate aDate = LocalDate.now();
        aDate = aDate.plusDays(3);
        aJob.setMyStartDate(aDate);
        
        assertFalse("Failed to allow a good job", myEmptyController.hasMinSignupDaysBeforeJobStartPassed(aJob));
    }

}
