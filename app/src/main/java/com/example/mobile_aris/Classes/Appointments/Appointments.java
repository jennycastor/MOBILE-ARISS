package com.example.mobile_aris.Classes.Appointments;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.Classes.User.user_profile;
import com.example.mobile_aris.R;
import com.example.mobile_aris.bites.bite_cases;
import com.example.mobile_aris.models.set_appointment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class Appointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.appointments);
        CardView pending_card;
                CardView completed_card;
        CardView cancelled_card;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointments:
                        return true;
                    case R.id.bitecases:
                        startActivity(new Intent(getApplicationContext(), bite_cases.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), user_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mypets:
                        startActivity(new Intent(getApplicationContext(), my_pets.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        pending_card = findViewById(R.id.pending_card);

        pending_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, Pending.class);
                startActivity(intent);
            }
        });
        completed_card = findViewById(R.id.completed_card);

        completed_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, completed_tab.class);
                startActivity(intent);
            }
        });
        cancelled_card = findViewById(R.id.cancelled_card);

        cancelled_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, cancelled_tab.class);
                startActivity(intent);
            }
        });
        FloatingActionButton SetAppointment;


        SetAppointment = findViewById(R.id.SetAppointment);

        SetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( Appointments.this, set_appointment.class);
                startActivity(intent);
            }
        });
    }
}