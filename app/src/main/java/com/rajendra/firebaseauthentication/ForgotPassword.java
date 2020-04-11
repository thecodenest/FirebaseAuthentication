package com.rajendra.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextView resetEmail;
    Button resetButton;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;
    ProgressBar forgotProgress;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        resetEmail = findViewById(R.id.reset_email);
        resetButton = findViewById(R.id.reset_button);
        constraintLayout = findViewById(R.id.fogotpasscontainer);
        forgotProgress = findViewById(R.id.forgotProgressBar);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = resetEmail.getText().toString();

                if(TextUtils.isEmpty(email)){

                    snackbar = Snackbar.make(constraintLayout, "Please enter your registered email address.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                if(!email.equals("")){

                    forgotProgress.setVisibility(View.VISIBLE);

                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        snackbar = Snackbar.make(constraintLayout, "Reset link successfully sent to your registered email address.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        forgotProgress.setVisibility(View.GONE);

                                    }
                                    else{
                                        snackbar = Snackbar.make(constraintLayout, "Failed to sent reset link.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        forgotProgress.setVisibility(View.GONE);


                                    }

                                }
                            });

                }



            }
        });
    }
}
