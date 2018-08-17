package com.londonappbrewery.VChat;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
 * Created by SubhasishNath on 8/7/2018.
 */

public class ChatListAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    // A DataSnapshot instance contains data from a Firebase Database location.
    // Any time you read Database data, you receive the data as a DataSnapshot.
    public ArrayList<DataSnapshot> mSnapshotlist;
    final ArrayList<String> keyList = new ArrayList<>();

    // created ChildEventListener type mListener variable as a member variable.
    // and assign a new ChildEventListener object to it
    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        // This method is triggered when a new child is added to the location to which this listener was added.
        // In our case this method will be triggered when a new chat message is added to the database.
        // When a new message will be added and this method is triggered, we will recieve a DataSnapShot
        // The DataSnapShot will contains our newly added chat message data and comes in the JSON format.
        // we will add the DataSnapShot as a new item of ArrayList<DataSnapshot> mSnapshotlist
            mSnapshotlist.add(dataSnapshot);
        // now the dataSnapShot is appended to our arraylist
        // Now we need to notify the listView to refresh itself, because new information is available
        notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
// This method is triggered when the data at a child location has changed.
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//            This method is triggered when a child is removed from the location to which this listener
//            was added.
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            This method is triggered when a child location's priority changes.
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
//            This method will be triggered in the event that this listener either failed at the server,
//            or is removed as a result of the security and Firebase rules.
        }
    };


    // creating the constructor.
    public ChatListAdapter(Activity activity,DatabaseReference ref,String name){
        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
//        Add a listener for child events occurring at this location.
//        When child locations are added, removed, changed, or moved,
//        the listener will be triggered for the appropriate event
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotlist = new ArrayList<>();
    }
    // helper class for holding the views for an individual chat row
    // chat row will be created by inflating chat_msg_row layout file.
    // NB: only nested or inner classes can be static in java********
    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

// How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return mSnapshotlist.size();
    }
// Get the data item associated with the specified position in the data set.
// The return type of getItem() method can be any object.Here it is - InstantMessage
    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotlist.get(position);
        // This method is used to convert the data contained in this snapshot into a class of your choosing.
        InstantMessage message = snapshot.getValue(InstantMessage.class);
        return message;
    }
// Get the row id associated with the specified position in the list.
    @Override
    public long getItemId(int position) {
        return 0;
    }
// Get a View that displays the data at the specified position in the data set.
// You can either create a View manually or inflate it from an XML layout file.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if there is not even a single row yet,
        if(convertView == null){
            // LAYOUT_INFLATER_SERVICE is used with getSystemService(String) to retrieve a LayoutInflater
            // for inflating layout resources in this context.

            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // I'm creating a new row by inflating the layout file for a row.
            convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);
            // creating new ViewHolder object ny instanciating ViewHolder class...
            // we are storing everything of that newly created row
            // inside a brand new viewholder...
            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            // Unlike IDs, tags are not used to identify views.
            // Tags are essentially an extra piece of information that can be associated
            // with a view. They are most often used as a convenience to store data
            // related to views in the views themselves
            // Tags may also be specified with arbitrary objects from code using setTag(Object)
            // setTag method saves the viewHolder for later.
            convertView.setTag(holder);
        }
        // If there already exists rows, following code wll be executed.
        // getting the item of a particular position
        // and storing it to an InstantMessage variable.
        final InstantMessage message = getItem(position);
        // retriving the existing viewHolder from convertView.
        // that was previously saved by - convertView.setTag(holder);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        // calling the getAuthor() method of InstantMessage object message.
        String author = message.getAuthor();
        // storing it to the view holder's authorname.
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);

        return convertView;
    }

    // this cleanup method will be called from MainChatActivity activity's onStop() method.
    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }

}
