package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {
    public static HashMap store;
    private EditText user;
    private EditText passWord1;
    private EditText passWord2;
    private Button signUp;
    private TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        user= (EditText) findViewById(R.id.editTextTextPersonName2);
        passWord1 = (EditText) findViewById(R.id.pass1);
        passWord2 = (EditText) findViewById(R.id.pass2);
        signUp= (Button) findViewById(R.id.button5);
        error = (TextView) findViewById(R.id.error);
        store = new HashMap<String, String>();
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate(user.getText().toString(),passWord1.getText().toString(), passWord2.getText().toString() );
            }
        });

    }


    private void validate (String user, String password1, String password2) {
        if (!password1.equals(password2)) {
            error.setVisibility(TextView.VISIBLE);
        } else {
            error.setVisibility(TextView.INVISIBLE);
            store.put(user, password1);
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
        }

    }
}