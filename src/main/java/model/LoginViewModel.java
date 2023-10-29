package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginViewModel {

    private String identifier ;
    private String password ;

    public String getIdentifier() {
        return identifier;
    }
    public boolean setIdentifier(String identifier){
        if(isValidEmail(identifier) || isValidUsername(identifier)) {
            this.identifier = identifier;
            return true;
        }else{
            return false;
        }
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    private boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // Define a regex pattern for a valid email address
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
