package com.mineru.hops.Function;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mineru.hops.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class CardTouchDialog extends Dialog {
    private ImageView mImageView;
    private String mImageUrl;
    public PhotoViewAttacher mAttacher;
    private RelativeLayout outSide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.home_image_dialog);

        setLayout();
        outSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        Glide.with(getContext())
                .load(mImageUrl)
                .into(mImageView);
        mAttacher = new PhotoViewAttacher(mImageView);
    }

    public CardTouchDialog(Context context, String imageUrl) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mImageUrl = imageUrl;
    }

    private void setLayout(){
        mImageView = (ImageView) findViewById(R.id.card_touch_iv_image);
        outSide = (RelativeLayout) findViewById(R.id.touch_outside);
    }
}