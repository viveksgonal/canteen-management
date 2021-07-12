package com.example.vescanteenstaff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//Making changes
public class Add_Item extends AppCompatActivity {
    private EditText Name;
    private Button Add;
    private EditText Value;

    String[] food_op;
    DatabaseReference dRef;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);
        Name = (EditText) findViewById(R.id.values);
        Add = (Button) findViewById(R.id.add);
        Value = (EditText) findViewById(R.id.values2);
        spin = (Spinner) findViewById(R.id.spinner);
        food_op= getResources().getStringArray(R.array.Food_Options);
        dRef = FirebaseDatabase.getInstance().getReference("Food");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,food_op);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spin.setAdapter(adapter);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1 = Name.getText().toString().trim();
                String v1 = Value.getText().toString().trim();
                String o1 = spin.getSelectedItem().toString();
                Passing_Values pv1 = new Passing_Values(n1,v1);
                if(n1.trim().isEmpty()){
                    Name.setError("Enter Name Of The Item");
                    Name.requestFocus();
                    return;
                }
                if (v1.trim().isEmpty()){
                    Value.setError("Enter Price Of The Item");
                    Value.requestFocus();
                    return;
                }
                dRef.child(o1).child(n1).setValue(pv1);
                Toast.makeText(Add_Item.this,"Written"+n1+v1+"Thisss"+o1+"Here",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

