package com.bstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        final Button buy_btn = findViewById(R.id.pay_btn);
        final EditText txtName = findViewById(R.id.name);
        final EditText txtPhone = findViewById(R.id.phone);
        final int id = getIntent().getIntExtra("id", -1);

        final Intent intent  = new Intent(this, Main3Activity.class);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtName.getText().toString();
                String phone = txtPhone.getText().toString();

                if (name == null || name.isEmpty()) {
                    ShowMessage("Name cannot be empty");
                    return;
                }

                if (phone == null || phone.isEmpty()) {
                    ShowMessage("Phone cannot be empty");
                    return;
                }

                ShowMessage("We will contact you soon");

                intent.putExtra("id", id);
                startActivity(intent);

                txtName.setText("");
                txtPhone.setText("");
            }
        });
    }

    private void ShowMessage(String message) {
        Toast.makeText(Main5Activity.this, message, Toast.LENGTH_SHORT).show();
    }
}
