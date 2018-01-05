package com.mineru.hops.Function.MakeCard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mineru.hops.R;

/**
 * Created by rmstj on 2017-10-14.
 */

public class MakeCard1 extends AppCompatActivity {

    public EditText inputName;
    public EditText inputEmail;
    public EditText inputPhoneNumber;
    private Button btnNext;

    public long card_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecard1_activity);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPhoneNumber = (EditText) findViewById(R.id.input_pnumber);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler(),1000);
                Intent intent = new Intent(MakeCard1.this,MakeCard2.class);
                intent.putExtra("inputName",inputName.getText().toString());
                intent.putExtra("inputEmail",inputEmail.getText().toString());
                intent.putExtra("inputPhoneNumber",inputPhoneNumber.getText().toString());
                intent.putExtra("card_num",card_num);
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
