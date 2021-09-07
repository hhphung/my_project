package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText passWord;
    private Button logIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user= (EditText) findViewById(R.id.editTextTextPersonName);
        passWord = (EditText) findViewById(R.id.editTextTextPassword);
        logIn = (Button) findViewById(R.id.button);



    }

    private void validate (String user, String password){
        if(user.equals("lienminh1998") && password.equals("hoiproa2")) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        }


    }
}