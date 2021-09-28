package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.meetme.api.apiClientFactory;
import com.example.meetme.api.LambdaInterface;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.api.UserApi;
import com.example.meetme.model.User;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        TextView welcomeText = findViewById(R.id.activity_welcome_text);

        GetUserApi().getUser().enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getUsername() + "!");
        }));
    }

}