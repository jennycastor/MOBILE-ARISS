package com.example.mobile_aris.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_aris.Details.BiteDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.announcementModel;
import com.example.mobile_aris.models.bite;

import java.util.ArrayList;

public class biteAdpater  extends RecyclerView.Adapter<biteAdpater.ViewHolder>{

    ArrayList<bite> bitemodel = new ArrayList<bite>();
    Context context;
    public biteAdpater(ArrayList<bite> items) {
        bitemodel = items;
    }



    public ArrayList<bite> getmValues() {
        return bitemodel;
    }

    public biteAdpater(Context context) {
        this.context =  context;
    }


    public void setmValues(ArrayList<bite> mValues) {
        this.bitemodel = mValues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bite_layout,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.a.setText(holder.bno + String.valueOf(bitemodel.get(position).getBite_case_no()));
        holder.b.setText(holder.loc + bitemodel.get(position).getLocation().toString());
        holder.c.setText(holder.date + bitemodel.get(position).getCreatedAt().toString());
        holder.d.setText(holder.cat + bitemodel.get(position).getExposure_category().toString());
        holder.e.setText(holder.stat + bitemodel.get(position).getStatus_of_vaccination().toString());

        holder.f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BiteDetailActivity.class);
                intent.putExtra("_id", bitemodel.get(position).get_id().toString());
                intent.putExtra("user", bitemodel.get(position).getUser_id().toString());
                intent.putExtra("clinic", bitemodel.get(position).getCliID().toString());

                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_edit_pet,null);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bitemodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public announcementModel mItem;
        TextView a,b,c,d,e;
        String bno, loc, date, cat, stat;
        CardView f;


        public ViewHolder(@NonNull View ItemView) {
            super(ItemView);
            a = (TextView) ItemView.findViewById(R.id.bitecaseno);
            b = (TextView) ItemView.findViewById(R.id.location);
            c = (TextView) ItemView.findViewById(R.id.dater);
            d = (TextView) ItemView.findViewById(R.id.cat);
            e = (TextView) ItemView.findViewById(R.id.stat);
            f = (CardView) ItemView.findViewById(R.id.bite_card);
            bno = ("Bite Case No. ");
            loc = ("Location: ");
            date = ("Date: ");
            cat = ("Category: ");
            stat = ("Status: ");

        }

    }
}
