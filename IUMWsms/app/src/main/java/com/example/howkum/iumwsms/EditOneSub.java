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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class EditOneSub extends AppCompatActivity {

    String ID;
    EditText subname,subcredit,subcode;
    Button okay_edit;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_one_sub);
        Intent myIntent = getIntent(); // gets the previously created intent
        ID = myIntent.getStringExtra("id");
        t=(TextView)findViewById(R.id.textViewCode);
        t.setText(ID);
        subname=(EditText)findViewById(R.id.editText6);
        //subcode=(EditText)findViewById(R.id.editText5);
        subcredit=(EditText)findViewById(R.id.editText7);

        okay_edit=(Button)findViewById(R.id.editsubokaybtn);

        okay_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam=subname.getText().toString();
                //String cd=subcode.getText().toString();
                String cred=subcredit.getText().toString();

                new UdateSubject(getApplicationContext()).execute(nam,ID,cred);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_one_sub, menu);
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

    public class UdateSubject extends AsyncTask<String, Void, String> {

        private Context context;

        public UdateSubject(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String Name = arg0[0];
            String Id = arg0[1];
            String cred = arg0[2];


            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?id=" + URLEncoder.encode(Id, "UTF-8");
                data += "&sub_name=" + URLEncoder.encode(Name, "UTF-8");
                data += "&sub_credit=" + URLEncoder.encode(cred, "UTF-8");




                link = "http://10.0.2.2:8078/project_update_subject_details.php" + data;
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
                        Toast.makeText(context, "Data Altered successfully.", Toast.LENGTH_SHORT).show();
                        //new new DeletetAstudent(context).execute()
                    } else {
                        Toast.makeText(context, "Data could not be altered.", Toast.LENGTH_SHORT).show();
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
