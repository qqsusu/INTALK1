package com.example.italk;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class RegisterNextActivity extends AppCompatActivity {

    Button btn_register_back,btn_select_date,btn_confirm;
    EditText nickname,editext_birthday,editext_Intro;
    TextView showAccount,view_nickname,view_bday;
    RadioGroup rg;
    int YEAR;
    private  int mYear,mMonth,mDay;
    private DatabaseReference myDB;
    private static final int REQUEST_CODE = 1;
    ImageView pic;
    private StorageReference mRef;
    int picPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);

        pic = findViewById(R.id.iv_pic);
        Button btn_choose_pic = findViewById(R.id.btn_choose_pic);
        btn_choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic_choose = new Intent(RegisterNextActivity.this, register_pic_choose.class);
                startActivityForResult(pic_choose,REQUEST_CODE);
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
                    Userdata userdata = new Userdata(userAccountID,nname,bday,intro,age,gen,picPos);
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

    /*public static Bitmap getBitmapFromUrl(String src){
        try{
            URL url = new URL(src);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            InputStream input = conn.getInputStream();
            Bitmap mBitmap = BitmapFactory.decodeStream(input);
            return mBitmap;
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

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
                           pic.setImageResource(R.drawable.student);
                           break;
                        case 1:
                            pic.setImageResource(R.drawable.bartender);
                            break;
                        case 2:
                            pic.setImageResource(R.drawable.detective);
                            break;
                        case 3:
                            pic.setImageResource(R.drawable.chef);
                            break;
                        case 4:
                            pic.setImageResource(R.drawable.electrician);
                            break;
                        case 5:
                            pic.setImageResource(R.drawable.engineer);
                            break;
                        case 6:
                            pic.setImageResource(R.drawable.farmer);
                            break;
                        case 7:
                            pic.setImageResource(R.drawable.joker);
                            break;
                        case 8:
                            pic.setImageResource(R.drawable.athlete);
                            break;
                        case 9:
                            pic.setImageResource(R.drawable.nurse);
                            break;
                        case 10:
                            pic.setImageResource(R.drawable.pilot);
                            break;
                        case 11:
                            pic.setImageResource(R.drawable.avatar);
                            break;
                        case 12:
                            pic.setImageResource(R.drawable.police);
                            break;
                        case 13:
                            pic.setImageResource(R.drawable.soldier);
                            break;
                        case 14:
                            pic.setImageResource(R.drawable.dj);
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

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
