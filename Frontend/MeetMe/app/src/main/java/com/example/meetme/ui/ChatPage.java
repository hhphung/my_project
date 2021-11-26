package com.example.meetme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import android.os.Bundle;

import com.example.meetme.R;

public class ChatPage extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;

    private Button bConnect, bDisconnect, bSendButton;
    private TextView mOutput;
    private EditText mInput;

    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        // Get the buttons
        bConnect = findViewById(R.id.b_connect);
        bSendButton = findViewById(R.id.b_sendMessage);
        bDisconnect = findViewById(R.id.b_Disconnect);

        // Get the textview
        mOutput = findViewById(R.id.m_output);

        // Add scrolling
        mOutput.setMovementMethod(new ScrollingMovementMethod());

        //Get the editText
        mInput = findViewById(R.id.m_input);

        //get username of current client
        username = getIntent().getStringExtra("username");

        // Add handlers to the buttons
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWebSocket();
            }
        });

        bDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebSocketClient.close();
                finish();
            }
        });

        bSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the message from the input
                String message = mInput.getText().toString();

                // If the message is not empty, send the message
                if(message != null && message.length() > 0){
                    mWebSocketClient.send(message);
                    mInput.setText("");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            /*
             * To test the clientside without the backend, simply connect to an echo server such as:
             *  "ws://echo.websocket.org"
             */
            uri = new URI("ws://coms-309-017.cs.iastate.edu:8080/chat/" + username);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {

                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                // Appends the message received to the previous messages
                mOutput.append("\n" + username + ": " + msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}
