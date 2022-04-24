package com.example.mobile_aris.Classes;

import android.content.Context;
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
import com.example.mobile_aris.Adapter.BarcountAdapter;
import com.example.mobile_aris.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class barcount extends AppCompatActivity {

    RecyclerView recyclerView;
    BarcountAdapter rhAdapter;
    ArrayList<barcountModel> hreports = new ArrayList<barcountModel>();
    SharedPreferences remember;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcount);

        recyclerView = findViewById(R.id.recbarcount);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rhAdapter = new BarcountAdapter(getApplicationContext());
        recyclerView.setAdapter(rhAdapter);
        remember = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/analytics/get/barangayCount", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = new JSONArray(response.getString("barangayCount"));
//                    JSONArray rprt = new JSONArray(data.getString("barangayCount"));
                    for (int i = 0; i < data.length(); i++) {
                        String name = "";
                        JSONObject rh = data.getJSONObject(i);

                        Log.d("data_length", String.valueOf(data.length()));
                                barcountModel each = new barcountModel(
                                        rh.getString("_id"),
                                        rh.getString("count")
                                );
                                hreports.add(each);
                            }

                    rhAdapter.setmValues(hreports);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON Exception", String.valueOf(error));
            }
        }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",
                        "Bearer "+remember.getString("access_token","").toString());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
