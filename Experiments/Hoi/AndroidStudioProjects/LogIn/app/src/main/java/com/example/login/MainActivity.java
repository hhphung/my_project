package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText passWord;
    private Button logIn;
    private TextView error;
    private Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user= (EditText) findViewById(R.id.editTextTextPersonName);
        passWord = (EditText) findViewById(R.id.editTextTextPassword);
        logIn = (Button) findViewById(R.id.button);
        error = (TextView) findViewById(R.id.textView);
        signUp = (Button) findViewById(R.id.button4);
        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                validate(user.getText().toString(),passWord.getText().toString() );
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });


    }
    private Map<String, String> store = MainActivity3.store;
    private void validate (String user, String password){



            if ((store.containsKey(user)) && store.containsValue(password)) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                error.setVisibility(TextView.INVISIBLE);
            } else {
                error.setVisibility(TextView.VISIBLE);


        }
    }
}