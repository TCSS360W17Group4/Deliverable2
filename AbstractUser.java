public class AbstractUser {
    private String myName;
    private String myPhone;
    private String myAddress;
    private String myUserName;
    
    public void setName(String s){
        myName = s;
    }
    public void setPhone(String s){
        myPhone = s;
    }
    public void setAddress(String s){
        myAddress = s;
    }
    public void setUserName(String s){
        myUserName = s;
    }
    
    public String getName(){
        return myName;
    }
    public String getPhone(){
        return myPhone;
    }
    public String getAddress(){
        return myAddress;
    }
    public String getUserName(){
        return myUserName;
    }
}