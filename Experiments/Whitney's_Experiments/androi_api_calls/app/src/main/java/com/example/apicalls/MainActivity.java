package com.example.apicalls;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apicalls.api.SlimCallback;
import com.example.apicalls.model.Photo;
import com.example.apicalls.model.Post;

import static com.example.apicalls.api.apiClientFactory.GetPhotoApi;
import static com.example.apicalls.api.apiClientFactory.GetPostApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);

        apiText1.setMovementMethod(new ScrollingMovementMethod());

        Button photoButton = findViewById(R.id.activity_main_btn_for_photo);
        EditText photoNumInput = findViewById(R.id.activity_main_photoNum_input);

        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                GetPhotoApi().getPhotoByNum(photoNumInput.getText().toString()).enqueue(new SlimCallback<Photo>(photo ->{
                    apiText1.setText(photo.printable());
                }));
            }
        });

//        GetPhotoApi().getFirstPhoto().enqueue(new SlimCallback<Photo>(responsePhoto -> {
//            apiText1.setText(responsePhoto.printable());
//        }));

        //getting scrollable list
//        GetPhotoApi().getAllPhotos().enqueue(new SlimCallback<List<Photo>>(photos -> {
//            apiText1.setText("");
//            for(int i = 0; i < photos.size(); i++) {
//                apiText1.append(photos.get(i).printable());
//            }
//        }, "MultiplePhotosApi"));


    }

}





