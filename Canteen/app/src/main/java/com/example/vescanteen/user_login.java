package com.example.vescanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_login extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText mail1,password1;
    FirebaseAuth mAuth;
    @Override
    public void onBackPressed() {
        //Nothing to be done
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        findViewById(R.id.SignUp_text_view).setOnClickListener(this);
        findViewById(R.id.Login_Button).setOnClickListener(this);
        findViewById(R.id.forgot_password_text_view).setOnClickListener(this);
        mail1 = findViewById(R.id.email_text_view);
        password1 = findViewById(R.id.email_password_view);
        progressBar=findViewById(R.id.progress_Bar_2);
        mAuth = FirebaseAuth.getInstance();
    }
    public void Login()
    {
        String email =mail1.getText().toString().trim();
        String password = password1.getText().toString().trim();

        if(email.isEmpty()){
            mail1.setError("Enter an  e-mail");
            mail1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail1.setError("Enter a valid E-mail");
            mail1.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password1.setError("Enter a password");
            password1.requestFocus();
            return;
        }
        if(password.length()<6){
            password1.setError("Minimum Characters required are 6");
            password1.requestFocus();

            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);

                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Donee",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(user_login.this,Slider.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void forgot_pass(){
        progressBar.setVisibility(View.VISIBLE);
        String mail2=mail1.getText().toString().trim();
        if(mail2.isEmpty()){
            mail1.setError("Enter E-mail");
            mail1.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        mAuth.sendPasswordResetEmail(mail2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.Login_Button:
                Login();

                break;
            case R.id.SignUp_text_view:
                startActivity(new Intent(this, User_Signup.class));
                break;
            case R.id.forgot_password_text_view:
                forgot_pass();
        }
    }
}

