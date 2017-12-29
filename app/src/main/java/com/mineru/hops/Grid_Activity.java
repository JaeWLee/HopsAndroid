package com.mineru.hops;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Grid_Activity extends Activity {
    GridView gridView;
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
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity);
        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new MyAdapter(this, GRID_DATA));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.group_name))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}