package com.example.howkum.iumwsms;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignUpActivity extends AsyncTask<String, Void, String> {

private Context context;

public SignUpActivity(Context context) {
        this.context = context;
        }

protected void onPreExecute() {

        }

@Override
protected String doInBackground(String... arg0) {
        String Name = arg0[0];
        String Passport = arg0[1];
        String Id = arg0[2];
        String HomeNo = arg0[3];
        String Mobile = arg0[4];
        String Enroll=arg0[5];
        String Intake=arg0[6];
        String Password=arg0[7];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;
        try {
            data = "?student_name=" + URLEncoder.encode(Name, "UTF-8");
            data += "&passport=" + URLEncoder.encode(Passport, "UTF-8");
            data += "&student_id=" + URLEncoder.encode(Id, "UTF-8");
            data += "&home_no=" + URLEncoder.encode(HomeNo, "UTF-8");
            data += "&mobile_no=" + URLEncoder.encode(Mobile, "UTF-8");
            data += "&semester_enroll=" + URLEncoder.encode(Enroll, "UTF-8");
            data += "&intake=" + URLEncoder.encode(Intake, "UTF-8");
            data += "&password=" + URLEncoder.encode(Password, "UTF-8");



            link = "http://10.0.2.2:8078/project_signup_student_t.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine().toString();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
        }
@Override
protected void onPostExecute(String result) {
        String jsonStr = result;
        //Toast.makeText(context, jsonStr, Toast.LENGTH_SHORT).show();
        if (jsonStr != null) {
        try {
        JSONObject jsonObj = new JSONObject(jsonStr);
        String query_result = jsonObj.getString("query_result");
        if (query_result.equals("SUCCESS")) {
        Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
        } else if (query_result.equals("FAILURE")) {
        Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
        } else {
        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
        }
        } catch (JSONException e) {
        e.printStackTrace();
        Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
        }
        } else {
        Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
        }
}
