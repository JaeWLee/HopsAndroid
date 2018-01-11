package com.mineru.hops.Function;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mineru.hops.R;

import static android.content.ContentValues.TAG;

/**
 * Created by mineru on 2018-01-11.
 */

public class CardDialog_List_Delete extends Dialog {
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private FirebaseAuth auth;

    public String imageName;
    public String group_name;
    public TextView btn_ok;
    public TextView btn_no;
    public RelativeLayout touch_outside;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.card_dialog_delete);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        touch_outside = (RelativeLayout) findViewById(R.id.touch_outside) ;
        touch_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        btn_no = (TextView) findViewById(R.id.btn_no);
        btn_ok = (TextView) findViewById(R.id.btn_ok);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.getReference().child("Users/"+auth.getCurrentUser().getUid()).child("Group").child(imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).child("Group/"+group_name).removeValue();
                        cancel();
                    }
                });
            }
        });
    }
    public CardDialog_List_Delete(Context context,String imageName,String group_name) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.imageName=imageName;
        this.group_name=group_name;
        Log.d(TAG,"tset : "+group_name +" : "+imageName);
    }
}
