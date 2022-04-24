package com.example.mobile_aris.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.R;
import com.example.mobile_aris.Classes.User.forgotpass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LogInFragment extends Fragment implements View.OnClickListener {
    private EditText email,password;
    private CheckBox remember_me;
    private Button login_button;
    SharedPreferences remember, gcount,ccount, bcount;
    String id,token;
    //    private TextView sign_up_link, forgot_pass;
    private View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListeners() {
        TextView forgot_pass = v.findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), forgotpass.class);
            startActivity(intent);
//            ForgotPass();
            Log.d("ForgotPass", "Forgot Pass Listener");
        });
    }

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        remember = getActivity().getSharedPreferences("user_info",Context.MODE_PRIVATE);
        gcount = getActivity().getSharedPreferences("gcount_info",Context.MODE_PRIVATE);
       ccount = getActivity().getSharedPreferences("ccount_info",Context.MODE_PRIVATE);
       bcount = getActivity().getSharedPreferences("bcount_info",Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//       login_button=(Button)

        v = inflater.inflate(R.layout.activity_login_user, container, false);
        login_button=(Button)v.findViewById(R.id.btn_login);
        login_button.setOnClickListener(this);


        setListeners();
        return v;
    }

    public void AttemptLogin(){
        email=(EditText)v.findViewById(R.id.email);
        password=(EditText)v.findViewById(R.id.password);
        remember_me=(CheckBox)v.findViewById(R.id.terms);
        final String URLLogin ="https://aris-backend.herokuapp.com/api/user/auth/login";


        //creating new JSONObj
        JSONObject ji=new JSONObject();

        //insert the data from the form to JSONObj
        try {
            Log.d("email",email.getText().toString());
            Log.d("password",password.getText().toString());
            ji.put("email", email.getText());
            ji.put("password",password.getText());
        }catch (JSONException je){
            je.printStackTrace();
        }

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLLogin, ji, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    token=response.getString("token");
                    getGenderCount();
                    getCatCount();
                    getBarCount();
                    JSONObject data = response.getJSONObject("user");
                    JSONObject avatar = data.getJSONObject("avatar");

                    if(token!=null){

                        SharedPreferences.Editor editor = remember.edit();

                        JSONObject obj=response.getJSONObject("user");
                        editor.putString("access_token", token);
                        if(remember_me.isChecked()){
                            editor.putString("remembered","true");
                            editor.putString("profile_url", avatar.getString("url"));
                            editor.putString("public_id", avatar.getString("public_id"));
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
                        }else{
                            editor.putString("profile_url", avatar.getString("url"));
                            editor.putString("public_id", avatar.getString("public_id"));
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
                        }
                        editor.apply();
                        Toast.makeText(getContext(),"Welcome " + obj.getString("first_name").toUpperCase(Locale.ROOT),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
//                        intent.putExtra("token", access_token);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON Exception", String.valueOf(error));
                Toast.makeText(getContext(),"Error Logging In Wrong Email or Password",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //get gender count analytics
    public void getGenderCount() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/analytics/get/genderCount", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    SharedPreferences.Editor geditor = gcount.edit();

                    JSONArray data = response.getJSONArray("genderCount");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String gen = jsonObject.getString("_id");
                        Log.d("GenData", String.valueOf(gen));
                        switch (gen) {
                            case "Male":
                                geditor.putString("mcount", jsonObject.getString("count"));
                                geditor.apply();
                                break;
                            case "Female":
                                geditor.putString("fcount", jsonObject.getString("count"));
                                geditor.apply();
                        }

//                        if (gen == "Male") {
//                            geditor.putString("mcount", jsonObject.getString("count"));
//                            geditor.apply();
//                        }
//                        if (gen == "Female") {
//                            geditor.putString("fcount", jsonObject.getString("count"));
//                            geditor.apply();
//                        }
                    }

                    Log.d("GenData", String.valueOf(data));
//                    Toast.makeText(getContext(),"Gcount",Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer "+ token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void getCatCount() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/analytics/get/categoryCount", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    SharedPreferences.Editor ceditor = ccount.edit();

                    JSONArray data = response.getJSONArray("categoryCount");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        String cat = jsonObject.getString("_id");
                        Log.d("GenData", String.valueOf(cat));
                        switch (cat) {
                            case "1":
                                ceditor.putString("ccount1", jsonObject.getString("count"));
                                ceditor.apply();
                                break;
                            case "2":
                                ceditor.putString("ccount2", jsonObject.getString("count"));
                                ceditor.apply();
                                break;
                            case "3":
                                ceditor.putString("ccount3", jsonObject.getString("count"));
                                ceditor.apply();
                                break;
                        }
                    }
                    Log.d("CatData", String.valueOf(data));
//                    Toast.makeText(getContext(),"Gcount",Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer "+ token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void getBarCount() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://aris-backend.herokuapp.com/api/analytics/get/barangayCount", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    SharedPreferences.Editor beditor = bcount.edit();

                    JSONArray data = response.getJSONArray("barangayCount");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        beditor.putString("bcount", jsonObject.getString("count"));
                    }
                    beditor.apply();
                    Log.d("BarData", String.valueOf(data));
//                    Toast.makeText(getContext(),"Gcount",Toast.LENGTH_LONG).show();
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
                params.put("Authorization", "Bearer "+ token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    //login button click
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                AttemptLogin();
                break;
        }
    }
}