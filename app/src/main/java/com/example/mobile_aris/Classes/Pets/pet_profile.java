package com.example.mobile_aris.Classes.Pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.R;

public class pet_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        Button btn_editPet;
        btn_editPet = findViewById(R.id.btn_editPet);
        btn_editPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( pet_profile.this, edit_pet.class);
                startActivity(intent);
            }
        });
    }
}