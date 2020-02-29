package com.bstore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main4Activity extends AppCompatActivity {

    static Book book;
    ServerAdapter server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        server = new ServerAdapter();

        final int id = getIntent().getIntExtra("id", -1);

        final TextView textView = findViewById(R.id.textBook);

        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==107) {
                    textView.setText(book.Text);
                }
            }
        };

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    book = server.GetBook(id);
                    handler.sendMessage(Message.obtain(handler, 107));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
