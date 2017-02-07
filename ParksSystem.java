import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ParksSystem to control the system data,
 * and login 
 * 
 * @author Dereje 
 *
 */
public class ParksSystem {

	private static AbstractUser myCurrentUser;
	List<Job> myJobs = new ArrayList<Job>();
	List<Volunteer> myVolunteers = new ArrayList<Volunteer>();
	private static List<ParkManager> myParkManagers= new ArrayList<ParkManager>();
	List<UrbanStaff> myUrbanStaff =  new ArrayList<UrbanStaff>();
	
	//public static void main(String[] args){
		
		//myParkManagers.add(new ParkManager());
		
		
	//}
	
	/*
	 * I don't believe this is the correct place for the main() function, 
	 * if this class does model-specific things then it should be instantiated 
	 * by a super class that invokes UI and testing classes
	 * 
    */
	
	
	/**
	 * search for list of Volunteers for a job (past or present)
	 * with specific manager id
	 * 
	 * @param theVolunteers the volunteers to be searched
	 * @param theManagerId the id used to filter the volunteers
	 */
	public void getVolunteersByManagerId(List<Volunteer> theVolunteers, int theManagerId) {
		List<Job> managerJobs = getJobsByManagerId(myJobs,theManagerId);
		List<Volunteer> myVolunteers = new ArrayList<>();
		
		for(int i = 0; i < managerJobs.size(); i++) {
			
			int currentJobId = managerJobs.get(i).getMyJobId();
			
			for(int j = 0; j < theVolunteers.size(); j++) {
				//list of jobs vol volunteered
				int[] currentVolJobIds = theVolunteers.get(j).getMyVolunteerJobs();
				//check if the job id belong to current manager id
				if(Arrays.asList(currentVolJobIds).contains(currentJobId)) {
					//add to volunteers under manager list
					myVolunteers.add(theVolunteers.get(j));
					
				}
			}
		}
	}
	
	/**
	 * Search for jobs by a manager id
	 * 
	 * @param theJobs all the jobs that already exist
	 * @param theManagerId the id of the manager
	 * 
	 * @return a list of jobs by specified manager id
	 */
	 //get jobs by manager Id
	 public List<Job> getJobsByManagerId(List<Job> theJobs, int theManagerId) {
		 List<Job> managerJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < theJobs.size(); i++) {
			 	Job jobChecked = theJobs.get(i);
				if(jobChecked.getMyJobManagerId()== theManagerId) {
						//add it to manager Jobs
						managerJobs.add(jobChecked);
					
				}
				
			}
		 
		 return managerJobs;
	 }
	
	 /**
	  * authenticate login of a user with specifice user name
	  */
	/**Handle login, and logOut**/
	public void login(String theUserName) {
		//parse the user name
		String userType = theUserName.substring(0,3);
		String userId = theUserName.substring(3,theUserName.length());
		
		//check if user type exist and user id is an int
		if(UserType.userExist(userType) && userId.matches("[0-9]+")) {
			int id = Integer.parseInt(userId);
			
			//logged in, now load the user to the system
			if(isUserLoaded(userType,id)) {
				
			} else {
				//load failed
			}
			
		} else {
			//user doesnt exist
		}
			
	}
	
	public void logout() {
		
	}
	
	/**
	 * loads the current user to the system
	 * 
	 * @param theUserType the type of user(enum Manager, Staff, Volunteer)
	 * @param theUserId the id of the user
	 * @return true if current user object created
	 */
	public boolean isUserLoaded(String theUserType, int theUserId) {
	
		boolean loadSuccess = false;
		if(theUserType == UserType.Manager.getMyType() && isIdExist(myParkManagers,theUserId)) {
			//check instanceof and init outside?
			myCurrentUser = myParkManagers.get(theUserId);
			
			loadSuccess = true;
			
		  
		} else if (theUserType == UserType.Staff.getMyType() && isIdExist(myParkManagers,theUserId)) {
			
			myCurrentUser = myUrbanStaff.get(theUserId);
			loadSuccess = true;
			 
		} else {
			
			myCurrentUser = myVolunteers.get(theUserId);
			loadSuccess = true;
			
		}
		
		return loadSuccess;
	}
	/**
	 * checks a user id exist for a list of users
	 * 
	 * @param users the list of users
	 * @param theId the id to be checked
	 * @return true if that id exist for a user
	 */
	public <E> boolean isIdExist(List<E> users, int theId) {

		//the user id follows the index
		//user id greater than or equal the size, it doesnt exist
		return (theId>=0 && theId < users.size());
			
	}
	
}
