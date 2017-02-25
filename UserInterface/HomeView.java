package UserInterface;


import java.util.Scanner;

import model.ParksSystem;

public class HomeView {
	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static Scanner myReader;
	private static ParksSystem mySystem;

	//private boolean pageRedirected = false;
	
	public HomeView() {
		
		HomeHeaderTitle();
	}
	private void HomeHeaderTitle() {
		System.out.println("Welcome to UParks");
	}
	
	public void initHome(Scanner theScanner) {
		myReader = theScanner;

		System.out.println("Enter Email or press q to quit system!");
	}


	public boolean loginTrialView(ParksSystem theSystem) {
		mySystem = theSystem;
		boolean pageRedirected = false;
		for (int retries = 0; retries < MAX_USER_INPUT_TRIAL; retries++) {
			String userCredential = myReader.nextLine();
			
			if(userCredential.equalsIgnoreCase("q")){
				mySystem.logout();
				break;
			}
			if(mySystem.loginSuccessful(userCredential)) {
				
				pageRedirected = true;
				System.out.println("Login Successful");
				break;
			} else {
			
				if ( retries == MAX_USER_INPUT_TRIAL -1) {
					System.out.println("Failed max trial system exiting....");
					mySystem.logout();
				} else {

					System.out.println("Login failed: try again! or ");
					System.out.println("press q to quit!");
				}
			}
		}
		//return pageRedirected;
		pageRedirected = mySystem.isPageRedirected();
	
		return pageRedirected;
	}

	public void exitSystem(){
		mySystem.logout();
	}
}
