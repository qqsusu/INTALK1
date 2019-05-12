package com.example.italk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HobbyActivity extends AppCompatActivity {

    private Button setting_complete,backToPrimary ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        Intent getID = this.getIntent();
        final String userID = getID.getStringExtra("userID");


        backToPrimary = findViewById(R.id.hobby_back2primary);
        backToPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent back2Primary = new Intent(HobbyActivity.this,PrimaryActivity.class);
                //back2Primary.putExtra("userID",userID);
                //startActivity(back2Primary);
                finish();
            }
        });
        Button feeling_btn = findViewById(R.id.feeling_btn);
        Button sport_btn = findViewById(R.id.sport_btn);
        Button game_btn = findViewById(R.id.game_btn);
        Button food_btn = findViewById(R.id.food_btn);
        Button wearing_btn = findViewById(R.id.wearing_btn);
        Button social_btn = findViewById(R.id.social_btn);
        Button joke_btn = findViewById(R.id.joke_btn);
        Button pet_btn = findViewById(R.id.pet_btn);
        Button music_btn = findViewById(R.id.music_btn);

        feeling_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID ="feeling";
                Intent ToFeelingIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToFeelingIntent.putExtra("userID",userID);
                ToFeelingIntent.putExtra("roomID",roomID);
                startActivity(ToFeelingIntent);

            }
        });
        sport_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "sport";
                Intent ToSportIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToSportIntent.putExtra("userID",userID);
                ToSportIntent.putExtra("roomID",roomID);
                startActivity(ToSportIntent);

            }
        });
        game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "game";
                Intent ToGameIntent = new Intent (HobbyActivity.this, ChatActivity.class);
                ToGameIntent.putExtra("userID",userID);
                ToGameIntent.putExtra("roomID",roomID);
                startActivity(ToGameIntent);
            }
        });
        food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "food";
                Intent ToFoodIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToFoodIntent.putExtra("userID",userID);
                ToFoodIntent.putExtra("roomID",roomID);
                startActivity(ToFoodIntent);
            }
        });
        wearing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "wearing";
                Intent ToWearingIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToWearingIntent.putExtra("userID",userID);
                ToWearingIntent.putExtra("roomID",roomID);
                startActivity(ToWearingIntent);
            }
        });
        social_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "social";
                Intent ToSocialIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToSocialIntent.putExtra("userID",userID);
                ToSocialIntent.putExtra("roomID",roomID);
                startActivity(ToSocialIntent);
            }
        });
        joke_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "joke";
                Intent ToJokeIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToJokeIntent.putExtra("userID",userID);
                ToJokeIntent.putExtra("roomID",roomID);
                startActivity(ToJokeIntent);
            }
        });
        pet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "pet";
                Intent ToPetIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToPetIntent.putExtra("userID",userID);
                ToPetIntent.putExtra("roomID",roomID);
                startActivity(ToPetIntent);
            }
        });
        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomID = "other";
                Intent ToOtherIntent = new Intent(HobbyActivity.this, ChatActivity.class);
                ToOtherIntent.putExtra("userID",userID);
                ToOtherIntent.putExtra("roomID",roomID);
                startActivity(ToOtherIntent);
            }
        });
    }
}
