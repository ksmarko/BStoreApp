package com.bstore;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BookInfo {
    public String name;
    public String author;
    public String summ;
    public String genre;
    public String date;
    public String price;
    public Bitmap img;
    public int rating;

    public ArrayList<Review> reviews = new ArrayList<>();
}
