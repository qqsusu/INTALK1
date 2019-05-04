package com.example.italk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    public int ckEyeClick = 0;
    private DatabaseReference cDB;
    Button LG_btn,LG_btn_psdCk;
    EditText userID,userPasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ckEyeClick = 0;
        userID = findViewById(R.id.LG_editext_account);
        userPasswd = findViewById(R.id.LG_editext_passwd);
        LG_btn = findViewById(R.id.LG_btn_login);
        LG_btn_psdCk = findViewById(R.id.LG_btn_psdCheck);
        LG_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] ck = {0};
                final String uid = userID.getText().toString();
                final String upasd = userPasswd.getText().toString();
                if(uid.equals("")||uid.isEmpty()){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("這個帳號不存在喇7777!")
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage("你根本沒輸入帳號阿==")
                            .setPositiveButton("重新輸入" ,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            })
                            .setNegativeButton("前往註冊", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent toRgstr = new Intent(LoginActivity.this,RegisterActivity.class);
                                    startActivity(toRgstr);
                                }
                            })
                            .show();
                }
                else {
                    cDB = FirebaseDatabase.getInstance().getReference("userAccount").child(uid);
                    cDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot mdataSnapshot) {
                            //帳號存在
                            if(mdataSnapshot.exists()){
                                User user = new User();
                                user.name = mdataSnapshot.child("name").getValue().toString();
                                user.password = mdataSnapshot.child("password").getValue().toString();
                                System.out.println("帳號存在666");
                                System.out.println("帳號:"+user.name+"\t密碼:"+user.password);
                                ck[0]=1;
                                //比對密碼
                                if(upasd.equals(user.password)){
                                    userID.setText("");
                                    userPasswd.setText("");
                                    Intent toPrimaryIntent = new Intent(LoginActivity.this,PrimaryActivity.class);
                                    toPrimaryIntent.putExtra("userID",uid);
                                    startActivity(toPrimaryIntent);
                                }
                                else{
                                    new AlertDialog.Builder(LoginActivity.this)
                                            .setTitle("密碼有誤")
                                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                            .setMessage("密碼錯啦88888")
                                            .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //finish();
                                                }
                                            }).show();
                                }
                            }
                            //帳號不存在
                            else{
                                ck[0]=-1;
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("這個帳號不存在喇7777!")
                                        .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                        .setMessage("喔喔喔喔喔喔喔喔喔喔 !")
                                        .setPositiveButton("重新輸入" ,new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //finish();
                                            }
                                        })
                                        .setNegativeButton("前往註冊", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent toRgstr = new Intent(LoginActivity.this,RegisterActivity.class);
                                                startActivity(toRgstr);
                                            }
                                        })
                                        .show();
                                System.out.println("查無帳號");
                            }
                            System.out.println("checkkkkkk值:"+ck[0]);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
            }
        });
        LG_btn_psdCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ckEyeClick++;
                if(ckEyeClick%2==1){
                    userPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    userPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    System.out.println("check eye click value:"+ckEyeClick);
                }
            }
        });
    }
}
