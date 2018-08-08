package com.londonappbrewery.VChat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
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
    private ArrayList<DataSnapshot> mSnapshotlist;

    // creating the constructor.
    public ChatListAdapter(Activity activity,DatabaseReference ref,String name){
        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        mSnapshotlist = new ArrayList<>();
    }
    // helper class for an individual chat row
    // NB: only nested or inner classes can be static in java********
    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

// How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return 0;
    }
// Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return null;
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
        return null;
    }
}
