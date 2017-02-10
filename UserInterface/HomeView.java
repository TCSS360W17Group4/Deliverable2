package UserInterface;

import java.util.Scanner;

import model.ParksSystem;

public class HomeView {
	
	private static Scanner myReader;
	private static ParksSystem mySystem;

	public static void main(String[] args) {
	
		myReader = new Scanner(System.in);
		mySystem = new ParksSystem();
		//System view
		System.out.println("Enter User name");
		String userName = myReader.nextLine();
		
		
			//user enter
		//if(!mySystem.loginSuccessful(userName)){
			//show home login again and again
		//}

		
		
		
		//close scanner
		myReader.close();
	}
	
	public static void initSystem() {
		
	}

}
