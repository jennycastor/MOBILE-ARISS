package com.example.mobile_aris.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.hreport;
import com.example.mobile_aris.models.announcementModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

public class RHAdapter extends RecyclerView.Adapter<RHAdapter.ViewHolder>{
    ArrayList<hreport> hrprt = new ArrayList<hreport>();
    Context context;
    public RHAdapter(ArrayList<hreport> items) {
        hrprt = items;
    }



    public ArrayList<hreport> getmValues() {
        return hrprt;
    }

    public RHAdapter(Context context) {
        this.context =  context;
    }


    public void setmValues(ArrayList<hreport> hrprt) {
        this.hrprt = hrprt;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.healt_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.a.setText(hrprt.get(position).getUsername());
        holder.b.setText(holder.desc + hrprt.get(position).getDescription());
        try {
            holder.c.setText(holder.sent + hrprt.get(position).getR_createdAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.d.setText(hrprt.get(position).getAdmin() + holder.op + hrprt.get(position).getRole() + holder.cl);
        holder.e.setText(holder.reply + hrprt.get(position).getText());
        try {
            holder.f.setText(holder.adsent + hrprt.get(position).getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.get().load(hrprt.get(position).getPhoto()).into(holder.g);
//        holder.h.setText(holder.op + hrprt.get(position).getRole() + holder.cl);
        holder.i.setText(holder.type + hrprt.get(position).getBType());

//        holder.j.setText(hrprt.get(position).getVday());
//        try {
//            holder.k.setText(hrprt.get(position).getVcdate().toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        holder.l.setText(hrprt.get(position).getVacc().toString());
//        holder.m.setText(hrprt.get(position).getLot().toString());
//        holder.n.setText(hrprt.get(position).getRem().toString());
    }

    @Override
    public int getItemCount() {
        return hrprt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public announcementModel mItem;
        TextView a,b,c,d,e,f,h,i,j,k,l,m,n;
        String op,cl, desc, adname, adsent, sent,type,reply;
        ImageView g;


        public ViewHolder(@NonNull View ItemView) {
            super(ItemView);
            a = (TextView) ItemView.findViewById(R.id.username);
            b = (TextView) ItemView.findViewById(R.id.desc);
            c = (TextView) ItemView.findViewById(R.id.date_hr);
            d = (TextView) ItemView.findViewById(R.id.admin);
            e = (TextView) ItemView.findViewById(R.id.text_reply);
            f = (TextView) ItemView.findViewById(R.id.createdAt);
            g = (ImageView) ItemView.findViewById(R.id.upro);
//            h = (TextView) ItemView.findViewById(R.id.role);
            i = (TextView) ItemView.findViewById(R.id.type);
//            j = (TextView) ItemView.findViewById(R.id.d0);
//            k = (TextView) ItemView.findViewById(R.id.dt0);
//            l = (TextView) ItemView.findViewById(R.id.b0);
//            m = (TextView) ItemView.findViewById(R.id.l0);
//            n = (TextView) ItemView.findViewById(R.id.r0);
            op = (" ( ");
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
