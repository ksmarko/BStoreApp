package com.bstore;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView errorLbl;
    EditText loginTxt;
    EditText passTxt;
    Button btnLog;
    Button btnReg;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerConnection.getClient();

        loginTxt = findViewById(R.id.editText);
        errorLbl = findViewById(R.id.lbl_error);
        passTxt = findViewById(R.id.editText2);
        btnLog = findViewById(R.id.button);
        btnReg = findViewById(R.id.button2);

        btnTest = findViewById(R.id.btn_test);

        final Intent intent = new Intent(this, Main2Activity.class);

        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:
                        if(msg.arg1==1){
                            ServerConnection.user = loginTxt.getText().toString();
                            startActivity(intent);
                        }
                        else {
                            errorLbl.setText(getString(R.string.loginerr));
                        }
                        break;
                    case 102:
                        if(msg.arg1==1){
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    ServerConnection.Register(loginTxt.getText().toString(), passTxt.getText().toString());
                                }
                            });

                            ServerConnection.user = loginTxt.getText().toString();
                            startActivity(intent);
                        }
                        else {
                            errorLbl.setText(getString(R.string.regerr));
                        }
                        break;
                }
            }
        };

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        ServerAdapter adapter = new ServerAdapter();

                        adapter.getAnything(MainActivity.this);

                        System.out.println("on button click");
                    }
                });
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain(handler, 101, 0,0);

                        if(ServerConnection.IsValid(loginTxt.getText().toString(),passTxt.getText().toString())){
                            message.arg1 = 1;
                        }

                        handler.sendMessage(message);
                    }
                });
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain(handler, 102, 0, 0);

                        if(ServerConnection.IsLogin(loginTxt.getText().toString())){
                            message.arg1 = 1;
                        }

                        handler.sendMessage(message);
                    }
                });
            }
        });
    }
}
