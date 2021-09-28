package tests.firstproject;

import static tests.firstproject.api.ApiClientFactory.GetJsonObjectApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import tests.firstproject.api.SlimCallback;
import tests.firstproject.model.JsonObject;

public class ApiTests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_tests);

        // Init buttons, textviews, and retrofit
        // Code in text values so they never revert (I guess)
        Button callApiButton = findViewById(R.id.api_call_api_button);
        callApiButton.setText("Call Postman API");
        Button backButton = findViewById(R.id.api_back_button);
        backButton.setText("Back");
        TextView apiResponse = findViewById(R.id.api_response_TextView);
        apiResponse.setText("No Response Yet");


        // Listen for the click of the callApiButton
        callApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enqueue the request to the server
                GetJsonObjectApi().getJsonObject().enqueue(new SlimCallback<JsonObject>(response -> {
                    String result = "Name: " + response.getName() +
                            "\nNumber: " + response.getNumber();
                    apiResponse.setText(result);
                }));
            }
        });

        //If user presses back, destroy this view.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}



//{name: "Whitney", Number: 3123099166}