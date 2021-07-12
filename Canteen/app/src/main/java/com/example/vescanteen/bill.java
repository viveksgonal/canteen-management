package com.example.vescanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class bill extends AppCompatActivity {

    TextView mob_tv,total_tv;
    String[] arrItems;
    ListView listView ;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(bill.this,user_login.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        final String date = LocalDate.now().toString();
        mob_tv = (TextView) findViewById(R.id.mob_tv);
        total_tv = (TextView) findViewById(R.id.total_tv);
        listView = (ListView) findViewById(R.id.listView);
        Otp o = new Otp();
        String items = o.getItems();
        arrItems=items.split("/");
        String total = o.getTotal();
        String mob = o.getNumber();
        total_tv.setText(total);
        mob_tv.setText(mob);
        ArrayAdapter adapter = new ArrayAdapter(bill.this,android.R.layout.simple_list_item_1,arrItems);
        listView.setAdapter(adapter);
    }
}
