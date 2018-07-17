package com.example.sk_rith.m_chat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SK_Rith on 7/16/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private Activity activity;
    private DatabaseReference databaseReference;
    private String userName;
    private ArrayList<DataSnapshot> snapshotArrayList;
    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("Child", "onChildAdded:" + dataSnapshot.getKey());
            snapshotArrayList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference databaseReference, String userName){
        this.activity = activity;
        this.databaseReference = databaseReference.child("message");
        this.databaseReference.addChildEventListener(listener);
        this.userName = userName;
        this.snapshotArrayList = new ArrayList<>();


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contextView = inflater.inflate(R.layout.message_list, parent,false);
        return new MyViewHolder(contextView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MessageHandler messageHandler = snapshotArrayList.get(position).getValue(MessageHandler.class);
        boolean isMe = messageHandler.getMessageAuthor().equals(userName);
        setChatRowAppearance(isMe,holder);

        holder.messageAuthor.setText(messageHandler.getMessage());
        holder.body.setText(messageHandler.getMessageAuthor());

    }

    @Override
    public int getItemCount() {
        return snapshotArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView messageAuthor;
        TextView body;


        public MyViewHolder(View itemView) {
            super(itemView);


            messageAuthor = itemView.findViewById(R.id.author);
            body = itemView.findViewById(R.id.messageForAuthor);


        }
    }
    public void removeListener(){
        databaseReference.removeEventListener(listener); //remove event when app leave foreground
    }
    private void setChatRowAppearance(boolean isItMe,MyViewHolder holder){
        if(isItMe){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            holder.messageAuthor.setLayoutParams(params);
            holder.body.setLayoutParams(params);
        }

    }
}
