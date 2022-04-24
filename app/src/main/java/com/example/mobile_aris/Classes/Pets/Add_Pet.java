package com.example.mobile_aris.Classes.Pets;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.mobile_aris.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_Pet extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Upload ###";
    final Calendar myCalendar = Calendar.getInstance();
    private static int IMAGE_REQ = 1;
    private Uri imagePath;
    Map config = new HashMap();
    private Button save_pet, pet_image;
    private ImageView p_avatar;
    private EditText p_name, p_age, p_color, p_breed, vdate, vname;
    private Spinner p_sex, p_specie;
    private View v;
    String qr_id, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);

        save_pet = (Button) findViewById(R.id.addpet);
        save_pet.setOnClickListener(this);

        p_avatar = (ImageView) findViewById(R.id.pet_avatar);
        p_name = (EditText) findViewById(R.id.petname);
        p_age = (EditText) findViewById(R.id.age);
        p_color = (EditText) findViewById(R.id.color);
        p_sex = (Spinner) findViewById(R.id.petsex);
        p_specie = (Spinner) findViewById(R.id.specie);
        p_breed = (EditText) findViewById(R.id.breed);
        vname = (EditText) findViewById(R.id.vacname);
        vdate = (EditText) findViewById(R.id.vacdate);
        SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
        token = info.getString("access_token", "");
        qr_id = info.getString("_id", "");

        setListeners();
        initCongif();
    }

    public void setListeners() {//Start of SetListener Method


        /*
         * 1. user want to click the imageview
         * 2. select the image/pdg/video file gallery
         * 3. show the preview of the image in imageview
         * 4. click the upload button to upload image to the cloudinary
         *
         * */

        p_avatar.setOnClickListener(v -> {
            /*
             * 1.1. ask the user to give the media permission
             * 1.2. moving to the gallery
             *
             * */

            //1.1
            requestPermission();
            Log.d(TAG, ": " + "request permission");
        });

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
        vdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    } // end nang listener

    private void initCongif() {

        config.put("cloud_name", "movietime-app");
        config.put("api_key", "431421295175674");
        config.put("api_secret", "XVMqWc4_gz3dhBrfxN7InQLPRpg");
//        config.put("secure", true);
        MediaManager.init(Add_Pet.this, config);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(Add_Pet.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            ActivityCompat.requestPermissions(Add_Pet.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, IMAGE_REQ);
        }

    }

    @Override
    public void onClick(View view) {
        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d(TAG, "onStart: " + "started");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d(TAG, "onStart: " + "uploading");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d(TAG, "onStart: " + "usuccess");
                Log.d(TAG, String.valueOf(resultData));
                new JSONObject(resultData);
                Log.d("token", token);
                JSONObject jo = new JSONObject(); //equivalent to FormData in JS

                //Inserting the values in JSON object
                try {
                    if (resultData != null) {
                        jo.put("url", resultData.get("secure_url"));
                        jo.put("public_id", resultData.get("public_id"));
                    } else {
                        jo.put("url", "");
                        jo.put("public_id", "");
                    }
                    jo.put("from", "android");
                    jo.put("name", p_name.getText());
                    jo.put("age", p_age.getText());
                    jo.put("color", p_color.getText());
                    jo.put("owner", qr_id);
                    jo.put("gender", p_sex.getSelectedItem()).toString();
                    jo.put("species", p_specie.getSelectedItem()).toString();
                    jo.put("breed", p_breed.getText()).toString();

                    postvaxx();

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://192.168.100.32:5000/api/pet/add", jo, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("token", token);
                        Intent petIntent = new Intent(getApplicationContext(), my_pets.class);
                        startActivity(petIntent);
                        Toast.makeText(getApplicationContext(),"Pet " + p_name.getText() + " Added", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error is", String.valueOf(error));
                    }
                })
                    {
                    //This is for Headers If You Needed
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer "+ token);
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: " + error);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d(TAG, "onStart: " + error);
            }
        }).dispatch();
    }


    public void postvaxx() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("vaccine_name", vname.getText().toString());
            jo.put("date_of_vaccination", vdate.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://192.168.100.32:5000/api/petadd/vaxx-detail", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Vaxx", "Added Successfully");
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

    /*
     * select the image from the gallery
     * */
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");// if you want to you can use pdf/gif/video
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);

    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imagePath = data.getData();
                        Picasso.get().load(imagePath).into(p_avatar);

                    }
                }
            });

    //Formatting Date
    private void updateLabel() {
        String mFormat="MMM d, yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(mFormat, Locale.US);
        vdate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}
