package com.example.italk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;//declare database reference
    private DatabaseReference ckDatabase;
    private Button btn_register_back,btn_register_next,r_btn_psdShow,r_btn_ConfirmPsdShow;
    EditText userID,userPasswd,confirm_passwd;
    int psdShow = 0,CnfirmPsdShow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register_back = findViewById(R.id.btn_register_back);
        btn_register_next = findViewById(R.id.btn_register_next);
        userID = findViewById(R.id.r_editext_account);
        userPasswd = findViewById(R.id.r_editext_passwd);
        confirm_passwd = findViewById(R.id.r_editext_confirm_passwd);
        r_btn_psdShow = findViewById(R.id.r_btn_psdShow);
        r_btn_ConfirmPsdShow = findViewById(R.id.r_btn_ConfirmPsdShow);
//            back intent
        btn_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(BackIntent);
            }
        });
//        next intent
        btn_register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent NextIntent = new Intent(RegisterActivity.this,RegisterNextActivity.class);
                final int[] check = {0};
                final String uID = userID.getText().toString();
                ckDatabase = FirebaseDatabase.getInstance().getReference("userAccount").child(uID);
                ckDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("這個帳號已經有人註冊喽!")
                                    .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                    .setMessage("請選擇其他帳號吧 !")
                                    .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //finish();
                                        }
                                    }).show();
                            check[0] = -1;
                            System.out.println("帳號存在1111");
                        }
                        else {
                            check[0]=1;
                            final String uid = userID.getText().toString();
                            final String upasd = userPasswd.getText().toString();
                            String cupasd = confirm_passwd.getText().toString();
                            System.out.println("帳號不存在2222");
                            //確認帳號長度，密碼符合
                            if(checkAccountLen(uid)&&checkPasswdOK(upasd,cupasd)){
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setTitle("喔喔喔喔喔喔")
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setMessage("確認要註冊此帳號嗎!!\n(帳號日後無法更改)\n\n帳號:"+uid+"\n密碼:"+upasd)
                                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                submitAccount(uid,upasd);
                                                NextIntent.putExtra("accountID",uid);
                                                startActivity(NextIntent);
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {}
                                        }).show();
                            }
                        }
                        System.out.println("check值:"+check[0]);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });
        r_btn_psdShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psdShow++;
                if(psdShow%2==1){
                    userPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    userPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        r_btn_ConfirmPsdShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CnfirmPsdShow++;
                if(CnfirmPsdShow%2==1){
                    confirm_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    confirm_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private  boolean submitAccount(String userID,String userPasswd){
        //if(!checkAccountLen(userID)){return false;}
        //else if(!checkAccountExist(userID)){return false;}
        //else if(!checkPasswdOK(userPasswd,confirm_passwd)){return false;}
        //submit to database
        User user = new User(userID,userPasswd);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://intalk-7b460.firebaseio.com/");//init database reference
        mDatabase.child("userAccount").child(userID).setValue(user);
        Toast.makeText(this,"帳號新增成功，接下來請填寫一些基本資料吧 !",Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean checkAccountLen(String userID){
        if(userID.length()<=1){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("輸入帳號太短啦!")
                    .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                    .setMessage("輸入帳號至少六個字元")
                    .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    private boolean checkPasswdOK(String passwd,String confirmPasswd){
        if(passwd.length()<=1){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("密碼長度請大於5喔 !")
                    .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                    .setMessage("你的密碼太短啦")
                    .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    }).show();
            return false;
        }
        else if(!passwd.equals(confirmPasswd)){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("輸入密碼有誤")
                    .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                    .setMessage("請輸入一樣的密碼")
                    .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    }).show();
            return false;
        }
        return true;
    }
}
