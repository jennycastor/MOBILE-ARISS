package com.example.mobile_aris.Classes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Adapter.MyannouncementRecyclerViewAdapter;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.announcementModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class announcement extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<announcementModel> annoucements = new ArrayList<announcementModel>();
    MyannouncementRecyclerViewAdapter adapter;
    String id,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MyannouncementRecyclerViewAdapter(annoucements);
        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        recyclerView.setAdapter(adapter);
        token= info.getString("access_token","");
        getAll();
    }

    public void getAll() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/announcement/view/announcements", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("announcement");
                    for (int i = 0; i < data.length(); i++) {
                        announcementModel announcementModel = new announcementModel();
                        JSONObject jsonObject = data.getJSONObject(i);
                        announcementModel.setDate(jsonObject.getString("date"));
                        announcementModel.setDesc(jsonObject.getString("desc"));
                        announcementModel.setTitle(jsonObject.getString("title"));
                        JSONArray clinic_name=jsonObject.getJSONArray("clinic");
                        if(clinic_name.length()>0){
                            JSONObject clinic_detail=clinic_name.getJSONObject(0);
                            announcementModel.setAuthor(clinic_detail.getString("name"));
                        }else{
                            announcementModel.setAuthor("Null");
                        }
                        annoucements.add(announcementModel);
                    }

                    adapter.setmValues(annoucements);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("Authorization", "Bearer "+ token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}