package com.mineru.hops.Function;

public class Card {
    private String imgURL;
    private String rank;
    private String name;
    private String company;

    public Card(String imgURL, String name, String company, String rank) {
        this.imgURL = imgURL;
        this.name = name;
        this.company = company;
        this.rank = rank;

    }

    public String getImgURL() {
        return imgURL;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public String getName() {
        return name;
    }
    public String getCompany() {
        return company;
    }
    public String getRank() {
        return rank;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
}