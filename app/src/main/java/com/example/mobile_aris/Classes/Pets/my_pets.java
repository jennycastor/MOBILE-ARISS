package com.example.mobile_aris.Classes.Pets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.example.mobile_aris.Adapter.MypetRecyclerViewAdapter;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.Details.PetDetailActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.example.mobile_aris.models.petModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class my_pets extends AppCompatActivity implements MypetRecyclerViewAdapter.OnItemClickListener {
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_PID = "pub_id";
    public static final String EXTRA_PETID = "petId";
    public static final String EXTRA_NAME = "petName";
    public static final String EXTRA_AGE = "age";
    public static final String EXTRA_GENDER = "gender";
    public static final String EXTRA_SPECIE = "specie";
    public static final String EXTRA_BREED = "breed";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_VNAME = "vacname";
    public static final String EXTRA_VDATE = "vacdate";
    public static final String EXTRA_REVAC = "revac";
    public static final String EXTRA_Vid = "vId";

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private MypetRecyclerViewAdapter mExampleAdapter;
//    private EditpetRecyclerViewAdapter mEditAdapter;
    private ArrayList<petModel> mExampleList;
    private RequestQueue mRequestQueue;

    String id,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        id = info.getString("_id", "");

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        setListeners();
        parseJSON();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.pet_refresh);

//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() { parseJSON(); }
//        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseJSON();
                refreshLayout.setRefreshing(false);
            }
        });

    }


    public void setListeners() {
        FloatingActionButton addPets;
        addPets = findViewById(R.id.addPets);

//        MaterialButton editPet;
//        editPet = findViewById(R.id.editPet);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.mypets);

        addPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
//                View dialogView= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.activity_add_pets,null);
//
//                builder.setView(dialogView);
//                builder.setCancelable(true);
//                builder.show();

                Intent intent = new Intent ( my_pets.this, Add_Pet.class);
                startActivity(intent);
            }
        });

//        editPet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(my_pets.this, edit_pet.class);
////                onItemClick();
//                startActivity(intent);
//            }
//        });

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


    private void parseJSON() {
        mExampleList.clear();
        String url = "https://aris-backend.herokuapp.com/api/pet/";

        JSONObject ji = new JSONObject();
        try {
            ji.put("id", id);
        }catch (JSONException je){
            je.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, ji,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("pets");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                //image
                                JSONArray image = hit.getJSONArray("images");
                                JSONObject imageObject = image.getJSONObject(0);
                                //vaccine status
//                                JSONArray vacc = hit.getJSONArray("vaccine_history");
//                                JSONObject vacstat = vacc.getJSONObject(0);

                                    String petId = hit.getString("_id");
                                    String petName = hit.getString("name");
                                    String imageUrl = imageObject.getString("url");
                                    String pub_id = imageObject.getString("public_id");
                                    int age = hit.getInt("age");
                                    String gender = hit.getString("gender");
                                    String specie = hit.getString("species");
                                    String breed = hit.getString("breed");
                                    String color = hit.getString("color");
                                    String date = hit.getString("created_at");


//                                String vacname = vacstat.getString("vaccine_name");
//                                String vacdate = vacstat.getString("date_of_vaccination");
//                                String revac = vacstat.getString("revaccination_schedule");

                                    mExampleList.add(new petModel(petId, imageUrl, pub_id, petName, age, date, gender, specie, breed, color));
//                                edit.apply();
                                }



                            Collections.sort(mExampleList, new Comparator<petModel>() {
                                @Override
                                public int compare(petModel item1, petModel item2) {
                                    return item1.getCreatedAt().compareToIgnoreCase(item2.getCreatedAt());

                                }
                            });
                            Collections.reverse(mExampleList);

                            mExampleAdapter = new MypetRecyclerViewAdapter(my_pets.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(my_pets.this);

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

    private void VaxxDet() {
        mExampleList.clear();
        String url = "https://aris-backend.herokuapp.com/api/pet/";

        JSONObject ji = new JSONObject();
        try {
            ji.put("id", id);
        }catch (JSONException je){
            je.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, ji,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("pets");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                //vaccine status
                                JSONArray vacc = hit.getJSONArray("vaccine_history");
                                Log.d("vaxxp_length", String.valueOf(vacc.length()));
                                for (int n = 0; n < vacc.length(); n++) {
                                    JSONObject vacstat = vacc.getJSONObject(n);

//                                    if (vacc != null) {

                                        String vacname = vacstat.getString("vaccine_name");
                                        String vacdate = vacstat.getString("date_of_vaccination");
                                        String revac = vacstat.getString("revaccination_schedule");

                                        mExampleList.add(new petModel(vacname, vacdate, revac));
//                                edit.apply();
//                                    } else {
//                                        String vacname = "";
//                                        String vacdate = "";
//                                        String revac = "";
//
//                                        mExampleList.add(new petModel(vacname, vacdate, revac));
//                                    }
                                }
                            }

//                                Collections.sort(mExampleList, new Comparator<petModel>() {
//                                    @Override
//                                    public int compare(petModel item1, petModel item2) {
//                                            if (item1 != null) {
//                                                return item1.getCreatedAt().compareToIgnoreCase(item2.getCreatedAt());
//                                            }else {
//
//                                                }
//                                            return 0;
//                                        }
//                                });
//                            Collections.reverse(mExampleList);


                            mExampleAdapter = new MypetRecyclerViewAdapter(my_pets.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(my_pets.this);

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
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, PetDetailActivity.class);
        petModel clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_PID, clickedItem.getpId());
        detailIntent.putExtra(EXTRA_PETID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getPname());
        detailIntent.putExtra(EXTRA_AGE, clickedItem.getPAge());
        detailIntent.putExtra(EXTRA_GENDER, clickedItem.getGender());
        detailIntent.putExtra(EXTRA_SPECIE, clickedItem.getSpecie());
        detailIntent.putExtra(EXTRA_BREED, clickedItem.getBreed());
        detailIntent.putExtra(EXTRA_COLOR, clickedItem.getPColor());
        detailIntent.putExtra(EXTRA_VNAME, clickedItem.getVname());
        detailIntent.putExtra(EXTRA_VDATE, clickedItem.getVdate());
        detailIntent.putExtra(EXTRA_REVAC, clickedItem.getVrevac());

        SharedPreferences rem = getApplicationContext().getSharedPreferences("petid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = rem.edit();

        editor.putString("pid", clickedItem.getId());
        editor.apply();

        Log.d("pId", clickedItem.getId());

        VaxxDet();

        startActivity(detailIntent);
    }

}