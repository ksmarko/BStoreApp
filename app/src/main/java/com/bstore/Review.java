package com.bstore;

public class Review {
    public String author;
    public String date;
    public String body;
    public int rating;

    public Review(String a, String d, String b, int r){
        author= a;
        date = d;
        rating = r;
        body = b;
    }
}
