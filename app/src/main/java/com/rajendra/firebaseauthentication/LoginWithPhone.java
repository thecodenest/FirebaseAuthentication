package com.rajendra.firebaseauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class LoginWithPhone extends AppCompatActivity {

    Spinner spinner;

    EditText phoneNumber;
    Button sendOtp;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);

        spinner = findViewById(R.id.spinner);
        phoneNumber = findViewById(R.id.phone_number);
        sendOtp = findViewById(R.id.send_otp_button);
        constraintLayout = findViewById(R.id.otpContainer);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String mobile = phoneNumber.getText().toString().trim();

                if(mobile.isEmpty() && phoneNumber.length() < 10){

                    snackbar = Snackbar.make(constraintLayout, "Enter valid details.", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
                else {

                    String phone = "+" + code + mobile;

                    Intent i = new Intent(LoginWithPhone.this, VerifyPhoneNumber.class);
                    i.putExtra("phonenumber", phone);
                    startActivity(i);
                }

            }
        });


    }
}
