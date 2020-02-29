package com.bstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;

public class Main2Activity extends AppCompatActivity {

    ListView listView;
    List<BookPreview> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        final Intent intent  = new Intent(this, Main3Activity.class);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookListAdapter.ViewHolder viewHolder = (BookListAdapter.ViewHolder) view.getTag();
                intent.putExtra("id", viewHolder.id);
                startActivity(intent);
            }
        };

        listView = findViewById(R.id.booklist);

        final BookListAdapter adapter = new BookListAdapter(this, R.layout.book_preview_cell, books, onClickListener);

        listView.setAdapter(adapter);

        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==105) {
                    adapter.notifyDataSetChanged();
                }
            }
        };


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject result = ServerConnection.GetPreviews();

                    JSONArray booksjson = result.getJSONArray("books");

                    for(int i =0; i<booksjson.length();i++){
                        JSONObject book = booksjson.getJSONObject(i);
                        byte[] image = Base64.decode(book.getString("img"), Base64.DEFAULT);

                        books.add(new BookPreview(book.getString("name"), book.getString("author"), BitmapFactory.decodeByteArray(image, 0, image.length), book.getInt("id")));
                    }

                    handler.sendMessage(Message.obtain(handler, 105));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
