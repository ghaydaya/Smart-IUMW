package com.example.howkum.iumwsms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class StartPage extends AppCompatActivity {

    Button login;
    Button signup;
    EditText uname,pss;
    String Suname,Spass;
    String role_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Intent myIntent = getIntent(); // gets the previously created intent
        role_ = myIntent.getStringExtra("role");
        login=(Button)findViewById(R.id.loginbtn);

        uname=(EditText)findViewById(R.id.uname);
        pss=(EditText)findViewById(R.id.pass);


        uname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname.setText("");

            }
        });
        pss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pss.setText("");

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Suname=uname.getText().toString();
                Spass=pss.getText().toString();
                if(role_.equals("student"))
                    new GetAstudent(getApplicationContext()).execute(Suname,Spass);
                else
                    new ValidateUser(getApplicationContext()).execute(Suname,Spass,role_);
//                Intent i = new Intent(getApplicationContext(), Student_home.class);
//                i.putExtra("user", Suname);
//                i.putExtra("pass",Spass);
//                startActivity(i);
            }
        });

        signup=(Button)findViewById(R.id.signupbtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),UserSignUp.class);
                i.putExtra("role",role_);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_page, menu);
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

    public class GetAstudent extends AsyncTask<String, Void, String> {

        private Context context;
        String Id;
        public GetAstudent(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            Id = arg0[0];
            String Pass=arg0[1];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?student_id=" + URLEncoder.encode(Id, "UTF-8");
                data += "&password=" + URLEncoder.encode(Pass, "UTF-8");



                link = "http://10.0.2.2:8078/project_login_student.php" + data;
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
                    String query_result = jsonObj.getString("success");

                    if (query_result.equals("1")) {

                        Intent i= new Intent(context,Student_home.class);
                        i.putExtra("id",Id);
                        startActivity(i);
                        //new Student_insert(context).execute(jsonObj.getString("student_name"),jsonObj.getString("passport"),jsonObj.getString("student_id"),jsonObj.getString("home_no"),jsonObj.getString("mobile_no"),jsonObj.getString("semester_enroll"),jsonObj.getString("intake"),jsonObj.getString("password"));


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Did not get from temp", Toast.LENGTH_SHORT).show();
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


    public class ValidateUser extends AsyncTask<String, Void, String> {

        String user,role;
        private Context context;
        String Id;
        public ValidateUser(Context context) {
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


                link = "http://10.0.2.2:8078/project_login_user.php" + data;
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
                    String query_result = jsonObj.getString("success");

                    if (query_result.equals("1")) {

                        if(role.equals("admin")) {
                            Intent i = new Intent(context, AdminHome.class);
                            i.putExtra("id", user);
                            startActivity(i);
                        }
                        else if(role.equals("dean")) {
                            Intent i = new Intent(context, DeanHome.class);
                            i.putExtra("id", user);
                            startActivity(i);
                        }
                        else if(role.equals("registry")) {
                            Intent i = new Intent(context, RegistryHome.class);
                            i.putExtra("id", user);
                            startActivity(i);
                        }
                        else if(role.equals("program leader"))
                        {
                            Intent i = new Intent(context, ProgramLeaderHome.class);
                            i.putExtra("id", user);
                            startActivity(i);
                        }

                        //new Student_insert(context).execute(jsonObj.getString("student_name"),jsonObj.getString("passport"),jsonObj.getString("student_id"),jsonObj.getString("home_no"),jsonObj.getString("mobile_no"),jsonObj.getString("semester_enroll"),jsonObj.getString("intake"),jsonObj.getString("password"));


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Did not get from temp", Toast.LENGTH_SHORT).show();
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
}
