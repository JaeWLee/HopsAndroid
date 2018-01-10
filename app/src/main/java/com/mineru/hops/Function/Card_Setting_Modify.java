package com.mineru.hops.Function;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mineru.hops.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card_Setting_Modify extends AppCompatActivity {
    private static final String TAG ="Card_Setting Modify";
    public TextView deleteButton;
    public TextView btnSave;
    public TextView edit_name;
    public TextView edit_des;
    public TextView edit_com;
    public TextView edit_pos;
    public TextView edit_pho;
    public TextView edit_email;

    public RelativeLayout l_des;
    public RelativeLayout l_com;
    public RelativeLayout l_pos;
    public RelativeLayout l_pho;
    public RelativeLayout l_email;
    public ImageView backImg;
    public ImageView mainImg;

    public String inputName;
    public String inputDescription;
    public String inputEmail;
    public String inputCompany;
    public String inputPhoneNumber;
    public String inputPosition;
    public String mainImageUrl;
    public String backImageUrl;
    public String imageName;
    public String card_key;
    private long card_num;
    public List<String> group_list1 = new ArrayList<>();
    public List<String> group_list2 = new ArrayList<>();
    public List<String> delete_list = new ArrayList<>();

    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        setContentView(R.layout.card_setting_modify);
        Intent intent = getIntent();
        card_key = intent.getStringExtra("card_key");
        intent.getLongExtra("card_num",card_num);
        mainImageUrl = intent.getStringExtra("mainImageUrl");
        backImageUrl = intent.getStringExtra("backImageUrl");
        inputName = intent.getStringExtra("inputName");
        imageName = intent.getStringExtra("imageName");
        inputDescription = intent.getStringExtra("inputDescription");
        inputCompany = intent.getStringExtra("inputCompany");
        inputPosition = intent.getStringExtra("inputPosition");
        inputPhoneNumber = intent.getStringExtra("inputPhoneNumber");
        inputEmail = intent.getStringExtra("inputEmail");

        setlayout();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fromleft,R.anim.toright);
            }
        });
        l_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),0);
            }
        });
        l_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),1);
            }
        });
        l_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),2);
            }
        });
        l_pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),3);
            }
        });
        l_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),4);
            }
        });
        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),5);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(),6);
            }
        });
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).orderByValue()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if(snapshot.getKey().equals("card_num")){
                                card_num= (long) snapshot.getValue();
                                if(card_num==1L){
                                    card_num=0;
                                }else if(card_num==2L) {
                                    card_num = 1L;
                                }else if(card_num==3L) {
                                    card_num = 2L;
                                }
                                Log.d(TAG,"card_num : "+card_num);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void setlayout(){
        deleteButton = (TextView) findViewById(R.id.deleteButton);
        btnSave = (TextView) findViewById(R.id.btn_save);
        edit_name = (TextView) findViewById(R.id.edit_name);
        edit_des = (TextView) findViewById(R.id.edit_des);
        edit_com = (TextView) findViewById(R.id.edit_com);
        edit_pos = (TextView) findViewById(R.id.edit_pos);
        edit_pho = (TextView) findViewById(R.id.edit_pho);
        edit_email = (TextView) findViewById(R.id.edit_email);
        l_des = (RelativeLayout) findViewById(R.id.layout_des);
        l_com = (RelativeLayout) findViewById(R.id.layout_com);
        l_pos = (RelativeLayout) findViewById(R.id.layout_pos);
        l_email = (RelativeLayout) findViewById(R.id.layout_email);
        l_pho = (RelativeLayout) findViewById(R.id.layout_pho);
        backImg = (ImageView) findViewById(R.id.card_background_set);
        mainImg = (ImageView) findViewById(R.id.card_image_set);

        //Glide
        Glide.with(mainImg.getContext())
                .load(mainImageUrl)
                .apply(new RequestOptions().circleCrop())
                .into(mainImg);
        edit_name.setText(inputName);
        edit_des.setText(inputDescription);
        edit_com.setText(inputCompany);
        edit_pos.setText(inputPosition);
        edit_pho.setText(inputPhoneNumber);
        edit_email.setText(inputEmail);

    }

    public void showDialog(Context context,final int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.card_setting_modify_dialog,null);
        final EditText editText = (EditText) view.findViewById(R.id.edit);

        if(type==0){
            if(editText.getText().toString()=="")
                editText.setHint("상태 메세지를 입력하세요");
            else editText.setText(String.valueOf(inputDescription));
        }else if(type==1){
            if(editText.getText().toString()=="")
                editText.setHint("소속을 입력하세요");
            else editText.setText(String.valueOf(inputCompany));
        }else if(type==2){
            if(editText.getText().toString()=="")
                editText.setHint("위치를 입력하세요");
            else editText.setText(String.valueOf(inputPosition));
        }else if(type==3){
            if(editText.getText().toString()=="")
                editText.setHint("전화번호를 입력하세요");
            else editText.setText(String.valueOf(inputPhoneNumber));
        }else if(type==4){
            if(editText.getText().toString()=="")
                editText.setHint("이메일을 입력하세요");
            else editText.setText(String.valueOf(inputEmail));
        }else if(type==5){
            if(editText.getText().toString()=="")
                editText.setHint("이름을 입력하세요");
            else editText.setText(String.valueOf(inputName));
        }else if(type==6){
            editText.setClickable(false);
            editText.setText("정말로 삭제하시겠습니까?");
        }
        builder.setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> stringObjectMap = new HashMap<>();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(type==0){
                    stringObjectMap.put("inputDescription",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_des.setText(editText.getText().toString());
                }else if(type==1){
                    stringObjectMap.put("inputCompany",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_com.setText(editText.getText().toString());
                }else if(type==2){
                    stringObjectMap.put("inputPosition",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_pos.setText(editText.getText().toString());
                }else if(type==3){
                    stringObjectMap.put("inputEmail",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_pho.setText(editText.getText().toString());
                }else if(type==4){

                    stringObjectMap.put("inputDescription",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_email.setText(editText.getText().toString());
                }else if(type==5){
                    stringObjectMap.put("inputName",editText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Card").child(card_key).updateChildren(stringObjectMap);
                    edit_name.setText(editText.getText().toString());
                }else if(type==6){
                    delete_content();
                    finish();
                    overridePendingTransition(R.anim.fromleft,R.anim.toright);
                }

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    @Override
    public void onStop(){
        super.onStop();
        overridePendingTransition(R.anim.fromleft,R.anim.toright);
    }
    private void delete_content(){
        storage.getReference().child("Users/"+auth.getCurrentUser().getUid()).child(imageName)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/"+"Card/")
                        .child(card_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {

                        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    if (snapshot.getKey().equals("main_card") && snapshot.getValue().equals(card_key)){
                                        Map<String,Object>  card1= new HashMap<>();
                                        card1.put("main_card", false);
                                        database.getReference().child("Users/" + auth.getCurrentUser().getUid()).updateChildren(card1);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Map<String,Object>  card= new HashMap<>();
                        card.put("card_num",card_num);
                        database.getReference().child("Users/" + auth.getCurrentUser().getUid()).updateChildren(card);
                        Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"test!!! 삭제완료");
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Group/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String name = snapshot.getKey();
                    group_list1.add(name);//나의 그룹의 갯수
                }
                int size = group_list1.size();//All

                for(int i=0;i<size;i++){//i=1
                    final int t=i;
                    database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Group/"+group_list1.get(i)+"/friends").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String name = snapshot.getKey();
                                delete_list.add(name);
                            }

                            for (int i = 0; i < delete_list.size(); i++) {//해당 그룹안에 있는 ID갯수 = 1

                                final int t = i;
                                group_list2.clear();
                                database.getReference().child("Users/" + delete_list.get(i) + "/Group").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String name = snapshot.getKey();
                                            if(group_list2.size()==0){
                                                group_list2.add(name);//상대 ID의 그룹 갯수 계산

                                            }else {
                                                for(int check=0;check<group_list2.size();check++){
                                                    if(!name.equals(group_list2.get(check))){
                                                        group_list2.add(name);//상대 ID의 그룹 갯수 계산
                                                    }
                                                }
                                            }

                                        }
                                        for (int i = 0; i < group_list2.size(); i++) {
                                            final int t2=i;
                                            database.getReference().child("Users/" + delete_list.get(t) + "/Group/" + group_list2.get(i) + "/friends").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        if (snapshot.getValue().equals(card_key)) {
                                                            database.getReference().child("Users/" + delete_list.get(t) + "/Group/" + group_list2.get(t2) + "/friends/"+auth.getCurrentUser().getUid())
                                                                    .removeValue();
                                                            database.getReference().child("Users/"+delete_list.get(t)+"/Group/"+group_list2.get(t2)).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                                                        long m_num;
                                                                        Map<String, Object> m_num_put = new HashMap<>();
                                                                        if(snapshot1.getKey().equals("m_num")){
                                                                            m_num=(long)snapshot1.getValue();
                                                                            m_num-=1L;
                                                                            Log.d(TAG,"test : "+m_num);
                                                                            m_num_put.put("m_num",m_num);
                                                                            database.getReference().child("Users/"+delete_list.get(t)+"/Group/"+group_list2.get(t2)).updateChildren(m_num_put);
                                                                        }
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });


                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
