package com.example.italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PrimaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        Intent getID = this.getIntent();
        final String userID = getID.getStringExtra("userID");

        ImageButton button_bottom_left = findViewById(R.id.button_bottom_Left);
        ImageButton button_top_left = findViewById(R.id.button_top_Left);
        ImageButton button_top_right = findViewById(R.id.button_top_Right);
        ImageButton logout_imgbtn = findViewById(R.id.button_bottom_Right);

        //to profile activity
        button_top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfileIntent = new Intent(PrimaryActivity.this,ProfileActivity.class);
                toProfileIntent.putExtra("userID",userID);
                startActivity(toProfileIntent);
            }
        });
            //to friend list activity
        button_top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_top_right = new Intent(PrimaryActivity.this,FriendListActivity.class);
                intent_top_right.putExtra("userID",userID);
                startActivity(intent_top_right);
            }
        });
            //to hobby set avtivity
        button_bottom_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_bottom_left = new Intent(PrimaryActivity.this,HobbyActivity.class);
                intent_bottom_left.putExtra("userID",userID);
                startActivity(intent_bottom_left);
            }
        });
        logout_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(PrimaryActivity.this,MainActivity.class);
                new AlertDialog.Builder(PrimaryActivity.this)
                        .setTitle("登出")
                        .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                        .setMessage("確認登出嗎？？？")
                        .setPositiveButton("確認" ,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Intent toMain = new Intent(PrimaryActivity.this,MainActivity.class);
                                //startActivity(toMain);
                                finish();
                            }
                        })
                        .setNegativeButton("不要好了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

    }
}
