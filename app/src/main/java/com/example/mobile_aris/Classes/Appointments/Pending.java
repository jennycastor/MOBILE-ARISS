package com.example.mobile_aris.Classes.Appointments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Adapter.PendingAdapter;
import com.example.mobile_aris.Details.PendingDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.pendingModel;
import com.example.mobile_aris.models.set_appointment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pending extends AppCompatActivity implements PendingAdapter.OnItemClickListener {
    public static final String EXTRA_DATE = "pdate";
    public static final String EXTRA_TIME = "ptime";
    public static final String EXTRA_STATUS = "pstatus";
    public static final String EXTRA_PURPOSE = "ppurpose";
    public static final String EXTRA_CLINIC = "pclinic";


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private PendingAdapter mExampleAdapter;
    private ArrayList<pendingModel> mExampleList;
    private RequestQueue mRequestQueue;

    String id,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog Pakita = new Dialog(Pending.this);
        Pakita.setContentView(R.layout.activity_pending_tab);
        Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Pakita.setCancelable(false);
        //        setContentView(R.layout.activity_pending_tab);

        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id = info.getString("_id", "");

        mRecyclerView = Pakita.findViewById(R.id.pending);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        refreshLayout = (SwipeRefreshLayout) Pakita.findViewById(R.id.pending_refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { parseJSON(); }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseJSON();
                refreshLayout.setRefreshing(false);
            }
        });

        Button bck = Pakita.findViewById(R.id.back);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( getApplicationContext(), Appointments.class);
                Pending.this.startActivity(intent);
//                Pending.this.onBackPressed();
            }
        });


//        setListeners();
        Pakita.show();

//    }


//    public void setListeners() {
//        FloatingActionButton addPets;
//        addPets = findViewById(R.id.addPets);
//
////        MaterialButton editPet;
////        editPet = findViewById(R.id.editPet);
//
//        BottomNavigationView bottomNavigationView = Pakita.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.appointments);

        FloatingActionButton SetAppointment;


        SetAppointment = Pakita.findViewById(R.id.addAppoint);

        SetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Pending.this, set_appointment.class);
                startActivity(intent);
            }
        });

//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.appointments:
//                        return true;
//                    case R.id.bitecases:
//                        startActivity(new Intent(getApplicationContext(), bite_cases.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(), user_profile.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.mypets:
//                        startActivity(new Intent(getApplicationContext(), my_pets.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });
    }


    private void parseJSON() {
        mExampleList.clear();
        String url = "http://192.168.100.32:5000/api/appointments/get/my-appointments";

        JSONObject ji = new JSONObject();
        try {
            ji.put("id", id);
        }catch (JSONException je){
            je.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("appointments");
                            JSONArray pending = jsonObject.getJSONArray("pending");
                            Log.d("pending", String.valueOf(pending));
                            for (int i = 0; i < pending.length(); i++) {
                                JSONObject data = pending.getJSONObject(i);
                                JSONArray clinic = data.getJSONArray("clinicId");
//                                Log.d("clinic", String.valueOf(clinic));
//                                Log.d("image_length", String.valueOf(image.length()));
//                                JSONArray cdata = clinic.getJSONArray("clinicId");
                                JSONObject clidata = clinic.getJSONObject(0);

                                String p_id = data.getString("_id");
                                String pdate = data.getString("date");
                                String ptime = data.getString("time_slot");
                                String ppurpose = data.getString("purpose");
                                String pstatus = data.getString("status");
                                String pclinic = clidata.getString("name");

                                mExampleList.add(new pendingModel( p_id, pdate, ptime, ppurpose, pstatus, pclinic));

                            }

//                            Collections.sort(mExampleList, new Comparator<pendingModel>() {
//                                @Override
//
//                                public int compare(pendingModel item1, pendingModel item2) {
//                                        return item1.getPDate().compareToIgnoreCase(item2.getPDate());
//
//                                }
//                            });
//                            Collections.reverse(mExampleList);

                            mExampleAdapter = new PendingAdapter(Pending.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(Pending.this);

                            mExampleAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, PendingDetailActivity.class);
        pendingModel clickedItem = mExampleList.get(position);

        try {
            detailIntent.putExtra(EXTRA_DATE, clickedItem.getPDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detailIntent.putExtra(EXTRA_TIME, clickedItem.getPTime());
        detailIntent.putExtra(EXTRA_PURPOSE, clickedItem.getPPurpose());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getPStatus());
        detailIntent.putExtra(EXTRA_CLINIC, clickedItem.getPClinic());

        startActivity(detailIntent);
    }


    @Override
    public void onRefresh() {

    }

}