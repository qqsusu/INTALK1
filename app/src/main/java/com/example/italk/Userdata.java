package com.example.italk;

class Userdata {
    public String AccountID;
    public String nickname;
    public String birthday;
    public String introduction;
    public int age;
    public boolean gender;

    public Userdata(){};
    public Userdata(String ID,String nname,String bday,String intro,int age,boolean gen){
        AccountID = ID;
        nickname = nname;
        birthday = bday;
        introduction = intro;
        this.age = age;
        gender = gen;
    }
}
