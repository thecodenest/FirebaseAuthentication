package com.rajendra.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.IMediaControllerCallback;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    EditText otp;
    Button veifyOtp;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;
    String verificationId;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        otp = findViewById(R.id.otp);
        veifyOtp = findViewById(R.id.otp_verify);
        constraintLayout = findViewById(R.id.verifyContainer);

        firebaseAuth = FirebaseAuth.getInstance();

        String phoNumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phoNumber);


        veifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = otp.getText().toString().trim();

                if(code.isEmpty() || code.length() <6){

                    snackbar = Snackbar.make(constraintLayout, "Enter valid details.", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
                else{
                    verifyCode(code);
                }

            }
        });


    }

    private  void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks

        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

               otp.setText(code);

            }


        }

        @Override
        public void onCodeSent( String s,
                                PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);

            verificationId = s;

            snackbar = Snackbar.make(constraintLayout, "OTP code has been sent.", Snackbar.LENGTH_SHORT);
            snackbar.show();


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            snackbar = Snackbar.make(constraintLayout, "Verification failed." + e.toString(), Snackbar.LENGTH_SHORT);
            snackbar.show();

        }
    };

    private void signWithCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            snackbar = Snackbar.make(constraintLayout, "Login Successful.", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            Intent i=new Intent(VerifyPhoneNumber.this, Home.class);
                            startActivity(i);

                        }
                        else{
                            snackbar = Snackbar.make(constraintLayout, "Login failed.", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    }
                });

    }

    private  void verifyCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signWithCredential(credential);

    }


}
