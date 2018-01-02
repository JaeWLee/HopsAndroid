package com.mineru.hops.Function.MakeCard;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mineru.hops.UserManage.Model.ImageDTO;
import com.mineru.hops.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rmstj on 2017-10-14.
 */

public class MakeCard4 extends AppCompatActivity {

    public EditText inputDescription;
    private String[] str = new String[6];
    private Button btnNext;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private String card_key;
    private long card_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecard4_activity);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        str[0] = intent.getExtras().getString("inputName");
        str[1] = intent.getExtras().getString("inputEmail");
        str[2] = intent.getExtras().getString("inputPhoneNumber");
        str[3] =intent.getExtras().getString("inputCompany");
        str[4] =intent.getExtras().getString("inputPosition");
        str[5] =intent.getExtras().getString("imagePath");
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).orderByValue()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if(snapshot.getKey().equals("card_num")){
                                card_num= (long) snapshot.getValue();
                                if(card_num==0){
                                    card_num=1L;
                                }else if(card_num==1L) {
                                    card_num = 2L;
                                }else if(card_num==2L) {
                                    card_num = 3L;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        inputDescription = (EditText) findViewById(R.id.inputdescription);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(str[5]);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
    private void upload(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://hops2lattop.appspot.com");
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss ");
        final String strNow = sdfNow.format(date);

        final Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("Users/"+auth.getCurrentUser().getUid()+"/"+strNow+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Map<String,Object> test= new HashMap<String,Object>();
                Map<String,Object> card= new HashMap<String,Object>();

                test.put("imageUrl",downloadUrl.toString());
                test.put("uid",auth.getCurrentUser().getUid());
                test.put("userId",auth.getCurrentUser().getEmail());
                test.put("imageName",strNow+file.getLastPathSegment());
                test.put("inputName",str[0]);
                test.put("inputEmail",str[1]);
                test.put("inputPhoneNumber",str[2]);
                test.put("inputCompany",str[3]);
                test.put("inputPosition",str[4]);
                test.put("inputDescription",inputDescription.getText().toString());
                card_key = database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Card/").push().getKey();
                test.put("card_key",card_key);
                database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Card/").child(card_key).setValue(test);
                card.put("card_num",card_num);
                database.getReference().child("Users/" + auth.getCurrentUser().getUid()).updateChildren(card);
            }
        });

    }
}
