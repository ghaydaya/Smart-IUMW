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

public class RemoveStudent extends ListActivity {

    ArrayList<HashMap<String, String>> productsList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "student";
    private static final String TAG_PID = "student_id";
    private static final String TAG_NAME = "student_name";

    JSONArray products = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_student);

        productsList = new ArrayList<HashMap<String, String>>();
        new GetALLStudent(getApplicationContext()).execute();
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
                //new GetAstudent(getApplicationContext()).execute(pid);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remove_student, menu);
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

    ////************************ get all student **************************** /////
    public class GetALLStudent extends AsyncTask<String, Void, String> {
        private Context context;

        public GetALLStudent(Context context) {
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
                link = "http://10.0.2.2:8078/project_get_all_student.php";
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

                        products = jsonObj.getJSONArray("student");
//
                        // looping through All Products
                        //semesters=new ArrayList<String>();
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


    ///////////***************** delete from database ****************/////////
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




                link = "http://10.0.2.2:8078/project_delete_from_student.php" + data;
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
                        Toast.makeText(context, "okay, deleted", Toast.LENGTH_SHORT).show();

                        //new Student_insert(context).execute(jsonObj.getString("student_name"),jsonObj.getString("passport"),jsonObj.getString("student_id"),jsonObj.getString("home_no"),jsonObj.getString("mobile_no"),jsonObj.getString("semester_enroll"),jsonObj.getString("intake"),jsonObj.getString("password"));


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Could not delete", Toast.LENGTH_SHORT).show();
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
