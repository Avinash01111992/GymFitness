package com.example.suma.physicalfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {

    ImageView image;
    String  excType;
    String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        image = (ImageView)findViewById(R.id.image);


        Bundle bundle = getIntent().getExtras();
        excType = bundle.getString("excType");

        Log.e("excTypeImage123",excType);


        if(excType.trim().equalsIgnoreCase("suat")) {
            image.setImageResource(R.drawable.suat);
        }else if(excType.trim().equalsIgnoreCase("Leg press"))
        {
            image.setImageResource(R.drawable.legpress);

        }else if(excType.trim().equalsIgnoreCase("Deadlift"))
        {
            image.setImageResource(R.drawable.deadlift);

        }else if(excType.trim().equalsIgnoreCase("Leg extension"))
        {
            image.setImageResource(R.drawable.legpress);
        }else
        {
            image.setImageResource(R.drawable.legcurl);
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(excType);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
