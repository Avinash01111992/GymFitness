package com.example.suma.physicalfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.suma.physicalfitness.Adapters.DrawerListAdapter;
import com.example.suma.physicalfitness.pojo.CustomerTaskListPojo;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
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

public class CustomerHome extends AppCompatActivity {

    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    ListView mainMenuList;
    String title;
    ListView mDrawerList;
    Toolbar toolbar;
    String[] navContent = {"Report","Chat","Share","Logout"};
    TextView cusEXCTv,cusEQPTv,userName,BodyTypeTv;
    String excStr,eqpStr,userNameStr,BodyTypeStr;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String un;
    CustomerTaskListPojo customerTaskListPojo;
    Button gymImageBtn,gymVideoBtn;
    String excType;
    private List<CustomerTaskListPojo> cusTaskList = new ArrayList<>();
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        Bundle bundle = getIntent().getExtras();
        un = bundle.getString("userName");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("customertask");
        readDB();


        gymImageBtn = (Button)findViewById(R.id.gymImage);
        gymVideoBtn = (Button)findViewById(R.id.gymVideo);

        cusEXCTv = (TextView)findViewById(R.id.cus_exc);
        cusEQPTv = (TextView)findViewById(R.id.cus_eqp);
        BodyTypeTv = (TextView)findViewById(R.id.bodyType);
        userName = (TextView)findViewById(R.id.userNameTv);
        userName.setText(un);

//        setTask();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                setTask();

                // close this activity
            }
        }, SPLASH_TIME_OUT);






        gymImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateImage();
            }
        });
        gymVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateVideo();
            }
        });


//        setTask();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mainMenuList = (ListView) findViewById(R.id.list_item);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list_view);



        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            /* called when the slider is open */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /* called when the slider is closed */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        DrawerListAdapter drawerAdapter = new DrawerListAdapter(getApplication(), navContent);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    Intent intent = new Intent(CustomerHome.this,Pdf.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


                if(position==1)
                {
                    Intent mangCustomers  = new Intent(CustomerHome.this,Users.class);
//                    mangCustomers.putExtra("from","report");
                    startActivity(mangCustomers);
                }
                mDrawerLayout.closeDrawers();
            }
        });


    }

    private void openPDF(ImageView targetView) throws IOException {

        //open file in assets

        ParcelFileDescriptor fileDescriptor;

        String FILENAME = "sample_pdf.pdf";

        // Create file object to read and write on
        File file = new File(getApplicationContext().getCacheDir(), FILENAME);
        if (!file.exists()) {
            AssetManager assetManager = getApplicationContext().getAssets();
            copyAsset(assetManager, FILENAME, file.getAbsolutePath());
        }

        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

        PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        targetView.setImageBitmap(bitmap);
        rendererPage.close();
        pdfRenderer.close();
    }


    public static boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void validateImage()
    {
       excType = cusTaskList.get(0).getExcType().toString();
       Log.e("excTypeImage",excType);
       Intent image = new Intent(CustomerHome.this,ViewImage.class);
       image.putExtra("excType",excType);
       startActivity(image);
    }

    public void validateVideo()
    {
        excType = cusTaskList.get(0).getExcType().toString();
        Log.e("excTypeVideo",excType);
        Intent video = new Intent(CustomerHome.this,PlayVideo.class);
        video.putExtra("excType",excType);
        startActivity(video);
    }


    public void readDB()

    {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                customerTaskListPojo = dataSnapshot.getValue(CustomerTaskListPojo.class);
                        cusTaskList.add(customerTaskListPojo);
//                BodyTypeTv.setText(customerTaskListPojo.getBodyType());
//                cusEQPTv.setText(customerTaskListPojo.getEqpType());
//                cusEXCTv.setText(customerTaskListPojo.getExcType());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                customerTaskListPojo = dataSnapshot.getValue(CustomerTaskListPojo.class);
                cusTaskList.add(customerTaskListPojo);
                setTask();


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

    public void setTask()
    {
        for(int i=0;i<cusTaskList.size();i++)
        {
            if(cusTaskList.get(i).getUname().equalsIgnoreCase(un))
            {
                BodyTypeTv.setText(cusTaskList.get(i).getBodyType());
                cusEQPTv.setText(cusTaskList.get(i).getEqpType());
                cusEXCTv.setText(cusTaskList.get(i).getExcType());
            }

        }



//        for(int i=0;i<cusTaskList.size();i++)
//        {
//
//            Log.e("cus",cusTaskList.get(i).getUname());
//        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Do you want to close the application");
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).show();
    }

}
