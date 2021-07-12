package com.example.vescanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Slider extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        //Nothing to be done
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmengt_container, new Menus()).commit();
        }
    }