package com.example.mobile_aris.Classes;

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
import com.example.mobile_aris.Adapter.VaxxAdapter;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.example.mobile_aris.models.PetVaxxModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pet_Vaxx extends AppCompatActivity {
    RecyclerView recyclerView;
    com.example.mobile_aris.Adapter.VaxxAdapter biteAdpater;
    ArrayList<PetVaxxModel> each_bite = new ArrayList<PetVaxxModel>();
    String token, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vaccine_status);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bitecases);
        recyclerView = findViewById(R.id.vac_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Pet_Vaxx.this));
        biteAdpater = new VaxxAdapter(Pet_Vaxx.this);
        recyclerView.setAdapter(biteAdpater);
        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        SharedPreferences pinfo = getSharedPreferences("petid",MODE_PRIVATE);
        id= pinfo.getString("pid","");
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
                        startActivity(new Intent(getApplicationContext(), bite_cases.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), user_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mypets:
                        return true;
                }
                return false;
            }
        });
    }


    public void getAll() {

//        JSONObject ji = new JSONObject();
//        try {
//            ji.put("_id", "id");
//        }catch (JSONException je){
//            je.printStackTrace();
//        }

        Log.d("Token1", token);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.100.32:5000/api/pet/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("petid", id);
                    JSONArray data = response.getJSONArray("pets");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        JSONArray vaxx = jsonObject.getJSONArray("vaccine_history");
                        Log.d("vaxx_length", String.valueOf(vaxx.length()));
                        for (int n = 0; n < vaxx.length(); n++) {
                            JSONObject vacstat = vaxx.getJSONObject(n);

//                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("history_of_exposure"));
                            PetVaxxModel each = new PetVaxxModel(
                                    vacstat.getString("_id"),
                                    vacstat.getString("vaccine_name"),
                                    vacstat.getString("date_of_vaccination"),
                                    vacstat.getString("revaccination_schedule")
                            );
                            each_bite.add(each);
                        }
                    }

                    biteAdpater.setmValues(each_bite);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Token", token);
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


//    @Override
//    public void onItemClick(int position) {
//        Intent detailIntent = new Intent(this, VaxxDetail.class);
//        petModel clickedItem = mExampleList.get(position);
//
//        detailIntent.putExtra(EXTRA_VNAME, clickedItem.getVname());
//        detailIntent.putExtra(EXTRA_VDATE, clickedItem.getVdate());
//        detailIntent.putExtra(EXTRA_REVAC, clickedItem.getVrevac());
//
//
//        startActivity(detailIntent);
//    }
}
