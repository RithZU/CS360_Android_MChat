package com.example.sk_rith.m_chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private String displayUsername;
    private RecyclerView chatRecyclerView;
    private EditText messageText;
    private ImageButton sendButton;
    private DatabaseReference databaseReference;
    private ChatListAdapter chatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTitle("Chat Activity");
        displayUserName();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_list_view);
        messageText = (EditText) findViewById(R.id.inputMessage);
        sendButton = (ImageButton) findViewById(R.id.sendButton);

        messageText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    private void displayUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        displayUsername = user.getDisplayName();
       // displayUsername = preferences.getString("Username",null);
        if(displayUsername==null){
            displayUsername = "NONAME";
        }

    }

    private void sendMessage(){
        Log.d("M-Chat","Message sent");
        String messageInput  = messageText.getText().toString();
        if(!messageInput.equals("")){
            MessageHandler chat = new MessageHandler(messageInput, displayUsername);
            databaseReference.child("message").push().setValue(chat);
            messageText.setText("");
            //store in message reference
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;



        }
        return true;

    }
    @Override
    public void onStart(){
        super.onStart();

        chatListAdapter = new ChatListAdapter(this,databaseReference,displayUsername);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        chatRecyclerView.setAdapter(chatListAdapter);

    }

    @Override
    public void onStop(){
        super.onStop();
        chatListAdapter.removeListener();

    }
}
