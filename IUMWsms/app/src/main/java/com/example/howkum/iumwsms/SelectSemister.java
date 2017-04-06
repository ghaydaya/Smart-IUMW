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
import java.util.ArrayList;

public class SelectSemister extends AppCompatActivity {

    private Spinner spinner;
    String now,id;
    Button okay;
    private String[]paths= {"", "","","","","","",""};
    ArrayList<String>semesters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_semister);
        Intent myIntent = getIntent(); // gets the previously created intent
        id = myIntent.getStringExtra("id");
        //paths=new String[10];
        new GetALLSemester(getApplicationContext()).execute();
        spinner = (Spinner)findViewById(R.id.semester_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now = (String) parent.getItemAtPosition(position);
                now= now.substring(15,20);
                Toast.makeText(getApplicationContext(), now,
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okay= (Button) findViewById(R.id.semconfirmbtn);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddSubject.class);
                i.putExtra("semester",now);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_semister, menu);
        return true;
    }
//    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//
//        switch (position) {
//            case 0:
//                // Whatever you want to happen when the first item gets selected
//                break;
//            case 1:
//                // Whatever you want to happen when the second item gets selected
//                break;
//            case 2:
//                // Whatever you want to happen when the thrid item gets selected
//                break;
//
//        }
//    }

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

    public class GetALLSemester extends AsyncTask<String, Void, String> {
        private Context context;

        public GetALLSemester(Context context) {
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
                link = "http://10.0.2.2:8078/project_get_all_semester.php";
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

                        JSONArray products = jsonObj.getJSONArray("semester");
//
                        // looping through All Products
                        semesters=new ArrayList<String>();
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String num = c.getString("sem_number");
                            String session = c.getString("sem_session");

                            String sem_id= "semester" + num + " " +session + "(" + num+session+")";
                            semesters.add(sem_id);//=sem_id;
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
}
