package com.mineru.hops.UserManage.Model;

/**
 * Created by rmstj on 2017-10-05.
 */

public class UserAccountSettings {

    private String username;
    private String verify_email;


    public UserAccountSettings(String usernmame, String verify_email){
        this.username = usernmame;
        this.verify_email=verify_email;
    }
    public UserAccountSettings(){
    }
    public String getEmail(){
        return verify_email;
    }
    public void setEmail(String verify_email){
        this.verify_email=verify_email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                ", username='" + username + '\'' +
                ", verify_email='" + verify_email + '\'' +
                '}';
    }
}
