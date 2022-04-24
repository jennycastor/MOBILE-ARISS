package com.example.mobile_aris.Classes.User;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.example.mobile_aris.Authentication.LoginAndSignUp;
import com.example.mobile_aris.Classes.about_aris;
import com.example.mobile_aris.Classes.contact_form;
import com.example.mobile_aris.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class update_profile extends AppCompatActivity implements View.OnClickListener {

    String id,token;
    private static final String TAG = "Upload ###";

    private static int IMAGE_REQ=1;
    private Uri imagePath;
    Map config = new HashMap();

    final Calendar myCalendar = Calendar.getInstance();
    private Button update, update_pass;
    private TextView tvsex, tvbar;
    private ImageView profile_image;
    private EditText edit_fname,fname,lname, email_add, username, phone, password, new_password, confirm_password, birthday;
    private Spinner sex, barangay;
    private CheckBox terms;
    private View v;

    private EditText edit_lname;
    private Spinner edit_sex;
    private Spinner edit_barangay;
    private EditText edit_email_add;
    private EditText edit_username;
    private EditText edit_phone;
    private EditText edit_old_password;
    private EditText edit_confirm_password;
    private EditText edit_new_password;
    private EditText edit_birthday, edit_birthday_user;
    private ImageView edit_avatar, edit_qr, edit_user_avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initCongif();
        setListeners();


        SharedPreferences info = getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");

                String av_d = info.getString("profile_url", "").toString();
                String fn1 = info.getString("firstname","").toString();
                String ln = info.getString("lastname","").toString();
                String e = info.getString("email_add","").toString();
                String b = info.getString("birthday","").toString();
                String sx = info.getString("sex", "").toString();
                String br = info.getString("barangay", "").toString();
                String u = info.getString("username","").toString();
                String pn = info.getString("phone","").toString();



        //      getting the position of spinner selected item
        int sexpos;
        if (sx.equals("Male")){
            sexpos = 2;
        } else {
            sexpos = 1;
        }

        int barpos;
        switch (br) {
            case "Bagumbayan":
                barpos = 1;
                break;
            case "Bambang":
                barpos = 2;
                break;
            case "Calzada":
                barpos = 3;
                break;
            case "Central Bicutan":
                barpos = 4;
                break;
            case "Signal Village":
                barpos = 5;
                break;
            case "Fort Bonifacio":
                barpos = 6;
                break;
            case "Hagonoy":
                barpos = 7;
                break;
            case "Ibayo-Tipas":
                barpos = 8;
                break;
            case "Katuparan":
                barpos = 9;
                break;
            case "Ligid-Tipas":
                barpos = 10;
                break;
            case "Lower Bicutan":
                barpos = 11;
                break;
            case "Maharlika Village":
                barpos = 12;
                break;
            case "Napindan":
                barpos = 13;
                break;
            case "New Lower Bicutan":
                barpos = 14;
                break;
            case "North Daang hari":
                barpos = 15;
                break;
            case "North Signal Village":
                barpos = 16;
                break;
            case "Paglingon":
                barpos = 17;
                break;
            case "Pinagsama":
                barpos = 18;
                break;
            case "San Miguel":
                barpos = 19;
                break;
            case "Santa Ana":
                barpos = 20;
                break;
            case "South Daang Hari":
                barpos = 21;
                break;
            case "South Signal Village":
                barpos = 22;
                break;
            case "Tanyag":
                barpos = 23;
                break;
            case "Tuktukan":
                barpos = 24;
                break;
            case "Upper Bicutan":
                barpos = 25;
                break;
            case "Ususan":
                barpos = 26;
                break;
            case "Wawa":
                barpos = 27;
                break;
            case "Western Bicutan":
                barpos = 28;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + br);
        }



                edit_avatar = (ImageView) findViewById(R.id.edit_avataruser);
                Picasso.get().load(av_d).into(edit_avatar);
////                edit_qr = (ImageView) findViewById(R.id.user_qr);
////                Picasso.get().load(qr).into(user_qr);
                edit_fname = (EditText) findViewById(R.id.edit_fname);
                edit_fname.setText(fn1);
                edit_lname = (EditText) findViewById(R.id.edit_lname);
                edit_lname.setText(ln);
//                tvsex = (TextView) findViewById(R.id.tvsex);
//                tvsex.setText(sx);
                edit_sex = (Spinner) findViewById(R.id.edit_sex);
                edit_sex.setSelection(sexpos);
//                tvbar = (TextView) findViewById(R.id.tvbar);
//                tvbar.setText(br);
                edit_barangay = (Spinner) findViewById(R.id.edit_barangay);
                edit_barangay.setSelection(barpos);
                edit_email_add = (EditText) findViewById(R.id.edit_email_add);
                edit_email_add.setText(e);
                edit_birthday = (EditText) findViewById(R.id.edit_user_birthday);
                edit_birthday.setText(b);
                edit_username = (EditText) findViewById(R.id.edit_username);
                edit_username.setText(u);
                edit_phone = (EditText) findViewById(R.id.edit_phone);
                edit_phone.setText(pn);


    }


    public void setListeners() {//Start of SetListener Method


        /*
         * 1. user want to click the imageview
         * 2. select the image/pdg/video file gallery
         * 3. show the preview of the image in imageview
         * 4. click the upload button to upload image to the cloudinary
         *
         * */

        edit_user_avatar = findViewById(R.id.edit_avataruser);

        edit_user_avatar.setOnClickListener(v -> {
            /*
             * 1.1. ask the user to give the media permission
             * 1.2. moving to the gallery
             *
             * */

            //1.1
            requestPermission();
            Log.d(TAG, ": "+"request permission");
        });

//        add_image.setOnClickListener(v -> {
//        });

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
        edit_birthday_user = findViewById(R.id.edit_user_birthday);
        edit_birthday_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        update = findViewById(R.id.btn_update_user);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
                String user_id = info.getString("_id", "").toString();
                String pub_id = info.getString("public_id", "").toString();
                String prof_url = info.getString("profile_url", "").toString();
                String pass = info.getString("password", " ").toString();
                final String URLUpdate = "http://192.168.100.32:5000/api/user/auth/update_profile/" + user_id;
//                String pass1, pass2;
//                pass1 = edit_new_password.getText().toString();
//                pass2 = edit_confirm_password.getText().toString();
//
//            if(pass != edit_old_password.toString()){
//                Toast.makeText(update_profile.this, "Old Password did not match", Toast.LENGTH_LONG).show();
//                }


                //Check if 2 passwords matched
//                if (pass1.equals(pass2)) {
                //create JSONObject

                //Cloudinary Upload ng avatar
                //enclosed in try and catch

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
                            new JSONObject(resultData);
                            JSONObject jo = new JSONObject(); //equivalent to FormData in JS
                            //Inserting the values in JSON object
                            try {
                                if (resultData != null) {
                                    SharedPreferences remember = getApplication().getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = remember.edit();
                                    jo.put("url", resultData.get("secure_url"));
                                    jo.put("public_id", resultData.get("public_id"));
                                    editor.putString("profile_url", resultData.get("secure_url").toString());
                                    editor.putString("public_id", resultData.get("public_id").toString());
                                    editor.apply();
                                } else {
                                    jo.put("url", "");
                                    jo.put("public_id", "");
                                }
                                jo.put("from", "android");
                                jo.put("first_name", edit_fname.getText());
                                jo.put("last_name", edit_lname.getText());
                                jo.put("birthday", edit_birthday.getText());
                                jo.put("sex", edit_sex.getSelectedItem()).toString();
                                jo.put("address", edit_barangay.getSelectedItem()).toString();
                                jo.put("email", edit_email_add.getText());
                                jo.put("phone_number", edit_phone.getText());
                                jo.put("username", edit_username.getText());
//                                jo.put("password", edit_new_password.getText());

                                Log.d("secure_url", resultData.get("secure_url").toString());
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }

                            //create Volley Request
                            RequestQueue requestQueue = Volley.newRequestQueue(update_profile.this);

                            //CALL SIGNUP API
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URLUpdate, jo, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String access_token = token;
                                        Log.d("User Updated", access_token);
                                        JSONObject qr_id = response.getJSONObject("user");
                                        Log.d("qr", qr_id.getString("_id").toString());
                                        Log.d("secure_url", resultData.get("secure_url").toString());


                                        if (access_token != null) {
                                            SharedPreferences remember = update_profile.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = remember.edit();


                                            JSONObject obj = response.getJSONObject("user");

//                                        Object image = obj.put("profile_url", resultData.get("secure_url"));
//                                        String imageUri = (image).toString();


                                            editor.putString("token", access_token);
//                                        editor.putString("_id", user_id);
//                                            Picasso.get().load(imageUri).into(profile_image);
//                                            Picasso.get().load((String) resultData.get("secure_url")).into(avatar);
                                            editor.putString("profile_url", resultData.get("secure_url").toString());
                                            editor.putString("public_id", resultData.get("public_id").toString());
                                            editor.putString("_id", obj.getString("_id").toString());
                                            editor.putString("firstname", obj.getString("first_name"));
                                            editor.putString("lastname", obj.getString("last_name"));
                                            editor.putString("birthday", obj.getString("birthday"));
                                            editor.putString("sex", obj.getString("sex"));
                                            editor.putString("barangay", obj.getString("address"));
                                            editor.putString("email_add", obj.getString("email"));
                                            editor.putString("phone", obj.getString("phone_number"));
                                            editor.putString("username", obj.getString("username"));


                                            editor.apply();
                                            Toast.makeText(update_profile.this, "User Updated Successfully", Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(update_profile.this, user_profile.class);
                                            startActivity(intent1);
                                            finish();
//                                            update_profile.this.onBackPressed();
                                        }

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
                    Log.d("secure", prof_url);
                    Log.d("public", pub_id);
                    JSONObject jo = new JSONObject(); //equivalent to FormData in JS
                    //Inserting the values in JSON object
                    try {
                        jo.put("url", prof_url);
                        jo.put("public_id", pub_id);
                        jo.put("from", "android");
                        jo.put("first_name", edit_fname.getText());
                        jo.put("last_name", edit_lname.getText());
                        jo.put("birthday", edit_birthday.getText());
                        jo.put("sex", edit_sex.getSelectedItem()).toString();
                        jo.put("address", edit_barangay.getSelectedItem()).toString();
                        jo.put("email", edit_email_add.getText());
                        jo.put("phone_number", edit_phone.getText());
                        jo.put("username", edit_username.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //create Volley Request
                    RequestQueue requestQueue = Volley.newRequestQueue(update_profile.this);

                    //CALL SIGNUP API
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URLUpdate, jo, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String access_token = token;
                                Log.d("User Updated", access_token);
                                JSONObject qr_id = response.getJSONObject("user");
                                Log.d("qr", qr_id.getString("_id").toString());


                                if (access_token != null) {
                                    SharedPreferences remember = update_profile.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = remember.edit();


                                    JSONObject obj = response.getJSONObject("user");

//                                        Object image = obj.put("profile_url", resultData.get("secure_url"));
//                                        String imageUri = (image).toString();


                                    edit.putString("token", access_token);
//                                        editor.putString("_id", user_id);
//                                            Picasso.get().load(imageUri).into(profile_image);
//                                            Picasso.get().load((String) resultData.get("secure_url")).into(avatar);
                                    edit.putString("profile_url", prof_url);
//                                    edit.putString("public_id", pub_id);
                                    edit.putString("_id", obj.getString("_id").toString());
                                    edit.putString("firstname", obj.getString("first_name"));
                                    edit.putString("lastname", obj.getString("last_name"));
                                    edit.putString("birthday", obj.getString("birthday"));
                                    edit.putString("sex", obj.getString("sex"));
                                    edit.putString("barangay", obj.getString("address"));
                                    edit.putString("email_add", obj.getString("email"));
                                    edit.putString("phone", obj.getString("phone_number"));
                                    edit.putString("username", obj.getString("username"));


                                    edit.apply();
                                    Toast.makeText(update_profile.this, "User Updated Successfully", Toast.LENGTH_LONG).show();
                                    Intent intent1 = new Intent(update_profile.this, user_profile.class);
                                    startActivity(intent1);
                                    finish();
//                                            update_profile.this.onBackPressed();
                                }

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
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", "Bearer " + token);
                            return params;

                        }
                    };

                    //add request to queue
                    requestQueue.add(jsonObjectRequest);
                }
            }

        });

        //updating password

        edit_old_password = findViewById(R.id.edit_old_password);
        edit_new_password = findViewById(R.id.edit_new_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        update_pass = findViewById(R.id.btn_update_pass);
        update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
                String user_id = info.getString("_id", "").toString();
//                String pass = info.getString("password", " ").toString();
//                Log.d(TAG, "onStart: " + pass);
                final String URLUpdatePass = "http://192.168.100.32:5000/api/user/auth/update_password/" + user_id;
                String pass1, pass2, old_pass;
//                EditText old_pass = v.findViewById(R.id.edit_old_password);
                old_pass = edit_old_password.getText().toString();
                pass1 = edit_new_password.getText().toString();
                pass2 = edit_confirm_password.getText().toString();


                    //Check if 2 passwords matched
                    if (pass1.equals(pass2)) {
                        //create JSONObject

                        //Cloudinary Upload ng avatar
                        //enclosed in try and catch

                        Log.d(TAG, ": " + " button clicked");

                        JSONObject jo = new JSONObject(); //equivalent to FormData in JS
                        //Inserting the values in JSON object
                        try {
                            jo.put("current_password", old_pass);
                            jo.put("new_password", pass1);

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        //create Volley Request
                        RequestQueue requestQueue = Volley.newRequestQueue(update_profile.this);

                        //CALL SIGNUP API
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URLUpdatePass, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String access_token = token;
                                    Log.d("User Password Updated", access_token);
                                    JSONObject qr_id = response.getJSONObject("user");
                                    Log.d("qr", qr_id.getString("_id").toString());


                                    if (access_token != null) {
                                        SharedPreferences remember = update_profile.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = remember.edit();


                                        JSONObject obj = response.getJSONObject("user");

                                        editor.putString("token", access_token);
//                                        editor.putString("password", obj.getString("password"));


                                        editor.apply();
                                        Toast.makeText(update_profile.this, "User Password Updated Successfully Please Login Again", Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(update_profile.this, LoginAndSignUp.class);
                                        startActivity(intent1);
                                        finish();
//                                            update_profile.this.onBackPressed();
                                    }

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
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", "Bearer " + token);
                                return params;

                            }
                        };

                        //add request to queue
                        requestQueue.add(jsonObjectRequest);

                    } else {
                        Toast.makeText(update_profile.this, "Passwords did not match. Please Try Again", Toast.LENGTH_LONG).show();

                    }
}

            });


    } // end nang listener


    private void initCongif() {

        config.put("cloud_name", "movietime-app");
        config.put("api_key","431421295175674");
        config.put("api_secret","XVMqWc4_gz3dhBrfxN7InQLPRpg");
//        config.put("secure", true);
        MediaManager.init(this, config);
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },IMAGE_REQ);
        }

    }


    @Override
    public void onClick(View view) {
//        update=(Button)v.findViewById(R.id.btn_update_user);
//        update.setOnClickListener(update_profile.this);

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
                        Picasso.get().load(imagePath).into(edit_user_avatar);

                    }
                }
            });

    //Formatting Date
    private void updateLabel() {
        String mFormat="MMM d, yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(mFormat, Locale.US);
        edit_birthday_user.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences remember = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editp = remember.edit();
        switch (item.getItemId()){
            case R.id.contact_form:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intent = new Intent ( update_profile.this, contact_form.class);
                startActivity(intent);
                return true;
            case R.id.about_aris:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intents = new Intent ( update_profile.this, about_aris.class);
                startActivity(intents);
                return true;
            case R.id.action_logout:
                editp.clear().apply();
                Intent intent1 = new Intent(update_profile.this, LoginAndSignUp.class);
                startActivity(intent1);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}