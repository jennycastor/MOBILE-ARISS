package com.example.mobile_aris.Classes.PetVaxx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_Vaxx extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private EditText p_name, p_age, p_color, p_breed, vdate, vname;
    private String pid, token;
    private Button addvaxx;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaxx);
        SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
        token = info.getString("access_token", "");
//        postvaxx();
        SharedPreferences pinfo = getApplicationContext().getSharedPreferences("petid", MODE_PRIVATE);
        pid = pinfo.getString("pid", "");

        addvaxx = findViewById(R.id.addvaxx);
        vname = findViewById(R.id.avacname);
        vdate = findViewById(R.id.avacdate);

        Button bc = findViewById(R.id.back);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(Add_Vaxx.this, my_pets.class);
                startActivity(detailIntent);
                Add_Vaxx.this.finish();
            }

        });

        setListeners();
    }

    public void setListeners() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.toInstant();
                updateLabel();
            }
        };
        //Date Onclick listener
//        vdate = findViewById(R.id.avacdate);
        vdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        addvaxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postvaxx();
            }
        });
    }


    //    @Override
//    public void onClick(View view) {
    public void postvaxx() {
        Log.d("Button Clicked", "Clicked");
        JSONObject jo = new JSONObject();
        try {
            if (vname != null) {
                jo.put("pet", pid);
                jo.put("name", vname.getText().toString());
                jo.put("date_of_vaccination", vdate.getText().toString());
            } else {
                jo.put("pet", "");
                jo.put("name", "");
                jo.put("date_of_vaccination", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "https://aris-backend.herokuapp.com/api/pet/add/vaxx-detail", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                Intent detailIntent = new Intent(Add_Vaxx.this, Pet_Vaxx.class);
                startActivity(detailIntent);
                Add_Vaxx.this.finish();
                Log.d("Vaxx", "Added Successfully");
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

    //Formatting Date
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String mFormat = "MMM. dd, yyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.US);
        vdate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}
