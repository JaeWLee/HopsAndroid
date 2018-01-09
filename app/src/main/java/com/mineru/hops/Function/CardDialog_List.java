package com.mineru.hops.Function;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mineru.hops.MessageBoard;
import com.mineru.hops.R;

public class CardDialog_List extends Dialog {
    public RelativeLayout relativeLayout1;
    public RelativeLayout relativeLayout2;
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mCompanyView;
    private TextView mPositionView;
    private TextView mDescriptionView;
    private ImageButton mBtn1;
    private ImageButton mBtn2;
    private ImageButton mBtn3;
    private String mUid;
    private String mDescription;
    private String mCompany;
    private String mPosition;
    private String mName;
    private String mImageUrl;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.card_dialog);


        relativeLayout1 = (RelativeLayout) findViewById(R.id.touch_outside);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


        relativeLayout2 = (RelativeLayout) findViewById(R.id.card_touch_outside2);
        relativeLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        setLayout();
        Glide.with(getContext())
                .load(mImageUrl)
                .apply(new RequestOptions().circleCrop())
                .into(mImageView);
        setDescription(mDescription);
        setPosition(mPosition);
        setCompany(mCompany);
        setName(mName);
        mBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageBoard.class);
                intent.putExtra("Uid",mUid);
                intent.putExtra("imageUrl",mImageUrl);
                intent.putExtra("inputName",mName);

                ActivityOptions activityOptions = null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                    activityOptions = ActivityOptions.makeCustomAnimation(getContext(),R.anim.fromright,R.anim.toleft);
                    getContext().startActivity(intent,activityOptions.toBundle());
                }
                cancel();
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("smsto:"+mPhoneNumber);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Hops Message Test");
                getContext().startActivity(it);
                cancel();
            }
        });
        mBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+mPhoneNumber)));
                cancel();
            }
        });
    }

    public CardDialog_List(Context context, String imageUrl, String inputName, String inputCompany, String inputPosition, String inputDescription, String inputPhoneNumber, String uid) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mUid = uid;
        this.mDescription = inputDescription;
        this.mImageUrl = imageUrl;
        this.mName = inputName;
        this.mCompany = inputCompany;
        this.mPosition = inputPosition;
        this.mPhoneNumber = inputPhoneNumber;
    }
    private void setDescription(String mDescription){
        mDescriptionView.setText(mDescription);
    }

    private void setName(String inputName) {
        mNameView.setText(inputName);
    }

    private void setCompany(String inputCompany) {
        mCompanyView.setText(inputCompany);
    }

    private void setPosition(String inputPosition) {
        mPositionView.setText(inputPosition);
    }

    private void setLayout(){
        mImageView = (ImageView) findViewById(R.id.card_iv_image);
        mNameView = (TextView) findViewById(R.id.card_tv_name);
        mCompanyView = (TextView) findViewById(R.id.card_tv_company);
        mPositionView = (TextView) findViewById(R.id.card_tv_position);
        mDescriptionView = (TextView) findViewById(R.id.card2_tv_description);
        mBtn1 = (ImageButton) findViewById(R.id.card_imgBtn1);
        mBtn2 = (ImageButton) findViewById(R.id.card_imgBtn2);
        mBtn3 = (ImageButton) findViewById(R.id.card_imgBtn3);

    }
}