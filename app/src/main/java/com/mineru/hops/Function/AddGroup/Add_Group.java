package com.mineru.hops.Function.AddGroup;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mineru.hops.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mineru on 2018-01-10.
 */

public class Add_Group extends AppCompatActivity {
    public static final int GALLERY_CODE = 10;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    public EditText edit_title;
    public ImageView image;
    public LinearLayout image_layout;
    public TextView change_title;
    public Button btn_next;
    public TextView tv;
    public TextView tv_num;

    private String imagePath;
    public GradientDrawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        imagePath="";

        edit_title = (EditText) findViewById(R.id.edit_title);
        image = (ImageView) findViewById(R.id.image);
        image.setBackground(drawable);
        image.setClipToOutline(true);
        tv = (TextView) findViewById(R.id.tv);
        tv_num = (TextView) findViewById(R.id.tv_num);

        image_layout = (LinearLayout) findViewById(R.id.image_layout);
        change_title = (TextView) findViewById(R.id.change_title);
        btn_next = (Button) findViewById(R.id.btn_next);
        edit_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                change_title.setText(edit_title.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                change_title.setText(edit_title.getText().toString());
            }
        });

        image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_layout.setEnabled(false);
                Handler h = new Handler();
                h.postDelayed(new splashhandler1(),1000);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
                tv.setHint("");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = edit_title.getText().toString();
                if(imagePath.equals("")&&check.getBytes().length<=0){
                    Toast.makeText(Add_Group.this, "           이미지를 넣고,\n그룹 이름을 지정해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(imagePath.equals("")){
                    Toast.makeText(Add_Group.this, "이미지를 넣어주세요.", Toast.LENGTH_SHORT).show();
                }else if(check.getBytes().length<=0){
                    Toast.makeText(Add_Group.this, "그룹 이름을 지정해 주세요.", Toast.LENGTH_SHORT).show();
                } else{
                    btn_next.setEnabled(false);
                    Handler h = new Handler();
                    h.postDelayed(new splashhandler2(),1000);
                    upload(imagePath);
                    finish();
                }
            }
        });
        edit_title.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        edit_title.addTextChangedListener(new myTextWatcher());
    }
    class splashhandler1 implements Runnable{
        public void run(){
            image_layout.setEnabled(true);

        }
    }
    class splashhandler2 implements Runnable{
        public void run(){
            btn_next.setEnabled(true);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_CODE) {

            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            image.setImageURI(Uri.fromFile(f));
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

    private void upload(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://hops2lattop.appspot.com");
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss ");
        final String strNow = sdfNow.format(date);

        final Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("Users/"+auth.getCurrentUser().getUid()+"/Group/"+strNow+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Map<String,Object> test= new HashMap<String,Object>();

                test.put("imageUrl",downloadUrl.toString());
                test.put("group_name",edit_title.getText().toString());
                test.put("m_num",0);
                test.put("imageName",strNow+file.getLastPathSegment());
                database.getReference().child("Users/" + auth.getCurrentUser().getUid()+"/Group/"+edit_title.getText().toString()).updateChildren(test);
            }
        });
    }

    private class myTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            tv_num.setText(s.length() + " / 12 자");
            if(s.length()==0)
                change_title.setText("그룹 이름");
        }
    }
}
