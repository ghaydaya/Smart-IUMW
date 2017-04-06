package com.example.howkum.iumwsms;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class ApproveRegistration extends ListActivity {

    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "student_id";
    private static final String TAG_NAME = "ss_code";
    private static final String TAG_CODE = "sub_code";
    JSONArray products = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_registration);
        productsList = new ArrayList<HashMap<String, String>>();

        new PendingApprovalGetAll(getApplicationContext()).execute();

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
                String name_= ((TextView)view.findViewById(R.id.name_text)).getText().toString();
                name_= name_.substring(name_.lastIndexOf("(")+1, name_.lastIndexOf(")"));
                //Toast.makeText(getApplicationContext(), "choco choco"+ name_ , Toast.LENGTH_SHORT).show();


                new GetAsubject(getApplicationContext()).execute(pid, name_);


                new DeleteFromTemp(getApplicationContext()).execute(pid,name_);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_approve_registration, menu);
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

    public class PendingApprovalGetAll extends AsyncTask<String, Void, String> {
        private Context context;

        public PendingApprovalGetAll(Context context) {
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
                link = "http://10.0.2.2:8078/project_get_temp_student_subjcet.php";
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

                        products = jsonObj.getJSONArray("subject");
//
                        // looping through All Products
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString("student_id");
                            String name = c.getString("sub_name");
                            String code=c.getString("sub_code");

                            String temp= id + "    " + name + "   ("+ code+")";
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

    ////////////////****************** Get A subject ********************************///
    public class GetAsubject extends AsyncTask<String, Void, String> {

        private Context context;

        public GetAsubject(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            String Id = arg0[0];
            String name=arg0[1];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?student_id=" + URLEncoder.encode(Id, "UTF-8");
                data += "&name=" + URLEncoder.encode(name, "UTF-8");



                link = "http://10.0.2.2:8078/project_get_a_subject_from_temp_student_subject.php" + data;
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
                            String code=c.getString(TAG_CODE);

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
                            new StudentSubjectInsert(context).execute(id,name,code);
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
    public class StudentSubjectInsert extends AsyncTask<String, Void, String> {

        private Context context;

        public StudentSubjectInsert(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String Name = arg0[1];
            String code = arg0[2];
            String Id = arg0[0];


            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?sname=" + URLEncoder.encode(Name, "UTF-8");
                data += "&sscode=" + URLEncoder.encode(code, "UTF-8");
                data += "&student_id=" + URLEncoder.encode(Id, "UTF-8");




                link = "http://10.0.2.2:8078/project_insert_student_sub.php" + data;
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
                        //Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                        //new new DeletetAstudent(context).execute()
                    } else if (query_result.equals("FAILURE")) {
                        //Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /////////********************* delete from temp ********************//////
    public class DeleteFromTemp extends AsyncTask<String, Void, String> {

        private Context context;

        public DeleteFromTemp(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            String Id = arg0[0];
            String code=arg0[1];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?student_id=" + URLEncoder.encode(Id, "UTF-8");
                data += "&code=" + URLEncoder.encode(code, "UTF-8");



                link = "http://10.0.2.2:8078/project_delete_from_temp_student_subject.php" + data;
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
