package com.example.italk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class register_pic_choose extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

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

        final int[] picMark = {0};
        GridAdapter gridAdapter = new GridAdapter(this, images);
        gridView = findViewById(R.id.pic_choose_grid);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                picMark[0] = position;
            }
        });

        Button completeChoose = findViewById(R.id.btn_pic_choose_complete);
        completeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getintent = getIntent();
                getintent.putExtra("picMark",Integer.toString(picMark[0]));
                setResult(REQUEST_CODE,getintent);
                finish();
            }
        });



    }
}
