package com.mineru.hops.Function.AddGroup;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
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
    private List<Grid_Item_list> grid_item_lists= new ArrayList<>();
    private RecyclerView recyclerView1;
    private RecyclerGridViewAdapter gridAdapter1;
    public List<String> str = new ArrayList<>();
    private GridLayoutManager gridManager = new GridLayoutManager(getApplication(),2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_firends_dialog);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            str.add(str1);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_group_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
            Glide.with(holder.itemView.getContext())
                    .load(grid_item_lists.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).groupTitle.setText(str.get(position));

            ((CustomViewHolder)holder).groupNumber.setText(grid_item_lists.get(position).m_num+"개의 Hops");

        }

        @Override
        public int getItemCount() {
            return grid_item_lists.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            CheckBox btnCheck;
            LinearLayout main_layout;
            TextView groupTitle;
            TextView groupNumber;

            public CustomViewHolder(View view) {
                super(view);
                btnCheck = (CheckBox) view.findViewById(R.id.btnCheck);
                imageView = (ImageView) view.findViewById(R.id.grid_item_image);
                main_layout = (LinearLayout) view.findViewById(R.id.main_grid);
                groupTitle = (TextView) view.findViewById(R.id.group_name);
                groupNumber = (TextView) view.findViewById(R.id.group_number);
            }
        }
    }

}