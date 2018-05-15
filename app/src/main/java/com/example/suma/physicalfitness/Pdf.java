package com.example.suma.physicalfitness;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.example.suma.physicalfitness.pojo.Upload;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Pdf extends AppCompatActivity {

    Button pdfbtn;
    ImageView pdfimg;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Upload upload;
    String uName;
    String pdfUrl;
    private List<Upload> pdfList = new ArrayList<>();
    WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);



        pdfbtn = (Button)findViewById(R.id.btn_okay);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        progressBar.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        uName = bundle.getString("uName");
//        Log.e("userName",userNme);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("uploads");
        readPdfList();

        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                    @Override
                    public void run() {
                        try {

                            openPDF();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        // close this activity
                    }
                }, 1000);
            }
        });





        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pdf");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void readPdfList()
    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                upload = dataSnapshot.getValue(Upload.class);
                pdfList.add(upload);
//                if(upload.getName().equalsIgnoreCase(uName))
//                {
//                 pdfUrl =   upload.getUrl();
//                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    private void openPDF() throws IOException {

        Log.e("sizze",Integer.toString(pdfList.size()));

        for(int i = 0;i < pdfList.size();i++)
        {
            if(pdfList.get(i).getName().trim().equalsIgnoreCase(uName))
            {
                pdfUrl = pdfList.get(i).getUrl().toString();
            }
        }


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(pdfUrl));
        startActivity(intent);





    }



}
