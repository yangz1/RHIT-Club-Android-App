package edu.rosehulman.krystal.rhitclub.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView clubTextView;

        public ViewHolder (View itemView){
            super(itemView);
            clubTextView = (TextView)
        }
    }

}
