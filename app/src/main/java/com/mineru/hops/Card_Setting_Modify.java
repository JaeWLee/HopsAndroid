package com.mineru.hops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Card_Setting_Modify extends AppCompatActivity {

    private TextView btnSave;
    private TextView btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_setting_modify);

        btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnSave = (TextView) findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fromleft,R.anim.toright);
            }
        });
    }
}
