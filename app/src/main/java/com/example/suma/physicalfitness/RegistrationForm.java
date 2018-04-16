package com.example.suma.physicalfitness;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationForm extends AppCompatActivity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_registration_form);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);



        userName = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.emial);
        pw = (EditText) findViewById(R.id.password);
        mobileNumber = (EditText) findViewById(R.id.mobileNum);
        addrs = (EditText) findViewById(R.id.addr);
        regBtn = (Button) findViewById(R.id.reg_button);





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
                        validateForm();

                    }

                    FirebaseCrash.logcat(Log.ERROR, "fire", "NPE caught");
//                    FirebaseCrash.report(e);

                } catch (Exception e) {
                    FirebaseCrash.logcat(Log.ERROR, "fire", "NPE caught");
                    FirebaseCrash.report(e);
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
        } else if (mn.equals("")) {
            mobileNumber.setError("can't be blank");
        } else if (adr.equals("")) {
            addrs.setError("can't be blank");

        } else {
            Log.e("role",ro);

            checkDbTable();
        }

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

    public void writeData()
    {
        Log.e("writeData","writeData");


//        myRef.removeValue();



//        String userId = myRef.push().getKey();
        try
        {
            Log.e("try","writeData");


            RegistrationPojo user = new RegistrationPojo(un, ro, mail, pwd, mn, adr);
//            registrationForms.add(user);


            Log.e("user",user.toString());

            myRef.child(un).setValue(user);
            Log.e("child",un);

            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
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









