package UserInterface;

import java.util.Scanner;

import model.ParksSystem;

public class HomeView {
	
	private static Scanner myReader;
	private static ParksSystem mySystem;

	public HomeView(ParksSystem theSystem) {
	    mySystem = theSystem;
    }

    public static void run() {
        String result = "";
		myReader = new Scanner(System.in);
		//mySystem = new ParksSystem();
		//System view

		
		result="\tWelcome to Urban Parks\n";
        result+="\t-------------------\n";
        result+="\tLogin Volunteer\t\t(Vol) <username>\n";
        result+="\tLogin Manager\t\t(Mgr) <username>\n";
        result+="\tLogin Urban Parks Staff\t(USt) <username>\n";
        result+="\tHELP(H)\n";
        result+="\tQUIT(Q)\n";

        System.out.println(result);
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
