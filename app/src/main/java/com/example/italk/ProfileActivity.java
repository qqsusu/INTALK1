package com.example.italk;

import android.content.Intent;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference getData;
    TextView username,userage,userbday,userintro;
    Button profileChange,back2Primary;
    ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
            //get userID from primary activity
        Intent getUID = this.getIntent();
        final String userID = getUID.getStringExtra("userID");
        username = findViewById(R.id.profile_name);
        userage = findViewById(R.id.profile_age);
        userbday = findViewById(R.id.profile_address);
        userintro = findViewById(R.id.profile_content);
        profileChange = findViewById(R.id.profile_change_btn);
        back2Primary = findViewById(R.id.btn_profileBack2primary);
        userPic = findViewById(R.id.profile_picture);

        final Userdata uData = new Userdata();

        getData = FirebaseDatabase.getInstance().getReference("userData").child(userID);
        getData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    uData.AccountID = userID;
                    uData.nickname = dataSnapshot.child("nickname").getValue().toString();
                    uData.age = Integer.parseInt(dataSnapshot.child("age").getValue().toString());
                    uData.introduction = dataSnapshot.child("introduction").getValue().toString();
                    uData.birthday = dataSnapshot.child("birthday").getValue().toString();
                    uData.picPos = Integer.parseInt(dataSnapshot.child("picPos").getValue().toString());
                    username.setText(uData.nickname);
                    userage.setText(Integer.toString(uData.age));
                    userbday.setText(uData.birthday);
                    userintro.setText(uData.introduction);
                    /*System.out.println("帳號:"+uData.AccountID);
                    System.out.println("暱稱:"+uData.nickname);
                    System.out.println("自我介試:"+uData.introduction);
                    System.out.println("生日:"+uData.birthday);*/
                    switch (uData.picPos){
                        case 0:
                            userPic.setImageResource(R.drawable.student);
                            break;
                        case 1:
                            userPic.setImageResource(R.drawable.bartender);
                            break;

                            //...add others

                         default:
                             break;
                    }
                }
                else{
                    System.out.println("使用者帳號:"+userID);
                    System.out.println("資料不存在");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        profileChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfileChange = new Intent(ProfileActivity.this,Profile_change.class);
                toProfileChange.putExtra("userID",userID);
                startActivity(toProfileChange);
            }
        });
        back2Primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent back2Primary = new Intent(ProfileActivity.this,PrimaryActivity.class);
                //back2Primary.putExtra("userID",userID);
                //startActivity(back2Primary);
                finish();
            }
        });
    }
}
