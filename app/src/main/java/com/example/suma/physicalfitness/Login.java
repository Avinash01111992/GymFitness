package com.example.suma.physicalfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    TextView register;
    EditText username, password;
    Button loginButton;
    String user, pass;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<RegistrationPojo> memberListCustomers = new ArrayList<>();
    private List<RegistrationPojo> memberListTrainers = new ArrayList<>();
    private List<RegistrationPojo> memberList = new ArrayList<>();


    private RegistrationPojo registrationPojo;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("registeredmembers");
          pd = new ProgressDialog(Login.this);

         readDB();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else {
                    pd.setMessage("Login...");
                    pd.show();
                    validate();
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
                    memberListTrainers.add(registrationPojo);
                }else
                {
                    memberListCustomers.add(registrationPojo);

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

    public void validate() {
        for (int i = 0; i < memberList.size(); i++) {
//            Log.e("user",user);
//            Log.e("nameee",memberList.get(i).getName());
            if(user.equalsIgnoreCase(memberList.get(i).getName())) {
                if (pass.equalsIgnoreCase(memberList.get(i).getPw())) {
                    pd.dismiss();
                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    if(memberList.get(i).getRo().equalsIgnoreCase("trainer"))
                    {
                        Intent gymMangr = new Intent(Login.this, TrainerHome.class);
                        startActivity(gymMangr);
                    }else
                    {
                        Intent gymMangr = new Intent(Login.this, CustomerHome.class);
                        gymMangr.putExtra("userName",memberList.get(i).getName());
                        startActivity(gymMangr);
                    }

                } else {
                    pd.dismiss();

                    Toast.makeText(Login.this, "In correct Password", Toast.LENGTH_SHORT).show();
                }
            }
        }

        pd.dismiss();
//        Toast.makeText(Login.this, "User Not found", Toast.LENGTH_SHORT).show();
    }

}




