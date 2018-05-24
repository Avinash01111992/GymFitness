package com.example.suma.physicalfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.suma.physicalfitness.Adapters.CustomerListAdaptors;
import com.example.suma.physicalfitness.Adapters.ManageCustomerAdaptors;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageCustomers extends AppCompatActivity {
    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    String validation;

    String title;
    private ManageCustomerAdaptors mAdapter;
    private RegistrationPojo registrationPojo;
    private List<RegistrationPojo> memberList = new ArrayList<>();
    RecyclerView recyclerView;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customers);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_all_customers);
        Bundle bundle = getIntent().getExtras();
      message = bundle.getString("from");
        if(message.trim().equalsIgnoreCase("report"))
        {
            validation = "re";
            title = "Upload Report";
        }else if(message.trim().equalsIgnoreCase("mnc"))
        {
            validation = "mn";
            title = "Manage Customers";
        }else
        {
            validation = "mt";
            title = "Manage Trainers";
        }
        prepareDB();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void prepareDB()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("registeredmembers");
        prepareMembersData();

        mAdapter = new ManageCustomerAdaptors(getApplication(),memberList,validation);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void prepareMembersData()

    {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                registrationPojo =  dataSnapshot.getValue(RegistrationPojo.class);

                if(message.trim().equalsIgnoreCase("mnc"))
                {
                    if(registrationPojo.getRo().equalsIgnoreCase("customer")) {
                        memberList.add(registrationPojo);
                    }
                }


                if(message.trim().equalsIgnoreCase("manageTrainers"))
                {
                    if(registrationPojo.getRo().equalsIgnoreCase("trainer")) {
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
//                memberList.clear();
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
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
