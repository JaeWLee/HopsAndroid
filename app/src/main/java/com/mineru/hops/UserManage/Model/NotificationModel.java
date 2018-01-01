package com.mineru.hops.UserManage.Model;

/**
 * Created by rmstj on 2018-01-01.
 */

public class NotificationModel {
    public String to;

    public Notification notification = new Notification();

    public static class Notification{
        public String title;
        public String text;
    }
}
