package com.example.mobile_aris.Classes;

import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_REVAC;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_VDATE;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_VNAME;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.R;

public class VaxxDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity_detail);

        Intent intent = getIntent();
        String vname = intent.getStringExtra(EXTRA_VNAME);
        String vdate = intent.getStringExtra(EXTRA_VDATE);
        String revac = intent.getStringExtra(EXTRA_REVAC);
    }
}
