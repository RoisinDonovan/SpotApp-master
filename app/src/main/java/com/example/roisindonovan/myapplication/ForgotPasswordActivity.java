package com.example.roisindonovan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Roisin Donovan on 19/06/2017.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetPass;
    private TextView btnBack;
    private RelativeLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        input_email = (EditText) findViewById(R.id.forgot_email);
        btnResetPass = (Button) findViewById(R.id.forgot_btn_reset);
        btnBack = (TextView) findViewById(R.id.forgot_btn_back);
        activity_forgot = (RelativeLayout) findViewById(R.id.activity_forgot_password);

        btnResetPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        //database reference
        auth = FirebaseAuth.getInstance();
    }

//If forgot password input email
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.forgot_btn_back) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else if (view.getId() == R.id.forgot_btn_reset)
        {
            resetPassword(input_email.getText().toString());

        }
    }
//Sending email to user
    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar snackBar = Snackbar.make (activity_forgot, "We have password reset to your email: " +email,Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                        else {
                            Snackbar snackBar = Snackbar.make (activity_forgot, "Failed",Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }

                    }
                });
    }
}
