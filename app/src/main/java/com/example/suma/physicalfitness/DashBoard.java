package com.example.suma.physicalfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity {

    Button mngrBtn,userLgnBtn,tranerLgnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mngrBtn = (Button)findViewById(R.id.mngr_btn);
        userLgnBtn = (Button)findViewById(R.id.user_btn);

        mngrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gymMangr = new Intent(DashBoard.this, GymManager.class);
                startActivity(gymMangr);
            }
        });


        userLgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userLgn = new Intent(DashBoard.this, Login.class);
                startActivity(userLgn);
            }
        });





    }
}
