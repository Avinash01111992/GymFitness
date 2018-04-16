package com.example.suma.physicalfitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suma.physicalfitness.R;


/* @method name : DrawerListAdapter
       * description : This method provide data to the drawerlist
       * @author : Avinash
       * reviewer : T. Anil Kumar
       */
public class DrawerListAdapter extends BaseAdapter  {
    Context context;
    String userSelectedLanguage;
    String[] navMenuContents;


    private String[] listIds;

    public DrawerListAdapter(Context context,  String[] navMenuContent ) {
        this.context = context;
        this.navMenuContents = navMenuContent;

    }

    @Override
    public int getCount() {
        return navMenuContents.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);

        TextView drawerText = (TextView) convertView.findViewById(R.id.text_drawer);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.drawer_img);

        drawerText.setText(navMenuContents[position]);

        return convertView;
    }
}
