package com.bstore;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerAdapter {

    public void AddReview(int bookId, Review review) {
        try {
            URL urlStr = new URL("https://postgretest.appspot.com/api/book/" + bookId);
            HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject request = new JSONObject();
            request.put("userId", review.UserId);
            request.put("text", review.Text);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(request.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book GetBook(int id){

        try {
            String method = "GET";
            URL obj = new URL("https://postgretest.appspot.com/api/book/" + id);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod(method);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null){
                response.append(inputLine);
            }

            reader.close();

            Gson g = new Gson();
            return g.fromJson(response.toString(), Book.class);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Book[] GetBooks(){
        try {
            String method = "GET";
            URL obj = new URL("https://postgretest.appspot.com/api/book");
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod(method);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null){
                response.append(inputLine);
            }

            reader.close();

            Gson g = new Gson();
            return g.fromJson(response.toString(), Book[].class);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public User Login(String email, String password) {

        try {
            URL urlStr = new URL("https://postgretest.appspot.com/api/");
            HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("password", password);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(request.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            Gson g = new Gson();
            return g.fromJson(response.toString(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User Register(String email, String password) {

        try {
            URL urlStr = new URL("https://postgretest.appspot.com/api/registration");
            HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("password", password);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(request.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            Gson g = new Gson();
            return g.fromJson(response.toString(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


