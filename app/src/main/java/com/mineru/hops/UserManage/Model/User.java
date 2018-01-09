package com.mineru.hops.UserManage.Model;

/**
 * Created by rmstj on 2017-10-05.
 */

public class User {
    private String uid;
    private String email;
    private String user_name;
    private int card_num;

    public User(String user_id,String email,String username,int card_num){
        this.uid = user_id;
        this.email=email;
        this.user_name=username;
        this.card_num=card_num;
    }
    public User(){

    }
    public int getCardNum(){
        return card_num;
    }
    public void setCardNum(int card_num){
        this.card_num=card_num;
    }

    public String getUid(){
        return uid;
    }
    public void setUid(String user_id){
        this.uid=user_id;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getUsername(){
        return user_name;
    }
    public void setUsername(String username){
        this.user_name=username;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", verify_email='" + email + '\'' +
                ", username='" + user_name + '\'' +
                ", card_num='"+ card_num +'\''+
                '}';

    }
}
