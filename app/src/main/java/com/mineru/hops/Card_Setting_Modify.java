package com.mineru.hops;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.mineru.hops.Function.MakeCard.MakeCard2.GALLERY_CODE;

public class Card_Setting_Modify extends AppCompatActivity {

    public TextView btnSave;

    public ImageView backgound_img;
    public ImageView main_img;

    public TextView edit_name;

    public RelativeLayout l_des;
    public RelativeLayout l_pos;
    public RelativeLayout l_com;
    public RelativeLayout l_email;
    public RelativeLayout l_pho;

    private String mImageUrl1;
    private String mImageUrl2;
    private String mName;
    private String mDescription;
    private String mCompany;
    private String mPosition;
    private String mEmail;
    private String mPhoneNumber;
    private String Card_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_setting_modify);
        Intent intent =getIntent();
        Card_key = intent.getStringExtra("card_key");
        setLayout();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fromleft, R.anim.toright);
            }
        });

        main_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        backgound_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });
        l_des.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(v.getContext());
            }
        });
    }

    void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_test,null);
        final EditText eText = (EditText) view.findViewById(R.id.edit);
        builder.setView(view).setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> stringObjectMap = new HashMap<>();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                stringObjectMap.put("inputDescription",eText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Users/"+uid+"/Card/"+Card_key).updateChildren(stringObjectMap);
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void setLayout(){
        btnSave = (TextView) findViewById(R.id.btn_save);

        edit_name = (TextView) findViewById(R.id.edit_name);
        l_com = (RelativeLayout) findViewById(R.id.layout_com);
        l_des = (RelativeLayout) findViewById(R.id.layout_des);
        l_email = (RelativeLayout) findViewById(R.id.layout_email);
        l_pho = (RelativeLayout) findViewById(R.id.layout_pho);
        l_pos = (RelativeLayout) findViewById(R.id.layout_pos);

        backgound_img = (ImageView) findViewById(R.id.card_background_set);
        main_img = (ImageView) findViewById(R.id.card_image_set);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_CODE) {

            mImageUrl1 = getPath(data.getData());
            File f1 = new File(mImageUrl1);
            main_img.setImageURI(Uri.fromFile(f1));

        }
            //mImageUrl2 = getPath(data.getData());
            //File f2 = new File(mImageUrl2);
            //backgound_img.setImageURI(Uri.fromFile(f2));
    }

    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fromleft, R.anim.toright);
    }
}