package com.mineru.hops;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Card_Setting_Modify extends AppCompatActivity {

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

    public String card_key;
    public String inputName;
    public String inputDescription;
    public String inputEmail;
    public String inputCompany;
    public String inputPhoneNumber;
    public String inputPosition;
    public String mainImageUrl;
    public String backImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_setting_modify);
        Intent intent = getIntent();
        card_key = intent.getStringExtra("card_key");

        mainImageUrl = intent.getStringExtra("mainImageUrl");
        backImageUrl = intent.getStringExtra("backImageUrl");

        inputName = intent.getStringExtra("inputName");

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
    }

    public void setlayout(){
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
        View view = layoutInflater.inflate(R.layout.card_setting_dialog,null);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
        if(type==0){
            editText.setHint("상태 메세지를 입력하세요");
        }else if(type==1){
            editText.setHint("소속을 입력하세요");
        }else if(type==2){
            editText.setHint("위치를 입력하세요");
        }else if(type==3){
            editText.setHint("전화번호를 입력하세요");
        }else if(type==4){
            editText.setHint("이메일을 입력하세요");
        }else if(type==5){
            editText.setHint("이름을 입력하세요");
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
}
