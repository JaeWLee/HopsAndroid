package com.mineru.hops.Function.AddGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mineru.hops.R;

/**
 * Created by mineru on 2017-12-29.
 */

public class AddGroupLatter2 extends AppCompatActivity {

    private String[] str = new String[4];
    public Button btn_finish;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_latter2);

        Intent intent = getIntent();

        str[0] = intent.getExtras().getString("title");
        str[1] = intent.getExtras().getString("year");
        str[2] = intent.getExtras().getString("month");
        str[3] = intent.getExtras().getString("day");

        btn_finish = (Button) findViewById(R.id.btnFinish);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddGroupLatter2.this, str[0]+"\n"+str[1]+"\n"+str[2]+"\n"+str[3], Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
}
