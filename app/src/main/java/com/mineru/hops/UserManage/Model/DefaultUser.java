package com.mineru.hops.UserManage.Model;

/**
 * Created by mineru on 2017-12-29.
 */

public class DefaultUser {

    private String my_id;

    public DefaultUser(String my_id){
        this.my_id = my_id;
    }
    public DefaultUser(){
    }
    public String getUser_id(){
        return my_id;
    }
    public void setUser_id(String my_id){
        this.my_id=my_id;
    }
    @Override
    public String toString() {
        return "DefaultUser{" +
                "my_id='" + my_id + '\'' +
                '}';
    }
}
