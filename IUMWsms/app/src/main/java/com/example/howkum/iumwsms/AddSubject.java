package com.example.howkum.iumwsms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddSubject extends AppCompatActivity {

    private Spinner spinner;
    private String[]paths = {"C Programming", "Discrete Math", "OOP", "Data Structure","Algorithm","Automata","Compiler","System Analysis and Design"};
    String now;
    String ID,semester;
    String name,iid,code;
    Button okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        Intent myIntent = getIntent(); // gets the previously created intent
        ID = myIntent.getStringExtra("id");
        semester=myIntent.getStringExtra("semester");
        new GetALLSub(getApplicationContext()).execute();
        spinner = (Spinner)findViewById(R.id.subject_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now = (String) parent.getItemAtPosition(position);
                code=now.substring(0,3);
                name=now.substring(4);
                iid=ID;
                Toast.makeText(getApplicationContext(), now + " Selected",
                        Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okay=(Button) findViewById(R.id.confirmsubjbtn);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), now + " Confirmed!! Pending for Approval",
                        Toast.LENGTH_LONG).show();
                new Student_sub_temp_insert(getApplicationContext()).execute(name,code,iid);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_subject, menu);
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

    public class GetALLSub extends AsyncTask<String, Void, String> {
        private Context context;

        public GetALLSub(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                link = "http://10.0.2.2:8078/project_get_all_sub.php";
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
                        //Toast.makeText(context, "Got Something", Toast.LENGTH_SHORT).show();

                        JSONArray products = jsonObj.getJSONArray("subject");
//
                        // looping through All Products
                        //semesters=new ArrayList<String>();
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String nam = c.getString("sub_name");
                            String s_code = c.getString("sub_code");

                            String sem_id= s_code + " " + nam;
                            //semesters.add(sem_id);//=sem_id;
                            paths[i]=sem_id;
                            // creating new HashMap
//                            HashMap<String, String> map = new HashMap<String, String>();
//
//                            // adding each child node to HashMap key => value
//                            map.put(TAG_PID, id);
//                            map.put(TAG_NAME, temp);
//
//                            // adding HashList to ArrayList
//                            productsList.add(map);
                        }


                    } else if (query_result.equals("0")) {
                        Toast.makeText(context, "Didn't get anything", Toast.LENGTH_SHORT).show();
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


    public class Student_sub_temp_insert extends AsyncTask<String, Void, String> {

        private Context context;

        public Student_sub_temp_insert(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String sscode = arg0[0];
            String sname = arg0[1];
            String sid = arg0[2];

            //sscode=sscode+sid;

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?sscode=" + URLEncoder.encode(sscode, "UTF-8");
                data += "&sname=" + URLEncoder.encode(sname, "UTF-8");
                data += "&student_id=" + URLEncoder.encode(sid, "UTF-8");




                link = "http://10.0.2.2:8078/project_insert_temp_student_sub.php" + data;
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
                        Toast.makeText(context, "Data inserted successfully.", Toast.LENGTH_SHORT).show();
                        //new new DeletetAstudent(context).execute()
                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
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
