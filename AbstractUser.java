/*
 * Stub for AbstractUser 
 * everyone feel free to use, fix, enhance, whatever
 * 
 * @author Christopher Hall
 * @Winter Quarter 2017
 * 
 */


public class AbstractUser {
    private String myName;
    private String myPhone;
    private String myAddress;
    private String myUserName;
    private String myEmail;
    
    public void setName(String s) {
        myName = s;
    }
    public void setPhone(String s) {
        myPhone = s;
    }
    public void setAddress(String s) {
        myAddress = s;
    }
    public void setUserName(String s) {
        myUserName = s;
    }
    public void setEmail(String s) {
        myEmail = s;
    }
    
    public String getName() {
        return myName;
    }
    public String getPhone() {
        return myPhone;
    }
    public String getAddress() {
        return myAddress;
    }
    public String getUserName() {
        return myUserName;
    }
    public String getEmail() {
        return myEmail;
    }
}