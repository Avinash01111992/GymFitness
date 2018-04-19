package com.example.suma.physicalfitness;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> alCustomers = new ArrayList<>();
    ArrayList<String> alTrainers = new ArrayList<>();
    private List<RegistrationPojo> memberList = new ArrayList<>();


    int totalUsers = 0;
    ProgressDialog pd;
    DatabaseReference myRef;
    FirebaseDatabase database;
    RegistrationPojo registrationPojo;
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("registeredmembers");
        Log.e("role",UserDetails.role);
        readDB();


        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        new Handler().postDelayed(new Runnable() {


            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                callListAdapters();

                // This method will be executed once the timer is over
                // Start your app main activity

            }
        }, SPLASH_TIME_OUT);

//        Log.e("role",UserDetails.role);
//
//        String url="https://gymfitness-82a70.firebaseio.com/registeredmembers.json";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s) {
//                doOnSuccess(s);
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("" + volleyError);
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
//        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(memberList.get(position).getRo().trim().equalsIgnoreCase("trainer"))
                {
                    UserDetails.chatWith = memberList.get(position).getName().toString();
                    startActivity(new Intent(Users.this, Chat.class));
                }else {
                    UserDetails.chatWith =  memberList.get(position).getName().toString();
                    startActivity(new Intent(Users.this, Chat.class));
                }

            }
        });
    }


    public void readDB()

    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                registrationPojo = dataSnapshot.getValue(RegistrationPojo.class);
                memberList.add(registrationPojo);
                if(registrationPojo.getRo().equalsIgnoreCase("trainer"))
                {
                    alTrainers.add(registrationPojo.getName());
                }else
                {
                    alCustomers.add(registrationPojo.getName());

                }

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

    public void callListAdapters(){


            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);

            if(UserDetails.role.equalsIgnoreCase("customer"))
            {
                Log.e("insideCustomer",UserDetails.role);
                usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alTrainers));

            }else {
                Log.e("insidetriner",UserDetails.role);
                usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,alCustomers ));

            }




        pd.dismiss();
    }
}
