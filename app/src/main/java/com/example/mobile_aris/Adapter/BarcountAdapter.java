package com.example.mobile_aris.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_aris.Classes.barcountModel;
import com.example.mobile_aris.R;

import java.util.ArrayList;

public class BarcountAdapter extends RecyclerView.Adapter<BarcountAdapter.ViewHolder>{
        ArrayList<barcountModel> hrprt = new ArrayList<barcountModel>();
        Context context;
public BarcountAdapter(ArrayList<barcountModel> items) {
        hrprt = items;
        }



public ArrayList<barcountModel> getmValues() {
        return hrprt;
        }

public BarcountAdapter(Context context) {
        this.context =  context;
        }


public void setmValues(ArrayList<barcountModel> hrprt) {
        this.hrprt = hrprt;
        notifyDataSetChanged();
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barcount,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.a.setText(hrprt.get(position).getBname());
        holder.b.setText(holder.op + hrprt.get(position).getBcount());
        }

@Override
public int getItemCount() {
        return hrprt.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView a, b, c, d, e, f, h, i;
    String op, cl, desc, adname, adsent, sent, type, reply;
    ImageView g;


    public ViewHolder(@NonNull View ItemView) {
        super(ItemView);
        a = (TextView) ItemView.findViewById(R.id.bar);
        b = (TextView) ItemView.findViewById(R.id.bc);
//        c = (TextView) ItemView.findViewById(R.id.date_hr);
//        d = (TextView) ItemView.findViewById(R.id.admin);
//        e = (TextView) ItemView.findViewById(R.id.text_reply);
//        f = (TextView) ItemView.findViewById(R.id.createdAt);
//        g = (ImageView) ItemView.findViewById(R.id.upro);
////            h = (TextView) ItemView.findViewById(R.id.role);
//        i = (TextView) ItemView.findViewById(R.id.type);
        op = ("  ");
        cl = (" )");
        desc = ("Description: ");
        type = ("Type: ");
        sent = ("Sent: ");
        adname = ("Name: ");
        reply = ("Reply: ");
        adsent = ("Sent: ");
        }
    }
}

