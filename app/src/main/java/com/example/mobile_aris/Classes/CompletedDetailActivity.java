package com.example.mobile_aris.Classes;

import static com.example.mobile_aris.Classes.Appointments.completed_tab.EXTRA_DATE;
import static com.example.mobile_aris.Classes.Appointments.completed_tab.EXTRA_PURPOSE;
import static com.example.mobile_aris.Classes.Appointments.completed_tab.EXTRA_STATUS;
import static com.example.mobile_aris.Classes.Appointments.completed_tab.EXTRA_TIME;
import static com.example.mobile_aris.Classes.Appointments.completed_tab.EXTRA_CLINIC;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.R;

public class CompletedDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(CompletedDetailActivity.this);
        dialog.setContentView(R.layout.completed_activity_detail);
        dialog.setTitle("Completed Detail");
        dialog.setCancelable(false);
//        setContentView(R.layout.pending_activity_detail);

        Intent intent = getIntent();
        String date = intent.getStringExtra(EXTRA_DATE);
        String time = intent.getStringExtra(EXTRA_TIME);
        String purpose = intent.getStringExtra(EXTRA_PURPOSE);
        String status = intent.getStringExtra(EXTRA_STATUS);
        String clinic = intent.getStringExtra(EXTRA_CLINIC);



        TextView textViewDate = dialog.findViewById(R.id.date);
        TextView textViewTime = dialog.findViewById(R.id.time);
        TextView textViewPurpose = dialog.findViewById(R.id.purpose);
        TextView textViewStatus = dialog.findViewById(R.id.status);
        TextView textViewClinic = dialog.findViewById(R.id.clinic);
        Button bck = dialog.findViewById(R.id.back);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent ( getApplicationContext(), Pet_Vaxx.class);
//                PendingDetailActivity.this.startActivity(intent);
                CompletedDetailActivity.this.onBackPressed();
            }
        });

        textViewDate.setText("Date: " + date);
        textViewTime.setText("Time: " + time);
        textViewPurpose.setText("Purpose: " + purpose);
        textViewStatus.setText("Status: " + status);
        textViewClinic.setText("Clinic: " + clinic);

        dialog.show();
    }
}
