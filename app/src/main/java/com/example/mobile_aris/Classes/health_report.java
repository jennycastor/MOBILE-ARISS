package com.example.mobile_aris.Classes;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_aris.R;

public class health_report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_animaldeath:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_reaction:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_other:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}