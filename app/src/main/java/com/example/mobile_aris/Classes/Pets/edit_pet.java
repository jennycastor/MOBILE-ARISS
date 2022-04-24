package com.example.mobile_aris.Classes.Pets;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class edit_pet extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Upload ###";

    private static int IMAGE_REQ = 1;
    private Uri imagePath;
    Map config = new HashMap();

//    private ArrayList<petModel> mExampleList;
//    private MypetRecyclerViewAdapter mExampleAdapter;

    private Button update_pet, pet_image;
    private ImageView p_avatar;
    private TextView p_id;
    private EditText p_name, p_age, p_color, p_breed;
    private Spinner p_sex, p_specie;
    private View v;
    String qr_id, token;
    String petId, petImage, pubId;

    private ImageView edit_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(edit_pet.this);
        dialog.setContentView(R.layout.activity_edit_pet);
        dialog.setTitle("Edit Pet");

//        TextView textView = (TextView)dialog.findViewById(R.id.textViewPopup);
//        textView.setText("bonjour!\My first customize popup!!");
        dialog.setCancelable(false);
        dialog.show();

        update_pet = (Button) dialog.findViewById(R.id.updatePet);
        update_pet.setOnClickListener(this);

        p_avatar = (ImageView) dialog.findViewById(R.id.image_view_detail);
        p_name = (EditText) dialog.findViewById(R.id.editname);
        p_age = (EditText) dialog.findViewById(R.id.age);
        p_color = (EditText) dialog.findViewById(R.id.color);
        p_sex = (Spinner) dialog.findViewById(R.id.text_view_gender);
        p_specie = (Spinner) dialog.findViewById(R.id.specie);
        p_breed = (EditText) dialog.findViewById(R.id.breed);

        SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
        token = info.getString("access_token", "");
        qr_id = info.getString("_id", "");

        initCongif();
        setListeners();

        SharedPreferences remember = getSharedPreferences("Edit_pet_info", Context.MODE_PRIVATE);

        String av_p = remember.getString("image", "").toString();
        String name = remember.getString("name", "").toString();
        String age = remember.getString("age", "").toString();
        String gender = remember.getString("gender", "").toString();
        String specie = remember.getString("specie", "").toString();
        String breed = remember.getString("breed", "").toString();
        String color = remember.getString("color", "").toString();

        int genpos;
                if (gender.equals("Male")){
                    genpos = 2;
                } else {
                    genpos = 1;
                }

                int spepos;
                if (specie.equals("Cat")){
                    spepos = 2;
                } else {
                    spepos = 1;
                }

                Button update_pet = dialog.findViewById(R.id.updatePet);
                ImageView editImage = dialog.findViewById(R.id.image_view_detail);
                Picasso.get().load(av_p).into(editImage);
                EditText editName = dialog.findViewById(R.id.editname);
                editName.setText(name);
                EditText editAge = dialog.findViewById(R.id.age);
                editAge.setText(age);
                Spinner editGender = dialog.findViewById(R.id.text_view_gender);
                editGender.setSelection(genpos);
                Spinner editSpecie = dialog.findViewById(R.id.specie);
                editSpecie.setSelection(spepos);
                EditText editBreed = dialog.findViewById(R.id.breed);
                editBreed.setText(breed);
                EditText editColor = dialog.findViewById(R.id.color);
                editColor.setText(color);

                p_avatar = dialog.findViewById(R.id.image_view_detail);

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


    } // end nang listener

    private void initCongif() {

        config.put("cloud_name", "movietime-app");
        config.put("api_key", "431421295175674");
        config.put("api_secret", "XVMqWc4_gz3dhBrfxN7InQLPRpg");
//        config.put("secure", true);
        MediaManager.init(edit_pet.this, config);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(edit_pet.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            ActivityCompat.requestPermissions(edit_pet.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, IMAGE_REQ);
        }

    }


        @Override
        public void onClick(View view) {
            SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences pet = getSharedPreferences("Edit_pet_info", MODE_PRIVATE);
            String user_id = info.getString("_id", "").toString();
            String pass = info.getString("password", " ").toString();
            petId = pet.getString("p_id", "");
            petImage = pet.getString("image", "");
            pubId = pet.getString("pubId", "");
            final String URLUpdate = "http://192.168.100.32:5000/api/pet/update/" + petId;

            Log.d(TAG, ": " + " button clicked");
            if (imagePath != null) {
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
                        Log.d("result data", String.valueOf(resultData));
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

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        //create Volley Request
                        RequestQueue requestQueue = Volley.newRequestQueue(edit_pet.this);

                        //CALL SIGNUP API
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URLUpdate, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("token", token);
                                Intent petIntent = new Intent(edit_pet.this, my_pets.class);
                                startActivity(petIntent);
//                            mExampleList.clear();
//                            mExampleAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Pet " + p_name.getText() + " Details Updated", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSON Exception", String.valueOf(error));
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", "Bearer " + token);
                                return params;

                            }
                        };

                        //add request to queue
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
            } else {
                Log.d("token", token);
                JSONObject jo = new JSONObject(); //equivalent to FormData in JS
                //Inserting the values in JSON object
                try {

                    jo.put("url", petImage);
                    jo.put("public_id", pubId);
                    jo.put("from", "android");
                    jo.put("name", p_name.getText());
                    jo.put("age", p_age.getText());
                    jo.put("color", p_color.getText());
                    jo.put("owner", qr_id);
                    jo.put("gender", p_sex.getSelectedItem()).toString();
                    jo.put("species", p_specie.getSelectedItem()).toString();
                    jo.put("breed", p_breed.getText()).toString();

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                //create Volley Request
                RequestQueue requestQueue = Volley.newRequestQueue(edit_pet.this);

                //CALL SIGNUP API
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URLUpdate, jo, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("token", token);
                        Intent petIntent = new Intent(edit_pet.this, my_pets.class);
                        startActivity(petIntent);
//                            mExampleList.clear();
//                            mExampleAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Pet " + p_name.getText() + " Details Updated", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON Exception", String.valueOf(error));
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer " + token);
                        return params;

                    }
                };

                //add request to queue
                requestQueue.add(jsonObjectRequest);
            }

        }


    /*
     * select the image from the gallery
     * */
    private void selectImage() {
        Intent intent=new Intent();
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
                        imagePath=data.getData();
                        Picasso.get().load(imagePath).into(p_avatar);

                    }
                }
            });

}