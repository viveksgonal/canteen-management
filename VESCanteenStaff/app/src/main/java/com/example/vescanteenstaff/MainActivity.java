package com.example.vescanteenstaff;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.imageslide);
        ImageView image = findViewById(R.id.imageView);
        //ImageView cover = findViewById(R.id.imageView1);
        //cover.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(MainActivity.this, Login.class); startActivity(i);
                finish(); } }, 3000);
    }
}
