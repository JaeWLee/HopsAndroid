package com.mineru.hops.UserManage.Model;

/**
 * Created by rmstj on 2017-10-05.
 */

public class UserAccountSettings {

    private String username;
    private String email;

    public UserAccountSettings(String usernmame, String email){
        this.username = usernmame;
        this.email=email;
    }
    public UserAccountSettings(){
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
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
                ", verify_email='" + email + '\'' +
                '}';
    }
}
