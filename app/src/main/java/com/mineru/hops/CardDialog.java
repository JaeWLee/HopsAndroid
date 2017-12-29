package com.mineru.hops;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CardDialog extends Dialog {
    public RelativeLayout relativeLayout1;
    public RelativeLayout relativeLayout2;
    private ImageView mImageView;
    private TextView mNameView;
    private TextView mCompanyView;
    private TextView mPositionView;
    private String mCompany;
    private String mPosition;
    private String mName;
    private String mImageUrl;

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


        relativeLayout2 = (RelativeLayout) findViewById(R.id.touch_outside2);
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
        setPosition(mPosition);
        setCompany(mCompany);
        setName(mName);
    }

    public CardDialog(Context context, String imageUrl, String inputName, String inputCompany, String inputPosition) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mImageUrl = imageUrl;
        this.mName = inputName;
        this.mCompany = inputCompany;
        this.mPosition = inputPosition;
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
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mCompanyView = (TextView) findViewById(R.id.tv_company);
        mPositionView = (TextView) findViewById(R.id.tv_position);
    }
}