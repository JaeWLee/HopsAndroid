package com.mineru.hops.Function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mineru.hops.R;
import com.mineru.hops.UserManage.Model.ImageDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mineru on 2017-09-15.
 */

public class Searching_friends extends Activity {
    private static final String TAG ="Searching_friends";
    private TextView tv_c;
    private RecyclerView recyclerView;
    private SearchingViewAdapter mAdapter;
    public EditText edit_searching;
    public String searching_group_name;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public List<String> list_key = new ArrayList<>();
    public List<String> list_value = new ArrayList<>();
    public List<ImageDTO> imageDTOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_friends);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        tv_c = (TextView) findViewById(R.id.tv_cancel);
        edit_searching = (EditText) findViewById(R.id.edit_searching);
        recyclerView = (RecyclerView) findViewById(R.id.searching_recycler);
        edit_searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG,"test1 : "+list_key.size());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG,"test2 : "+list_key.size());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                for(int i=0;i<list_key.size();i++) {

                    database.getReference().child("Users/" + list_key.get(i)+"/Card")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        if(snapshot.getValue().equals("")&&snapshot.getValue().equals("inputName")){
                                            ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                                            imageDTOs.add(imageDTO);
                                        }

                                    }
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }
        });
        Intent intent =getIntent();
        searching_group_name=intent.getStringExtra("title");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchingViewAdapter();
        recyclerView.setAdapter(mAdapter);

        tv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
            }
        });


    }

    private class SearchingViewAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);

            return new SearchingViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).tv_name.setText(String.valueOf(imageDTOs.get(position).inputName));
            ((CustomViewHolder)holder).tv_description.setText(String.valueOf(imageDTOs.get(position).inputDescription));

            Glide.with(holder.itemView.getContext())
                    .load(imageDTOs.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).item_imageView);
        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView item_imageView;
            RelativeLayout cardLayer;
            GradientDrawable drawable;
            TextView tv_name;
            TextView tv_description;

            public CustomViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_description = (TextView) view.findViewById(R.id.tv_description);
                cardLayer = (RelativeLayout) view.findViewById(R.id.cardLayer);
                item_imageView = (ImageView) view.findViewById(R.id.item_imageView);
                drawable = (GradientDrawable) view.getContext().getDrawable(R.drawable.round_view);
                item_imageView.setBackground(drawable);
                item_imageView.setClipToOutline(true);
            }
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Group/"+searching_group_name+"/friends").orderByValue()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list_key.clear();
                        list_value.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            String val = snapshot.getValue().toString();
                            int size = list_key.size();
                            int identy = 0,t1=2, t2=0;
                            for(int i =0;i<size+1;i++){
                                if(list_key.size()==0){
                                    list_key.add(key);
                                    list_value.add(val);
                                }
                                else{
                                    while(t2<size){
                                        if(key==list_key.get(t2).toString()){
                                            t1=3;
                                            t2+=1;
                                            break;
                                        }
                                        if (key!=list_key.get(t2).toString()){
                                            identy+=1;
                                            t2+=1;
                                            if(t2==size&&identy%2==1&&t1==2){
                                                list_key.add(key);
                                                list_value.add(val);
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        imageDTOs.clear();
                        for(int i=0;i<list_key.size();i++) {
                            final int t=i;
                            database.getReference().child("Users/" + list_key.get(i)+"/Card").orderByKey().equalTo(list_value.get(i))
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                                                imageDTOs.add(imageDTO);
                                            }
                                            mAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
