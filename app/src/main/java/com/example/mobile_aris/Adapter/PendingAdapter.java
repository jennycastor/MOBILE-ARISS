package com.example.mobile_aris.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.Appointments.Pending;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.pendingModel;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<pendingModel> mExampleList;
    private Pending mListener;
    String id,token;
    private SwipeRefreshLayout refreshLayout;

    public interface OnItemClickListener {
        void onRefresh();

        void onItemClick(int position);
    }

    public void setOnItemClickListener(Pending listener) {
        mListener = listener;
    }

    public PendingAdapter(Context context, ArrayList<pendingModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_pending, parent, false);
        SharedPreferences info = mContext.getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id = info.getString("_id", "");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pendingModel currentItem = mExampleList.get(position);

        String pid = currentItem.getPId();
        String pdate = null;
        try {
            pdate = currentItem.getPDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ptime = currentItem.getPTime();
        String ppurpose = currentItem.getPPurpose();
        String pstatus = currentItem.getPStatus();
        String pclinic = currentItem.getPClinic();


        holder.mTextViewCreator.setText("Date: " + pdate);
        holder.mTextViewLikes.setText("Time: " + ptime);
        holder.mTextViewGender.setText("Purpose: " + ppurpose);
//        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.mTextViewCreatedAt.setText("Status: " + pstatus);
        holder.mTextViewSpecie.setText("Clinic: " + pclinic);
//        holder.mTextViewBreed.setText("Breed" + breed);
//        holder.mTextViewColor.setText("Color" + color);

//        Picasso.get(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,
                        "https://aris-backend.herokuapp.com/api/appointments/cancel/" + pid, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("token", token);
//                        LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_my_pets,null).refreshDrawableState();
                        Intent cIntent = new Intent(mContext.getApplicationContext(), Pending.class);
                        mContext.startActivity(cIntent);
                        Toast.makeText(mContext,"Appointment Cancelled", Toast.LENGTH_LONG).show();
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

    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        public TextView mTextViewGender;
        public TextView mTextViewCreatedAt;
        public TextView mTextViewSpecie;
        public TextView mTextViewBreed;
        public TextView mTextViewColor;
        public MaterialButton medit;
        public MaterialButton editbutton;
        public MaterialButton deletebutton;

        public ViewHolder(View itemView) {
            super(itemView);


//            editbutton= itemView.findViewById(R.id.editPet);
            deletebutton= itemView.findViewById(R.id.deleteApp);
//            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.app_date);
            mTextViewLikes = itemView.findViewById(R.id.app_time);
            mTextViewGender = itemView.findViewById(R.id.app_purpose);
            mTextViewCreatedAt = itemView.findViewById(R.id.app_status);
            mTextViewSpecie = itemView.findViewById(R.id.app_clinic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
