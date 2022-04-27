package com.example.mobile_aris.Classes.Appointments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Appointments extends AppCompatActivity {

    String token, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.appointments);

        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");

        CardView pending_card;
                CardView completed_card;
        CardView cancelled_card;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
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
                        startActivity(new Intent(getApplicationContext(), my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        pending_card = findViewById(R.id.pending_card);

        pending_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, Pending.class);
                startActivity(intent);
            }
        });
        completed_card = findViewById(R.id.completed_card);

        completed_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, completed_tab.class);
                startActivity(intent);
            }
        });
        cancelled_card = findViewById(R.id.cancelled_card);

        cancelled_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, cancelled_tab.class);
                startActivity(intent);
            }
        });
        FloatingActionButton SetAppointment;


        SetAppointment = findViewById(R.id.SetAppointment);

        SetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "https://aris-backend.herokuapp.com/api/appointments/check/eligibility", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

//                            JSONObject data = response.getJSONObject("eligibility");
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject jsonObject = data.getJSONObject(i);

                            String elg = response.getString("eligibility");
                            
                            if (elg == "true") {
                                Intent intent = new Intent(Appointments.this, set_appointment.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "You exceeded the number of cancelled Appointments within 5 days. You are not eligible to request for one", Toast.LENGTH_LONG).show();
                                Log.d("eligib", elg);
                            }

                            } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
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

            });
        }
    }
