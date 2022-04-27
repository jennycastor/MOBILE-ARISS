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
import com.example.mobile_aris.Adapter.CompletedAdapter;
import com.example.mobile_aris.Classes.CompletedDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.completedModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class completed_tab extends AppCompatActivity implements CompletedAdapter.OnItemClickListener{
    public static final String EXTRA_DATE = "pdate";
    public static final String EXTRA_TIME = "ptime";
    public static final String EXTRA_STATUS = "pstatus";
    public static final String EXTRA_PURPOSE = "ppurpose";
    public static final String EXTRA_CLINIC = "pclinic";


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private CompletedAdapter mExampleAdapter;
    private ArrayList<completedModel> mExampleList;
    private RequestQueue mRequestQueue;

    String id,token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog Pakita = new Dialog(completed_tab.this);
        Pakita.setContentView(R.layout.activity_completed_tab);
        Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        setContentView(R.layout.activity_completed_tab);
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

//        setListeners();
        Pakita.setCancelable(false);
        Pakita.show();

        Button bck = Pakita.findViewById(R.id.back);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( getApplicationContext(), Appointments.class);
               completed_tab.this.startActivity(intent);
//                Pending.this.onBackPressed();
            }
        });

    }


    private void parseJSON() {
        mExampleList.clear();
        String url = "https://aris-backend.herokuapp.com/api/appointments/get/my-appointments";

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
                            JSONArray pending = jsonObject.getJSONArray("completed");
                            Log.d("pending", String.valueOf(pending));
                            for (int i = 0; i < pending.length(); i++) {
                                JSONObject data = pending.getJSONObject(i);
                                JSONArray clinic = data.getJSONArray("clinicId");
//                                Log.d("clinic", String.valueOf(clinic));
//                                Log.d("image_length", String.valueOf(image.length()));
//                                JSONArray cdata = clinic.getJSONArray("clinicId");
                                JSONObject clidata = clinic.getJSONObject(0);

//                                String p_id = data.getString("_id");
                                String pdate = data.getString("date");
                                String ptime = data.getString("time_slot");
                                String ppurpose = data.getString("purpose");
                                String pstatus = data.getString("status");
                                String pclinic = clidata.getString("name");

                                mExampleList.add(new completedModel( pdate, ptime, ppurpose, pstatus, pclinic));

                            }

                            Collections.sort(mExampleList, new Comparator<completedModel>() {
                                @Override
                                public int compare(completedModel item1, completedModel item2) {
                                    return item1.getPDate().compareToIgnoreCase(item2.getPDate());

                                }
                            });
                            Collections.reverse(mExampleList);

                            mExampleAdapter = new CompletedAdapter(completed_tab.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(completed_tab.this);

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
        Intent detailIntent = new Intent(this, CompletedDetailActivity.class);
        completedModel clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_DATE, clickedItem.getPDate());
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