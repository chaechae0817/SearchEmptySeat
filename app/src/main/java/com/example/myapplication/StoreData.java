package com.example.myapplication;

public class StoreData {
    String title;
    String price;

    public StoreData(String title,String price) {
        this.title = title;
        this.price =price;
    }
    public String getTitle() {
        return title;
    }

    public String getPrice(){
        return price;
    }

}
