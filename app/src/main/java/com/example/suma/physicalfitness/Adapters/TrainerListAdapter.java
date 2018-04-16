package com.example.suma.physicalfitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suma.physicalfitness.R;


import org.json.JSONObject;

import java.util.List;


/* @method name : DrawerListAdapter
       * description : This method provide data to the home list activity
       * @author : Abhishek
       * reviewer : T. Anil Kumar
       */

public class TrainerListAdapter extends BaseAdapter  {
    Context context;
    JSONObject cusDetails;


    public TrainerListAdapter(Context context,JSONObject cusDet) {
        this.context = context;
        this.cusDetails= cusDet;

    }
    @Override
    public int getCount() {
        return cusDetails.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_trainer_home, parent, false);

        ImageView drawerImgView = (ImageView) convertView.findViewById(R.id.level_module_image);
        ImageView drawerImgView1 = (ImageView) convertView.findViewById(R.id.image_module_icon);

        TextView homeContentText = (TextView) convertView.findViewById(R.id.text_module_title);





        return convertView;
    }

}
