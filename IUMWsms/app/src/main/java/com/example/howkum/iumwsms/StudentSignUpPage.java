package com.example.howkum.iumwsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentSignUpPage extends AppCompatActivity {

    EditText Name,Passport,Id,Homeno,Mobile,Enroll,Intake,Password;
    String Names,Passports,Ids,Homenos,Mobiles,Enrolls,Intakes,Passwords;
    Button okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up_page);

        Name= (EditText)findViewById(R.id.signup_name_btn);
        Passport=(EditText)findViewById(R.id.signup_passport_btn);
        Id=(EditText)findViewById(R.id.signup_id_btn);
        Homeno=(EditText)findViewById(R.id.signup_homeno_btn);
        Mobile=(EditText)findViewById(R.id.signup_mobile_btn);
        Enroll=(EditText)findViewById(R.id.signup_enroll_btn);
        Intake=(EditText)findViewById(R.id.signup_intake_btn);
        Password=(EditText)findViewById(R.id.signup_password_btn);

        Names=Name.getText().toString();
        Passports=Passport.getText().toString();
        Ids=Id.getText().toString();
        Homenos=Homeno.getText().toString();
        Mobiles=Mobile.getText().toString();
        Enrolls=Enroll.getText().toString();
        Intakes=Intake.getText().toString();
        Passwords=Password.getText().toString();


        okay= (Button) findViewById(R.id.signup_student_btn);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new SignUpActivity(getApplicationContext()).execute(Names,Passports,Ids,Homenos,Mobiles,Enrolls,Intakes,Passwords);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_sign_up_page, menu);
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
