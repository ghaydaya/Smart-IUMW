package com.example.howkum.iumwsms;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddSemester extends AppCompatActivity {

    Button okay;
    EditText year;
    private Spinner spinner;
    String now;
    private static final String[]paths = {"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        ///spinner
        spinner = (Spinner)findViewById(R.id.add_sem_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now = (String) parent.getItemAtPosition(position);
                now=now.substring(9);
                Toast.makeText(getApplicationContext(), now + " Selected",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///spinner ends

        year = (EditText) findViewById(R.id.year_select);

        okay= (Button)findViewById(R.id.sem_add_okay_btn);

        //final

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String y=year.getText().toString();
                new Semester_insert(getApplicationContext()).execute(now,y,now+y);
                Toast.makeText(getApplicationContext(), "New Semester Created!!!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_semester, menu);
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


    //////////////************ main database insert *******************///
    public class Semester_insert extends AsyncTask<String, Void, String> {

        private Context context;

        public Semester_insert(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String Num = arg0[0];
            String Y = arg0[1];
            String Cd = arg0[2];


            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?sem_num=" + URLEncoder.encode(Num, "UTF-8");
                data += "&sem_session=" + URLEncoder.encode(Y, "UTF-8");
                data += "&sem_code=" + URLEncoder.encode(Cd, "UTF-8");




                link = "http://10.0.2.2:8078/project_add_semester.php" + data;
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
                        Toast.makeText(context, "Data could not be inserted.", Toast.LENGTH_SHORT).show();
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
