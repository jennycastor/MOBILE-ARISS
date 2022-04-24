package com.example.mobile_aris.Classes.User;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.Authentication.LoginAndSignUp;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.Classes.about_aris;
import com.example.mobile_aris.Classes.contact_form;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class user_profile extends AppCompatActivity {

    private TextView fname, lname, sex, barangay, email_add, username, phone, password, confirm_password, birthday;
    private ImageView user_avatar;
    private ImageView user_qr;
    private Animator currentAnimator;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);

        String av = info.getString("profile_url", "").toString();
        String qr = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + info.getString("_id" , "").toString();
        String fn = info.getString("firstname","").toString();
        String ln = info.getString("lastname","").toString();
        String e = info.getString("email_add","").toString();
        String b = info.getString("birthday","").toString();
        String sx = info.getString("sex","").toString();
        String bar = info.getString("barangay","").toString();
        String u = info.getString("username","").toString();
        String pn = info.getString("phone","").toString();

        Button updateprofile;
        updateprofile = findViewById(R.id.btn_update_user);

        user_avatar = (ImageView) findViewById(R.id.user_avatar);
        Picasso.get().load(av).into(user_avatar);
        user_qr = (ImageView) findViewById(R.id.user_qr);
        Picasso.get().load(qr).into(user_qr);
        fname = (TextView) findViewById(R.id.fname_user);
        fname.setText(String.format("%s %s", fn, ln));
//        lname = (TextView) findViewById(R.id.lname_user);
//        lname.setText(ln);
        sex = (TextView) findViewById(R.id.user_sex);
        sex.setText(sx);
        barangay = (TextView) findViewById(R.id.UserBarangay);
        barangay.setText(bar);
        email_add = (TextView) findViewById(R.id.UserEmail);
        email_add.setText(e);
        birthday = (TextView) findViewById(R.id.UserBirthday);
        birthday.setText(b);
        username = (TextView) findViewById(R.id.UserUserName);
        username.setText(u);
        phone = (TextView) findViewById(R.id.UserPhoneNumber);
        phone.setText(pn);

        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( user_profile.this, update_profile.class);
                startActivity(intent);
            }
        });

        user_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.qr_code,null);

                user_qr = (ImageView) dialogView.findViewById(R.id.qr_pic);
                Picasso.get().load(qr).into(user_qr);

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
                        startActivity(new Intent(getApplicationContext(), Appointments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bitecases:
                        startActivity(new Intent(getApplicationContext(), bite_cases.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        return true;

                    case R.id.mypets:
                        startActivity(new Intent(getApplicationContext(), my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences remember = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editup = remember.edit();
        editup.putString("remembered", "false");
        editup.apply();
        switch (item.getItemId()){
            case R.id.contact_form:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intent = new Intent ( user_profile.this, contact_form.class);
                startActivity(intent);
                return true;
            case R.id.about_aris:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intents = new Intent ( user_profile.this, about_aris.class);
                startActivity(intents);
                return true;
            case R.id.action_logout:
//                editup.clear().apply();
                SharedPreferences info = getSharedPreferences("user_info", MODE_PRIVATE);
                info.getString("remembered", "false");
                Intent intent1 = new Intent(user_profile.this, LoginAndSignUp.class);
                startActivity(intent1);
                finish();
                break;
                
        }
        return super.onOptionsItemSelected(item);
    }
}