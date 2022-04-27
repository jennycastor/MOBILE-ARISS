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
import com.example.mobile_aris.Classes.Pets.edit_pet;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.petModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MypetRecyclerViewAdapter extends RecyclerView.Adapter<MypetRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<petModel> mExampleList;
    private OnItemClickListener mListener;
    String id,token;
    private SwipeRefreshLayout refreshLayout;

    public interface OnItemClickListener {
        void onRefresh();

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
        notifyDataSetChanged();
    }

    public MypetRecyclerViewAdapter(Context context, ArrayList<petModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_item2, parent, false);
        SharedPreferences info = mContext.getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id = info.getString("_id", "");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        petModel currentItem = mExampleList.get(position);

        String petId = currentItem.getId();
        String imageUrl = currentItem.getImageUrl();
        String pId = currentItem.getpId();
        String petName = currentItem.getPname();
        String gender = currentItem.getGender();
        int age = currentItem.getPAge();
        String date = currentItem.getCreatedAt();
        String specie = currentItem.getSpecie();
        String breed = currentItem.getBreed();
        String color = currentItem.getPColor();

        holder.mTextViewCreator.setText(petName);
        holder.mTextViewLikes.setText("Age: " + pId);
//        holder.mTextViewGender.setText("Gender: " + gender);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.mTextViewCreatedAt.setText("date" + date);
//        holder.mTextViewSpecie.setText("Specie" + specie);
//        holder.mTextViewBreed.setText("Breed" + breed);
//        holder.mTextViewColor.setText("Color" + color);


//        Picasso.get(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gagawa ka ng edit mo dito ha
                Toast.makeText(mContext,"edit button clicked", Toast.LENGTH_LONG).show();
//
                SharedPreferences remember = mContext.getSharedPreferences("Edit_pet_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = remember.edit();

                String age = "" + currentItem.getPAge();

                editor.putString("p_id", currentItem.getId());
                editor.putString("image", currentItem.getImageUrl());
                editor.putString("pubId", currentItem.getpId());
                editor.putString("name", currentItem.getPname());
                editor.putString("age", age);
                editor.putString("gender", currentItem.getGender());
                editor.putString("specie", currentItem.getSpecie());
                editor.putString("breed", currentItem.getBreed());
                editor.putString("color", currentItem.getPColor());
                editor.apply();

                Intent intent = new Intent ( mContext, edit_pet.class);
                mContext.startActivity(intent);

            }

        });

        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                        "https://aris-backend.herokuapp.com/api/pet/delete/" + petId, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("token", token);
//                        LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_my_pets,null).refreshDrawableState();
                        Intent petIntent = new Intent(mContext.getApplicationContext(), my_pets.class);
                        mContext.startActivity(petIntent);
                        Toast.makeText(mContext,"Pet " + petName + " Deleted", Toast.LENGTH_LONG).show();
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


            editbutton= itemView.findViewById(R.id.editPet);
            deletebutton= itemView.findViewById(R.id.deletePet);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.pid);
//            mTextViewGender = itemView.findViewById(R.id.gender);
            mTextViewCreatedAt = itemView.findViewById(R.id.date);

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
