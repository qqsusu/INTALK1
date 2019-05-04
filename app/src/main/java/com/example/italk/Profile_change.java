package com.example.italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_change extends AppCompatActivity {

    EditText nicknameChange,introChange;
    Button changeConfirm,changeCancel;
    private DatabaseReference getdata;
    private DatabaseReference changData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        Intent getUID = this.getIntent();
        final String userID = getUID.getStringExtra("userID");

        nicknameChange = findViewById(R.id.change_editext_name);
        introChange = findViewById(R.id.change_editext_introduction);
        changeConfirm = findViewById(R.id.change_btn_confirm);
        changeCancel = findViewById(R.id.btn_discard);

        final Userdata userData = new Userdata();
        getdata = FirebaseDatabase.getInstance().getReference("userData").child(userID);
        getdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pdataSnapshot) {
                if(pdataSnapshot.exists()){
                    userData.AccountID = userID;
                    userData.nickname = pdataSnapshot.child("nickname").getValue().toString();
                    userData.introduction = pdataSnapshot.child("introduction").getValue().toString();
                    nicknameChange.setText(userData.nickname);
                    introChange.setText(userData.introduction);
                }
                else {
                    System.out.println("GG");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        changeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Profile_change.this)
                        .setTitle("修改確認")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("確認修改嗎!!\n\n暱稱:"+nicknameChange.getText().toString()+"\n自我介紹:\n"+introChange.getText().toString())
                        .setPositiveButton("確認修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                submitChange(userID,nicknameChange.getText().toString(),introChange.getText().toString());
                                Intent Back2Profile = new Intent(Profile_change.this,ProfileActivity.class);
                                Back2Profile.putExtra("userID",userID);
                                startActivity(Back2Profile);
                            }
                        })
                        .setNegativeButton("取消修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        changeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Profile_change.this)
                        .setTitle("取消修改")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("確認取消修改嗎!!")
                        .setPositiveButton("確認取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent Back2Profile = new Intent(Profile_change.this,ProfileActivity.class);
                                Back2Profile.putExtra("userID",userID);
                                startActivity(Back2Profile);
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
    private void submitChange(String uid,String nickname,String intro){
        changData = FirebaseDatabase.getInstance().getReferenceFromUrl("https://intalk-7b460.firebaseio.com/");
        changData.child("userData").child(uid).child("nickname").setValue(nickname);
        changData.child("userData").child(uid).child("introduction").setValue(intro);
        Toast.makeText(this,"修改成功 !",Toast.LENGTH_SHORT).show();
    }
}
