package se.qamcom.fileupload.api;

/**
 * Created by alexander.sopov on 2016-12-14.
 */
public class LoginCredentials {
    String email;
    String password;

    public LoginCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }
}
