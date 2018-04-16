package com.example.suma.physicalfitness.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.suma.physicalfitness.DashBoard;
import com.example.suma.physicalfitness.GymManager;
import com.example.suma.physicalfitness.R;
import com.example.suma.physicalfitness.RegistrationForm;
import com.example.suma.physicalfitness.UpdateCustomerTaskSheet;
import com.example.suma.physicalfitness.pojo.MembersNameData;
import com.example.suma.physicalfitness.pojo.RegistrationPojo;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by avinash on 3/4/18.
 */

public class CustomerListAdaptors extends RecyclerView.Adapter<CustomerListAdaptors.MyViewHolder> {

    private List<RegistrationPojo> moviesList;
    Context context;
    RegistrationPojo member;
    String userName;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,  genre ,removeCustomer;
       public Button task;


        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            task = (Button) view.findViewById(R.id.task);

        }
    }


    public CustomerListAdaptors(Context context,List<RegistrationPojo> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        member = moviesList.get(position);
        holder.title.setText(member.getName());
        holder.genre.setText(member.getMobileNumber());
       holder.task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = moviesList.get(position).getName();

                Intent gymMangr = new Intent(context, UpdateCustomerTaskSheet.class);

                gymMangr.putExtra("userName",userName);
                gymMangr.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(gymMangr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
