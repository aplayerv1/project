package com.example.myapplication;

/**
 *
 */
public class Data {

    private String name;
    private String url;
    private String description;
    private boolean fav;

    public Data(String name, String url, String description, boolean i){
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

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFav(Boolean i){
        this.fav=i;
    }

    public boolean getFav(){
        return fav;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    /**
     * @return
     */
    public String getDescription(){
        return description;
    }
}