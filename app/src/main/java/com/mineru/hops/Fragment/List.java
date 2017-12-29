package com.mineru.hops.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mineru.hops.Function.AddGroup.AddGroup1;
import com.mineru.hops.Function.Code_Scanner;
import com.mineru.hops.Function.Hopping2;
import com.mineru.hops.UserManage.Model.ImageDTO;
import com.mineru.hops.Function.Searching_friends;
import com.mineru.hops.MessageBoard;
import com.mineru.hops.R;

import java.util.ArrayList;

/**
 * Created by Mineru on 2017-09-08.
 */

public class List extends Fragment {
    private static final String TAG ="ListFragment";

    private GridView gridView;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    public CardView cardView;
    public EditText item_editText;

    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabQr,fabHopping,fabGroup;

    private java.util.List<ImageDTO> imageDTOs = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_fragment_layout,container,false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        cardView = (CardView) view.findViewById(R.id.card_layout);
        item_editText = (EditText) view.findViewById(R.id.editSearch);
        //gridView = (GridView) view.findViewById(R.id.gridview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();

        recyclerView.setAdapter(boardRecyclerViewAdapter);

        item_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Searching_friends.class);
                startActivity(intent);
            }
        });
        fabQr = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_qr);
        fabHopping = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_hopping);
        fabGroup = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_group);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_plus_list);

        fabQr.setOnClickListener(onButtonClick());
        fabGroup.setOnClickListener(onButtonClick());
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


        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    imageDTOs.add(imageDTO);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();

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
                else if(view == fabGroup){
                    Intent intent = new Intent(getActivity(),AddGroup1.class);
                    startActivity(intent);
                }
                fam.close(true);
            }
        };
    }
    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).item_textView1.setText(imageDTOs.get(position).inputName);
            ((CustomViewHolder)holder).item_textView2.setText(imageDTOs.get(position).inputDescription);
            Glide.with(holder.itemView.getContext()).load(imageDTOs.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), MessageBoard.class);
                    intent.putExtra("Uid",imageDTOs.get(position).uid);
                    intent.putExtra("imageUrl",imageDTOs.get(position).imageUrl);
                    intent.putExtra("inputName",imageDTOs.get(position).inputName);

                    ActivityOptions activityOptions = null;
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                        activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.fromright,R.anim.toleft);
                        startActivity(intent,activityOptions.toBundle());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return imageDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView item_textView1;
            TextView item_textView2;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                item_textView1 = (TextView) view.findViewById(R.id.item_textView1);
                item_textView2 = (TextView) view.findViewById(R.id.item_textView2);
            }
        }
    }
}


