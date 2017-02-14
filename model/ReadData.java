package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/*
 * @author Dereje Bireda
 */
public class ReadData  {

	
	
	public static void main(String[] args) {
		
		try {
			ParksSystem p = readFile();
		    List<Job> jobs = p.getMyJobs();
		    List<Volunteer> vols = p.getMyVolunteers();
		    
		    for (int i = 0; i < 3; i ++) {
		    	System.out.println(jobs.get(i).getMyDescription());
		    }
		    for (int i = 0; i < 3; i ++) {
		    	System.out.println(vols.get(i).getMyName());
		    }
//			System.out.println(p.getMyUrbanStaff());
//			System.out.println(p.getMyParkManagers());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ParksSystem readFile() throws FileNotFoundException, IOException{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("uparksdata.bin"));
		ParksSystem parksSystem = null;
		try {
			 parksSystem = (ParksSystem)input.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("failed");
			
			e.printStackTrace();
		}
		input.close();
		return parksSystem;
	}
}
