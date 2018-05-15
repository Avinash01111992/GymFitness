package com.example.suma.physicalfitness;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationForm extends AppCompatActivity   {


    EditText userName, userRole, email, pw, mobileNumber, addrs;
    Button regBtn;

    String un, ro, pwd, mn, adr, mail;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String memberRole;
    int userId;
    String[] navContent;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner trainers;
    String trainerName;
    ArrayAdapter aa;
    String emailPattern;

    String[] listOftrainers = null;
    private List<RegistrationPojo> memberList = new ArrayList<>();
    private List<String> memberListTrainers = new ArrayList<>();


    private RegistrationPojo registrationPojo;
    TextView tvTrain;
    private static int READ_DB = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_registration_form);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        tvTrain = (TextView) findViewById(R.id.tv_trin);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("registeredmembers");


        userName = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.emial);
        pw = (EditText) findViewById(R.id.password);
        mobileNumber = (EditText) findViewById(R.id.mobileNum);
        addrs = (EditText) findViewById(R.id.addr);
        regBtn = (Button) findViewById(R.id.reg_button);


       trainers = (Spinner) findViewById(R.id.trainers_spiner);



        FirebaseCrash.log("Activity created");
        database = FirebaseDatabase.getInstance();
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));


//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)           // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric);
       myRef = database.getReference("registeredmembers");
       Log.e("myRefdgjkwg",myRef.toString());


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Registration Form");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


//        trainers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//
//                trainerName = memberListTrainers.get(arg2).toString();
//                Log.e("trainerName",trainerName);
////                Toast.makeText(getApplicationContext(),memberListTrainers.get(arg2) ,Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });





        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (radioGroup.getCheckedRadioButtonId() == -1)
                    {

                        Toast.makeText(getApplicationContext(), "Please select the role", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        // get selected radio button from radioGroup
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        radioButton = (RadioButton)findViewById(selectedId);
                        ro = radioButton.getText().toString();
                        if (ro.trim().equalsIgnoreCase("customer"))
                        {

                            readDB();
                            tvTrain.setVisibility(View.VISIBLE);

                            trainers.setVisibility(View.VISIBLE);
                            //Creating the ArrayAdapter instance having the country list

                            Log.e("trainerName",trainerName);
                            if(trainerName!=null)
                            {
                               validateForm();
                            }else
                            {
                                Toast.makeText(getApplicationContext(), "Please assign the trainer", Toast.LENGTH_SHORT).show();

                            }



                        }else
                        {
                            trainerName = null;
                            validateForm();

                        }

                    }

                    FirebaseCrash.logcat(Log.ERROR, "fire", "NPE caught");
//                    FirebaseCrash.report(e);

                } catch (Exception e) {
                    FirebaseCrash.logcat(Log.ERROR, "fire", "NPE caught");
                    FirebaseCrash.report(e);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                readTrainers();

                if (checkedId == R.id.rb_cus) {

                    new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                        @Override
                        public void run() {
                            trainers.setVisibility(View.VISIBLE);
                            tvTrain.setVisibility(View.VISIBLE);
                            aa = new ArrayAdapter(RegistrationForm.this,android.R.layout.simple_spinner_item,memberListTrainers);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
//                            trainers.setAdapter(null);
                            trainers.setAdapter(aa);

                            trainers.setOnItemSelectedListener(
                                    new AdapterView.OnItemSelectedListener() {
                                        public void onItemSelected(
                                                AdapterView<?> parent, View view, int position, long id) {
                                            trainerName = memberListTrainers.get(position).toString();
                                        }

                                        public void onNothingSelected(AdapterView<?> parent) {
//                                    showToast("Spinner1: unselected");
                                        }
                                    });
                        }
                    }, READ_DB);


//
//                    //Creating the ArrayAdapter instance having the country list
//                    ArrayAdapter aa = new ArrayAdapter(RegistrationForm.this,android.R.layout.simple_spinner_item,memberListTrainers);
//                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    //Setting the ArrayAdapter data on the Spinner
//                    trainers.setAdapter(aa);


                } else
                {
                    trainers.setVisibility(View.GONE);
                    tvTrain.setVisibility(View.GONE);


                }

            }
        });

    }

    public void validateForm() {

        Log.e("validateForm","validate");

        un = userName.getText().toString();
        pwd = pw.getText().toString();
        mail = email.getText().toString();
        mn = mobileNumber.getText().toString();
        adr = addrs.getText().toString();


        if (un.equals("")) {
            userName.setError("can't be blank");

        } else if (pwd.equals("")) {
            pw.setError("can't be blank");
        } else if (!(isValidMobile(mn))) {
            mobileNumber.setError("enter correct mobile number");
        } else if (adr.equals("")) {
            addrs.setError("can't be blank");

        } else if (!mail.matches(emailPattern))
        {
            //or
            email.setError("Invalid emailId");

        } else {
            Log.e("role", ro);

            checkDbTable();
        }

    }


    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.activity_main, menu);
////        return true;
//    }

    public void readDB()

    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                registrationPojo = dataSnapshot.getValue(RegistrationPojo.class);
                memberList.add(registrationPojo);
//                if(registrationPojo.getRo().equalsIgnoreCase("trainer"))
//                {
//                    memberListTrainers.add(registrationPojo.getName().toString());
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


    public void readTrainers()

    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                registrationPojo = dataSnapshot.getValue(RegistrationPojo.class);
                if(registrationPojo.getRo().equalsIgnoreCase("trainer"))
                {
                    memberListTrainers.add(registrationPojo.getName().toString());
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

    public void checkDbTable() {

        if(myRef!=null)
        {
           if(myRef.getParent()!=null) {
               validateUserName();
           }else
           {
               writeData();

           }

        }else
        {
           writeData();
        }

    }

    protected void regDone() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Registration successful");
        alertbox.setPositiveButton(" Ok", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {

                Intent intent = new Intent(RegistrationForm.this,GymManager.class);
//                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }).show();

    }

    public void writeData()
    {
        try
        {

            RegistrationPojo user = new RegistrationPojo(un, ro, mail, pwd, mn, adr,trainerName);
            myRef.child(un).setValue(user);
            regDone();
//            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Crashlytics.getInstance().crash(); // Force a crash
            e.printStackTrace();

        }



    }

    public void validateUserName() {
        final ProgressDialog pd = new ProgressDialog(RegistrationForm.this);
        pd.setMessage("Loading...");
        pd.show();
        String url="https://gymfitness-82a70.firebaseio.com/registeredmembers.json";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("null")) {
                    writeData();
                    Toast.makeText(RegistrationForm.this, "user not found", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        Log.e("obj",obj.toString());
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonParser jp = new JsonParser();
                        JsonElement je = jp.parse(s);
                        String prettyJsonString = gson.toJson(je);
                        Log.e("prettyJsonString",prettyJsonString);
                        if(!obj.has(un))
                        {
                            writeData();
                        }else
                        {
                            Toast.makeText(RegistrationForm.this, "user name already taken , please use different username", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(RegistrationForm.this);
        rQueue.add(request);

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









