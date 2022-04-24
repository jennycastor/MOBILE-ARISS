package com.example.mobile_aris.Classes.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Authentication.LogInFragment;
import com.example.mobile_aris.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgotpass extends AppCompatActivity implements View.OnClickListener {

    private View v;
    private EditText email;
    String token;

    public void setListeners() {
        Button reset_pass = findViewById(R.id.btn_reset);
        reset_pass.setOnClickListener(v -> {
            ForgotPass();
            Log.d("ResetPass", "Reset Pass Listener");
        });

        TextView rem_pass = findViewById(R.id.rem_pass);
        rem_pass.setOnClickListener(v -> {
            forgotpass.this.onBackPressed();
            Log.d("RemPass", "Remember Pass Listener");
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        SharedPreferences info =getSharedPreferences("user_info",MODE_PRIVATE);
        token= info.getString("access_token","");
        setListeners();
    }


    public void ForgotPass(){
//        forgot_pass = v.findViewById(R.id.forgot_pass);
        email=(EditText) findViewById(R.id.reset_email);
        final String URLForgotPass ="http://192.168.100.32:5000/api/user/auth/forgot-password";
        //creating new JSONObj
        JSONObject ji=new JSONObject();

        //insert the data from the form to JSONObj
        try {
            Log.d("email", email.getText().toString());
            ji.put("email", email.getText());
        }catch (JSONException je){
            je.printStackTrace();
        }

        RequestQueue requestQueue= Volley.newRequestQueue(forgotpass.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLForgotPass, ji, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(forgotpass.this,"Email Sent",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(forgotpass.this, LogInFragment.class);
                        startActivity(intent);
                        forgotpass.this.finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON Exception", String.valueOf(error));
                Toast.makeText(forgotpass.this,"No User found with this email you provided",Toast.LENGTH_LONG).show();
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


    @Override
    public void onClick(View view) {
//        ForgotPass();
//        Log.d("ForgotPass", "Forgot Pass Listener");
    }
}
