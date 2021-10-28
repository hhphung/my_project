package com.example.meetme;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.EditText;

import com.example.meetme.api.apiClientFactory;
import com.example.meetme.model.User;
import com.example.meetme.ui.RegisterPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class RegisterTests {

    private RegisterPage activity;

    @Before
    public void setUp() {
        //Test
        RegisterPage activity = Robolectric.buildActivity(RegisterPage.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testPassMisMatch()
    {
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
    }

    @Test
    public void testPassEmpty()
    {
        // enter text
        EditText usernameEntry = activity.findViewById(R.id.activity_main_username_input);
        usernameEntry.setText("Whittles1");

        EditText passwordEntry = activity.findViewById(R.id.activity_main_password_input);
        passwordEntry.setText("123");

        //try to leave confirm blank
        EditText confirmPassEntry = activity.findViewById(R.id.activity_main_password_input2);
        confirmPassEntry.setText("");
        activity.findViewById(R.id.activity_main_create_account_button).callOnClick();

        verify(confirmPassEntry).setError("Cannot be empty");

    }

    @Test
    public void NotNullActivity() throws Exception{
        assertNotNull(activity);
    }

}
