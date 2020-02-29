package com.bstore;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView listView;
    List<BookPreview> books = new ArrayList<>();

    ServerAdapter server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        server = new ServerAdapter();
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
                    Book[] booksResponse = server.GetBooks();

                    for(int i =0; i < booksResponse.length; i++){
                        Book book = booksResponse[i];
                        String pureBase64Encoded = book.Img.substring(book.Img.indexOf(",")  + 1);

                        byte[] image = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

                        books.add(new BookPreview(book.Name, book.Author, BitmapFactory.decodeByteArray(image, 0, image.length), book.Id));
                    }

                    handler.sendMessage(Message.obtain(handler, 105));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
