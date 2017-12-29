package com.mineru.hops.Function.MakeCard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mineru.hops.UserManage.Model.ImageDTO;
import com.mineru.hops.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makecard4_activity);

        test="basic_photo";
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
        Date date = new Date(now);
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
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.imageUrl = downloadUrl.toString();
                imageDTO.uid = auth.getCurrentUser().getUid();
                imageDTO.userId = auth.getCurrentUser().getEmail();
                imageDTO.imageName = strNow+file.getLastPathSegment();
                imageDTO.inputName = str[0];
                imageDTO.inputEmail = str[1];
                imageDTO.inputPhoneNumber = str[2];
                imageDTO.inputCompany = str[3];
                imageDTO.inputPosition = str[4];
                imageDTO.inputDescription = inputDescription.getText().toString();
                database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Card/").push().setValue(imageDTO);

            }
        });

    }
}
