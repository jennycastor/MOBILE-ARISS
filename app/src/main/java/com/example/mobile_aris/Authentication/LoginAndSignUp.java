package com.example.mobile_aris.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobile_aris.Classes.MainActivity;
import com.example.mobile_aris.R;

public class LoginAndSignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_sign_up);
        SharedPreferences remember=getSharedPreferences("user_info",MODE_PRIVATE);
        String authenticated=remember.getString("remembered","");
        Log.d("authenticated", authenticated);
        if(authenticated.equals("true")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Fragment LoginFragment=new LogInFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_container,LoginFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }

    }


    public void FragmentSignUp(View v){
        Fragment SignUp = new SignUpFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_container,SignUp)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
    }


    public void FragmentLogin(View v){
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

}