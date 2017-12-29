package com.mineru.hops.UserManage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mineru.hops.R;
import com.mineru.hops.UserManage.Model.DefaultUser;
import com.mineru.hops.UserManage.Model.User;
import com.mineru.hops.UserManage.Model.UserAccountSettings;

/**
 * Created by rmstj on 2017-10-05.
 */

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;


        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkItUsernameExists(String username, DataSnapshot dataSnapshot){
        Log.d(TAG,"checkItUsernameExists: checking if"+username + " already exists.");
        User user = new User();
        DefaultUser d_user = new DefaultUser();
        UserAccountSettings u_setting = new UserAccountSettings();


        for(DataSnapshot ds: dataSnapshot.child(userID).getChildren()){
            Log.d(TAG,"checkItUsernameExists: datasnapshot: "+ ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG,"checkItUsernameExists: username: "+ user.getUsername());

            if(StringMainpulation.expandUsername(user.getUsername()).equals(username)){
                Log.d(TAG,"checkItUsernameExists: FOUND A MATCH: "+ user.getUsername());
                return true;
            }
        }
        return false;
    }
    public void registerNewEmail(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if(!task.isSuccessful()){
                    Toast.makeText(mContext, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                }
                else if(task.isSuccessful()){
                    sendVerificationEmail();

                    userID = mAuth.getCurrentUser().getUid();
                    Log.d(TAG,"onComplete: Authstate changed "+userID);
                }
            }
        });
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void addNewUSer(String email, String username)
    {
        UserAccountSettings settings = new UserAccountSettings(username,email);
        User user = new User(userID,email,StringMainpulation.condenseUsername(username));
        DefaultUser d_user = new DefaultUser(userID);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child("Group")
                .child("All")
                .setValue(d_user);
    }


}
