package com.example.vescanteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Menus extends Fragment {
    private ViewPager viewPager;
    //static String[] cart_items = new String[30];
    private static ArrayList<String> cart_items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
            throws NullPointerException
    {
        TabLayout tabLayout;
        View v = inflater.inflate(R.layout.activity_menus, container, false);
        tabLayout = v.findViewById(R.id.tab_layout);
        viewPager = v.findViewById(R.id.pager);
        Button v_cart = v.findViewById(R.id.cart);
        final Intent intent  = new Intent(getActivity(),Cart.class);
        new Extra();

        v_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
        //        intent.putExtra("KEY_StringName1", access_cart_list());
                Toast.makeText(getActivity(),"Data sent",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        final MyAdapter adapter = new MyAdapter( getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        return v;
    }
    void addItem(String item){
        cart_items.add(item);
    }
    ArrayList<String> access_cart_list(){
        return cart_items;
    }
    void deleteItem(int index){
        cart_items.remove(index);
    }


}

