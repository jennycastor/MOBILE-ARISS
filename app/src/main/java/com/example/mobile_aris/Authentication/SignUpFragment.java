package com.example.mobile_aris.Authentication;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Upload ###";

    private static int IMAGE_REQ=1;
    private Uri imagePath;
    Map config = new HashMap();

    final Calendar myCalendar = Calendar.getInstance();
    private Button register, add_image;
    private ImageView profile_image, avatar;
    private EditText fname,lname, email_add, username, phone, password, confirm_password, birthday;
    private Spinner sex, barangay, SexSpinner, BarSpinner;
    private CheckBox terms;
    TextView name;
    SharedPreferences sp, LastSelect, LastSelectbar;
    //    private Bitmap bitmap=null;
//    Context c;
    private View v;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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

        profile_image.setOnClickListener(v -> {
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
        birthday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                           }
//        });


    } // end nang listener


    private void initCongif() {

        config.put("cloud_name", "movietime-app");
        config.put("api_key","431421295175674");
        config.put("api_secret","XVMqWc4_gz3dhBrfxN7InQLPRpg");
//        config.put("secure", true);
        MediaManager.init(getActivity().getApplicationContext(), config);
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },IMAGE_REQ);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.activity_register_user, container, false);

        register=(Button)v.findViewById(R.id.btn_register);
        register.setOnClickListener(this);

        profile_image=(ImageView)v.findViewById(R.id.avatar);
        fname=(EditText)v.findViewById(R.id.fname);
        lname=(EditText)v.findViewById(R.id.lname);
        birthday =(EditText)v.findViewById(R.id.user_birthday);
        sex = (Spinner)v. findViewById(R.id.sex);
        barangay= (Spinner) v.findViewById(R.id.barangay);
        email_add=(EditText)v.findViewById(R.id.email_add);
        username=(EditText)v.findViewById(R.id.username);
        phone=(EditText)v.findViewById(R.id.phone);
        password=(EditText)v.findViewById(R.id.password);
        confirm_password=(EditText)v.findViewById(R.id.confirm_password);
        avatar = (ImageView) v.findViewById(R.id.user_avatar);


        setListeners();
        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//        sp = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        return v;

    }


    //Sign-Up process using Volley
    @Override
    public void onClick(View view) {

        final String URLSignUp="https://aris-backend.herokuapp.com/api/user/auth/register";
        String pass1,pass2;
        pass1=password.getText().toString();
        pass2=confirm_password.getText().toString();


        //Check if 2 passwords matched
        if(pass1.equals(pass2)){
            //create JSONObject

            //Cloudinary Upload ng avatar
            //enclosed in try and catch

            Log.d(TAG, ": "+" button clicked");

            MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart: "+"started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart: "+"uploading");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    Log.d(TAG, "onStart: "+"usuccess");
//                    Log.d(TAG, String.valueOf(resultData));
                    new JSONObject(resultData);
                    JSONObject jo=new JSONObject(); //equivalent to FormData in JS
                    //Inserting the values in JSON object
                    try{
                        if(resultData!=null){
                            jo.put("profile_url", resultData.get("secure_url"));
                            jo.put("profile_id", resultData.get("public_id"));
                        }else {
                            jo.put("profile_url","");
                            jo.put("profile_id","");
                        }
                        jo.put("from", "android");
                        jo.put("first_name",fname.getText());
                        jo.put("last_name",lname.getText());
                        jo.put("birthday", birthday.getText());
                        jo.put("sex", sex.getSelectedItem()).toString();
                        jo.put("address", barangay.getSelectedItem()).toString();
//                        jo.put("address", barangay.getSelectedItem()).toString();
                        jo.put("email",email_add.getText());
                        jo.put("phone_number",phone.getText());
                        jo.put("username",username.getText());
                        jo.put("password",password.getText());

                    }catch (JSONException jsonException){
                        jsonException.printStackTrace();
                    }

                    //create Volley Request
                    RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());

                    //CALL SIGNUP API
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URLSignUp, jo, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Success_SignUP", response.getString("token").toString());
                                String access_token = response.getString("token");
                                JSONObject qr_id = response.getJSONObject("user");
                                Log.d("qr", qr_id.getString("_id").toString());
//                                String user_id = qr_id.getString("_id");




                                if(access_token!=null) {
                                    SharedPreferences remember = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = remember.edit();


                                    JSONObject obj = response.getJSONObject("user");

//                                        Object image = obj.put("profile_url", resultData.get("secure_url"));
//                                        String imageUri = (image).toString();


                                    editor.putString("token", access_token);
//                                        editor.putString("_id", user_id);
//                                        Picasso.get().load(imageUri).into(profile_image);
//                                        Picasso.get().load((String) resultData.get("secure_url")).into(avatar);
                                    editor.putString("profile_url", String.valueOf(resultData.get("secure_url")));
                                    editor.putString("_id",obj.getString("_id").toString());
                                    editor.putString("firstname",obj.getString("first_name"));
                                    editor.putString("lastname",obj.getString("last_name"));
                                    editor.putString("birthday",obj.getString("birthday"));
                                    editor.putString("sex", obj.getString("sex"));
                                    editor.putString("barangay", obj.getString("address"));
                                    editor.putString("email_add",obj.getString("email"));
                                    editor.putString("phone",obj.getString("phone_number"));
                                    editor.putString("username",obj.getString("username"));
                                    editor.putString("password",obj.getString("password"));



                                    editor.apply();
                                    Toast.makeText(getContext(), "User Registered! Please Log-in", Toast.LENGTH_LONG).show();
                                    getActivity().onBackPressed();
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
                    });

                    //add request to queue
                    requestQueue.add(jsonObjectRequest);
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }
            }).dispatch();

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Passwords did not match. Please Try Again",Toast.LENGTH_LONG).show();

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
                        Picasso.get().load(imagePath).into(profile_image);

                    }
                }
            });


    //Formatting Date
    private void updateLabel() {
        String mFormat="MMM d, yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(mFormat, Locale.US);
        birthday.setText(simpleDateFormat.format(myCalendar.getTime()));
    }


}