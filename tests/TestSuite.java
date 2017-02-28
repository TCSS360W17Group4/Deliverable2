package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   VolunteerControllerTest.class,
   JobControllerTest.class,
   ParkManagerControllerTest.class,
   ParksSystemTest.class,
   VolunteerTest.class
})

public class TestSuite {   
}   