package com.example.howkum.iumwsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProgramLeaderHome extends AppCompatActivity {

    Button addsem;
    Button edit;
    Button subdetail;
    Button student_details;
    //String
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_leader_home);

        //Intent myIntent = getIntent(); // gets the previously created intent
        //role_ = myIntent.getStringExtra("role");

        addsem= (Button)findViewById(R.id.add_sem_pl_btn);
        addsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddSemester.class);
                startActivity(i);
            }
        });

        edit= (Button) findViewById(R.id.edit_sub_reg_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),EditSubDetails.class);
                startActivity(i);
            }
        });
        subdetail= (Button) findViewById(R.id.sub_details_btn);
        subdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),SeeSubDetail.class);
                startActivity(i);
            }
        });

        student_details= (Button) findViewById(R.id.s_details_btn);
        student_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),SeeStudentDetails.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_program_leader_home, menu);
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
