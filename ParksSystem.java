import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * @author Dereje 
 *
 */
public class ParksSystem {

	private static AbstractUser myCurrentUser;
	List<Job> myJobs = new ArrayList<Job>();
	List<Volunteer> myVolunteers = new ArrayList<Volunteer>();
	private static List<ParkManager> myParkMangers= new ArrayList<ParkManager>();
	List<UrbanStaff> myUrbanStaff =  new ArrayList<UrbanStaff>();
	
	public static void main(String[] args){
		
		myParkMangers.add(new ParkManager());
		
		
	}
	
	
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
	
	/**Handle login, and logOut**/
	public void login(String theUserName) {
		String userType = theUserName.substring(0,3);
		String userId = theUserName.substring(3,theUserName.length());
		
		if(UserType.userExist(userType) && userId.matches("[0-9]+")) {
			int id = Integer.parseInt(userId);
			
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
	
	
	public boolean isUserLoaded(String theUserType, int theUserId) {
	
		boolean loadSuccess = false;
		if(theUserType == UserType.Manager.getMyType() && isIdExist(myParkMangers,theUserId)) {
			//check instanceof and init outside?
			myCurrentUser = myParkMangers.get(theUserId);
			
			loadSuccess = true;
			
		  
		} else if (theUserType == UserType.Staff.getMyType() && isIdExist(myParkMangers,theUserId)) {
			
			myCurrentUser = myUrbanStaff.get(theUserId);
			loadSuccess = true;
			 
		} else {
			
			myCurrentUser = myVolunteers.get(theUserId);
			loadSuccess = true;
			
		}
		
		return loadSuccess;
	}
	
	public <E> boolean isIdExist(List<E> users, int theId) {

		return (theId>=0 && theId < users.size());
			
	}
	
	
	
	
}
