package com.example.homework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.R;
import com.example.homework.data.User;
import com.example.homework.db.MyDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.homework.activities.SingInActivity.USERNAME_TAG;

public class RegisterActivity extends AppCompatActivity {

    public static final String EMPTY_STRING = "";
    public static final String ZERO_STRING = "0";
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = MyDatabase.getInstance(getApplicationContext());

        final Button singinButton = findViewById(R.id.alreadyaccount_button);
        final Button registerButton = findViewById(R.id.register_button);

        final EditText usernameText = findViewById(R.id.usernameText);
        final EditText passText = findViewById(R.id.passText);
        final EditText emailText = findViewById(R.id.textEmailAddress);

        singinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(RegisterActivity.this, SingInActivity.class);
                startActivity(signinIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameText.getText().toString().equals(EMPTY_STRING) && !passText.getText().toString().equals(EMPTY_STRING) && !emailText.getText().toString().equals(EMPTY_STRING)) {
                    String passEnq = md5(passText.getText().toString());
                    database.getAppDatabase().userDao().insertUser(new User(null, usernameText.getText().toString(), passEnq, emailText.getText().toString(), null, null));
                    Intent signinIntent = new Intent(RegisterActivity.this, WelcomeSplashActivity.class);
                    signinIntent.putExtra(USERNAME_TAG, usernameText.getText().toString());
                    startActivity(signinIntent);
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.incomplete), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = ZERO_STRING + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return EMPTY_STRING;
    }
}