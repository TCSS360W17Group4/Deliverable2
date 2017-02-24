package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.ParkManager;
import model.ParksSystem;

import model.Volunteer;
public class ParksSystemTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		
	}

	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 */
	@Test
	public void testLoginSuccessufl() {
		ParksSystem uSystem = new ParksSystem();
		
		//users
		List<Volunteer> myVolunteers = new ArrayList<>();
		Volunteer volOne = new Volunteer();
		volOne.setMyUserName("vol0");
		myVolunteers.add(volOne);
		String nonExistantVol = "vol1";
		uSystem.setMyVolunteers(myVolunteers);
		//manager
		List<ParkManager> myParkManagers = new ArrayList<>();
		ParkManager mgrOne = new ParkManager();
		mgrOne.setMyUserName("mgr0");
		myParkManagers.add(mgrOne);
		String nonExistantMgr = "mgr2";
		uSystem.setMyParkManagers(myParkManagers);
		
		//Urban Parks
//		List<UrbanParksStaff> myUrbanParks = new ArrayList<>();
//		UrbanParksStaff stfTwo = new UrbanParksStaff();
//		stfTwo.setMyUserName("sft0");
//		myUrbanParks.add(0,stfTwo);
//		String nonExistantStf = "stf1";
//		uSystem.setMyUrbanStaff(myUrbanParks);
		
//		assertTrue("login failed with valid username",uSystem.loginSuccessful("vol0"));
//		assertFalse("login failed with valid username",uSystem.loginSuccessful(nonExistantVol));
//		
//		assertTrue("login failed with valid manager",uSystem.loginSuccessful("mgr0"));
//		assertFalse("login failed with invalid manager",uSystem.loginSuccessful(nonExistantMgr));
//		
//		assertTrue("login failed with valid staff",uSystem.loginSuccessful("stf0"));
//		assertFalse("login failed with invalid staff",uSystem.loginSuccessful(nonExistantStf));
		
		
	} 

}
