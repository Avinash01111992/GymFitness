package com.example.suma.physicalfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GymManager extends AppCompatActivity {


    Button registrationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_manager);
        registrationBtn = (Button)findViewById(R.id.register_tranr_btn);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");

        }


        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regCus = new Intent(GymManager.this, RegistrationForm.class);
                startActivity(regCus);
            }
        });




    }
}
