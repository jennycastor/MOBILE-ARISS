package com.example.mobile_aris.Details;

import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_AGE;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_BREED;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_COLOR;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_GENDER;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_NAME;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_REVAC;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_SPECIE;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_URL;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_VDATE;
import static com.example.mobile_aris.Classes.Pets.my_pets.EXTRA_VNAME;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.Classes.PetVaxx.Pet_Vaxx;
import com.example.mobile_aris.Classes.Pets.my_pets;
import com.example.mobile_aris.R;
import com.squareup.picasso.Picasso;

public class PetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(PetDetailActivity.this);
        dialog.setContentView(R.layout.pet_activity_detail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setTitle("Pet Details");
        dialog.setCancelable(false);
//        setContentView(R.layout.pet_activity_detail);

        Button bck = dialog.findViewById(R.id.back);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( getApplicationContext(), my_pets.class);
                PetDetailActivity.this.startActivity(intent);
//                PetDetailActivity.this.onBackPressed();
            }
        });


        dialog.show();


        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String petName = intent.getStringExtra(EXTRA_NAME);
        int age = intent.getIntExtra(EXTRA_AGE, 0);
        String gender = intent.getStringExtra(EXTRA_GENDER);
        String specie = intent.getStringExtra(EXTRA_SPECIE);
        String breed = intent.getStringExtra(EXTRA_BREED);
        String color = intent.getStringExtra(EXTRA_COLOR);
        String vname = intent.getStringExtra(EXTRA_VNAME);
        String vdate = intent.getStringExtra(EXTRA_VDATE);
        String revac = intent.getStringExtra(EXTRA_REVAC);


        Button vaxx = dialog.findViewById(R.id.vacStat);
        vaxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Pet_Vaxx.class));
            }
        });

                ImageView imageView = dialog.findViewById(R.id.image_view_detail);
                TextView textViewName = dialog.findViewById(R.id.name);
                TextView textViewAge = dialog.findViewById(R.id.age);
                TextView textViewGender = dialog.findViewById(R.id.text_view_gender);
                TextView textViewSpecie = dialog.findViewById(R.id.specie);
                TextView textViewBreed = dialog.findViewById(R.id.breed);
                TextView textViewColor = dialog.findViewById(R.id.color);

                Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
                textViewName.setText(petName);
                textViewAge.setText("Age in Months: " + age);
                textViewGender.setText("Gender: " + gender);
                textViewSpecie.setText("Specie: " + specie);
                textViewBreed.setText("Breed: " + breed);
                textViewColor.setText("Color: " + color);
    }
}

