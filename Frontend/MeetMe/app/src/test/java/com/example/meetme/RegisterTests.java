package com.example.meetme;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.meetme.ui.RegisterPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class RegisterTests {

    private RegisterPage activity;

    @Before
    public void setUp() {
        //Test
        activity = Robolectric.buildActivity(RegisterPage.class)
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
        Button createAct = activity.findViewById(R.id.activity_main_create_account_button);
        createAct.callOnClick();

        String error = "Passwords do not match. Try again";
        TextView errMsg = activity.findViewById(R.id.activity_main_err_msg);

        assertEquals(error, errMsg.getText());

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

        String error = "Cannot be empty";
        TextView errMsg = activity.findViewById(R.id.activity_main_err_msg);

        assertEquals(error, errMsg.getText());

    }

    @Test
    public void NotNullActivity() throws Exception{
        assertNotNull(activity);
    }

}