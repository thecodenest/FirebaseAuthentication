package com.rajendra.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSetting extends AppCompatActivity {


    EditText updatedEmail, updatedPassword;
    Button changeEmail, changePassword, removeAccount;
    ProgressBar accountSettingProgress;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        changeEmail = findViewById(R.id.change_email);
        changePassword = findViewById(R.id.change_password);
        removeAccount = findViewById(R.id.delete_account);
        accountSettingProgress = findViewById(R.id.accountsettin_progress);
        updatedEmail = findViewById(R.id.change_email_text);
        updatedPassword = findViewById(R.id.change_password_text);
        constraintLayout = findViewById(R.id.settingContainer);

        //get firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null){

                    Intent i = new Intent(AccountSetting.this, MainActivity.class );
                    startActivity(i);
                    finish();
                }

            }
        };

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                accountSettingProgress.setVisibility(View.VISIBLE);

                if(updatedEmail.getText().toString().trim().equals("")){

                    snackbar = Snackbar.make(constraintLayout, "Enter Email.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    accountSettingProgress.setVisibility(View.GONE);

                }

                if(user != null && !updatedEmail.getText().toString().trim().equals("")){

                    user.updateEmail(updatedEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        snackbar = Snackbar.make(constraintLayout, "Email updated successfully.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);
                                    }else{

                                        snackbar = Snackbar.make(constraintLayout, "Failed to update email. Please try gain later.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);
                                    }

                                }
                            });

                }


            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                accountSettingProgress.setVisibility(View.VISIBLE);

                if(changePassword.getText().toString().trim().equals("")){

                    snackbar = Snackbar.make(constraintLayout, "Enter Password.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    accountSettingProgress.setVisibility(View.GONE);

                }

                if(changePassword.getText().toString().length() < 7){

                    snackbar = Snackbar.make(constraintLayout, "Please enter minimum 7 character password.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    accountSettingProgress.setVisibility(View.GONE);

                }

                if(user != null && changePassword.getText().toString().length() >= 7){

                    user.updatePassword(updatedPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        snackbar = Snackbar.make(constraintLayout, "Password updated successfully.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);
                                    }else{
                                        snackbar = Snackbar.make(constraintLayout, "Failed to update password.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);
                                    }

                                }
                            });

                }

            }
        });

        removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                accountSettingProgress.setVisibility(View.VISIBLE);

                if(user != null){

                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        snackbar = Snackbar.make(constraintLayout, "Your account has been deleted successfully.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);

                                        Intent i = new Intent(AccountSetting.this, MainActivity.class);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        snackbar = Snackbar.make(constraintLayout, "Failed to delete account.", Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                        accountSettingProgress.setVisibility(View.GONE);
                                    }

                                }
                            });

                }

            }
        });


    }
}











