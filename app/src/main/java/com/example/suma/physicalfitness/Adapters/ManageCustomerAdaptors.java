package com.example.suma.physicalfitness.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suma.physicalfitness.GenarateReport;
import com.example.suma.physicalfitness.R;
import com.example.suma.physicalfitness.UpdateCustomerTaskSheet;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by avinash on 3/4/18.
 */

public class ManageCustomerAdaptors extends RecyclerView.Adapter<ManageCustomerAdaptors.MyViewHolder> {

    private List<RegistrationPojo> moviesList;
    Context context;
    RegistrationPojo member;
    String userName;
    String validation;
    int REQUEST_FOR_ACTIVITY_CODE  = 100;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ,databaseReference1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,  genre ,removeCustomer;
       public Button delete;
//        alertbox  = new AlertDialog.Builder(context);


        public MyViewHolder(final View view) {
            super(view);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("registeredmembers");
            databaseReference1 = firebaseDatabase.getReference("customertask");

            title = (TextView) view.findViewById(R.id.title);
            delete = (Button) view.findViewById(R.id.remove);


        }
    }


    public ManageCustomerAdaptors(Context context, List<RegistrationPojo> moviesList,String vali) {
        this.moviesList = moviesList;
        this.context = context;
        this.validation = vali;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.managecustomer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(validation.trim().equalsIgnoreCase("re"))
        {
           holder.delete.setText("Upload Report");
        }else
        {
            holder.delete.setText("Remove");

        }

        member = moviesList.get(position);
        holder.title.setText(member.getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validation.trim().equalsIgnoreCase("re"))
                {
                    userName = moviesList.get(position).getName();
                    getDocument();
                }else {
                    userName = moviesList.get(position).getName();
                    confirm();
                }


            }
        });
    }

    private void getDocument()
    {
        Intent intent = new Intent(context, GenarateReport.class);
        Log.e("userNameAdap",userName);
        intent.putExtra("userName",userName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void confirm()

    {

        databaseReference1.child(userName).setValue(null);
        databaseReference.child(userName).setValue(null);
        Toast.makeText(context, "You have removed"+ " "+userName, Toast.LENGTH_LONG).show();

    }



    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
