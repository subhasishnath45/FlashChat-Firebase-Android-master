package com.londonappbrewery.VChat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        // initializing mDatabaseReference...
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();




        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button of the soft keyboard is pressed
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    sendMessage();
                    // return value to true to indicate that the event actually happened.
                    return true;
            }
        });

        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName(){
        // getSharedPreferences(name, mode);
        // name = String: Desired preferences file. If a preferences file by this name does not exist, it will be created when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
        // mode = 	int: Operating mode. Value is either 0 or combination of MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE or MODE_MULTI_PROCESS.
        SharedPreferences prefs = this.getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if(mDisplayName == null){
            mDisplayName = "Anonymous";
        }
    }

    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
        Toast.makeText(this, "Messege sending..." ,Toast.LENGTH_SHORT ).show();
        String input = mInputText.getText().toString();
        if(!input.equals("")){// if input is not empty
            // creating InstantMessage instance by calling the parameterized constructor
            // 1st parameter - 
            InstantMessage chat = new InstantMessage(input,mDisplayName);
            // creating the child node for the database reference
            // push() - Creates a reference to an auto-generated child location.
            // setValue(object) = Set the data at this location to the given value( or object).
            mDatabaseReference.child("messages").push().setValue(chat);
            // After sending the message I'm making the message input box empty again
            mInputText.setText("");

        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.

    }

}
