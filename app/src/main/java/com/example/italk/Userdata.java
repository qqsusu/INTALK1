package com.example.italk;

class Userdata {
    public String AccountID;
    public String nickname;
    public String birthday;
    public String introduction;
    public int age;
    public boolean gender;
    public int picPos;

    public Userdata(){};
    public Userdata(String userID){
        AccountID = userID;
        nickname = birthday = introduction = "";
        gender = true;
        age = picPos = 0;
    }
    public Userdata(String ID,String nname,String bday,String intro,int age,boolean gen){
        AccountID = ID;
        nickname = nname;
        birthday = bday;
        introduction = intro;
        this.age = age;
        gender = gen;
    }
    public Userdata(String ID,String nname,String bday,String intro,int age,boolean gen,int pic){
        AccountID = ID;
        nickname = nname;
        birthday = bday;
        introduction = intro;
        this.age = age;
        gender = gen;
        picPos = pic;
    }
}
