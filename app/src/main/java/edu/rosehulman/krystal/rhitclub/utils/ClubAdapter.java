package edu.rosehulman.krystal.rhitclub.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.fragments.ClubListFragment;

/**
 * Created by KrystalYang on 1/20/17.
 */

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder>{

    private List<Club> mClubs;
    private ClubListFragment.OnClubSelectedListener mListener;
    private Context mContext;

    public ClubAdapter(ClubListFragment.OnClubSelectedListener listener,Context context){
        mListener = listener;
        mContext = context;
        mClubs = Club.initializeClubs();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_row_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount(){
        return mClubs.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Club club = mClubs.get(position);
        holder.clubTextView.setText(club.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView clubTextView;

        public ViewHolder (View itemView){
            super(itemView);
            clubTextView = (TextView)itemView.findViewById(R.id.club_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClubSelected(mClubs.get(getAdapterPosition()));
        }
    }

}
