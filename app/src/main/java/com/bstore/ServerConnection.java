package com.bstore;

import android.util.Log;

import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerConnection {
    public static String server = null;
    public static String user = "";

    public static void getClient() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null) {
                    // Basic sanity check
                    String mac = splitted[3];
                    if (mac.equals("34:23:87:eb:66:09")) {
                        server = splitted[0];
                        Log.w("IP", server);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getBasic(String type) {
        JSONObject result = new JSONObject();
        put(result, "type", type);
        put(result, "user", user);
        Log.w("getBasic", result.toString());
        return result;
    }

    private static JSONObject put(JSONObject o, String n, String v) {
        try {
            o.put(n, v);
        } catch (Exception e) {
            Log.w("JSON", e);
        }

        return o;
    }

    private static void Post(JSONObject o) {
        try {
            Socket clientSocket = new Socket(InetAddress.getByName(server), 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.write((o.toString() + '\n').getBytes(StandardCharsets.UTF_8));
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject Get(JSONObject o) {
        String request;
        String answer;
        try {
            Socket clientSocket = new Socket(InetAddress.getByName(server), 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            request = o.toString();
            Log.w("Request", request);
            outToServer.write((request + '\n').getBytes(StandardCharsets.UTF_8));
            answer = inFromServer.readLine();
            Log.w("Answer", answer);
            clientSocket.close();

            return new JSONObject(answer);
        } catch (Exception e) {
            Log.w("Get", e);
        }

        return null;
    }

    private static boolean Ask(JSONObject o) {
        try {
            return Get(o).getString("value").equals("true");
        } catch (Exception e) {
            Log.w("Ask", e);
        }
        return false;
    }

    public static boolean IsValid(String log, String pass) {
        JSONObject o = getBasic("101");
        put(o, "log", log);
        put(o, "pass", pass);
        return Ask(o);
    }

    public static boolean IsLogin(String log) {
        JSONObject o = getBasic("102");
        put(o, "log", log);
        return Ask(o);
    }

    public static void Register(String log, String pass) {
        JSONObject o = getBasic("104");
        put(o, "log", log);
        put(o, "pass", pass);
        Post(o);
    }

    static JSONObject GetPreviews() {
        return Get(getBasic("105"));
    }

    static JSONObject GetBook(int id) {
        JSONObject request = getBasic("106");
        put(request, "id", String.valueOf(id));
        return Get(request);
    }

    static JSONObject GetText(int id) {
        JSONObject request = getBasic("107");
        put(request, "id", String.valueOf(id));
        return Get(request);
    }

    static void AddReview(int id, Review review) {
        JSONObject body = getBasic("108");
        put(body, "id", String.valueOf(id));
        put(body, "author", review.author);
        put(body, "date", review.date);
        put(body, "ratingValue", String.valueOf(review.rating));
        put(body, "body", review.body);
        Post(body);
    }

    static void Purchase(PurchaseInfo purchaseInfo) {
        JSONObject body = getBasic("109");
    }
}