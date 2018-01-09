package com.mineru.hops.Fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mineru.hops.Function.CardDialog_List;
import com.mineru.hops.Function.Code_Scanner;
import com.mineru.hops.Function.Hopping2;
import com.mineru.hops.UserManage.Model.Group_model;
import com.mineru.hops.Function.Searching_friends;
import com.mineru.hops.R;
import com.mineru.hops.UserManage.Model.ImageDTO;

import java.util.ArrayList;

/**
 * Created by Mineru on 2017-09-08.
 */

public class List extends Fragment {
    private static final String TAG ="ListFragment";

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private BoardRecyclerViewAdapter mAdapter;
    private TestAdapter mAdapter2;
    private GridLayoutManager recyclerManager = new GridLayoutManager(getContext(),2);
    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabQr,fabHopping;

    private CardDialog_List mCardDialog;
    public TextView tv_group_title;
    public ImageView searching_btn;
    public ImageView back_btn;
    public java.util.List<Group_model> group_models =new ArrayList<>();
    public java.util.List<String> list_key = new ArrayList<>();
    public java.util.List<String> list_value = new ArrayList<>();
    public java.util.List<ImageDTO> imageDTOs = new ArrayList<>();
    public String str_title="All";
    public int m_num;
    public int test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_fragment_layout,container,false);
        test=0;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        recyclerManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position){
                if(position==0) return 2;
                return 1;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        mAdapter = new BoardRecyclerViewAdapter();
        mAdapter2 = new TestAdapter();

        recyclerView.setAdapter(mAdapter);

        searching_btn = (ImageView) view.findViewById(R.id.search_btn);
        back_btn = (ImageView) view.findViewById(R.id.back_btn);
        tv_group_title = (TextView) view.findViewById(R.id.tv_group_title);
        tv_group_title.setText("");

        searching_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Searching_friends.class);
                intent.putExtra("title",str_title);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test==1){
                    back_btn.setImageResource(R.drawable.img_home);
                    recyclerManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                        @Override
                        public int getSpanSize(int position){
                            if(position==0) return 2;
                            return 1;
                        }
                    });
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    mAdapter = new BoardRecyclerViewAdapter();
                    recyclerView.setAdapter(mAdapter);
                    test=0;
                    tv_group_title.setText("");
                }
            }
        });

        fabQr = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_qr);
        fabHopping = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_hopping);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_plus_list);

        fabQr.setOnClickListener(onButtonClick());
        fabHopping.setOnClickListener(onButtonClick());
        fam.setClosedOnTouchOutside(true);

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        //FirebaseDatabase.getInstance().getReference("Users").keepSynced(true);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Group")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        group_models.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Group_model group_model = snapshot.getValue(Group_model.class);
                            group_models.add(group_model);
                        }
                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        return view;
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabQr) {
                    Intent intent = new Intent(getActivity(), Code_Scanner.class);
                    startActivity(intent);
                }
                else if(view == fabHopping){
                    Intent intent = new Intent(getActivity(), Hopping2.class);
                    startActivity(intent);
                }
                fam.close(true);
            }
        };
    }

    public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);

            return new TestAdapter.CustomViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).tv_name.setText(String.valueOf(imageDTOs.get(position).inputName));
            ((CustomViewHolder)holder).tv_description.setText(String.valueOf(imageDTOs.get(position).inputDescription));
            ((CustomViewHolder)holder).cardLayer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mCardDialog = new CardDialog_List(getActivity(),imageDTOs.get(position).imageUrl,imageDTOs.get(position).inputName, imageDTOs.get(position).inputCompany,
                            imageDTOs.get(position).inputPosition,imageDTOs.get(position).inputDescription,imageDTOs.get(position).inputPhoneNumber,imageDTOs.get(position).uid);
                    mCardDialog.show();
                }
            });

            Glide.with(holder.itemView.getContext())
                    .load(imageDTOs.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).item_imageView);

        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
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



    public class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).group_title.setText(group_models.get(position).group_name);
            ((CustomViewHolder)holder).group_number.setText(String.valueOf(group_models.get(position).m_num)+"개의 Hops");

            ((CustomViewHolder)holder).list_layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    m_num = (group_models.get(position).m_num);
                    str_title = (String.valueOf(group_models.get(position).group_name));
                    if(test==0) {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        mAdapter2 = new TestAdapter();
                        recyclerView.setAdapter(mAdapter2);
                        test = 1;
                        back_btn.setImageResource(R.drawable.image_failed);
                        tv_group_title.setText(String.valueOf(str_title)+"("+m_num+"명)");
                        database.getReference().child("Users/" + auth.getCurrentUser().getUid() + "/Group/" + str_title + "/friends").orderByValue()
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
                                            database.getReference().child("Users/" + list_key.get(i)+"/Card").orderByKey().equalTo(list_value.get(i))
                                                    .addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                                                                imageDTOs.add(imageDTO);
                                                            }
                                                            mAdapter2.notifyDataSetChanged();
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                        }

                                        mAdapter2.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                    }

                }
            });
            Glide.with(holder.itemView.getContext())
                    .load(group_models.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).list_image);

        }

        @Override
        public int getItemCount() {
            return group_models.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView list_image;
            LinearLayout list_layout;
            TextView group_title;
            TextView group_number;
            GradientDrawable drawable;

            public CustomViewHolder(View view) {
                super(view);
                drawable = (GradientDrawable) view.getContext().getDrawable(R.drawable.round_view2);
                list_layout = (LinearLayout) view.findViewById(R.id.list_layout);
                list_image = (ImageView) view.findViewById(R.id.list_image);
                list_image.setBackground(drawable);
                list_image.setClipToOutline(true);
                group_title = (TextView) view.findViewById(R.id.group_title);
                group_number = (TextView) view.findViewById(R.id.group_number);
            }
        }
    }

}