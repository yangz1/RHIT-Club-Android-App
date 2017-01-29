package edu.rosehulman.krystal.rhitclub.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.fragments.EventListFragment;
import edu.rosehulman.krystal.rhitclub.fragments.MyAccountFragment;

import static edu.rosehulman.krystal.rhitclub.R.string.event;

/**
 * Created by KrystalYang on 1/27/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<Club> mClubs;
    private List<Event> mEvents;
    private MyAccountFragment.OnMyAccountSelectedListener mListener;
    private Context mContext;
    private boolean isClub;

    public UserAdapter(MyAccountFragment.OnMyAccountSelectedListener listener, Context context,boolean flag){
        mListener=listener;
        mContext=context;
        isClub=flag;
        mEvents=Event.initializeEvents();
        mClubs=Club.initializeClubs();
    }

    public void setmEvents(List<Event> mEvents) {
        this.mEvents = mEvents;
        notifyDataSetChanged();
    }

    public void setmClubs(List<Club> mClubs) {
        this.mClubs = mClubs;
        notifyDataSetChanged();
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_account_row_view,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount(){
        if(isClub){
            return mClubs.size();
        }else{
            return mEvents.size();
        }
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder,int position){
        String s ="";
        if(isClub){
            Club club=mClubs.get(position);
            s = club.getName();
        }else{
            Event event=mEvents.get(position);
            s = event.getName();
        }
        holder.eventOrClub.setText(s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventOrClub;

        public ViewHolder(View itemView) {
            super(itemView);
            eventOrClub = (TextView) itemView.findViewById(R.id.event_or_club);
        }
    }
}
