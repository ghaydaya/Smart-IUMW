package com.example.howkum.iumwsms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserSignUp extends AppCompatActivity {

    String role_;
    EditText uname,pass;
    Button okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        Intent myIntent = getIntent(); // gets the previously created intent
        role_ = myIntent.getStringExtra("role");

        uname=(EditText)findViewById(R.id.et_username);
        pass=(EditText)findViewById(R.id.et_pass);

        okay=(Button)findViewById(R.id.button4);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userr= uname.getText().toString();
                String passs=pass.getText().toString();

                new InsertUser(getApplicationContext()).execute(userr,passs,role_);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class InsertUser extends AsyncTask<String, Void, String> {

        String user,role;
        private Context context;
        String Id;
        public InsertUser(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            user = arg0[0];
            String Pass=arg0[1];
            role=arg0[2];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?username=" + URLEncoder.encode(user, "UTF-8");
                data += "&password=" + URLEncoder.encode(Pass, "UTF-8");
                data += "&role=" + URLEncoder.encode(role, "UTF-8");


                link = "http://10.0.2.2:8078/project_insert_user.php" + data;
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

                        Toast.makeText(context, "Sign Up Completed", Toast.LENGTH_SHORT).show();
//                        if(role.equals("admin")) {
//                            Intent i = new Intent(context, AdminHome.class);
//                            i.putExtra("id", user);
//                            startActivity(i);
//                        }
                        //new Student_insert(context).execute(jsonObj.getString("student_name"),jsonObj.getString("passport"),jsonObj.getString("student_id"),jsonObj.getString("home_no"),jsonObj.getString("mobile_no"),jsonObj.getString("semester_enroll"),jsonObj.getString("intake"),jsonObj.getString("password"));


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show();
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
}
