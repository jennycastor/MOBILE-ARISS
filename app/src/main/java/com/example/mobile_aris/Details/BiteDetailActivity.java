package com.example.mobile_aris.Details;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.mobile_aris.Classes.PetVaxx.Edit_Vaxx;
import com.example.mobile_aris.Classes.PetVaxx.Pet_Vaxx;
import com.example.mobile_aris.Classes.Uservaxx;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.hreport;
import com.example.mobile_aris.models.UserVaxxModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BiteDetailActivity extends AppCompatActivity {
    String _id,user,clinic;
    TextView a,b,c,d,e,f,g,j,k,l,m,n;
    ImageView img;
    RecyclerView recyclerView, rw;
    RHAdapter rhAdapter;
    ArrayList<hreport> hreports = new ArrayList<hreport>();
    Button report, uvaxx;
    RadioButton a1,a2,a3;
    String type, token;
    SharedPreferences vaxx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_case_file);
        Intent here = getIntent();

        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");

        SharedPreferences vaxx = BiteDetailActivity.this.getSharedPreferences("vaxx", Context.MODE_PRIVATE);
        SharedPreferences.Editor vaxxx = vaxx.edit();

        vaxxx.putString("_id", _id);

        vaxxx.apply();

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
//        j = findViewById(R.id.dt0);
//        k = (TextView) findViewById(R.id.dt3);
//        l = (TextView) findViewById(R.id.dt7);
//        m = (TextView) findViewById(R.id.dt14);
//        n = (TextView) findViewById(R.id.dt28);
        img =findViewById(R.id.qrcode);
        String qr = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + _id;
        Picasso.get().load(qr).into(img);

        report = findViewById(R.id.SendHealthReport);
        uvaxx = findViewById(R.id.usvaxx);

        recyclerView = findViewById(R.id.r_health_report);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rhAdapter = new RHAdapter(getApplicationContext());
        recyclerView.setAdapter(rhAdapter);

        getval();

        uvaxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dialog Pakita = new Dialog(BiteDetailActivity.this);
//                Pakita.setContentView(R.layout.rec_uservaxx);
//                Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                Pakita.show();

                Intent detailIntent = new Intent(BiteDetailActivity.this, Uservaxx.class);
                startActivity(detailIntent);
                BiteDetailActivity.this.finish();
            }
        });

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

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://aris-backend.herokuapp.com/api/bitecase/file/report/", jsonitem, new Response.Listener<JSONObject>() {
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



                Pakita.show();
            }
        });
//
//        Picasso.get().load(" https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+_id).into(img);

        //history of exposure
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/bitecase/get/bite/"+ _id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("bite");
                    JSONObject jsonObject1 = new JSONObject(data.getString("history_of_exposure"));

                    DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

                    String inputT = jsonObject1.getString("date");
                    Date date = inputFormat.parse(inputT);
                    String outputT = outputFormat.format(date);

                    a.setText("Date of Exposure: "+ outputT);
                    b.setText("Place of Incedent: "+ jsonObject1.getString("place"));
                    c.setText("Type of Exposure: "+ jsonObject1.getString("type_of_exposure"));
                    d.setText("Exposure Category: "+ data.getString("exposure_category"));
                    e.setText("Vaccination Status: "+ data.getString("status_of_vaccination"));
                    f.setText("Patient Status: "+ data.getString("patient_status"));
                    g.setText("Body Part Affected: "+ jsonObject1.getString("bodypart"));


                } catch (JSONException | ParseException e) {
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
                "https://aris-backend.herokuapp.com/api/bitecase/post-vaxx/get/bitecase/" + _id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject data = new JSONObject(response.getString("details"));
                    JSONArray rprt = new JSONArray(data.getString("report"));
//                    JSONArray uv = new JSONArray(data.getString("vaxx"));
                    for (int i = 0; i < rprt.length(); i++) {
                        String name = "";
                        JSONObject rh = rprt.getJSONObject(i);
//                        JSONArray user = new JSONArray(rh.getString("user"));
                        JSONArray user = rh.getJSONArray("user");
//                        JSONArray uv = rh.getJSONArray("vaxx");
                        Log.d("user_length", String.valueOf(user.length()));
                        JSONObject userobj = user.getJSONObject(0);
                        JSONObject userdetails = user.getJSONObject(0);
                        JSONObject pic = new JSONObject(userdetails.getString("avatar"));
//                        JSONArray uv = new JSONArray(data.getString("vaxx"));
//                        JSONObject uservaxx = uv.getJSONObject(0);
//                        JSONArray vadm = new JSONArray(uservaxx.getString("admin"));
//                        Log.d("user_vaxx", String.valueOf(uv));


                        JSONArray reply = rh.getJSONArray("reply");
                        Log.d("reply_length", String.valueOf(reply.length()));
                        for (int n = 0; n < reply.length(); n++) {
                            JSONObject vacstat = reply.getJSONObject(n);

                            JSONArray vaxx = data.getJSONArray("vaxx");

//                        JSONObject uservaxx = vaxx.getJSONObject(0);
                            Log.d("vaxx_length", String.valueOf(vaxx.length()));
                            for (int v = 0; v < vaxx.length(); v++) {
                                JSONObject vacx = vaxx.getJSONObject(v);


                                JSONArray admin = rh.getJSONArray("admin");
                                Log.d("admin_length", String.valueOf(admin.length()));
                                for (int j = 0; j < admin.length(); j++) {
                                    JSONObject adm = admin.getJSONObject(j);

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
                                            rh.getString("type"),
                                            vacx.getString("day"),
                                            vacx.getString("date_injected"),
                                            vacx.getString("vaccine"),
                                            vacx.getString("lot"),
                                            vacx.getString("remarks")

                                    );
                                    hreports.add(each);
                                }
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
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
