package com.bstore;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerAdapter {

    public String getAnything(Activity context){
        SendRequest sendRequest = new SendRequest(context);
        sendRequest.execute("https://configproviderapp.appspot.com/api/values", "GET");

        return null;
    }

    private class SendRequest extends AsyncTask<String, Void, String> {

        private final Activity _context;

        public SendRequest(Activity context){
            _context = context;
        }

        @Override
        protected String doInBackground(String[] params) {

            System.out.println("bg task");

            String responseFromServer = null;

            try {
                String url = params[0];
                String method = params[1];
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod(method);

                //DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                //wr.close();

                int responseCode = conn.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null){
                    response.append(inputLine);
                }

                reader.close();
                responseFromServer = response.toString();
            }
            catch (Exception ex) {
                return ex.getMessage();
            }

            return responseFromServer;
        }

        @Override
        protected void onPostExecute(String message){
            System.out.println("on post execute");
            Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
        }
    }
}


