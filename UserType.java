

public enum UserType {
	
	Volunteer("vol")
	,Manager("mgr") 
	
	,Staff("stf"),
	None("no");
	
	private String myType;
	
	UserType(final String theType) {
        myType = theType;
    }
	
	public static boolean userExist(final String theString) {
        boolean userTypeExist = false ;

        for(final UserType type : UserType.values()) {
        	if(type.myType == theString) {
        		userTypeExist = true;
        		break;
        	}
        }
        return userTypeExist;
    }
	

	public String getMyType() {
		return myType;
	}
}