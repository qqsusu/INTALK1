package com.example.italk;

public class User{
    public String name;
    public String password;
    public String email;

    public User() {}
    public User(String uname,String passwd){
        this.name = uname;
        this.password = passwd;
    }
}