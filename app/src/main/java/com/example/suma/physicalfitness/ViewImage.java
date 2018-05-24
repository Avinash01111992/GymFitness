package com.example.suma.physicalfitness;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.suma.physicalfitness.Adapters.MyCustomPagerAdapter;

public class ViewImage extends AppCompatActivity {

    ImageView image;
    String  excType;
    String imageName;
    ViewPager viewPager;
    String[] images;
    int[] imageSource;
    //        int images[] = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};
    MyCustomPagerAdapter myCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        image = (ImageView)findViewById(R.id.image);


        Bundle bundle = getIntent().getExtras();
        excType = bundle.getString("excType");

        Log.e("excTypeImage123",excType);

        images = excType.split("[,]");

        Log.e("imagesLen",Integer.toString(images.length));








            viewPager = (ViewPager)findViewById(R.id.viewPager);

            myCustomPagerAdapter = new MyCustomPagerAdapter(ViewImage.this, images);
            viewPager.setAdapter(myCustomPagerAdapter);











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
