package com.example.mobile_aris.bites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
import com.example.mobile_aris.Adapter.biteAdpater;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.bite;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.Classes.User.user_profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bite_cases extends AppCompatActivity {
    RecyclerView recyclerView;
    com.example.mobile_aris.Adapter.biteAdpater biteAdpater;
    ArrayList<bite> each_bite = new ArrayList<bite>();
    String token, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bite_recycle);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bitecases);
        recyclerView = findViewById(R.id.bite_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        biteAdpater = new biteAdpater(getApplicationContext());
        recyclerView.setAdapter(biteAdpater);
        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id= info.getString("_id","");
        getAll();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
                        startActivity(new Intent(getApplicationContext(), Appointments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bitecases:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), user_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mypets:
                        startActivity(new Intent(getApplicationContext(), my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    public void getAll() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.100.32:5000/api/bitecase/get/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray data = response.getJSONArray("bitecase");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        JSONArray cli = jsonObject.getJSONArray("clinic");
//                                Log.d("image_length", String.valueOf(image.length()));
                        JSONObject cliId = cli.getJSONObject(0);
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("history_of_exposure"));
                        bite each = new bite(
                                jsonObject1.getString("place"),
                                jsonObject.getString("createdAt"),
                                jsonObject.getString("status_of_vaccination"),
                                jsonObject.getString("patient_status"),
                                jsonObject.getString("vaccine"),
                                jsonObject.getString("classification"),
                                jsonObject.getString("_id"),
                                Integer.parseInt(jsonObject.getString("exposure_category")),
                                Integer.parseInt(jsonObject.getString("bite_case_no")),
                                jsonObject.getString("clinic"),
                                jsonObject.getString("user"),
                                cliId.getString("_id")
                        );
                        each_bite.add(each);
                    }

                    biteAdpater.setmValues(each_bite);
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
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}