package com.example.howkum.iumwsms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SignUpRoleSelction extends AppCompatActivity {

    Button student_selector,admin_selector,registry_selector,dean_selector,pl_selector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_role_selction);

        student_selector=(Button)findViewById(R.id.student_selector_btn);
        student_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StudentSignUpPage.class);

                startActivity(i);
            }
        });

        admin_selector=(Button)findViewById(R.id.button3);
        admin_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UserSignUp.class);
                i.putExtra("role","admin");
                startActivity(i);
            }
        });

        pl_selector=(Button)findViewById(R.id.pl_role_selector_btn);
        pl_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UserSignUp.class);
                i.putExtra("role","dean");
                startActivity(i);
            }
        });

        dean_selector=(Button)findViewById(R.id.dean_selector_btn);
        dean_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UserSignUp.class);
                i.putExtra("role","dean");
                startActivity(i);
            }
        });

        registry_selector=(Button)findViewById(R.id.registry_selector_btn);
        registry_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UserSignUp.class);
                i.putExtra("role","registry");
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_role_selction, menu);
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
