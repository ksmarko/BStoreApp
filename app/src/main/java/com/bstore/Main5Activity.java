package com.bstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        final TextView surname = findViewById(R.id.surname);
        final TextView name = findViewById(R.id.name);
        final TextView address = findViewById(R.id.address);
        final TextView phone = findViewById(R.id.phone);
        final Button buy_btn = findViewById(R.id.pay_btn);
        final int id = getIntent().getIntExtra("id", -1);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {


                        PurchaseInfo purchaseInfo = new PurchaseInfo();
                        purchaseInfo.surname = surname.getText().toString();
                        purchaseInfo.phone = phone.getText().toString();
                        purchaseInfo.address = address.getText().toString();
                        purchaseInfo.name = name.getText().toString();
                        purchaseInfo.id = id;

                        ServerConnection.Purchase(purchaseInfo);
                    }
                });

                finish();
            }
        });
    }
}
