package com.mineru.hops.Function;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.mineru.hops.R;

/**
 * Created by mineru on 2017-09-15.
 */

public class Searching_friends extends Activity {
    private TextView tv_c;
    public EditText edit_searching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_friends);
        tv_c = (TextView) findViewById(R.id.tv_cancel);
        edit_searching = (EditText) findViewById(R.id.edit_searching);

        tv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
            }
        });
    }
}
