package com.example.vescanteenstaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class
Delete_Item extends AppCompatActivity {

    Spinner rem_spin;
    Button rem_btn;
    EditText rem_et;
    String[] food_op;
    ListView listView;
    DatabaseReference dRef;
    int flag=0;
    ArrayList<String> arrayList;
    FirebaseDatabase database;
    ArrayAdapter deleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__item);
        rem_spin = (Spinner) findViewById(R.id.remove_spin);
        food_op = getResources().getStringArray(R.array.Food_Options);
        listView = (ListView) findViewById(R.id.delete_list);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,food_op);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        rem_spin.setAdapter(adapter);
        final Vector v = new Vector();
        v.add("Hello");
        v.add("Hello");
        v.add("Hello");
        rem_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String s = rem_spin.getSelectedItem().toString();
                refresh(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void refresh(final String s){

        database = FirebaseDatabase.getInstance();
        dRef = database.getInstance().getReference("Food/"+s);
        arrayList = new ArrayList();
        arrayList.clear();
        dRef.addValueEventListener(new ValueEventListener() {

            Passing_Values pv = new Passing_Values();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    pv = ds.getValue(Passing_Values.class);
                    arrayList.add(pv.getName());

                }
                deleteAdapter = new ArrayAdapter(Delete_Item.this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(deleteAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Delete_Item.this);                   //Original alert Box
                View rView = getLayoutInflater().inflate(R.layout.remove_alert, null);
                TextView message = (TextView) rView.findViewById(R.id.remove_cart_item_tv);
                Button ok = (Button) rView.findViewById(R.id.remove_cart_item_btn);
                Button cancel = (Button) rView.findViewById(R.id.cancel_cart_item_btn);
                message.setText(arrayList.get(position));
                alert.setView(rView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String e1 = arrayList.get(position);
                        Toast.makeText(Delete_Item.this,e1,Toast.LENGTH_SHORT).show();
                        dRef.child(e1).setValue(null);
                        arrayList.remove(position);
                        arrayList.clear();
                        deleteAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
            }
        });

    }
}
