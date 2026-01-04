package com.example.quickbite.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickbite.R;
import com.example.quickbite.ui.login.MainActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnLogout = findViewById(R.id.btnLogout);

        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);

        String data =
                "Name: " + pref.getString("name", "") + "\n\n" +
                        "Email: " + pref.getString("email", "") + "\n\n" +
                        "Phone: " + pref.getString("phone", "") + "\n\n" +
                        "Gender: " + pref.getString("gender", "") + "\n\n" +
                        "DOB: " + pref.getString("dob", "");

        tvDetails.setText(data);

        btnLogout.setOnClickListener(v -> {
            pref.edit().putBoolean("loggedIn", false).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
