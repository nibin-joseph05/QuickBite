package com.example.quickbite.ui.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickbite.R;
import com.example.quickbite.ui.home.HomeActivity;
import com.example.quickbite.ui.login.MainActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etPassword;
    RadioGroup rgGender;
    TextView tvDob;
    String dob = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        rgGender = findViewById(R.id.rgGender);
        tvDob = findViewById(R.id.tvDob);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        tvDob.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                dob = d + "/" + (m + 1) + "/" + y;
                tvDob.setText(dob);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnRegister.setOnClickListener(v -> {

            if (etName.getText().toString().trim().isEmpty()) {
                etName.setError("Enter name");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
                etEmail.setError("Enter valid email");
                return;
            }

            if (etPhone.getText().toString().trim().length() < 10) {
                etPhone.setError("Enter valid phone number");
                return;
            }

            if (etPassword.getText().toString().length() < 6) {
                etPassword.setError("Minimum 6 characters");
                return;
            }

            if (rgGender.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dob.isEmpty()) {
                Toast.makeText(this, "Select date of birth", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rb = findViewById(rgGender.getCheckedRadioButtonId());

            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putBoolean("registered", true);
            editor.putBoolean("loggedIn", true);
            editor.putString("name", etName.getText().toString());
            editor.putString("email", etEmail.getText().toString());
            editor.putString("phone", etPhone.getText().toString());
            editor.putString("password", etPassword.getText().toString());
            editor.putString("gender", rb.getText().toString());
            editor.putString("dob", dob);
            editor.apply();

            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
