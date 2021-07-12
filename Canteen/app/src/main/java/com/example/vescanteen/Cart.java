package com.example.vescanteen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final TextView total;

            ListView order_list;
            final Menus m = new Menus();
            final ArrayList<String> items = new ArrayList<>();
            final ArrayAdapter<String> adapter;
            Button proceed = (Button) findViewById(R.id.proceed_btn);
            final Vector item_name_v = new Vector();
            final Vector item_price_v = new Vector();
            final Vector item_quantity_v = new Vector();
            final Vector item_v = new Vector();
            String item_name;
            String item_price;
            String item_quantity;
            String temp[];
            int sum = 0;

            total = (TextView) findViewById(R.id.total);
            order_list = (ListView) findViewById(R.id.cart_list_view);
        for (int i = 0; i < m.access_cart_list().size(); i++) {
            if (m.access_cart_list().get(i) == null) {
                break;
            }
            items.add(m.access_cart_list().get(i));
            temp = m.access_cart_list().get(i).split("/", 3);
            item_name = temp[0];
            item_price = temp[1];
            item_quantity = temp[2];

            item_name_v.add(item_name);
            item_price_v.add(item_price);
            item_quantity_v.add(item_quantity);
            item_v.add(item_name+"\n  "+item_quantity);

            sum = totalPrice(sum, Integer.parseInt(item_price), Integer.parseInt(item_quantity));
            }
            total.setText("Total = " + sum);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item_v);
            order_list.setAdapter(adapter);
            order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);                   //Original alert Box
                    View rView = getLayoutInflater().inflate(R.layout.remove_item_alert, null);
                    TextView message = (TextView) rView.findViewById(R.id.remove_cart_item_tv);
                    Button ok = (Button) rView.findViewById(R.id.remove_cart_item_btn);
                    Button cancel = (Button) rView.findViewById(R.id.cancel_cart_item_btn);
                    message.setText("Remove " + item_name_v.get(position) + "?");
                    alert.setView(rView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    Objects.requireNonNull(alertDialog.getWindow()).setLayout(1000, 500);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name = item_name_v.get(position).toString();
                            int index = item_name_v.indexOf(name);
                            item_name_v.remove(index);
                            item_price_v.remove(index);
                            item_quantity_v.remove(index);
                            item_v.remove(index);
                            items.remove(index);
                            m.deleteItem(index);
                            adapter.notifyDataSetChanged();
                            int sum1 = 0;
                            for (int i = 0; i < item_name_v.size(); i++) {
                                sum1 = totalPrice(sum1, Integer.parseInt(item_price_v.get(i).toString()), Integer.parseInt(item_quantity_v.get(i).toString()));
                            }
                            total.setText("Total = " + sum1);
                            Toast.makeText(Cart.this, sum1 + " ", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(total.getText().equals("Total = 0")){
                        Toast.makeText(Cart.this,"Cart is empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(Cart.this, Otp.class);
                        intent.putExtra("StringKey", items);
                        intent.putExtra("Total", total.getText());
                        startActivity(intent);
                    }
                }
            });
        }
    public int totalPrice ( int t1, int price, int quantity){
        t1 = (price * quantity) + t1;
        return t1;
    }
}

