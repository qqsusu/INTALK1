package com.example.italk;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterNextActivity extends AppCompatActivity {

    Button btn_register_back,btn_select_date,btn_confirm;
    EditText nickname,editext_birthday,editext_Intro;
    TextView showAccount,view_nickname,view_bday;
    RadioGroup rg;
    int YEAR;
    private  int mYear,mMonth,mDay;
    private DatabaseReference myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);

        final ImageButton btn_boy_1 = findViewById(R.id.btn_boy_1);
        final ImageButton btn_boy_2 = findViewById(R.id.btn_boy_2);
        final ImageButton btn_girl_1 = findViewById(R.id.btn_girl_1);
        final ImageButton btn_girl_2 = findViewById(R.id.btn_girl_2);

        btn_boy_1.setSelected(false);
        btn_boy_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_boy_1.setSelected(true);
                btn_boy_2.setSelected(false);
                btn_girl_1.setSelected(false);
                btn_girl_2.setSelected(false);
                // Do something
            }
        });

        btn_girl_1.setSelected(false);
        btn_girl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_boy_1.setSelected(false);
                btn_boy_2.setSelected(false);
                btn_girl_1.setSelected(true);
                btn_girl_2.setSelected(false);
                // Do something
            }
        });

        btn_boy_2.setSelected(false);
        btn_boy_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_boy_1.setSelected(false);
                btn_boy_2.setSelected(true);
                btn_girl_1.setSelected(false);
                btn_girl_2.setSelected(false);
                // Do something
            }
        });

        btn_girl_2.setSelected(false);
        btn_girl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_boy_1.setSelected(false);
                btn_boy_2.setSelected(false);
                btn_girl_1.setSelected(false);
                btn_girl_2.setSelected(true);
                // Do something
            }
        });





        Intent getAccount = this.getIntent();
        final String userAccountID = getAccount.getStringExtra("accountID");

        //get element from XML
        //btn_register_back = findViewById(R.id.btn_register_back);
        showAccount = findViewById(R.id.text_show_account);
        editext_birthday = findViewById(R.id.RN_editext_birthday);
        btn_select_date = findViewById(R.id.RN_btn_select_date);
        nickname = findViewById(R.id.RN_editext_name);
        editext_Intro = findViewById(R.id.editext_introduction);
        btn_confirm = findViewById(R.id.RN_btn_confirm);
        view_nickname = findViewById(R.id.RN_view_name);
        rg = findViewById(R.id.ratioGroup_gender);
        view_bday = findViewById(R.id.view_birthday);
        //set AccountID text
        showAccount.setText(showAccount.getText().toString()+userAccountID);

        //back intent
        /*
        btn_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(RegisterNextActivity.this, RegisterActivity.class);
                startActivity(BackIntent);
            }
        });*/
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //get value from element
                String nname  = nickname.getText().toString();//nickname
                String bday = editext_birthday.getText().toString();//birthday
                String intro = editext_Intro.getText().toString();//introduction
                int age = 2019-YEAR;
                boolean gen = true;//true for male,false for female
                switch (rg.getCheckedRadioButtonId()){
                    case R.id.male:
                        gen=true;
                        break;
                    case R.id.female:
                        gen = false;
                        break;
                    default:
                        gen = true;
                        break;
                }
                if(nname.equals("")||nname.isEmpty()){
                    view_nickname.setTextColor(Color.parseColor("#FF3333"));
                    new AlertDialog.Builder(RegisterNextActivity.this)
                            .setTitle("請填寫資訊喔")
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage("暱稱欄位一定要填喇")
                            .setPositiveButton("關閉" ,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            }).show();
                }
                else if(bday.isEmpty()||bday.equals("")){
                    view_bday.setTextColor(Color.parseColor("#FF3333"));
                    new AlertDialog.Builder(RegisterNextActivity.this)
                            .setTitle("請填寫資訊喔")
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage("生日欄位一定要填喇")
                            .setPositiveButton("關閉" ,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            }).show();
                }
                else{
                    Userdata userdata = new Userdata(userAccountID,nname,bday,intro,age,gen);
                    myDB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://intalk-7b460.firebaseio.com/");
                    myDB.child("userData").child(userAccountID).setValue(userdata);
                    new AlertDialog.Builder(RegisterNextActivity.this)
                            .setTitle("註冊完成 !")
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage("快來開始聊天吧 !")
                            .setPositiveButton("關閉視窗" ,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent toPrimaryIntent = new Intent(RegisterNextActivity.this,PrimaryActivity.class);
                                    toPrimaryIntent.putExtra("userID",userAccountID);
                                    startActivity(toPrimaryIntent);
                                }
                            }).show();
                }
                //declare object
            }
        });
    }


    public void datePicker(View vv){
        //get current date
        final Calendar myCalendar = Calendar.getInstance();
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH)+1;
        mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(vv.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                editext_birthday.setText(year+"-"+month+"-"+dayOfMonth);
                YEAR = year;
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }//end of onclick
}
