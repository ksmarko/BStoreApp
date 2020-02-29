package com.bstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        final Button buy_btn = findViewById(R.id.pay_btn);
        final int id = getIntent().getIntExtra("id", -1);

        final Intent intent  = new Intent(this, Main3Activity.class);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Main5Activity.this, "We will contact you soon", Toast.LENGTH_SHORT).show();

                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}
