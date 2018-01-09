package com.mineru.hops.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mineru.hops.Function.CardDialog_Home;
import com.mineru.hops.Function.CardTouchDialog;
import com.mineru.hops.Function.Card_Setting_Modify;
import com.mineru.hops.UserManage.Model.ImageDTO;
import com.mineru.hops.Function.MakeCard.MakeCard1;
import com.mineru.hops.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Mineru on 2017-09-08.
 */

public class Home extends Fragment {
    private static final String TAG ="HomeFragment";
    private CardDialog_Home mCardDialog;

    private CardTouchDialog mCardTouchDialog;
    private RecyclerView recyclerView;
    private long card_num=3L;
    private List<ImageDTO> imageDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,container,false);
        setHasOptionsMenu(true);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.home_fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);


        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).orderByValue()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if(snapshot.getKey().equals("card_num")){
                                card_num= (long) snapshot.getValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        fabAdd = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_add);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_plus_home);

        fabAdd.setOnClickListener(onButtonClick());
        fam.setClosedOnTouchOutside(true);

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });
        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/"+"Card/")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                imageDTOs.clear();
                uidLists.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    String uidKey = snapshot.getKey();
                    imageDTOs.add(imageDTO);
                    uidLists.add(uidKey);
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
                if (view == fabAdd) {
                    Toast.makeText(getContext(), "test : "+card_num, Toast.LENGTH_SHORT).show();
                    if(card_num>=3L){
                        Toast.makeText(getContext(), "더 이상 카드를 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        fam.close(true);
                    }
                    else if(card_num<3L){
                        Intent intent = new Intent(getActivity(), MakeCard1.class);
                        startActivity(intent);
                        fam.close(true);
                    }
                }
            }
        };
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


            ((CustomViewHolder)holder).textView.setText(imageDTOs.get(position).inputName);
            ((CustomViewHolder)holder).textView2.setText(imageDTOs.get(position).inputCompany);
            ((CustomViewHolder)holder).textView3.setText(imageDTOs.get(position).inputPosition);

            ((CustomViewHolder) holder).btnFront.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    Intent intent = new Intent(getContext(), Card_Setting_Modify.class);
                    intent.putExtra("card_key",imageDTOs.get(position).card_key);
                    intent.putExtra("mainImageUrl",imageDTOs.get(position).imageUrl);
                    intent.putExtra("inputName",imageDTOs.get(position).inputName);
                    intent.putExtra("inputDescription",imageDTOs.get(position).inputDescription);
                    intent.putExtra("inputCompany",imageDTOs.get(position).inputCompany);
                    intent.putExtra("inputPosition",imageDTOs.get(position).inputPosition);
                    intent.putExtra("inputPhoneNumber",imageDTOs.get(position).inputPhoneNumber);
                    intent.putExtra("inputEmail",imageDTOs.get(position).inputEmail);
                    intent.putExtra("imageName",imageDTOs.get(position).imageName);
                    intent.putExtra("card_num",card_num);
                    ActivityOptions activityOptions = null;
                    activityOptions = ActivityOptions.makeCustomAnimation(getContext(),R.anim.fromright,R.anim.toleft);
                    startActivity(intent,activityOptions.toBundle());

                    return false;
                }
            });
            ((CustomViewHolder) holder).btnFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    mCardDialog = new CardDialog_Home(getActivity(),imageDTOs.get(position).imageUrl,imageDTOs.get(position).inputName, imageDTOs.get(position).inputCompany,
                            imageDTOs.get(position).inputPosition,imageDTOs.get(position).inputDescription,imageDTOs.get(position).inputPhoneNumber,imageDTOs.get(position).uid);
                    mCardDialog.show();
                }
            });

            ((CustomViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    mCardTouchDialog = new CardTouchDialog(getActivity(),imageDTOs.get(position).imageUrl);
                    mCardTouchDialog.show();
                }
            });

            ((CustomViewHolder)holder).mCallbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), imageDTOs.get(position).inputPhoneNumber, Toast.LENGTH_SHORT).show();
                }
            });

            ((CustomViewHolder)holder).mMailbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), imageDTOs.get(position).inputPhoneNumber, Toast.LENGTH_SHORT).show();
                }
            });


            Glide.with(holder.itemView.getContext())
                    .load(imageDTOs.get(position).imageUrl)
                    .into(((CustomViewHolder)holder).imageView);

            ((CustomViewHolder)holder).pinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.getReference().child("Users/"+auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                if(snapshot.getKey().equals("main_card")&&snapshot.getValue().equals(false)) {
                                    onStarClicked(database.getReference().child("Users/" + auth.getCurrentUser().getUid() + "/" + "Card").child(uidLists.get(position)));
                                }
                                Log.d(TAG, "test : " + snapshot.getValue() + " test 2 : " + imageDTOs.get(position).card_key);
                                if(snapshot.getKey().equals("main_card")&&snapshot.getValue().equals(String.valueOf(imageDTOs.get(position).card_key))){
                                    Log.d(TAG, "test : " + snapshot.getKey() + " test 2 : " + imageDTOs.get(position).card_key);
                                    onStarClicked(database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/"+"Card").child(uidLists.get(position)));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

            if (imageDTOs.get(position).cardpins.containsKey(auth.getCurrentUser().getUid())) {
                ((CustomViewHolder)holder).pinButton.setImageResource(R.drawable.card_pin);

                database.getReference().child("Users").child(auth.getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    if(snapshot.getKey().equals("main_card")&&snapshot.getValue().equals(false)) {
                                        Map<String, Object> stringObjectMap = new HashMap<>();
                                        stringObjectMap.put("imageUrl", imageDTOs.get(position).imageUrl);
                                        stringObjectMap.put("inputCompany", imageDTOs.get(position).inputCompany);
                                        stringObjectMap.put("inputDescription", imageDTOs.get(position).inputDescription);
                                        stringObjectMap.put("inputName", imageDTOs.get(position).inputName);
                                        stringObjectMap.put("inputPhoneNumber", imageDTOs.get(position).inputPhoneNumber);
                                        stringObjectMap.put("inputPosition", imageDTOs.get(position).inputPosition);
                                        stringObjectMap.put("inputEmail", imageDTOs.get(position).inputEmail);
                                        stringObjectMap.put("uid", imageDTOs.get(position).uid);
                                        database.getReference().child("Users/" + auth.getCurrentUser().getUid() + "/Main/Main").updateChildren(stringObjectMap);
                                        Map<String,Object> main_card = new HashMap<>();
                                        main_card.put("main_card",imageDTOs.get(position).card_key);
                                        database.getReference().child("Users/"+auth.getCurrentUser().getUid()).updateChildren(main_card);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

            }else{
                ((CustomViewHolder)holder).pinButton.setImageResource(R.drawable.icon_cardpin_off);
                database.getReference().child("Users").child(auth.getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    if(snapshot.getKey().equals("main_card")&&snapshot.getValue().equals(imageDTOs.get(position).card_key)){
                                        database.getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Main/Main").removeValue();
                                        Map<String,Object> test = new HashMap<>();
                                        test.put("main_card",false);
                                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).updateChildren(test);

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
        public int getItemCount() {
            return imageDTOs.size();
        }

        private void onStarClicked(DatabaseReference postRef) {

            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDTO imageDTO = mutableData.getValue(ImageDTO.class);
                    if (imageDTO == null) {
                        return Transaction.success(mutableData);
                    }

                    if (imageDTO.cardpins.containsKey(auth.getCurrentUser().getUid())) {
                        // Unstar the post and remove self from stars
                        imageDTO.cardpinCount = imageDTO.cardpinCount - 1;
                        imageDTO.cardpins.remove(auth.getCurrentUser().getUid());

                    } else {
                        // Star the post and add self to stars
                        imageDTO.cardpinCount = imageDTO.cardpinCount + 1;
                        imageDTO.cardpins.put(auth.getCurrentUser().getUid(), true);
                    }
                    // Set value and report transaction success
                    mutableData.setValue(imageDTO);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                }
            });
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ImageView pinButton;
            TextView textView;
            TextView textView2;
            TextView textView3;

            RelativeLayout btnFront;
            ImageButton mCallbtn;
            ImageButton mMailbtn;


            public CustomViewHolder(View view) {
                super(view);
                btnFront = (RelativeLayout) view.findViewById(R.id.cardLayer);
                mCallbtn = (ImageButton) view.findViewById(R.id.imgBtn1);
                mMailbtn = (ImageButton) view.findViewById(R.id.imgBtn2);


                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);
                textView3 = (TextView) view.findViewById(R.id.item_textView3);
                pinButton = (ImageView) view.findViewById(R.id.card_pin);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }
}