package com.example.mobile_aris.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_aris.Classes.PetVaxx.Pet_Vaxx;
import com.example.mobile_aris.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class edit_vaxx extends AppCompatActivity {

    String qr_id, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(edit_vaxx.this);
        dialog.setContentView(R.layout.activity_edit_vaxx);
        dialog.setTitle("Edit Vaxx");

        SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
        token = info.getString("access_token", "");

//        TextView textView = (TextView)dialog.findViewById(R.id.textViewPopup);
//        textView.setText("bonjour!\My first customize popup!!");
        EditText evacname = (EditText) dialog.findViewById(R.id.evacname);
        EditText evacdate = (EditText) dialog.findViewById(R.id.evacdate);

        SharedPreferences remember = getSharedPreferences("Edit_vaxx_info", Context.MODE_PRIVATE);

        String vcname = remember.getString("vacname", "").toString();
        String vcdate = remember.getString("vacdate", "").toString();

        evacname.setText(vcname);
        evacdate.setText(vcdate);

        dialog.setCancelable(false);
        dialog.show();

        Button evaxx = (Button) dialog.findViewById(R.id.eaddpet);
        evaxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jo = new JSONObject();
                try {
                    SharedPreferences remember = getSharedPreferences("Edit_vaxx_info", Context.MODE_PRIVATE);

                    String vcid = remember.getString("_id", "").toString();
                    jo.put("vaccine_history._id", vcid);
                    jo.put("vaccine_name", evacname.getText().toString());
                    jo.put("date_of_vaccination", evacdate.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        "https://aris-backend.herokuapp.com/api/pet/update/vaxx-detail", jo, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Vaxx", "Added Successfully");
                        Intent intent = new Intent ( getApplicationContext(), Pet_Vaxx.class);
                        edit_vaxx.this.startActivity(intent);
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
                requestQueue.add(jsonObjectRequest);
            }
        });

        Button back = (Button) dialog.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( getApplicationContext(), Pet_Vaxx.class);
                edit_vaxx.this.startActivity(intent);
            }
        });


    }


}
