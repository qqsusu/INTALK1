package com.example.italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_change extends AppCompatActivity {

    EditText nicknameChange,introChange;
    Button changeConfirm,changeCancel,changePic;
    ImageView userPicture;
    private DatabaseReference getdata;
    private DatabaseReference changData;
    private static final int REQUEST_CODE = 1;
    int picPos = 0;

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
        userPicture = findViewById(R.id.iv_pic);
        changePic = findViewById(R.id.btn_choose_pic);

        final Userdata userData = new Userdata();
        getdata = FirebaseDatabase.getInstance().getReference("userData").child(userID);
        getdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pdataSnapshot) {
                if(pdataSnapshot.exists()){
                    userData.AccountID = userID;
                    userData.nickname = pdataSnapshot.child("nickname").getValue().toString();
                    userData.introduction = pdataSnapshot.child("introduction").getValue().toString();
                    picPos = userData.picPos = Integer.parseInt(pdataSnapshot.child("picPos").getValue().toString());
                    nicknameChange.setText(userData.nickname);
                    introChange.setText(userData.introduction);
                    switch (userData.picPos){
                        case 0:
                            userPicture.setImageResource(R.drawable.student);
                            break;
                        case 1:
                            userPicture.setImageResource(R.drawable.bartender);
                            break;
                        case 2:
                            userPicture.setImageResource(R.drawable.detective);
                            break;
                        case 3:
                            userPicture.setImageResource(R.drawable.chef);
                            break;
                        case 4:
                            userPicture.setImageResource(R.drawable.electrician);
                            break;
                        case 5:
                            userPicture.setImageResource(R.drawable.student);
                            break;
                        case 6:
                            userPicture.setImageResource(R.drawable.farmer);
                            break;
                        case 7:
                            userPicture.setImageResource(R.drawable.joker);
                            break;
                        case 8:
                            userPicture.setImageResource(R.drawable.athlete);
                            break;
                        case 9:
                            userPicture.setImageResource(R.drawable.nurse);
                            break;
                        case 10:
                            userPicture.setImageResource(R.drawable.pilot);
                            break;
                        case 11:
                            userPicture.setImageResource(R.drawable.avatar);
                            break;
                        case 12:
                            userPicture.setImageResource(R.drawable.police);
                            break;
                        case 13:
                            userPicture.setImageResource(R.drawable.soldier);
                            break;
                        case 14:
                            userPicture.setImageResource(R.drawable.dj);
                            break;
                        //...add others
                        default:
                            break;
                    }
                }
                else {
                    System.out.println("GG");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic_choose = new Intent(Profile_change.this, register_pic_choose.class);
                startActivityForResult(pic_choose,REQUEST_CODE);
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
                                submitChange(userID,nicknameChange.getText().toString(),introChange.getText().toString(),picPos);
                                //Intent Back2Profile = new Intent(Profile_change.this,ProfileActivity.class);
                                //Back2Profile.putExtra("userID",userID);
                                //startActivity(Back2Profile);
                                finish();
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
                                //Intent Back2Profile = new Intent(Profile_change.this,ProfileActivity.class);
                                //Back2Profile.putExtra("userID",userID);
                                //startActivity(Back2Profile);
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
    private void submitChange(String uid,String nickname,String intro,int picPos){
        changData = FirebaseDatabase.getInstance().getReferenceFromUrl("https://intalk-7b460.firebaseio.com/");
        changData.child("userData").child(uid).child("nickname").setValue(nickname);
        changData.child("userData").child(uid).child("introduction").setValue(intro);
        changData.child("userData").child(uid).child("picPos").setValue(picPos);
        Toast.makeText(this,"修改成功 !",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                String res = (data.getStringExtra("picMark"));
                int resPic = Integer.parseInt(res);picPos = resPic;
                //StorageReference mref = FirebaseStorage.getInstance().getReference().child("Sticker").child("student");
                System.out.println("照片位置:"+resPic);
                switch (resPic){
                    case 0:
                        userPicture.setImageResource(R.drawable.student);
                        break;
                    case 1:
                        userPicture.setImageResource(R.drawable.bartender);
                        break;
                    case 2:
                        userPicture.setImageResource(R.drawable.detective);
                        break;
                    case 3:
                        userPicture.setImageResource(R.drawable.chef);
                        break;
                    case 4:
                        userPicture.setImageResource(R.drawable.electrician);
                        break;
                    case 5:
                        userPicture.setImageResource(R.drawable.engineer);
                        break;
                    case 6:
                        userPicture.setImageResource(R.drawable.farmer);
                        break;
                    case 7:
                        userPicture.setImageResource(R.drawable.joker);
                        break;
                    case 8:
                        userPicture.setImageResource(R.drawable.athlete);
                        break;
                    case 9:
                        userPicture.setImageResource(R.drawable.nurse);
                        break;
                    case 10:
                        userPicture.setImageResource(R.drawable.pilot);
                        break;
                    case 11:
                        userPicture.setImageResource(R.drawable.avatar);
                        break;
                    case 12:
                        userPicture.setImageResource(R.drawable.police);
                        break;
                    case 13:
                        userPicture.setImageResource(R.drawable.soldier);
                        break;
                    case 14:
                        userPicture.setImageResource(R.drawable.dj);
                        break;
                }
                //new DownloadImageTask(pic).execute("https://i.imgur.com/OgkFKeU.jpg ");
                if(resPic==0){
                    //Bitmap myBitmap = getBitmapFromUrl("gs://intalk-7b460.appspot.com/Sticker/student.png");
                    //pic.setImageBitmap(myBitmap);
                    System.out.print("設定成功");
                }
                //else System.out.print("設定失敗");
                break;
            default:
                System.out.println("跑出switch QQQ");
                break;
        }
    }


}
