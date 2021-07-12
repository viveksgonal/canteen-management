package com.example.vescanteenstaff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static android.graphics.Color.LTGRAY;

public class Rec_Order extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Manipulations m;
    int sms=0;
    String date = LocalDate.now().toString();
    String phone_num="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec__order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        final ListView list_view = (ListView) findViewById(R.id.list_of_orders);
        final DatabaseReference fRef = FirebaseDatabase.getInstance().getReference("Orders/" + date);
        m = new Manipulations();
        //ArrayList<Item> arr=m.getListOfItems();
        fRef.addValueEventListener(new ValueEventListener() {
            Item i;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                m.deleteAllItems();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    i = ds.getValue(Item.class);
                    m.addItem(i);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        call(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vector v1 = new Vector();
                v1.add("Hello");
                v1.add("Testing");
                v1.add("App");
                //final ArrayAdapter adapter = new ArrayAdapter(Rec_Order.this, R.layout.recieve_order_list, R.id.receive_order_tv, v1);
                final ArrayAdapter adapter = new ArrayAdapter(Rec_Order.this, R.layout.recieve_order_list, R.id.receive_order_tv, m.printOrderName());

                list_view.setAdapter(adapter);
                list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(Rec_Order.this);
                        View mView = getLayoutInflater().inflate(R.layout.rec_alert, null);
                        Button accept = (Button) mView.findViewById(R.id.accept);
                        Button reject = (Button) mView.findViewById(R.id.reject);
                        Button ready = (Button) mView.findViewById(R.id.ready);
                        Button pick = (Button) mView.findViewById(R.id.pick);
                        ListView alert_list = (ListView) mView.findViewById(R.id.alert_list);
                        TextView total = (TextView) mView.findViewById(R.id.total_tv);
                        TextView phone = (TextView) mView.findViewById(R.id.phone_tv);
                        total.setText(m.getItemTotal(position));
                        String item_name = m.getItemName(position);
                        String phone_num = m.getPhone(position);
                        phone.setText(phone_num);
                        setPhoneNuber(phone_num);
                        String[] temp = item_name.split("/");
                        ArrayList<String> alert_name = new ArrayList<>();
                        for (int i = 0; i < temp.length; i++) {
                            alert_name.add("  "+temp[i]+"         "+m.getItemQuantity(position).charAt(0));
                        }
                        ArrayAdapter alert_adapter = new ArrayAdapter(Rec_Order.this, R.layout.rec_alert_lay, R.id.rec_alert_list_tv, alert_name);
                        alert_list.setAdapter(alert_adapter);

                        alert.setView(mView);
                        final AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                        ready.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sms=1;
                                sendSMS();
                                alertDialog.dismiss();
                            }
                        });
                        pick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fRef.child(m.getOrderName(position)).setValue(null);
                                m.deleteItem(position);
                                adapter.notifyDataSetChanged();
                                sms=4;
                                sendSMS();
                                alertDialog.dismiss();
                            }
                        });
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sms=2;
                                sendSMS();
                                alertDialog.dismiss();
                            }
                        });
                        reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(Rec_Order.this, position + " ", Toast.LENGTH_SHORT).show();
                                fRef.child(m.getOrderName(position)).setValue(null);
                                m.deleteItem(position);
                                adapter.notifyDataSetChanged();
                                sms=3;
                                sendSMS();
                                alertDialog.dismiss();
                            }
                        });

                    }
                });
            }

        });
    }
    public void call(final FloatingActionButton button){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.callOnClick();
                call(button);
            }
        }, 20000);
    }
    protected void sendSMS() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            String s1="Hello";
            s1 = smsText(sms);
            smsManager.sendTextMessage("+91"+getPhoneNumber(), null,s1, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        String s1="Hello";
        s1 = smsText(sms);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91"+getPhoneNumber(), null,s1+" ", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                sms=0;
            }
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rec__order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            startActivity(new Intent(Rec_Order.this,Add_Item.class));
        }
        // else if (id == R.id.nav_home) {
        //   startActivity(new Intent(Rec_Order.this,Rec_Order.class));
        //}

        else if (id == R.id.nav_delete) {
            startActivity(new Intent(Rec_Order.this,Delete_Item.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setPhoneNuber(String phone){
        phone_num = phone;
    }
    public String getPhoneNumber(){
        return phone_num;
    }
    public String smsText(int sms_val){
        String text="";
        if(sms_val==1){
            text="Hey! \r\n Your Order Is Ready To Be Picked Up";
        }
        else if(sms_val==2){
            text="Hey! \r\n Your Order Has Been Accepted";
        }
        else if(sms_val==3){
            text="Hey! \r\n Your Order Has Been Rejected.Sorry for the inconvenience";
        }
        else if(sms_val==4){
            text = "Hey! \r\n Thankyou for ordering. \r\n Hope you enjoy your meal.";
        }
        return text;
    }
}

