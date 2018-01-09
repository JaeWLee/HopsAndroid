package com.mineru.hops.Function.MakeCard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mineru.hops.R;

/**
 * Created by rmstj on 2017-10-14.
 */

public class MakeCard3 extends AppCompatActivity {
    private static final String TAG = "MakeCard3";

    private String[] str = new String[6];
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecard3_activity);
        Intent intent = getIntent();
        str[0] = intent.getExtras().getString("inputName");
        str[1] = intent.getExtras().getString("inputEmail");
        str[2] = intent.getExtras().getString("inputPhoneNumber");
        str[3] = intent.getExtras().getString("inputCompany");
        str[4] = intent.getExtras().getString("inputPosition");
        str[5] = intent.getExtras().getString("imagePath");
        Log.v(TAG,"imagePath : "+str[5]);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler(),1000);
                Intent intent = new Intent(MakeCard3.this,MakeCard4.class);
                intent.putExtra("inputName",str[0]);
                intent.putExtra("inputEmail",str[1]);
                intent.putExtra("inputPhoneNumber",str[2]);
                intent.putExtra("inputCompany",str[3]);
                intent.putExtra("inputPosition",str[4]);
                intent.putExtra("imagePath",str[5]);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    class splashhandler implements Runnable{
        public void run(){
            btnNext.setEnabled(true);

        }
    }
}
