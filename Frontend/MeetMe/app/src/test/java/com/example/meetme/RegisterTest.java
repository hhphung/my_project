package com.example.meetme;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import android.app.Activity;
import android.widget.EditText;

import com.example.meetme.api.apiClientFactory;
import com.example.meetme.model.User;
import com.example.meetme.ui.RegisterPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RegisterTest {

    @Mock
    User createdUser;

    @Before
    public void SetUpTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test()
    {
        //Test
        RegisterPage activity = Robolectric.buildActivity(RegisterPage.class)
                .create()
                .resume()
                .get();

        // enter text
        EditText usernameEntry = activity.findViewById(R.id.activity_main_username_input);
        usernameEntry.setText("Whittles1");

        EditText passwordEntry = activity.findViewById(R.id.activity_main_password_input);
        passwordEntry.setText("123");

        //purposefully have wrong second password
        EditText confirmPassEntry = activity.findViewById(R.id.activity_main_password_input2);
        confirmPassEntry.setText("12");

        //click create account btn
        activity.findViewById(R.id.activity_main_create_account_button).callOnClick();

        verify(confirmPassEntry).setError("Passwords do not match. Try again");

        //try to leave confirm blank
        confirmPassEntry.setText("");
        activity.findViewById(R.id.activity_main_create_account_button).callOnClick();

        verify(confirmPassEntry).setError("Cannot be empty");

        //Finally match passwords
        confirmPassEntry.setText("123");
        activity.findViewById(R.id.activity_main_create_account_button).callOnClick();

        verify(confirmPassEntry).setError(null);
    }






}
