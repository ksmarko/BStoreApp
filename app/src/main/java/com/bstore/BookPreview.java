package com.bstore;

import android.graphics.Bitmap;

public class BookPreview {
    public BookPreview(String name, String author, Bitmap img, int id){
        _id = id;
        _name = name;
        _author = author;
        _img = img;
    }

    public int _id;
    public  String _name;
    public  String _author;
    public Bitmap _img;
}
