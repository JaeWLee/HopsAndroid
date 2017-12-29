package com.mineru.hops.Function.AddGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mineru.hops.R;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by mineru on 2017-12-29.
 */

public class AddGroup1 extends AppCompatActivity {
    public EditText et_title;
    private Spinner sp_year;
    private Spinner sp_month;
    private Spinner sp_day;
    public String year;
    public String month;
    public String day;
    public String empty;
    private Button btn_next;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group1);
        et_title = (EditText) findViewById(R.id.et_title1);
        sp_year = (Spinner)findViewById(R.id.spinner_year);
        sp_month = (Spinner)findViewById(R.id.spinner_month);
        sp_day =(Spinner)findViewById(R.id.spinner_day);
        btn_next = (Button) findViewById(R.id.btnNext);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty = et_title.getText().toString();
                empty=empty.trim();
                if(empty.getBytes().length<=0){
                    Toast.makeText(AddGroup1.this, "입력이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (empty.getBytes().length>0){
                    Intent intent = new Intent(AddGroup1.this,AddGroup2.class);
                    intent.putExtra("title",et_title.getText().toString());
                    intent.putExtra("year",year);
                    intent.putExtra("month",month);
                    intent.putExtra("day",day);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                year= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                month= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });sp_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                day= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
