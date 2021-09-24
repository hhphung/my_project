package tests.firstproject;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://90f5d506-b47d-45fe-a176-b7061ac29f55.mock.pstmn.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Implement Interface via retrofit
        JsonObjectApi apiClient = retrofit.create(JsonObjectApi.class);

        // Listen for the click of the callApiButton
        callApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enqueue the request to the server
                apiClient.getJsonObject().enqueue(new Callback<JsonObject>() {

                    //On a response, display the Object we received.
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String toDisplay = "name: " + response.body().getName() +
                                "\nNumber: " + response.body().getNumber().toString();

                        apiResponse.setText(toDisplay);
                    }

                    //On a failure, notify user.
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //use to log our failures by using throw. (t)
                        apiResponse.setText("Failed");
                    }
                });
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

//Interface for retrofit to use.
interface JsonObjectApi {
    @GET("/JsonObject")
    Call<JsonObject> getJsonObject();
}

//Class to represent the object we received from the server. (Based on the postman JsonObject Whitney created)
class JsonObject {
    //The @SerializedName tag allows you to retrieve a tag from the body of the JSON response
    //and call it something else in the program.
    //If I un-comment line 90, I could rename line 91 to anything I want.

    //@SerializedName("name")
    private String name;

    //@SerializedName("Number")
    private Long Number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Long number) {
        Number = number;
    }

    public String getName(){
        return name;
    }

    public Long getNumber(){
        return Number;
    }
}

//{name: "Whitney", Number: 3123099166}