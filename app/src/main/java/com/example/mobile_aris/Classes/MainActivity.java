package com.example.mobile_aris.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mobile_aris.Authentication.LoginAndSignUp;
import com.example.mobile_aris.Classes.Appointments.Appointments;
import com.example.mobile_aris.Classes.PetVaxx.Pet_Vaxx;
import com.example.mobile_aris.Classes.Pets.edit_pet;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private TextView mcount, fcount, cc1,cc2,cc3, barcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        CardView Export_card, bar_card, about_card;

        getGCount();
        getCCount();
        getBCount();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.appointments:
                        startActivity(new Intent(MainActivity.this, Appointments.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bitecases:
                        startActivity(new Intent(MainActivity.this, bite_cases.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this, user_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mypets:
                        startActivity(new Intent(MainActivity.this, my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }

        });
        Export_card = findViewById(R.id.Export_card);

        Export_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, announcement.class);
                startActivity(intent);
            }
        });

        bar_card = findViewById(R.id.barcard);

        bar_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, barcount.class);
                startActivity(intent);
            }
        });


        about_card = findViewById(R.id.about_card);

        about_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent ( MainActivity.this, barcount.class);
//                startActivity(intent);
                Dialog Pakita = new Dialog(MainActivity.this);
                Pakita.setContentView(R.layout.about_aris_new);
                Pakita.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                Button bc = Pakita.findViewById(R.id.back);
                bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent petIntent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(petIntent);
//                        onBackPressed();
                        Pakita.dismiss();
                    }
                });

                FloatingActionButton scup;
                scup = Pakita.findViewById(R.id.scup);
                scup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScrollView sccc = Pakita.findViewById(R.id.sccc);
                        sccc.smoothScrollTo(0,0);
                    }
                });

                Pakita.show();
                Pakita.setCancelable(false);
            }
        });
    }

    public void getGCount() {
        SharedPreferences gcount = getSharedPreferences("gcount_info",Context.MODE_PRIVATE);

        String mc = gcount.getString("mcount", "").toString();
        Log.d("mcount", mc);

        mcount = (TextView) findViewById(R.id.mcc);
        mcount.setText(mc);

        String fc = gcount.getString("fcount", "").toString();

        fcount = (TextView) findViewById(R.id.fcc);
        fcount.setText(fc);
    }

    public void getCCount() {
        SharedPreferences ccount = getSharedPreferences("ccount_info",Context.MODE_PRIVATE);

        String cc = ccount.getString("ccount1", "").toString();
        String ccc = ccount.getString("ccount2", "").toString();
        String cccc = ccount.getString("ccount3", "").toString();

        cc1 = (TextView) findViewById(R.id.cc1);
        cc1.setText(cc);

        cc2 = (TextView) findViewById(R.id.cc2);
        cc2.setText(ccc);

        cc3 = (TextView) findViewById(R.id.cc3);
        cc3.setText(cccc);
    }

    public void getBCount() {
        SharedPreferences bcount = getSharedPreferences("bcount_info", Context.MODE_PRIVATE);

        String bc = bcount.getString("bcount", "").toString();

//        barcount = (TextView) findViewById(R.id.cc2);
//        barcount.setText(bc);
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
        SharedPreferences.Editor editor = remember.edit();
        switch (item.getItemId()){
            case R.id.contact_form:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intent = new Intent ( MainActivity.this, contact_form.class);
                startActivity(intent);
                return true;
            case R.id.about_aris:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                Intent intents = new Intent ( MainActivity.this, about_aris.class);
                startActivity(intents);
                return true;
            case R.id.action_logout:
                editor.clear().apply();
                Intent intent1 = new Intent(MainActivity.this, LoginAndSignUp.class);
                startActivity(intent1);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}