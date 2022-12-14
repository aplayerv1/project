package com.example.myapplication;

import java.util.ArrayList;

public class Data {

    private String name;
    private String url;
    private String description;
    private int fav = 0;

    public Data(String name, String url, String description, int i){
        this.name = name;
        this.url = url;
        this.description=description;
        this.fav = i;
    }

    public Data() {

    }



    public void setUrl(String url) {
        this.url = url;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getUrl(){
        return url;
    }
    public String getDescription(){
        return description;
    }
}