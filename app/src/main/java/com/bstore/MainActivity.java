package com.bstore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView errorLbl;
    EditText loginTxt;
    EditText passTxt;
    Button btnLog;
    Button btnReg;

    ServerAdapter server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = new ServerAdapter();

        loginTxt = findViewById(R.id.editText);
        errorLbl = findViewById(R.id.lbl_error);
        passTxt = findViewById(R.id.editText2);
        btnLog = findViewById(R.id.button);
        btnReg = findViewById(R.id.button2);

        final Intent intent = new Intent(this, Main2Activity.class);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = loginTxt.getText().toString();
                final String password = passTxt.getText().toString();

                if (email == null || email.isEmpty()){
                    WriteError("Email cannot be empty");
                    return;
                }

                if (password == null || password.isEmpty()) {
                    WriteError("Password cannot be empty");
                    return;
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        User response = server.Login(email, password);

                        if (response == null){
                            WriteError("Invalid email or password");
                            return;
                        }

                        UserInfo.UserId = response.Id;
                        startActivity(intent);

                        Clear();
                    }
                });
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = loginTxt.getText().toString();
                final String password = passTxt.getText().toString();

                if (email == null || email.isEmpty()) {
                    WriteError("Email cannot be empty");
                    return;
                }

                if (password == null || password.isEmpty()) {
                    WriteError("Password cannot be empty");
                    return;
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        User response = server.Register(email, password);

                        if (response == null){
                            WriteError("User with the same email already exists");
                            return;
                        }

                        UserInfo.UserId = response.Id;

                        startActivity(intent);

                        Clear();
                    }
                });
            }
        });
    }

    private void WriteError(final String message){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorLbl.setText(message);
            }
        });
    }

    private void Clear(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginTxt.setText("");
                passTxt.setText("");
                errorLbl.setText("");
            }
        });
    }
}