package com.example.howkum.iumwsms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartUpRoleSelect extends AppCompatActivity {

    Button student,dean,admin,prog_leader,registry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_role_select);

        student=(Button)findViewById(R.id.student_selector_btnp);
        dean=(Button)findViewById(R.id.dean_selector_btnp);
        admin=(Button)findViewById(R.id.button2);
        prog_leader=(Button)findViewById(R.id.pl_role_selector_btnp);
        registry=(Button)findViewById(R.id.registry_selector_btnp);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartPage.class);
                i.putExtra("role","student");
                startActivity(i);
            }
        });

        dean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartPage.class);
                i.putExtra("role","dean");
                startActivity(i);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartPage.class);
                i.putExtra("role","admin");
                startActivity(i);
            }
        });

        prog_leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartPage.class);
                i.putExtra("role","program leader");
                startActivity(i);
            }
        });

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartPage.class);
                i.putExtra("role","registry");
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_up_role_select, menu);
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
