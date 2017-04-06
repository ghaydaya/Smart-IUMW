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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SeeOneFull extends AppCompatActivity {

    TextView t;
    String ID;
    Button course;
    EditText nam,mob,intake,sems,passp,homen;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "student_id";
    private static final String TAG_NAME = "student_name";

    JSONArray products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_one_full);
        Intent myIntent = getIntent(); // gets the previously created intent
        ID = myIntent.getStringExtra("id");
        t= (TextView) findViewById(R.id.RollText);
        t.setText("Student ID: "+ID);

        nam=(EditText)findViewById(R.id.etname);
        mob=(EditText)findViewById(R.id.etmob);
        intake=(EditText)findViewById(R.id.editText);
        passp=(EditText)findViewById(R.id.editText3);
        homen=(EditText)findViewById(R.id.editText4);
        sems=(EditText)findViewById(R.id.editText2);
        products= new JSONArray();
        new GetOnestudent(getApplicationContext()).execute(ID);

        course=(Button) findViewById(R.id.see_course_enroll_btn);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CoursesOfXs.class);
                i.putExtra("id",ID);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_one_full, menu);
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


    public class GetOnestudent extends AsyncTask<String, Void, String> {

        private Context context;

        public GetOnestudent(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            String Id = arg0[0];


            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?student_id=" + URLEncoder.encode(Id, "UTF-8");




                link = "http://10.0.2.2:8078/project_get_one_student.php" + data;
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
                        products = jsonObj.getJSONArray(TAG_PRODUCTS);
//
                        // looping through All Products
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable

                            String id = c.getString(TAG_PID);
                            String name = c.getString(TAG_NAME);
                            String passport=c.getString("passport");
                            String home_n=c.getString("home_no");
                            String mob_=c.getString("mobile_no");
                            String enroll=c.getString("semester_enroll");
                            String intake_=c.getString("intake");
                            String pass=c.getString("password");

                            nam.setText(name);
                            passp.setText(passport);
                            homen.setText(home_n);
                            mob.setText(mob_);
                            sems.setText(enroll);
                            intake.setText(intake_);
//                            String temp= id + "    " + name;
//                            // creating new HashMap
//                            HashMap<String, String> map = new HashMap<String, String>();
//
//                            // adding each child node to HashMap key => value
//                            map.put(TAG_PID, id);
//                            map.put(TAG_NAME, temp);
//
//                            // adding HashList to ArrayList
//                            productsList.add(map);
                            //new Student_insert(context).execute(name,passport,id,home_n,mob,enroll,intake,pass);
                        }
                        //Toast.makeText(context, "GOT from temp successfully", Toast.LENGTH_SHORT).show();

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
