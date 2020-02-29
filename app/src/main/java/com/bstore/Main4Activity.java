package com.bstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class Main4Activity extends AppCompatActivity {

    static String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        final int id = getIntent().getIntExtra("id", -1);

        final TextView textView = findViewById(R.id.textBook);

        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==107) {
                    textView.setText(text);
                }
            }
        };

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject result = ServerConnection.GetText(id);

                    text = result.getString("text");
                    handler.sendMessage(Message.obtain(handler, 107));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
