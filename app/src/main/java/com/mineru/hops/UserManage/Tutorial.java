package com.mineru.hops.UserManage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mineru.hops.BuildConfig;
import com.mineru.hops.R;

/**
 * Created by rmstj on 2017-10-02.
 */

public class Tutorial extends Activity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    public static boolean mCheck;
    boolean check;
    ViewPager viewPager;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        SharedPreferences mCount=getSharedPreferences("Setting",0);

        check= mCount.getBoolean("Count",mCheck);

        if(check==true){
            Intent intent = new Intent(Tutorial.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new Tutorial_Adapter(Tutorial.this);
        loginButton = (Button) findViewById(R.id.loginBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Tutorial.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tutorial.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager.setAdapter(adapter);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);

        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {


                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {

                        }
                        displayMessage();
                    }
                });
    }
    void displayMessage() {
        String splash_background = mFirebaseRemoteConfig.getString("splash_background");
        boolean caps = mFirebaseRemoteConfig.getBoolean("splash_message_caps");
        String splash_message = mFirebaseRemoteConfig.getString("splash_message");

        if(caps){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(splash_message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.create().show();

        }

    }
    public class Tutorial_Adapter extends PagerAdapter{

        Context context;
        int[] imageId = {R.drawable.tutorial1, R.drawable.tutorial2, R.drawable.tutorial3, R.drawable.tutorial4};
        String[] stringTitle = {"Sustainability","Together","Actionfor society","Group","Regional-Trust building"};
        int[] stringExplain = {R.string.tutorial_1,R.string.tutorial_2,R.string.tutorial_3,R.string.tutorial_4,R.string.tutorial_5};

        public Tutorial_Adapter(Context context){
            this.context = context;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.tutorial_item, container, false);

            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            imageView.setImageResource(imageId[position]);

            TextView textView1 = (TextView) viewItem.findViewById(R.id.textView1);
            textView1.setText(stringTitle[position]);
            TextView textView2 = (TextView) viewItem.findViewById(R.id.textView2);
            textView2.setText(stringExplain[position]);
            ((ViewPager)container).addView(viewItem);

            return viewItem;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub

            return view == ((View)object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView((View) object);
        }

    }
}