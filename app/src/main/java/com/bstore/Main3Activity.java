package com.bstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    BookInfo info = new BookInfo();
    ListView listView;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final int id = getIntent().getIntExtra("id", -1);

        listView = findViewById(R.id.reviews);

        final ReviewListAdapter adapter = new ReviewListAdapter(this, R.layout.review_cell, info.reviews);

        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, Main4Activity.class);

        Button read_btn = findViewById(R.id.read_button);

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button dwn_btn = findViewById(R.id.download_button);

        final ImageView imageView = findViewById(R.id.info_image);
        final TextView info_name = findViewById(R.id.info_name);
        final TextView info_date = findViewById(R.id.info_date);
        final TextView info_author = findViewById(R.id.info_author);
        final TextView info_genre = findViewById(R.id.genre_name);
        final TextView info_summ = findViewById(R.id.summ_text);
        final RatingBar info_stars = findViewById(R.id.info_stars);

        Button review_btn = findViewById(R.id.review_btn);
        final TextView review_text = findViewById(R.id.review_text);
        final Spinner rating = findViewById(R.id.spinner2);
        final Button buy_btn = findViewById(R.id.buy_button);


        final Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==106) {
                    imageView.setImageBitmap(info.img);
                    info_name.setText(info.name);
                    info_author.setText(info.author);
                    info_date.setText(String.format(getResources().getText(R.string.publish_year).toString(), info.date));
                    info_stars.setRating(info.rating);
                    info_genre.setText(info.genre);
                    info_summ.setText(info.summ);
                    buy_btn.setText(String.format(getResources().getText(R.string.buy_text).toString(), info.price));

                    ReviewListAdapter.setListViewHeightBasedOnChildren(listView);
                    adapter.notifyDataSetChanged();

                }
                if(msg.what==107) {
                    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                    try {
                        File file = new File(folder + "/", info.name +".txt");

                        FileOutputStream stream = new FileOutputStream(file);
                        try {
                            stream.write(text.getBytes());

                            Context context = getApplicationContext();
                            CharSequence text = "Файл збережено в " + folder;
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } finally {
                            stream.close();
                        }
                    }
                    catch (Exception e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }
                if(msg.what==108){
                    review_text.setText("");
                    rating.setSelection(0);
                    adapter.notifyDataSetChanged();
                    ReviewListAdapter.setListViewHeightBasedOnChildren(listView);
                }
            }
        };

        final Intent buyI = new Intent(this, Main5Activity.class);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyI.putExtra("id", id);
                startActivity(buyI);
            }
        });


        dwn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Review r : info.reviews){
                    if(r.author.equals(ServerConnection.user)){
                        review_text.setText("Ви вже лишали відгук");
                        return;
                    }
                }

                Calendar calendar = Calendar.getInstance();
                String date = calendar.get(Calendar.YEAR) +"."+calendar.get(Calendar.MONTH) +"." +calendar.get(Calendar.DATE);

                final Review review = new Review(ServerConnection.user, date, review_text.getText().toString(), Integer.valueOf(rating.getSelectedItem().toString()));

                info.reviews.add(review);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ServerConnection.AddReview(id, review);
                            handler.sendMessage(Message.obtain(handler, 108));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject result = ServerConnection.GetBook(id);

                    byte[] image = Base64.decode(result.getString("img"), Base64.DEFAULT);
                    info.img = BitmapFactory.decodeByteArray(image, 0, image.length);
                    info.name=result.getString("name");
                    info.genre=result.getString("genre");
                    info.date=result.getString("date");
                    info.summ=result.getString("summ");
                    info.author=result.getString("author");
                    info.price=result.getString("price");

                    JSONArray reviewjson = result.getJSONArray("reviews");

                    int rating = 0;

                    for(int i =0; i<reviewjson.length();i++){
                        JSONObject review = reviewjson.getJSONObject(i);

                        int r = review.getInt("ratingValue");
                        rating+=r;
                        info.reviews.add(new Review(review.getString("author"), review.getString("date"), review.getString("body"), r));
                    }

                    info.rating = (int)Math.round((double)rating/reviewjson.length());

                    handler.sendMessage(Message.obtain(handler, 106));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
