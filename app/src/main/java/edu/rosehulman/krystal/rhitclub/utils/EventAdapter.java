package edu.rosehulman.krystal.rhitclub.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.krystal.rhitclub.MainActivity;
import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.fragments.EventListFragment;

/**
 * Created by KrystalYang on 1/20/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event>mEvents;
    private EventListFragment.OnEventSelectedListener mListener;
    private Context mContext;

    public EventAdapter(EventListFragment.OnEventSelectedListener listener,Context context){
            mListener=listener;
            mContext=context;
            mEvents=Event.initializeEvents();
        }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_view,parent,false);
        return new EventAdapter.ViewHolder(view);
        }

    @Override
    public int getItemCount(){
        return mEvents.size();
        }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder,int position){
        Event event=mEvents.get(position);
        holder.eventTextView.setText(event.getName());
        holder.eventHolerView.setText(mContext.getResources().getString(R.string.holder_is,event.getHolder().getName()));
        }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView eventTextView;
        private TextView eventHolerView;
        private Button addEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            eventTextView = (TextView) itemView.findViewById(R.id.event_text_view);
            eventHolerView = (TextView) itemView.findViewById(R.id.event_club_view);
            addEvent = (Button)itemView.findViewById(R.id.add_event);
            itemView.setOnClickListener(this);
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean flag = MainActivity.getmUser().addEvent(mEvents.get(getAdapterPosition()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    if(flag) {
                        builder.setTitle(mContext.getResources().getString(R.string.wantEvent, mEvents.get(getAdapterPosition()).getName()));
                    }else{
                        builder.setTitle(mContext.getResources().getString(R.string.dontwantEvent, mEvents.get(getAdapterPosition()).getName()));
                    }
                    builder.setPositiveButton(android.R.string.ok,null);
                    builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.getmUser().addEvent(mEvents.get(getAdapterPosition()));
                        }
                    });
                    builder.create().show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            mListener.onEventSelected(mEvents.get(getAdapterPosition()));
        }

    }
}
