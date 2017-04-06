package com.example.howkum.iumwsms;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;

public class PendingNewStudent extends ListActivity {
    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "student_id";
    private static final String TAG_NAME = "student_name";

    JSONArray products = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_new_student);

        productsList = new ArrayList<HashMap<String, String>>();

        new PendingStudentexec(getApplicationContext()).execute();

        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();
                new GetAstudent(getApplicationContext()).execute(pid);
                new DeletetAstudent(getApplicationContext()).execute(pid);
                // Starting new intent
//                Intent in = new Intent(getApplicationContext(),
//                        EditProductActivity.class);
//                // sending pid to next activity
//                in.putExtra(TAG_PID, pid);
//
//                // starting new activity and expecting some response back
//                startActivityForResult(in, 100);
            }
        });

    }
    public class PendingStudentexec extends AsyncTask<String, Void, String> {
        private Context context;

        public PendingStudentexec(Context context) {
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
                link = "http://10.0.2.2:8078/project_get_all_pending_student.php";
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
            Toast.makeText(context, jsonStr, Toast.LENGTH_SHORT).show();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("success");
                    if (query_result.equals("1")) {
                        //Toast.makeText(context, "Got Something", Toast.LENGTH_SHORT).show();

                        products = jsonObj.getJSONArray(TAG_PRODUCTS);
//
                        // looping through All Products
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_PID);
                            String name = c.getString(TAG_NAME);

                            String temp= id + "    " + name;
                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_PID, id);
                            map.put(TAG_NAME, temp);

                            // adding HashList to ArrayList
                            productsList.add(map);
                        }


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Didn't get anything", Toast.LENGTH_SHORT).show();
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
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            getApplicationContext(), productsList,
                            R.layout.list_p, new String[] { TAG_PID,
                            TAG_NAME},
                            new int[] { R.id.pid, R.id.name_text });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }
    }


    public class GetAstudent extends AsyncTask<String, Void, String> {

        private Context context;

        public GetAstudent(Context context) {
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




                link = "http://10.0.2.2:8078/project_get_student_t.php" + data;
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
                            String mob=c.getString("mobile_no");
                            String enroll=c.getString("semester_enroll");
                            String intake=c.getString("intake");
                            String pass=c.getString("password");
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
                            new Student_insert(context).execute(name,passport,id,home_n,mob,enroll,intake,pass);
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


    //////// ******************* MAIN data base a insert **********************
    public class Student_insert extends AsyncTask<String, Void, String> {

        private Context context;

        public Student_insert(Context context) {
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



                link = "http://10.0.2.2:8078/project_signup_student_confirm.php" + data;
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
                        //new new DeletetAstudent(context).execute()
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


    //////////////////////********************* delete from temp student *****************************/////////

    public class DeletetAstudent extends AsyncTask<String, Void, String> {

        private Context context;

        public DeletetAstudent(Context context) {
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




                link = "http://10.0.2.2:8078/project_delete_from_temp.php" + data;
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

                    if (query_result.equals("1")) {
                        Toast.makeText(context, "okay", Toast.LENGTH_SHORT).show();

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
