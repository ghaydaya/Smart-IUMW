package com.example.howkum.iumwsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Student_home extends AppCompatActivity {

    TextView t;
    Button subreg;
    Button timetable;
    Button events;
    Button prevcourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Intent myIntent = getIntent(); // gets the previously created intent
        final String firstKeyName = myIntent.getStringExtra("id");
        t= (TextView) findViewById(R.id.textView5);
        t.setText("What do you want "+firstKeyName+"?");

        subreg=(Button) findViewById(R.id.subregbtn);
        //timetable=(Button)findViewById(R.id.timetablebtn);
        //events=(Button)findViewById(R.id.eventbtn);
        prevcourse=(Button)findViewById(R.id.prevcoursebtn);

        subreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SelectSemister.class);
                i.putExtra("id",firstKeyName);
                startActivity(i);
            }
        });

        prevcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CoursesOfXs.class);
                i.putExtra("id",firstKeyName);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
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
}
