package UserInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import model.Job;
import model.ParkManager;
import model.ParkManagerController;
import model.ParksSystem;
import model.Volunteer;

public class MainGUI {

	private static Scanner myReader;
	private static ParksSystem mySystem;
	private static HomeView home;
	public static void main(String[] args) {
		myReader = new Scanner(System.in);
		
		try {
			readFile();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		//accept parkSystem pass views accordingly
		
		home = new HomeView();
		home.initHome(myReader);
		//mySystem.isPageRedirected();true as long as we are not logged out
		if(home.loginTrialView(mySystem)) {
			System.out.println(mySystem.getMyCurrentUser());
			
			if (mySystem.getMyCurrentUser() instanceof ParkManager) {
//				home = new ParkManagerView((ParkManagerController) mySystem.getMyUserController(), 
//						  mySystem.getMyJobController(), (ParkManager)mySystem.getMyCurrentUser());
//			
//				home.initHome(myReader);
				//mySystem.getMyUserController().logout();
				parkManagerView();
			} else if (mySystem.getMyCurrentUser() instanceof Volunteer){
				System.out.println("volunteer loggin");
			} else {
				System.out.println("Unknown");
			}
		} else {
			
			//make system logout
		}

	}

	public static void parkManagerView() {
		
		home = new ParkManagerView((ParkManagerController) mySystem.getMyUserController(), 
				  mySystem.getMyJobController(), (ParkManager)mySystem.getMyCurrentUser());
		//while we are not logged out
	
		while(!mySystem.getMyUserController().getIsPageRedicrected()) {
			home.initHome(myReader);
			
		}
		Job j = mySystem.getMyJobs().get(mySystem.getMyJobs().size()-1);
		System.out.println(j.getMyDescription());
	}
	
	public static void readFile() throws FileNotFoundException, IOException{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("uparksdata.bin"));
		try {
			mySystem = (ParksSystem)input.readObject();
		} catch (ClassNotFoundException e) {
			input.close();
			e.printStackTrace();
		}
	}
	
}
