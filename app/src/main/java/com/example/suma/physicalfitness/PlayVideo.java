package com.example.suma.physicalfitness;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class PlayVideo extends AppCompatActivity {
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    String excType;
    String videoName;
    private ProgressDialog mProgressDialog;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
//        mProgressDialog.setCancelable(false);
        vv = (VideoView)findViewById(R.id.video);

        Bundle bundle = getIntent().getExtras();
        excType = bundle.getString("excType");

        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        if(excType.trim().equalsIgnoreCase("suat")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.suat;
        }else if(excType.trim().equalsIgnoreCase("Leg press"))
        {
            path = "android.resource://" + getPackageName() + "/" + R.raw.suat;

        }else if(excType.trim().equalsIgnoreCase("Deadlift"))
        {
         path = "android.resource://" + getPackageName() + "/" + R.raw.suat;

        }else if(excType.trim().equalsIgnoreCase("Leg extension"))
        {
            path = "android.resource://" + getPackageName() + "/" + R.raw.suat;
        }else
        {
            path = "android.resource://" + getPackageName() + "/" + R.raw.suat;
        }
        uri = Uri.parse(path);
        vv.setMediaController(mediacontroller);
        vv.setVideoURI(uri);
        vv.requestFocus();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Video");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                mProgressDialog.dismiss();
                vv.start();
            }
        });

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
