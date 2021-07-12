package com.example.vescanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Otp extends AppCompatActivity {

    Button btnGenerateOTP, btnVerify;
    EditText etPhoneNumber, etOTP;
    String phoneNumber, otp;
    ProgressBar progressBar;
    FirebaseAuth auth;
    private String verificationCode;
    ArrayList<String> items = new ArrayList<>();
    final DatabaseReference fRef = FirebaseDatabase.getInstance().getReference("Orders");
    String total = "";
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    int rand;
    int flag = 0;
    Random rnd = new Random();
    static String mob_num_send;
    static String item_names_send;
    static String total_send;


    @Override
    public void onBackPressed() {
        //Nothing to be done
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        progressBar = findViewById(R.id.progress_bar);
        items = (ArrayList<String>) getIntent().getSerializableExtra("StringKey");
        total = getIntent().getStringExtra("Total");
        findViews();
        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViews();
                StartFirebaseLogin();
                String temp1=etPhoneNumber.getText().toString();
                phoneNumber="+91 "+temp1;
                String textChk="[6-9][0-9]{9}";
                Pattern ptrChk= Pattern.compile(textChk);
                Matcher matcher=ptrChk.matcher(temp1);
                if(matcher.matches()) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,                     // Phone number to verify
                            60,                           // Timeout duration
                            TimeUnit.SECONDS,                // Unit of timeout
                            Otp.this,        // Activity (for callback binding)
                            mCallback);
                }// OnVerificationStateChangedCallbacks
                else{
                    etPhoneNumber.setError("Invalid Number");
                    etPhoneNumber.requestFocus();
                    return;
                }
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                if(phoneNumber.isEmpty())
                {
                    etPhoneNumber.setError("Enter Phone Number");
                    etPhoneNumber.requestFocus();
                    return;
                }

                otp=etOTP.getText().toString();
                if(otp.isEmpty())
                {
                    etOTP.setError("Enter OTP");
                    etOTP.requestFocus();
                    return;
                }
                if(otp.length()!=6)
                {
                    etOTP.setError(" Invalid OTP ");
                    etOTP.requestFocus();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                VerifyOTP(credential);

            }
        });
    }
    private void VerifyOTP(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Otp.this,"Correct OTP",Toast.LENGTH_SHORT).show();
                            next();
                            startActivity(new Intent(Otp.this,bill.class));
                            // finish();
                        } else {
                          //  progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Otp.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void findViews() {
        btnGenerateOTP = findViewById(R.id.OTP_button);
        btnVerify = findViewById(R.id.verify_button);
        etPhoneNumber = findViewById(R.id.phone_text_view);
        etOTP = findViewById(R.id.OTP_text_view);
        btnVerify.setEnabled(false);
    }
    public void next() {
        int order_num = 0;
        final String date = LocalDate.now().toString();
        String temp1[] = (fRef.child(date).toString()).split("/", 5);
        final String database_date = temp1[4];
        String[] temp;
        String item_name = "";
        String item_price = "";
        String item_quantity = "";

        rand = rnd.nextInt(500);
        order_num = check(rand);
        String mobile_num;
        mobile_num = etPhoneNumber.getText().toString();
        for (int i = 0; i < items.size(); i++) {
            temp = items.get(i).split("/", 3);
            item_name += temp[0] + "/";
            item_price += temp[1] + "/";
            item_quantity += temp[2] + "/";
            Item o1 = new Item(item_name, item_price, item_quantity, total, Integer.toString(order_num), mobile_num);
            fRef.child(date).child(Integer.toString(order_num)).setValue(o1);
            Toast.makeText(Otp.this,"Order placed",Toast.LENGTH_SHORT).show();
            btnVerify.setEnabled(false);
            mob_num_send = mobile_num.toString();
            item_names_send = item_name;
            total_send = total.toString();
        }
    }
    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Otp.this,"verification completed",Toast.LENGTH_SHORT).show();
                next();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
             //   progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Otp.this,"verification failed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
//                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Otp.this,"Code sent",Toast.LENGTH_SHORT).show();
                btnVerify.setEnabled(true);
            }
        };
    }

    public int check(final int temp)   //To check if order number is not already present in the Database
    {
        final String date = LocalDate.now().toString();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Orders/" + date);
        flag = 0;
        dRef.addValueEventListener(new ValueEventListener() {
            Item i1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    i1 = ds.getValue(Item.class);
                    if (Integer.toString(temp).equals(i1.getOrder_num())) {
                        flag = 1;
                    }
                    //Toast.makeText(Otp.this, "Here" + i1.getOrder_num(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (flag == 1){
            int temp1 = rnd.nextInt(500);
            check(temp1);
        }
        return temp;
    }
    public String getNumber(){
        return mob_num_send;
    }
    public String getItems(){
        return item_names_send;
    }
    public String getTotal(){
        return total_send;
    }
}
