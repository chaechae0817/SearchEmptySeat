package com.example.myapplication;

public class ItemData {
    String title;
    String content;
    String url;


    public ItemData(String title, String content,String url) {
        this.title = title;
        this.content = content;
        this.url =url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl(){
        return url;
    }
}
