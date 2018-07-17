package com.example.sk_rith.m_chat;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by SK_Rith on 7/16/2018.
 */

public class ChatAdapter extends BaseAdapter{
    private Activity activity;
    private DatabaseReference databaseReference;
    private String userName;
    private ArrayList<DataSnapshot> snapshotArrayList;
    //read database, return datasnapshot

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("Add Child","Added");
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


    public ChatAdapter(Activity activity, DatabaseReference databaseReference, String userName){
        this.activity = activity;
        this.databaseReference = databaseReference.child("message");
        databaseReference.addChildEventListener(listener);
        this.userName = userName;
        this.snapshotArrayList = new ArrayList<>();
    }
    static class ViewHolder{ //hold view in a single chat row
        TextView messageAuthor;
        TextView body;
        LinearLayout.LayoutParams params;

    }

    @Override
    public int getCount() {
        return snapshotArrayList.size();
    }

    @Override
    public MessageHandler getItem(int position) {
        DataSnapshot snapshot = snapshotArrayList.get(position);
        return snapshot.getValue(MessageHandler.class); //convert snapshot to class
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_list, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.messageAuthor = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.messageAuthor.getLayoutParams();
            convertView.setTag(holder);
        }

        final MessageHandler message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String author = message.getMessageAuthor();
        holder.messageAuthor.setText(author);
        Log.d("Author", author);
        String msg = message.getMessage();
        holder.body.setText(msg);

        return convertView;
    }

    public void cleanup(){
        databaseReference.removeEventListener(listener); //remove event when app leave foreground
    }
}
