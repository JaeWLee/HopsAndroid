package com.mineru.hops.UserManage.Model;

/**
 * Created by rmstj on 2017-10-05.
 */

public class User {
    private String user_id;
    private String email;
    private String username;
    private int card_num;

    public User(String user_id,String email,String username,int card_num){
        this.user_id = user_id;
        this.email=email;
        this.username=username;
        this.card_num=card_num;
    }
    public User(){
    }

    public int getCard_num(){
        return card_num;
    }
    public void setCar_num(){
        this.card_num=card_num;
    }

    public String getUser_id(){
        return user_id;
    }
    public void setUser_id(String user_id){
        this.user_id=user_id;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    @Override
    public String toString() {
        return "User{" +
                "my_id='" + user_id + '\'' +
                ", verify_email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", card_num='"+ card_num +'\''+
                '}';

    }
}
