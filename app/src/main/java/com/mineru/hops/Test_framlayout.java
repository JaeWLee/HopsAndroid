package com.mineru.hops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Test_framlayout extends AppCompatActivity {
    public RelativeLayout frame1;
    public RelativeLayout frame2;
    public RelativeLayout listView1;
    public RelativeLayout listView2;
    public TextView tv1;
    public TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_framlayout);
        frame1 = (RelativeLayout) findViewById(R.id.frame1);
        frame2 = (RelativeLayout) findViewById(R.id.frame2);
        listView1 = (RelativeLayout) findViewById(R.id.listView1);
        listView2 = (RelativeLayout) findViewById(R.id.listView2);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("1");
                frame2.setVisibility(View.INVISIBLE);
                listView2.setVisibility(View.INVISIBLE);
                frame1.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.VISIBLE);
            }
        });

        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setText("2");
                frame1.setVisibility(View.INVISIBLE);
                listView1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.VISIBLE);

            }
        });
    }
}
