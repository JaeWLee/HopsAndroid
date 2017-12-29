package com.mineru.hops;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mineru on 2017-12-29.
 */


public class MyAdapter extends BaseAdapter {

    private Context context;
    private final String[] gridValues;

    public MyAdapter(Context context, String[ ] gridValues) {

        this.context        = context;
        this.gridValues     = gridValues;
    }

    @Override
    public int getCount() {
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);

        paint.setAlpha(70);

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate( R.layout.row , null);

            TextView textView1 = (TextView) gridView
                    .findViewById(R.id.group_name);
            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.group_number);

            textView1.setText(gridValues[position]);
            textView2.setText(gridValues[position]);

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            LinearLayout main = (LinearLayout) gridView.findViewById(R.id.main_grid);

            main.setBackgroundColor(paint.getColor());

            String arrLabel = gridValues[ position ];

            if (arrLabel.equals("Windows")) {

                imageView.setImageResource(R.drawable.a);

            } else if (arrLabel.equals("iOS")) {

                imageView.setImageResource(R.drawable.b);

            } else if (arrLabel.equals("Blackberry")) {

                imageView.setImageResource(R.drawable.a);

            } else {

                imageView.setImageResource(R.drawable.b);
            }

        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}