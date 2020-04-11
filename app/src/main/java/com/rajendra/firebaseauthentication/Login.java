package com.rajendra.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button submit;
    ProgressBar progressBar;
    String email, password;
    ConstraintLayout constraintLayout;
    FirebaseAuth firebaseAuth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.login_email);
        userPassword = findViewById(R.id.login_password);
        submit = findViewById(R.id.login);
        progressBar = findViewById(R.id.loginProgressBar);
        constraintLayout = findViewById(R.id.logincontainer);

        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = userEmail.getText().toString();
                password = userPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    snackbar = Snackbar.make(constraintLayout, "Enter email !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                if(TextUtils.isEmpty(password)){
                    snackbar = Snackbar.make(constraintLayout, "Enter password !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                if(password.length() < 6){
                    snackbar = Snackbar.make(constraintLayout, "Password too short enter minimum 6 character long !", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && password.length() > 6){

                    progressBar.setVisibility(View.VISIBLE);

                    // firebase signIn

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Intent homeIntent = new Intent(Login.this, Home.class);
                                        startActivity(homeIntent);

                                    }else{

                                        progressBar.setVisibility(View.GONE);
                                        snackbar = Snackbar.make(constraintLayout, "Login failed Please check your email or password.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();

                                    }


                                }
                            });

                }


                }
        });



    }
}
