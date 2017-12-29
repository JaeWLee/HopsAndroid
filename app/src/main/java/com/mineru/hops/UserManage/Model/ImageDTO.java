package com.mineru.hops.UserManage.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rmstj on 2017-10-16.
 */
public class ImageDTO {


    public String imageUrl;
    public String imageName;
    public String inputCompany;
    public String inputPosition;
    public String uid;
    public String userId;
    public int cardpinCount=0;
    public Map<String, Boolean> cardpins = new HashMap<>();
    public String inputName;
    public String inputEmail;
    public String inputPhoneNumber;
    public String inputDescription;
}
