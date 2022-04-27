package com.example.mobile_aris.Classes;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.example.mobile_aris.Adapter.UserVaxxAdapter;
import com.example.mobile_aris.Adapter.VaxxAdapter;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.PetVaxx.Pet_Vaxx;
import com.example.mobile_aris.Classes.Pets.edit_pet;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.Details.BiteDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.example.mobile_aris.models.PetVaxxModel;
import com.example.mobile_aris.models.UserVaxxModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Uservaxx extends AppCompatActivity {
    RecyclerView recyclerView;
    com.example.mobile_aris.Adapter.UserVaxxAdapter biteAdpater;
    ArrayList<UserVaxxModel> each_bite = new ArrayList<UserVaxxModel>();
    String token, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                Dialog Pakita = new Dialog(Uservaxx.this);
                Pakita.setContentView(R.layout.rec_uservaxx);
                Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                Pakita.show();
                Pakita.setCancelable(false);
//        setContentView(R.layout.rec_uservaxx);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.bitecases);
        recyclerView = Pakita.findViewById(R.id.uvaxxx);
        recyclerView.setLayoutManager(new LinearLayoutManager(Uservaxx.this));
        biteAdpater = new UserVaxxAdapter(Uservaxx.this);
        recyclerView.setAdapter(biteAdpater);
        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
//        SharedPreferences pinfo = getSharedPreferences("petid",MODE_PRIVATE);
//        id= pinfo.getString("pid","");

        SharedPreferences vinfo =getSharedPreferences("vaxxuser_info",MODE_PRIVATE);
        _id= vinfo.getString("_id","");

        getAll();

        Button bc = Pakita.findViewById(R.id.back);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petIntent = new Intent(getApplicationContext(), bite_cases.class);
                startActivity(petIntent);
                Uservaxx.this.finish();
                Pakita.dismiss();
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
                "https://aris-backend.herokuapp.com/api/bitecase/post-vaxx/get/bitecase/622cb565701d59b46d6cec96", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("_id", _id);
                    JSONObject data = new JSONObject(response.getString("details"));
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject jsonObject = data.getJSONObject(i);
                        JSONArray vaxx = data.getJSONArray("vaxx");

//                        JSONObject uservaxx = vaxx.getJSONObject(0);
                        Log.d("vaxx_length", String.valueOf(vaxx.length()));
                        for (int n = 0; n < vaxx.length(); n++) {
                            JSONObject vacstat = vaxx.getJSONObject(n);


//                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("history_of_exposure"));
                            UserVaxxModel each = new UserVaxxModel(
                                    vacstat.getString("day"),
                                    vacstat.getString("date_injected"),
                                    vacstat.getString("vaccine"),
                                    vacstat.getString("lot"),
                                    vacstat.getString("remarks")
                            );
                            each_bite.add(each);
                        }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }

                biteAdpater.setmValues(each_bite);
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
}
