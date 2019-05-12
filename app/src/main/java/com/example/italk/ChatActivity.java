package com.example.italk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private Button btn_send_msg;
    private TextView input_text;
    private EditText msg;

    private DatabaseReference GroupChatRef;
    private String temp_key;
    private String room;
    private ScrollView SW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent getRoom = this.getIntent();
        final String roomID = getRoom.getStringExtra("roomID");
        Intent getID = this.getIntent();
        final String userID = getID.getStringExtra("userID");
        room = roomID;
        SW = findViewById(R.id.chatroom_scrollView);


        GroupChatRef = FirebaseDatabase.getInstance().getReference().child("GroupChat");
        btn_send_msg = findViewById(R.id.send_btn);
        msg = findViewById(R.id.msg);
        input_text = findViewById(R.id.input_text);

        SW.scrollTo(0,99999);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_key = GroupChatRef.child(roomID).push().getKey();
                DatabaseReference message_root = GroupChatRef.child(roomID).child(temp_key);
                Map<String,Object> map = new HashMap<>();
                if(!msg.getText().toString().equals("")){
                    map.put("name",userID);
                    map.put("msg",msg.getText().toString());
                    message_root.updateChildren(map);
                    msg.setText("");
                    //SW.requestFocus(View.FOCUS_UP);
                    SW.scrollTo(0,99999);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GroupChatRef.child(room).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    append_chat_conversation(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    append_chat_conversation(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private String chat_msg, chat_user_name;
    private void append_chat_conversation(DataSnapshot dataSnapshot){
        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_user_name = (String)((DataSnapshot)i.next()).getValue();
            input_text.append(chat_user_name+":"+chat_msg+"\n");
        }
    }
}
