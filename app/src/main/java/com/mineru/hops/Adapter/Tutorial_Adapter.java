package com.mineru.hops.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mineru.hops.R;

public class Tutorial_Adapter extends PagerAdapter{

    Context context;
    int[] imageId = {R.drawable.tutorial1, R.drawable.tutorial2, R.drawable.tutorial3, R.drawable.tutorial4};
    String[] stringTitle = {"Sustainability","Together","Actionfor society","Group","Regional-Trust building"};
    String[] stringExplain = {"다양한 스티커와 테마 디자인으로\n센스잇는 채팅을","HOPS친구들과 함께 음성통화,영상통화를 마음껏","다양한 표현이 가능한 스티커와 테마 기능",
            "그룹으로 나누는 우리만의 이야기","모바일과 pc를 연동으로 더욱 편리한 채팅을"};

    public Tutorial_Adapter(Context context){
        this.context = context;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.tutorial_item, container, false);

        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        imageView.setImageResource(imageId[position]);

        TextView textView1 = (TextView) viewItem.findViewById(R.id.textView1);
        textView1.setText(stringTitle[position]);
        TextView textView2 = (TextView) viewItem.findViewById(R.id.textView2);
        textView2.setText(stringExplain[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}
