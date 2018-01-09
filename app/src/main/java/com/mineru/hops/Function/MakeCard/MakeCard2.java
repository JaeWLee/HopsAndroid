package com.mineru.hops.Function.MakeCard;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mineru.hops.R;
import java.io.File;


/**
 * Created by rmstj on 2017-10-14.
 */

public class MakeCard2 extends AppCompatActivity {


    public static final int GALLERY_CODE = 10;

    private EditText inputCompany;
    private EditText inputPosition;
    private ImageView inputLogo;
    private Button btnNext;
    private String imagePath;
    private String[] str = new String[3];

    private static final String TAG = "MakeCard2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecard2_activity);

        Intent intent = getIntent();
        str[0] = intent.getExtras().getString("inputName");
        str[1] = intent.getExtras().getString("inputEmail");
        str[2] = intent.getExtras().getString("inputPhoneNumber");



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        inputCompany = (EditText) findViewById(R.id.input_company);
        inputPosition = (EditText) findViewById(R.id.input_postion);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler2(),1000);
                Intent intent = new Intent(MakeCard2.this, MakeCard3.class);
                intent.putExtra("inputName", str[0]);
                intent.putExtra("inputEmail", str[1]);
                intent.putExtra("inputPhoneNumber", str[2]);
                intent.putExtra("inputCompany", inputCompany.getText().toString());
                intent.putExtra("inputPosition", inputPosition.getText().toString());
                intent.putExtra("imagePath",imagePath);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        inputLogo = (ImageView) findViewById(R.id.input_logo);
        inputLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputLogo.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler1(),1000);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });
    }
    class splashhandler1 implements Runnable{
        public void run(){
            inputLogo.setEnabled(true);

        }
    }
    class splashhandler2 implements Runnable{
        public void run(){
            btnNext.setEnabled(true);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_CODE) {

            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            inputLogo.setImageURI(Uri.fromFile(f));
        }
    }

    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }
}

