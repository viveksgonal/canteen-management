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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class User_Signup extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onBackPressed() {
        //Nothing to be done
    }
    ProgressBar progressBar;
    EditText editTextEmail,editTextPassword,editTextConfirmPassword;
    private FirebaseAuth mAuth;

    EditText name,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__signup);
        name=(EditText)findViewById(R.id.name);
        findViewById(R.id.SignUp_Button).setOnClickListener(this);
        findViewById(R.id.SignUp_text_view).setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.email_text_view );
        editTextPassword = (EditText) findViewById(R.id.password_text_view);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirm_password_text_view);
        progressBar= findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();

    }

    public void check(){
        String mail,password,conpassword;
        mail= editTextEmail.getText().toString();
        password=editTextPassword.getText().toString();
        conpassword=editTextConfirmPassword.getText().toString();
        String Name=name.getText().toString().trim();
        if(Name.isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
            return;
        }

        if(mail.isEmpty()) {
            editTextEmail.setError("Enter an e-mail");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            editTextEmail.setError("Enter a valid e-mail");
            editTextEmail.requestFocus();
            return;
        }
        if(mail.indexOf('@')!=-1) {
            String check[] = mail.split("@", 2);
            if (!check[1].equals("ves.ac.in")) {
                editTextEmail.setError("Sign Up with your VES e-mail");
                editTextEmail.requestFocus();
            }
        }
        else{
            editTextEmail.setError("In-Correct Email");
            editTextEmail.requestFocus();
        }
        if(password.isEmpty()) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPassword.setError("Minimum length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(conpassword.isEmpty()) {
            editTextConfirmPassword.setError("Enter Confirm Password");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if(password.equals(conpassword)==false)
        {
            editTextConfirmPassword.setError("Password doesn't match");
            editTextConfirmPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(User_Signup.this,Slider.class));
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered,Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.SignUp_Button):
                check();

                break;
            case(R.id.SignUp_text_view):
                startActivity(new Intent(this, user_login.class));
        }

    }
}
