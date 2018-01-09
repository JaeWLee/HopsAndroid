package com.mineru.hops.UserManage.Model;

/**
 * Created by mineru on 2017-12-29.
 */

public class DefaultUser {

    private String group_name;
    private String imageUrl;
    private int m_num;

    public DefaultUser(String group_name,int m_num,String imageUrl){
        this.group_name = group_name;
        this.imageUrl = imageUrl;
        this.m_num=m_num;
    }
    public DefaultUser(){

    }

    public int getm_num(){
        return m_num;
    }
    public void setm_num(int m_num){
        this.m_num=m_num;
    }

    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }

    public String getgroup_name(){
        return group_name;
    }
    public void setgroup_name(String group_name){
        this.group_name=group_name;
    }
    @Override
    public String toString() {
        return "DefaultUser{" +
                ", group_name='" + group_name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", m_num='" + m_num + '\'' +
                '}';
    }
}
