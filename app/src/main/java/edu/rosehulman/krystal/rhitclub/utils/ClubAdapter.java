package edu.rosehulman.krystal.rhitclub.utils;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import edu.rosehulman.krystal.rhitclub.MainActivity;
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
        mClubs.add(new Club("TestClub","This is a test Club","officer: none",R.drawable.sleeve));
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
        private Button addClub;
        private Button subscribe;

        public ViewHolder (View itemView){
            super(itemView);
            clubTextView = (TextView)itemView.findViewById(R.id.club_text_view);
            addClub = (Button)itemView.findViewById(R.id.add_club);
            itemView.setOnClickListener(this);
            addClub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Show dialog
                    final boolean flag = MainActivity.getmUser().addClub(mClubs.get(getAdapterPosition()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    if(flag){
                        builder.setTitle(mContext.getResources().getString(R.string.wantClub,mClubs.get(getAdapterPosition()).getName()));
                    }else{
                        builder.setTitle(mContext.getResources().getString(R.string.dontwantClub,mClubs.get(getAdapterPosition()).getName()));
                    }
                    builder.setPositiveButton(android.R.string.ok,null);
                    builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.getmUser().addClub(mClubs.get(getAdapterPosition()));
                        }
                    });
                    builder.create().show();
                }
            });
            subscribe = (Button)itemView.findViewById(R.id.subscribe_club);
            subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean flag = MainActivity.getmUser().subsClub(mClubs.get(getAdapterPosition()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    if(flag){
                        builder.setTitle(mContext.getResources().getString(R.string.subsClub,mClubs.get(getAdapterPosition()).getName()));
                    }else{
                        builder.setTitle(mContext.getResources().getString(R.string.dontsubsClub,mClubs.get(getAdapterPosition()).getName()));
                    }
                    builder.setPositiveButton(android.R.string.ok,null);
                    builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.getmUser().subsClub(mClubs.get(getAdapterPosition()));
                        }
                    });
                    builder.create().show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            mListener.onClubSelected(mClubs.get(getAdapterPosition()));
        }
    }

}
