package com.example.mobile_aris.Classes.Appointments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.R;
import com.example.mobile_aris.models.clinics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class set_appointment extends AppCompatActivity implements View.OnClickListener {
    String[] time_slots={"8-9 am","9-10 am","10-11 am","1-2 pm","2-3 pm","3-4 pm"};
    // initializing
    // FusedLocationProviderClient
    // object
    private FusedLocationProviderClient mFusedLocationClient;
    public Double longitude_here,latititude_here;

    private ArrayList<clinics> cliniclist = new ArrayList<clinics>();
    private ArrayList<clinics> cliniclist1 = new ArrayList<clinics>();

    private ArrayList<String> stringall = new ArrayList<>();
    private ArrayList<String> stringnearby = new ArrayList<>();
    String time_slot_var_here;

    private DatePicker datePicker;
    final Calendar myCalendar = Calendar.getInstance();
    private TextView dateView;
    private Button btn_date;
    private int year, month, day;



    private int PERMISSION_ID = 44;
    private Spinner spinerclinic,spinerclinic2,spinner_time;
    private TextView date,purpose,clinic,time;
    String clinic_id;
    String id,token;
    EditText indate;

    private View v;

    ArrayAdapter<String> a1,a2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        indate = (EditText) findViewById(R.id.in_date);
        purpose = (TextView) findViewById(R.id.Purpose);

        spinerclinic = (Spinner) findViewById(R.id.fa1);
        spinerclinic2 = (Spinner) findViewById(R.id.fa2);
        spinner_time = (Spinner) findViewById(R.id.add_time);

        dateView = (TextView) findViewById(R.id.in_date);
        setListeners();
//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month+1, day);

        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");

        // method to get the location
        getLastLocation();
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), time_slots[position], Toast.LENGTH_SHORT).show();
                time_slot_var_here = time_slots[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,time_slots);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spinner_time.setAdapter(aa);

        spinerclinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clinic_id = cliniclist.get(position).get_id();

                Toast.makeText(set_appointment.this, "_id:"+clinic_id, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinerclinic2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clinic_id = cliniclist1.get(position).get_id();
                Toast.makeText(set_appointment.this, "_id:"+clinic_id, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_nearby:
                if (checked)

                spinerclinic.setVisibility(View.VISIBLE);
                spinerclinic2.setVisibility(View.INVISIBLE);

                    break;
            case R.id.radio_all:
                if (checked)


                spinerclinic.setVisibility(View.INVISIBLE);
                spinerclinic2.setVisibility(View.VISIBLE);

                    break;

        }
    }

    public void setListeners() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        //Date Onclick listener
        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    } //end nang listener

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            longitude_here = location.getLongitude();
                            latititude_here = location.getLatitude();
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String url = "clinic/get/clinic?latitude="+latititude_here+"&longitude="+longitude_here;
                            Log.d("coord", "getdata: "+url);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.GET,
                                    getString(R.string.url) + url, null, response -> {
                                        try {
                                            cliniclist.clear();
                                            cliniclist1.clear();
                                            stringall.clear();
                                            stringnearby.clear();
                                            JSONObject clinics = response.getJSONObject("clinics");
                                            Log.d("response", "onComplete: "+clinics);
                                            JSONArray nearby = new JSONArray(clinics.getString("nearby"));
                                            JSONArray all = new JSONArray(clinics.getString("all"));

                                            for (int i = 0; i < nearby.length(); i++) {

                                                JSONObject clinic_1 = nearby.getJSONObject(i);
                                                clinics near_clinic = new clinics(
                                                        clinic_1.getString("_id"),
                                                        clinic_1.getString("name")
                                                );

                                                cliniclist.add(near_clinic);
                                                stringnearby.add(near_clinic.getName_clinic());

                                            }
                                            a1 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, stringnearby);
                                            a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinerclinic.setAdapter(a1);

                                            for (int i = 0; i < all.length(); i++) {

                                                JSONObject clinic_2 = all.getJSONObject(i);
                                                clinics all_clinic = new clinics(
                                                        clinic_2.getString("_id"),
                                                        clinic_2.getString("name")
                                                );

                                                cliniclist1.add(all_clinic);
                                                stringall.add(all_clinic.getName_clinic());

                                            }

                                            a2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, stringall);
                                            a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinerclinic2.setAdapter(a2);



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    },
                                    error -> {
                                        Log.e("volleyError", error.toString());
                                    }
                            );

                            requestQueue.add(jsonObjectRequest);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitude_here = mLastLocation.getLongitude();
            latititude_here = mLastLocation.getLatitude();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


    public void create_apment(View v) throws ParseException {
//        Intent intent =getIntent();

        String purpose = ((EditText) findViewById(R.id.Purpose)).getText().toString();
//        String date = ((EditText) findViewById(R.id.in_date)).getText().toString();
//        @SuppressLint("SimpleDateFormat") Date date=new SimpleDateFormat("dd/MM/yyyy").parse(((EditText) findViewById(R.id.in_date)).getText().toString());
        String time_slot = ((Spinner) findViewById(R.id.add_time)).getSelectedItem().toString();
        String clinicId = clinic_id;
        Log.d("clinic-id" , clinicId);

        JSONObject jsonitem = new JSONObject();
        try {

                jsonitem.put("clinicId", clinicId);
                jsonitem.put("date", indate.getText().toString());
                jsonitem.put("time_slot", time_slot);
                jsonitem.put("purpose", purpose);
                jsonitem.put("status", "Pending");


        }
        catch (JSONException e){
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.100.32:5000/api/appointments/add/request", jsonitem, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("success");
                    Toast.makeText(set_appointment.this, "message: " + message, Toast.LENGTH_LONG).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("runtime", "somethings wrong in json");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+ token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    //Formatting Date
    private void updateLabel() {
        String mFormat="MMM d, yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(mFormat, Locale.US);
        indate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }


    @Override
    public void onClick(View view) {

    }
}