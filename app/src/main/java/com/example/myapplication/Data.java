package com.example.myapplication;

public class Data {

    private String name;
    private String url;
    private String description;

    public Data(String name, String url, String description){
        this.name = name;
        this.url = url;
        this.description=description;
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