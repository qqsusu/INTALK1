package com.example.italk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity{
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private String room,temp_key,id;
    private DatabaseReference GroupChatRef;
    private DatabaseReference getPic;
    private List<Msg> msgList = new ArrayList<>();
    private ImageView RightHead,LeftHead;
    private ListView LV;
    int picNum ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //LV.scrollTo(0,999999);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent getRoom = this.getIntent();
        final String roomID = getRoom.getStringExtra("roomID");
        Intent getID = this.getIntent();
        final String userID = getID.getStringExtra("userID");
        room = roomID;
        id = userID;

        GroupChatRef = FirebaseDatabase.getInstance().getReference().child("GroupChat");

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        setContentView(R.layout.msg_item);

        RightHead = findViewById(R.id.head_right);
        LeftHead = findViewById(R.id.head_left);
        LV = findViewById(R.id.msg_list_view);

        //show picture
/*
        getPic = FirebaseDatabase.getInstance().getReference("userData").child(id);
        getPic.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                picNum[0] = Integer.parseInt(dataSnapshot.child("picPos").getValue().toString());
                System.out.println("Picture Position"+ picNum[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        //initMsgs();
        adapter = new MsgAdapter(Chat.this, R.layout.chat, msgList);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgListView = findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {

                    temp_key = GroupChatRef.child(roomID).push().getKey();
                    DatabaseReference message_root = GroupChatRef.child(roomID).child(temp_key);
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",userID);
                    map.put("msg",inputText.getText().toString());
                    message_root.updateChildren(map);

                   /* Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());*/
                    inputText.setText("");
                    //LV.scrollTo(0,10000);
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
                    System.out.println("fuck u John");
                }
                else System.out.println("no such datasnapshot");
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
        RightHead = findViewById(R.id.head_right);
        LeftHead = findViewById(R.id.head_left);

        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_user_name = (String)((DataSnapshot)i.next()).getValue();

            //get user picture
            /*getPic = FirebaseDatabase.getInstance().getReference("userData").child(chat_user_name);
            getPic.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    picNum = Integer.parseInt(dataSnapshot.child("picPos").getValue().toString());
                    System.out.println("Picture Position"+ picNum);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

            if(chat_user_name.equals(id) ) {
                Msg msg = new Msg(chat_msg, Msg.TYPE_SEND);
                msgList.add(msg);
                adapter.notifyDataSetChanged();
                //  setImage(RightHead,picNum);
            }
            else {
                Msg msg = new Msg(chat_msg, Msg.TYPE_RECEIVED);
                msgList.add(msg);
                adapter.notifyDataSetChanged();
                //setImage(LeftHead,picNum);
            }

        }
    }

    private void setImage(ImageView IM,int picPos){
        switch(picPos){
            case 0:
                IM.setImageResource(R.drawable.student);
                break;
            case 1:
                IM.setImageResource(R.drawable.bartender);
                break;
            case 2:
                IM.setImageResource(R.drawable.detective);
                break;
            case 3:
                IM.setImageResource(R.drawable.chef);
                break;
            case 4:
                IM.setImageResource(R.drawable.electrician);
                break;
            case 5:
                IM.setImageResource(R.drawable.student);
                break;
            case 6:
                IM.setImageResource(R.drawable.farmer);
                break;
            case 7:
                IM.setImageResource(R.drawable.joker);
                break;
            case 8:
                IM.setImageResource(R.drawable.athlete);
                break;
            case 9:
                IM.setImageResource(R.drawable.nurse);
                break;
            case 10:
                IM.setImageResource(R.drawable.pilot);
                break;
            case 11:
                IM.setImageResource(R.drawable.avatar);
                break;
            case 12:
                IM.setImageResource(R.drawable.police);
                break;
            case 13:
                IM.setImageResource(R.drawable.soldier);
                break;
            case 14:
                IM.setImageResource(R.drawable.dj);
                break;
            //...add others
            default:
                break;
        }
    }
}
