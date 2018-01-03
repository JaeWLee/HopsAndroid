package com.mineru.hops.Function.AddGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mineru.hops.MyAdapter;
import com.mineru.hops.R;

/**
 * Created by mineru on 2018-01-03.
 */

public class Add_Firends_Group extends AppCompatActivity{
    public GridView gridView;
    public CheckBox btnCheck;
    public Button btnNext;
    static final String[] GRID_DATA = new String[]{
            "Windows",
            "iOS",
            "Android",
            "Blackberry",
            "Java",
            "JQuery",
            "Phonegap",
            "SQLite",
            "Thread",
            "Video"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_firends_dialog);
        gridView = (GridView) findViewById(R.id.gridView1);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridView.setAdapter(new MyAdapter(this, GRID_DATA));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(btnCheck.isChecked()==true){
                    btnCheck.setChecked(false);
                }else {
                    btnCheck.setChecked(true);
                }
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.group_name))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}