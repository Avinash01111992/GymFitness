package com.example.suma.physicalfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.suma.physicalfitness.pojo.CustomerTaskListPojo;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateCustomerTaskSheet extends AppCompatActivity {
    String type, exc, eqp;
    String exc1, exc2, exc3, exc4, exc5, eqp1, eqp2, eqp3, eqp4, eqp5, eqp6;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button buttontask;
    CheckBox ex1, ex2, ex3, ex4, ex5, ep1, ep2, ep3, ep4, ep5, ep6;
    String userName;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String listOfExc,listOfEqp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer_task_sheet);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("userName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("customertask");
        buttontask = (Button) findViewById(R.id.task_update_btn);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ex1 = (CheckBox) findViewById(R.id.suat_cb);
        ex2 = (CheckBox) findViewById(R.id.leg_press_cb);
        ex3 = (CheckBox) findViewById(R.id.dead_cb);
        ex4 = (CheckBox) findViewById(R.id.exten_cb);
        ex5 = (CheckBox) findViewById(R.id.leg_curl_bx);

        ep1 = (CheckBox) findViewById(R.id.dum_cb);
        ep2 = (CheckBox) findViewById(R.id.kettlebells_cb);
        ep3 = (CheckBox) findViewById(R.id.press_cb);
        ep4 = (CheckBox) findViewById(R.id.barbell_cb);
        ep5 = (CheckBox) findViewById(R.id.trapbar_cb);
        ep6 = (CheckBox) findViewById(R.id.cable_cb);

//        spinnerex = (Spinner) findViewById(R.id.spinner_exc);
//        spinnereq = (Spinner) findViewById(R.id.spinner_eqpmt);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Update task");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        }


        buttontask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please select the body type", Toast.LENGTH_SHORT).show();

                    } else {
                        // get selected radio button from radioGroup
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        radioButton = (RadioButton) findViewById(selectedId);
                        type = radioButton.getText().toString();
                        getCheckBoxData();
                        writeData();

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


//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)           // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric);

//        Spinner spinnerex = (Spinner) findViewById(R.id.spinner_exc);
//        Spinner spinnereq = (Spinner) findViewById(R.id.spinner_eqpmt);
//
//        // Spinner click listener
//        spinnerex.setOnItemSelectedListener(this);
//        spinnereq.setOnItemSelectedListener(this);
//
//        // Spinner Drop down elements
//        List<String> exxcs = new ArrayList<>();
//        exxcs.add("Select Exercises");
//        exxcs.add("Squat");
//        exxcs.add("Leg press");
//        exxcs.add("Deadlift");
//        exxcs.add("Leg extension");
//        exxcs.add("Leg curl");
//
//
//
//
//        // Spinner Drop down elements
//        List<String> eqpmt = new ArrayList<>();
//        eqpmt.add("Select Equipments");
//        eqpmt.add("dumbbells");
//        eqpmt.add("kettlebells");
//        eqpmt.add("Leg press machine");
//        eqpmt.add("barbell");
//        eqpmt.add("trapbar");
//        eqpmt.add("cable machine");

//
//        // Creating adapter for spinner
//        ArrayAdapter dataAdapterEx = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exxcs);
//
//        // Drop down layout style - list view with radio button
//        dataAdapterEx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinnerex.setAdapter(dataAdapterEx);
//
//
//        // Creating adapter for spinner
//        ArrayAdapter dataAdapterEqp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eqpmt);
//
//        // Drop down layout style - list view with radio button
//        dataAdapterEqp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinnereq.setAdapter(dataAdapterEqp);


    public void writeData() {
        CustomerTaskListPojo user = new CustomerTaskListPojo(userName, type, listOfExc, listOfEqp);
        myRef.child(userName).setValue(user);
        updateConfirm();
    }

    public void getCheckBoxData() {

        if (ex1.isChecked()) {
            exc1 = ex1.getText().toString();

        }else
        {
            exc1="";
        }


        if (ex2.isChecked()) {
            exc2 = ex2.getText().toString();

        }else
        {
            exc2 = "";
        }
        // Cheese me


        if (ex3.isChecked()) {
            exc3 = ex3.getText().toString();

        }else
        {
            exc3 = "";
        }
        // Cheese me


        if (ex4.isChecked()) {
            exc4 = ex4.getText().toString();
        }else
        {
            exc4 = "";

        }


        if (ex5.isChecked()) {
            exc5 = ex5.getText().toString();
        }else
        {
            exc5 = "";
        }
        // Cheese me


        if (ep1.isChecked()) {
            eqp1 = ep1.getText().toString();
        }else
        {
            eqp1 = "";
        }
        if (ep2.isChecked()) {
            eqp2 = ep2.getText().toString();

        }else
        {
            eqp2 = "";

        }
        if (ep3.isChecked()) {
            eqp3 = ep3.getText().toString();

        }else
        {
            eqp3 = "";

        }
        if (ep4.isChecked()) {
            eqp4 = ep4.getText().toString();

        }else
        {
            eqp4 = "";

        }
        if (ep5.isChecked()) {
            eqp5 = ep5.getText().toString();

        }else
        {
            eqp5 = "";

        }
        if (ep6.isChecked()) {
            eqp6 = ep6.getText().toString();

        }else
        {
            eqp6 = "";
        }

        listOfExc = exc1 + "," + exc2  + "," + exc3  + "," + exc4 +"," + exc5;
        listOfEqp = eqp1 + "," +  eqp2 + "," + eqp3 + "," + eqp4 + "," + eqp5 + "," + eqp6 ;

        Log.e("listOfEqp", listOfEqp);
        Log.e("listOfExc", listOfExc);


    }


// On selecting a spinner item


    // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateConfirm() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Updated Successfully");
        alertbox.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {



            }
        }).show();

    }


}
