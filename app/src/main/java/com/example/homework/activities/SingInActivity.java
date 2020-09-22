package com.example.homework.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.R;
import com.example.homework.data.User;
import com.example.homework.db.MyDatabase;
import com.example.homework.services.ForegroundService;

import java.util.List;

public class SingInActivity extends AppCompatActivity {

    public static final String USERNAME_TAG = "username";
    private MyDatabase database;
    public static final String REMEMBER_TAG = "remember";
    public static final String SERVICE_INPUT = "inputText";
    public static final String SERVICE_TEXT = "Log in or register for saving your data!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        database = MyDatabase.getInstance(getApplicationContext());

        final Button singInButton = findViewById(R.id.register_button);
        final Button guestButton = findViewById(R.id.alreadyaccount_button);
        final EditText usernameText = findViewById(R.id.usernameText);
        final EditText passwordText = findViewById(R.id.passText);
        final Button register = findViewById(R.id.registerButton);
        final CheckBox rememberMe = findViewById(R.id.remembercheckBox);

        final Intent welcomeSplashIntent = new Intent(this, WelcomeSplashActivity.class);

        singInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameValue = usernameText.getText().toString();
                String passwordValue = passwordText.getText().toString();

                String passwordEn = RegisterActivity.md5(passwordValue);

                List<User> users = database.getAppDatabase().userDao().selectUsers(usernameValue);


                boolean isOk = false;
                for (User user : users) {
                    if (user.getPassword().equals(passwordEn)) {
                        isOk = true;
                        break;
                    }
                }
                if (isOk) {
                    if (rememberMe.isChecked()) {
                        SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_TAG, Context.MODE_PRIVATE);
                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                        mEditor.putBoolean(REMEMBER_TAG, true);
                        mEditor.putString(USERNAME_TAG, usernameValue);
                        mEditor.apply();
                    }
                    welcomeSplashIntent.putExtra(USERNAME_TAG, usernameValue);
                    startActivity(welcomeSplashIntent);
                } else {
                    Toast.makeText(SingInActivity.this, getString(R.string.wrong), Toast.LENGTH_LONG).show();
                }
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(SingInActivity.this, ForegroundService.class);
                serviceIntent.putExtra(SERVICE_INPUT, SERVICE_TEXT);
                startService(serviceIntent);
                welcomeSplashIntent.putExtra(USERNAME_TAG, getString(R.string.guest));
                startActivity(welcomeSplashIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(SingInActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
