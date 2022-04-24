package com.example.mobile_aris.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.Pet_Vaxx;
import com.example.mobile_aris.Classes.edit_vaxx;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.PetVaxxModel;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VaxxAdapter extends RecyclerView.Adapter<VaxxAdapter.ViewHolder> {

    ArrayList<PetVaxxModel> bitemodel = new ArrayList<PetVaxxModel>();
    Context context;

    String id,token;

    public VaxxAdapter(ArrayList<PetVaxxModel> items) {
        bitemodel = items;
    }

    public ArrayList<PetVaxxModel> getmValues() {
        return bitemodel;
    }

    public VaxxAdapter(Context context) {
        this.context =  context;
    }


    public void setmValues(ArrayList<PetVaxxModel> mValues) {
        this.bitemodel = mValues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VaxxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_vacstat,parent,false);
        return new VaxxAdapter.ViewHolder(view);
    }
//
//    @SuppressLint("SetTextI18n")
//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull VaxxAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.a.setText(holder.bno + bitemodel.get(position).getVId().toString());
        holder.b.setText(holder.loc + bitemodel.get(position).getVname().toString());
        try {
            holder.c.setText(holder.date + bitemodel.get(position).getVdate().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            holder.d.setText(holder.cat + bitemodel.get(position).getVrevac().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "edit button clicked", Toast.LENGTH_LONG).show();
//
                SharedPreferences remember = context.getSharedPreferences("Edit_vaxx_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = remember.edit();

                editor.putString("_id", bitemodel.get(position).getVId().toString());
                editor.putString("vacname", bitemodel.get(position).getVname().toString());
                try {
                    editor.putString("vacdate", bitemodel.get(position).getVdate().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                editor.putString("color", currentItem.getPColor());
                editor.apply();

                Intent intent = new Intent ( context, edit_vaxx.class);
                context.startActivity(intent);
            }
        });


        holder.f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject ji = new JSONObject();
                try {
                    ji.put("_id", bitemodel.get(position).getVId().toString());
                }catch (
                        JSONException je){
                    je.printStackTrace();
                }
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://192.168.100.32:5000/api/pet/delete/vaxx-detail" , ji, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences info = context.getSharedPreferences("user_info",MODE_PRIVATE);
                        token= info.getString("access_token","");
                        Log.d("token", token);
//                        LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_my_pets,null).refreshDrawableState();
                        Intent petIntent = new Intent(context, Pet_Vaxx.class);
                        context.startActivity(petIntent);
                        Toast.makeText(context,"Pet " + bitemodel.get(position).getVname() + " Deleted", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error is", String.valueOf(error));
                    }
                })
                {
                    //This is for Headers If You Needed
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer "+ token);
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });

        holder.g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "add button clicked", Toast.LENGTH_LONG).show();
                Dialog Pakita = new Dialog(context);
                Pakita.setContentView(R.layout.activity_add_vaxx);
                Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                Button bc = Pakita.findViewById(R.id.back);
                bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Pakita.cancel();
                    }
                });

                Button addv = Pakita.findViewById(R.id.addvaxx);
                addv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject jo = new JSONObject();
                        try {
                            jo.put("vaccine_name", Pakita.findViewById(R.id.avacname));
                            jo.put("date_of_vaccination", Pakita.findViewById(R.id.avacdate));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestQueue requestQueue= Volley.newRequestQueue(context.getApplicationContext());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                "http://192.168.100.32:5000/api/petadd/vaxx-detail", jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Vaxx", "Added Successfully");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSON Exception", String.valueOf(error));
                            }
                        }){

                            //This is for Headers If You Needed
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", "Bearer " + token);
                                return params;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);
                    }
                });
                Pakita.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bitemodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public PetVaxxModel mItem;
        TextView a,b,c,d,e;
        String bno, loc, date, cat, stat;
        MaterialButton f,g;


        public ViewHolder(@NonNull View ItemView) {
            super(ItemView);
            a = (TextView) ItemView.findViewById(R.id.vacId);
            b = (TextView) ItemView.findViewById(R.id.vaccine_name);
            c = (TextView) ItemView.findViewById(R.id.vacDate);
            d = (TextView) ItemView.findViewById(R.id.revacSched);
            e = (Button) ItemView.findViewById(R.id.editVaxx);
            f = (MaterialButton) ItemView.findViewById(R.id.deleteVaxx);
            g = (MaterialButton) ItemView.findViewById(R.id.addVaxxx);
//            f = (CardView) ItemView.findViewById(R.id.bite_card);
            bno = ("id ");
            loc = ("Vaccine Name: ");
            date = ("Vaccine Date: ");
            cat = ("Revac Sched: ");
//            stat = ("Status: ");

        }

    }
}
