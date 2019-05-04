package com.example.italk;

class Model {
    private String title;
    private String desc;
    private int icon;

    //Constructor
    public Model(String title, String desc, int icon){
        this.title = title;
        this.desc = desc;
        this.icon = icon;
    }

    //Getters

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getIcon() {
        return this.icon;
    }
}
