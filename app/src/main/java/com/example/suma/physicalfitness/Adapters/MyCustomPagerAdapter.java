package com.example.suma.physicalfitness.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.suma.physicalfitness.R;

/**
 * Created by avinash on 21/5/18.
 */

public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    String images[];
    LayoutInflater layoutInflater;


    public MyCustomPagerAdapter(Context context, String images[]) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

                if(images[position].equalsIgnoreCase("suat")) {
                    imageView.setImageResource(R.drawable.suat);
        }else if(images[position].equalsIgnoreCase("Leg press"))
        {
            imageView.setImageResource(R.drawable.legpress);

        }else if(images[position].equalsIgnoreCase("Deadlift"))
        {
            imageView.setImageResource(R.drawable.deadlift);

        }else if(images[position].equalsIgnoreCase("Leg extension"))
        {
            imageView.setImageResource(R.drawable.legpress);
        }else
        {
            imageView.setImageResource(R.drawable.legcurl);
        }


        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}