package com.example.vescanteenstaff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText s_user,s_password;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        s_user = (EditText) findViewById(R.id.editText_user);
        s_password = (EditText) findViewById(R.id.editText_password);
        btn_submit = (Button) findViewById(R.id.button_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }
    void check()
    {
        String user = s_user.getText().toString().trim();

        if (user.isEmpty()) {
            s_user.setError("Enter E-mail");
            s_user.requestFocus();
            return;
        }
        if(user.equals("Glen") != true)
        {
            s_user.setError("User Not Registered");
            s_user.requestFocus();
            return;
        }
        String password = s_password.getText().toString().trim();
        if(password.isEmpty())
        {
            s_password.setError("Enter Password");
            s_password.requestFocus();
            return;
        }
        if(password.equals("ves@123") != true)
        {
            s_password.setError("Incorrect password");
            s_password.requestFocus();
            return;
        }
        start();
    }
    public void start()
    {
        startActivity(new Intent(this, Rec_Order.class));
    }
}
