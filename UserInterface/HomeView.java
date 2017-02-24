package UserInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import model.ParksSystem;

public class HomeView {
	
	private static Scanner myReader;
	private static ParksSystem mySystem;

	//private boolean pageRedirected = false;
	
	public HomeView() {
		
		//mySystem = new ParksSystem();

	}
	
	public void initHome(Scanner theScanner) {
		myReader = theScanner;
//		try {
//			//readFile();
//		} catch (FileNotFoundException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//	
//			e.printStackTrace();
//		}
		System.out.println("Welcome to UParks");
		System.out.println("Enter User name");
		
		
		
//		for (int retries = 0; retries < 3; retries++) {
//			String textUserInput = myReader.nextLine();
//			if(mySystem.loginSuccessful(textUserInput)) {
//				
//				//pageRedirected = true;
//				break;
//			} else {
//				System.out.println("Login failed: try again!");
//			}
//		}
//	
		
	}
	
	public boolean loginTrialView(ParksSystem theSystem) {
		mySystem = theSystem;
		boolean pageRedirected = false;
		for (int retries = 0; retries < 3; retries++) {
			String textUserInput = myReader.nextLine();
			//myReader.nextLine();
			if(mySystem.loginSuccessful(textUserInput)) {
				
				pageRedirected = true;
				System.out.println("Login Successful");
				break;
			} else {
			
				System.out.println("Login failed: try again!");
				if ( retries == 2) {
					mySystem.logout();
				}
			}
		}
		//return pageRedirected;
		pageRedirected = mySystem.isPageRedirected();
	
		return pageRedirected;
	}

//	public static void readFile() throws FileNotFoundException, IOException{
//		ObjectInputStream input = new ObjectInputStream(new FileInputStream("uparksdata.bin"));
//		try {
//			mySystem = (ParksSystem)input.readObject();
//		} catch (ClassNotFoundException e) {
//			input.close();
//			e.printStackTrace();
//		}
//	}
	
//	public boolean isPageRedirected(){
//		
//		return pageRedirected;
//	}
//	
//	public void logOut() {
//		//system exit
//		this.pageRedirected = true;
//	
//	}
}
