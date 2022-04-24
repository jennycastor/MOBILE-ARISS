package com.example.mobile_aris.Details;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.mobile_aris.Adapter.RHAdapter;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.hreport;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BiteDetailActivity extends AppCompatActivity {
    String _id,user,clinic;
    TextView a,b,c,d,e,f,g;
    ImageView img;
    RecyclerView recyclerView;
    RHAdapter rhAdapter;
    ArrayList<hreport> hreports = new ArrayList<hreport>();
    Button report;
    RadioButton a1,a2,a3;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_case_file);
        Intent here = getIntent();

        _id = here.getStringExtra("_id");
        user = here.getStringExtra("user");
        clinic = here.getStringExtra("clinic");

        Toast.makeText(this, "_id:"+_id, Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onCreate: "+_id);
        a = findViewById(R.id.dateOfExposure);
        b = findViewById(R.id.PlaceOfIncident);
        c = findViewById(R.id.TypeOfExposure);
        d = findViewById(R.id.ExposureCategory);
        e = findViewById(R.id.VaccinationStatus);
        f = findViewById(R.id.biteCaseStatus2);
        g = findViewById(R.id.bodypart);
        img =findViewById(R.id.qrcode);
        String qr = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + _id;
        Picasso.get().load(qr).into(img);
        report = findViewById(R.id.SendHealthReport);

        recyclerView = findViewById(R.id.r_health_report);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rhAdapter = new RHAdapter(getApplicationContext());
        recyclerView.setAdapter(rhAdapter);

        getval();

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog Pakita = new Dialog(BiteDetailActivity.this);
                Pakita.setContentView(R.layout.activity_health_report);
                Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                Log.d("clinic", "" + clinic);

                 a1 = Pakita.findViewById(R.id.radio_animaldeath);
                 a2 = Pakita.findViewById(R.id.radio_reaction);
                 a3 = Pakita.findViewById(R.id.radio_other);
                TextView descp = Pakita.findViewById(R.id.healthreportmessage);

                Button send = Pakita.findViewById(R.id.btn_SubmitHealthReport);

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject jsonitem = new JSONObject();
                        try {
                            jsonitem.put("clinic", clinic);
                            jsonitem.put("user", user);
                            jsonitem.put("bitecase", _id);
                            jsonitem.put("description", descp.getText());
                            jsonitem.put("type", type);

                            Log.d("type", "" + type);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.100.32:5000/api/bitecase/file/report/", jsonitem, new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("success");
                                    Pakita.dismiss();
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
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyMjM4ZWM2MzU1Nzk1NGQyZWExZWU0NSIsImlhdCI6MTY0ODEwOTUyMCwiZXhwIjoxNjQ4NTQxNTIwfQ.TWJ0LfeE0V8v48DojVJmxrYVrdyliYDD8WNCZLMUa0Q");
                                return params;
                            }
                        };

                        requestQueue.add(jsonObjectRequest);
                    }
                });



                Pakita.show();
            }
        });
//
//        Picasso.get().load(" https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+_id).into(img);

        //history of exposure
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.100.32:5000/api/bitecase/get/bite/"+ _id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("bite");
                    JSONObject jsonObject1 = new JSONObject(data.getString("history_of_exposure"));

                    a.setText("Date of Exposure: "+ jsonObject1.getString("date"));
                    b.setText("Place of Incedent: "+ jsonObject1.getString("place"));
                    c.setText("Type of Exposure: "+ jsonObject1.getString("type_of_exposure"));
                    d.setText("Exposure Category: "+ data.getString("exposure_category"));
                    e.setText("Vaccination Status: "+ data.getString("status_of_vaccination"));
                    f.setText("Patient Status: "+ data.getString("patient_status"));
                    g.setText("Body Part Affected: "+ jsonObject1.getString("bodypart"));


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
                params.put("Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyMzE2M2Q0MjU5ZDhkOTE2NTg4MTE3NiIsImlhdCI6MTY0OTI0NTIyOSwiZXhwIjoxNjQ5Njc3MjI5fQ.Ia5trMMzwuux8ioa9fYtfkFEmMGtJb4OZftvwTJCraI");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_animaldeath:
                type = "Death of Animal";
                break;
            case R.id.radio_reaction:
                type = "Reactions to Vaccine";
                break;
            case R.id.radio_other:
                type = "Others";
                break;

        }
    }

    //report
    public void getval(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.100.32:5000/api/bitecase/post-vaxx/get/bitecase/"+ _id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject data = new JSONObject(response.getString("details"));
                    JSONArray rprt = new JSONArray(data.getString("report"));
                    for (int i = 0; i < rprt.length(); i++) {
                        String name = "";
                        JSONObject rh = rprt.getJSONObject(i);
//                        JSONArray user = new JSONArray(rh.getString("user"));
                        JSONArray user = rh.getJSONArray("user");
                                Log.d("user_length", String.valueOf(user.length()));
                        JSONObject userobj = user.getJSONObject(0);
                        JSONObject userdetails = user.getJSONObject(0);
                        JSONObject pic = new JSONObject(userdetails.getString("avatar"));
//                        Toast.makeText(BiteDetailActivity.this, "here"+pic.getString("url"), Toast.LENGTH_SHORT).show();

                        JSONArray reply = rh.getJSONArray("reply");
                        Log.d("reply_length", String.valueOf(reply.length()));
                        for (int n = 0; n < reply.length(); n++) {
                            JSONObject vacstat = reply.getJSONObject(n);

                            JSONArray admin = rh.getJSONArray("admin");
                            Log.d("admin_length", String.valueOf(admin.length()));
                            for (int j = 0; j < admin.length(); j++) {
                                JSONObject adm = admin.getJSONObject(n);

                                name = userobj.getString("first_name") + " " + userobj.getString("last_name");
                                hreport each = new hreport(
                                        name,
                                        pic.getString("url"),
                                        rh.getString("type"),
                                        rh.getString("description"),
                                        rh.getString("createdAt"),
                                        adm.getString("admin_name"),
                                        vacstat.getString("text"),
                                        vacstat.getString("createdAt"),
                                        adm.getString("role"),
                                        rh.getString("type")

                                );
                                hreports.add(each);
                            }
                        }
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
        }){

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyMzE2M2Q0MjU5ZDhkOTE2NTg4MTE3NiIsImlhdCI6MTY0OTI0NTIyOSwiZXhwIjoxNjQ5Njc3MjI5fQ.Ia5trMMzwuux8ioa9fYtfkFEmMGtJb4OZftvwTJCraI");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
