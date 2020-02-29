package com.bstore;

public class Review {
    public String Text;
    public int UserId;

    public Review(int userId, String text){
        Text = text;
        UserId = userId;
    }
}
