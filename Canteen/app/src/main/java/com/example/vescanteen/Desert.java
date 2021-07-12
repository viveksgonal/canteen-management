package com.example.vescanteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class Desert extends ListFragment {
    ArrayList<String> item_list=new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayAdapter<String> adapter;
    Passing_Values pv2;
    Vector selected_items = new Vector();
    Vector selected_item_price = new Vector();
    Menus m = new Menus();
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String s = "Food/Desert";
        item_list.clear();
        selected_item_price.clear();
        selected_items.clear();
        ret(s,getActivity());

    }
    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        // TODO implement some logic
        onclick(position);
    }
    public void ret(String s1, final Activity activity){
        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference(s1+"");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() )
                {
                    pv2 = ds.getValue(Passing_Values.class);
                    addItemToList(pv2.getName().toString()+"\n"+"  ₹"+pv2.getValue().toString());
                    selected_items.add(pv2.getName());
                    selected_item_price.add(pv2.getValue());
                }
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,accessList());
                setListAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onclick(final int pos){
        final AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog,null);
        final NumberPicker pick = (NumberPicker) mView.findViewById(R.id.numberPicker);
        Button ok = (Button)mView.findViewById(R.id.ok);
        Button cancel = (Button)mView.findViewById(R.id.cancel);
        TextView title = (TextView) mView.findViewById(R.id.title);
        final TextView price = (TextView) mView.findViewById(R.id.price);

        title.setKeyListener(null);
        price.setText(selected_item_price.elementAt(pos).toString());

        alert.setView(mView);
        pick.setMinValue(1);
        pick.setMaxValue(9);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        title.setText(selected_items.elementAt(pos).toString());
        pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                int p1 = Integer.parseInt(selected_item_price.elementAt(pos).toString())*pick.getValue();
                price.setText(p1+" ");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { alertDialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item_quantity = pick.getValue();
                String item_name =  selected_items.elementAt(pos).toString();
                int item_price = Integer.parseInt(selected_item_price.elementAt(pos).toString());
                m.addItem(item_name+"/"+item_price+"/"+item_quantity);
                alertDialog.dismiss();
            }
        });

    }
    public void addItemToList(String item){
        item_list.add(item);
    }
    public ArrayList<String> accessList(){
        return item_list;
    }


}
