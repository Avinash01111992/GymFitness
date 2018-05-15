package com.example.suma.physicalfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.suma.physicalfitness.Adapters.CustomerListAdaptors;
import com.example.suma.physicalfitness.Adapters.DrawerListAdapter;
import com.example.suma.physicalfitness.pojo.MembersNameData;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainerHome extends AppCompatActivity {


    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    ListView mainMenuList;
    String title;
    ListView mDrawerList;
    Toolbar toolbar;
    String[] navContent = {"Manage Customers","Genarate Report","Chat","About","Logout"};
    JSONObject obj;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<RegistrationPojo> memberList = new ArrayList<>();
     RecyclerView recyclerView;
    private CustomerListAdaptors mAdapter;
    private  RegistrationPojo registrationPojo;
    ImageView refresh;
    String trainerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Bundle bundle = getIntent().getExtras();
        trainerName = bundle.getString("userName");

        prepareDB();





        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        toolbar = (Toolbar)findViewById(R.id.toolbar);





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

                if(position==3)
                {
                    Intent regCus = new Intent(TrainerHome.this, Login.class);
                    startActivity(regCus);
                }

                if(position==0)
                {
                    Intent mangCustomers  = new Intent(TrainerHome.this,ManageCustomers.class);
                    mangCustomers.putExtra("from","mnc");
                    startActivity(mangCustomers);
                }

                if(position==1)
                {
                    Intent mangCustomers  = new Intent(TrainerHome.this,ManageCustomers.class);
                    mangCustomers.putExtra("from","report");
                    startActivity(mangCustomers);
                }

                if(position==2)
                {
                    Intent mangCustomers  = new Intent(TrainerHome.this,Users.class);
//                    mangCustomers.putExtra("from","report");
                    startActivity(mangCustomers);
                }
                mDrawerLayout.closeDrawers();

            }
        });



    }

    public void prepareDB()
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("registeredmembers");


        mAdapter = new CustomerListAdaptors(getApplication(),memberList);
        prepareMembersData();



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



    public void prepareMembersData()

    {
      myRef.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            registrationPojo =  dataSnapshot.getValue(RegistrationPojo.class);
            if(registrationPojo.getRo().equalsIgnoreCase("customer")) {
                if(registrationPojo.getAsgndTrainer().trim().equalsIgnoreCase(trainerName)) {
                    memberList.add(registrationPojo);
                }
            }
            mAdapter.notifyDataSetChanged();

          }

          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String s) {
              mAdapter.notifyDataSetChanged();

          }

          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {
              mAdapter.notifyDataSetChanged();

          }

          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String s) {
              mAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              mAdapter.notifyDataSetChanged();

          }
      });
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
