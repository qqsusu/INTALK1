package com.example.italk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class register_pic_choose extends AppCompatActivity {

    GridView gridView;

    int[] images ={
            R.drawable.student,
            R.drawable.bartender,
            R.drawable.detective,
            R.drawable.chef,
            R.drawable.electrician,
            R.drawable.engineer,
            R.drawable.farmer,
            R.drawable.joker,
            R.drawable.athlete,
            R.drawable.nurse,
            R.drawable.pilot,
            R.drawable.avatar,
            R.drawable.police,
            R.drawable.soldier,
            R.drawable.dj,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pic_choose);

        gridView = findViewById(R.id.pic_choose_grid);
        GridAdapter gridAdapter = new GridAdapter(this, images);
        gridView.setAdapter((gridAdapter));





    }
}
