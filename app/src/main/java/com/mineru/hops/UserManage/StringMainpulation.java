package com.mineru.hops.UserManage;

/**
 * Created by rmstj on 2017-10-05.
 */

public class StringMainpulation {

    public static String expandUsername(String username){
        return username.replace("."," ");
    }
    public static String condenseUsername(String username){
        return username.replace(" ",".");
    }
}
