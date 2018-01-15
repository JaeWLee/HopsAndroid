package com.mineru.hops.UserManage.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rmstj on 2017-12-12.
 */

public class MessageBoard_Model {


    public Map<String,String> users = new HashMap<>();//채팅방의 유저들
    public Map<String,Comment> comments = new HashMap<>();//내용
    public Object lasttime;
    public static class Comment{
       public String uid;
       public String message;
       public Object timestamp;
    }
 }
