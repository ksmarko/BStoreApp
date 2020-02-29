package com.bstore;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BookInfo {
    public String name;
    public String author;
    public String text;
    public String downloadLink;
    public String year;
    public String price;
    public Bitmap img;

    public ArrayList<Review> reviews = new ArrayList<>();
}
