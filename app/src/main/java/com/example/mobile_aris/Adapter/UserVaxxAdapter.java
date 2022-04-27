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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_aris.Details.BiteDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.UserVaxxModel;
import com.example.mobile_aris.models.announcementModel;
import com.example.mobile_aris.models.bite;

import java.text.ParseException;
import java.util.ArrayList;

public class UserVaxxAdapter extends RecyclerView.Adapter<UserVaxxAdapter.ViewHolder>{

    ArrayList<UserVaxxModel> bitemodel = new ArrayList<UserVaxxModel>();
    Context context;
    public UserVaxxAdapter(ArrayList<UserVaxxModel> items) {
        bitemodel = items;
    }



    public ArrayList<UserVaxxModel> getmValues() {
        return bitemodel;
    }

    public UserVaxxAdapter(Context context) {
        this.context =  context;
    }


    public void setmValues(ArrayList<UserVaxxModel> mValues) {
        this.bitemodel = mValues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserVaxxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uservaxx,parent,false);
        return new UserVaxxAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull UserVaxxAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.a.setText(bitemodel.get(position).getDay());
        try {
            holder.b.setText(bitemodel.get(position).getDti().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.c.setText(bitemodel.get(position).getVac().toString());
        holder.d.setText(bitemodel.get(position).getLot().toString());
        holder.e.setText(bitemodel.get(position).getRem().toString());

//        holder.f.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, BiteDetailActivity.class);
//                intent.putExtra("_id", bitemodel.get(position).get_id().toString());
//                intent.putExtra("user", bitemodel.get(position).getUser_id().toString());
//                intent.putExtra("clinic", bitemodel.get(position).getCliID().toString());

//                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
//                View dialogView= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_edit_pet,null);

//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
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
            a = (TextView) ItemView.findViewById(R.id.d0);
            b = (TextView) ItemView.findViewById(R.id.dt0);
            c = (TextView) ItemView.findViewById(R.id.b0);
            d = (TextView) ItemView.findViewById(R.id.l0);
            e = (TextView) ItemView.findViewById(R.id.r0);
//            f = (CardView) ItemView.findViewById(R.id.bite_card);
//            e = (TextView) ItemView.findViewById(R.id.stat);
            bno = ("Bite Case No. ");
            loc = ("Location: ");
            date = ("Date: ");
            cat = ("Category: ");
            stat = ("Status: ");

        }

    }
}
