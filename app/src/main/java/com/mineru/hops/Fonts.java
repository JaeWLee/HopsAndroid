package com.mineru.hops;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

import static com.tsengvn.typekit.Typekit.getInstance;

/**
 * Created by mineru on 2017-12-29.
 */

public class Fonts extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.otf"));
    }
}
