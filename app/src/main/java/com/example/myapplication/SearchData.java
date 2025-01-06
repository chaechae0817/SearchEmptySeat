package com.example.myapplication;

public class SearchData {

    String title;
    int ranking;
    public SearchData(String title,int ranking) {
        this.title = title;
        this.ranking=ranking;
    }
    public String getTitle() {
        return title;
    }
    public int getRanking(){
        return ranking;
    }
}
