package com.mineru.hops.Function.AddGroup;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mineru.hops.R;
import com.mineru.hops.UserManage.Model.Grid_Item_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mineru on 2018-01-03.
 */

public class Add_Firends_Group extends AppCompatActivity{
    private static final String TAG ="Add_Firends_Group";
    public Button btnFinish;
    public FirebaseDatabase database;
    public FirebaseAuth auth;
    public String uid;
    public String username;
    private List<Grid_Item_list> grid_item_lists= new ArrayList<>();

    private RecyclerView recyclerView1;
    private RecyclerGridViewAdapter gridAdapter1;
    private GridLayoutManager gridManager = new GridLayoutManager(getApplication(),2);

    public List<String> group_name = new ArrayList<>();
    public List<String> group_key= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_firends_group_activity);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        uid="3qULhidczVa1g31csTY4VaaiVRl2";
        username="임근석";
        //Intent intent = getIntent();
        //uid = intent.getExtras().getString("uid");
        //username = intent.getExtras().getString("inputName");

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i<group_key.size();i++){
                    String tmp = group_key.get(i).toString();
                    Log.d(TAG,"test : "+tmp +" test2 :"+username);
                    gridAdapter1.onStarClicked(database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Group/friends").child(tmp));
                }
                finish();
            }
        });


        recyclerView1 = (RecyclerView) findViewById(R.id.recycle1);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position){
                if(position==0) return 2;
                return 1;
            }
        });

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new GridLayoutManager(this,2));

        gridAdapter1 = new RecyclerGridViewAdapter();
        recyclerView1.setAdapter(gridAdapter1);

        database.getReference().child("Users/" + auth.getCurrentUser().getUid() + "/Group/")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        grid_item_lists.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Grid_Item_list imageDTO1 = snapshot.getValue(Grid_Item_list.class);
                            String str1 = snapshot.getKey();
                            grid_item_lists.add(imageDTO1);
                            group_name.add(str1);
                        }
                        gridAdapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }




    public class RecyclerGridViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friends_group_item1, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Glide.with(holder.itemView.getContext())
                    .load(grid_item_lists.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).groupTitle.setText(group_name.get(position));

            ((CustomViewHolder)holder).groupNumber.setText(grid_item_lists.get(position).m_num+"개의 Hops");

            ((CustomViewHolder)holder).main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CustomViewHolder)holder).result==0){
                        ((CustomViewHolder)holder).btnCheck.setChecked(true);
                        group_key.add(grid_item_lists.get(position).group_name);
                        ((CustomViewHolder)holder).result=1;
                        Log.d(TAG,"test : CheckOn");
                    }
                    else if(((CustomViewHolder)holder).result==1){
                        ((CustomViewHolder)holder).btnCheck.setChecked(false);
                        group_key.remove(grid_item_lists.get(position).group_name);
                        ((CustomViewHolder)holder).result=0;
                        Log.d(TAG,"test : CheckOff");
                    }

                }
            });
        }
        private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    Grid_Item_list test = mutableData.getValue(Grid_Item_list.class);
                    if (test == null) {
                        return Transaction.success(mutableData);
                    }

                    if (test.friends.containsKey(uid)!=true) {
                        // Star the post and add self to stars
                        test.m_num = test.m_num + 1;
                        test.friends.put(uid, username);//uid는 상대방 id값 ,card_key는 교환한 카드key값
                    }
                    else{
                        Log.d(TAG,group_key+"에 이미 등록 되었습니다.");
                        Toast.makeText(Add_Firends_Group.this, group_key+"에 이미 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    // Set value and report transaction success
                    mutableData.setValue(test);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                }
            });
        }

        @Override
        public int getItemCount() {
            return grid_item_lists.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            int result=0;
            ImageView imageView;
            LinearLayout main_layout;
            CheckBox btnCheck;
            TextView groupTitle;
            TextView groupNumber;
            GradientDrawable drawable;

            public CustomViewHolder(View view) {
                super(view);
                drawable = (GradientDrawable) view.getContext().getDrawable(R.drawable.round_view2);
                btnCheck = (CheckBox) view.findViewById(R.id.btnCheck);
                imageView = (ImageView) view.findViewById(R.id.grid_item_image);
                imageView.setBackground(drawable);
                imageView.setClipToOutline(true);
                main_layout = (LinearLayout) view.findViewById(R.id.main_grid);
                groupTitle = (TextView) view.findViewById(R.id.group_name);
                groupNumber = (TextView) view.findViewById(R.id.group_number);
            }
        }
    }
}