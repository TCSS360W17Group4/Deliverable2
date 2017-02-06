import java.util.ArrayList;
import java.util.List;

public class ParkManager extends AbstractUser {
	
	private List<Park> myParks = new ArrayList<Park>();

	public void setMyParks(List<Park> theParks) {
		this.myParks = theParks;
	}

	public List<Park> getMyParks() {
		return myParks;
	}
    
}
