package edu.rosehulman.krystal.rhitclub.utils;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> implements PostClub.ChangeConsumer, GetUser.UserConsumer{

    private List<Club> mClubs;
    private ClubListFragment.OnClubSelectedListener mListener;
    private Context mContext;

    public ClubAdapter(ClubListFragment.OnClubSelectedListener listener,Context context,List<Club> clubs){
        mListener = listener;
        mContext = context;
        mClubs = clubs;
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
        if(MainActivity.getUser().containsClub(club)){
            holder.addClub.setBackgroundResource(R.drawable.ic_quit);
        }else{
            holder.addClub.setBackgroundResource(R.drawable.ic_add_circle_outline_black_48dp);
        }
        if(MainActivity.getUser().containsSubs(club)){
            holder.subscribe.setBackgroundResource(R.drawable.minus);
        }else{
            holder.subscribe.setBackgroundResource(R.drawable.ic_flag_black_48dp);
        }
        holder.clubTextView.setText(club.getName());
    }

    @Override
    public void onClubChangeLoaded() {
        new GetUser(this).execute("https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username);
    }

    @Override
    public void onUserLoaded(User user) {
        Log.d("New User: ",user.getmClubs().toString());
        ((MainActivity)mContext).setUser(user);
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
                    final boolean flag = MainActivity.getUser().addClub(mClubs.get(getAdapterPosition()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    if(flag){
                        builder.setTitle(mContext.getResources().getString(R.string.wantClub,mClubs.get(getAdapterPosition()).getName()));
                    }else{
                        builder.setTitle(mContext.getResources().getString(R.string.dontwantClub,mClubs.get(getAdapterPosition()).getName()));
                    }
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(flag){
                                addClub.setBackgroundResource(R.drawable.ic_quit);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"sign","post"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }else {
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"sign","delete"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                                addClub.setBackgroundResource(R.drawable.ic_add_circle_outline_black_48dp);
                            }
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(MainActivity.getUser().addClub(mClubs.get(getAdapterPosition()))){
                                addClub.setBackgroundResource(R.drawable.ic_quit);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"sign","post"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }else {
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"sign","delete"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                                addClub.setBackgroundResource(R.drawable.ic_add_circle_outline_black_48dp);
                            }
                        }
                    });
                    builder.create().show();
                }
            });
            subscribe = (Button)itemView.findViewById(R.id.subscribe_club);
            subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final boolean flag = MainActivity.getUser().subsClub(mClubs.get(getAdapterPosition()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    if(flag){
                        builder.setTitle(mContext.getResources().getString(R.string.subsClub,mClubs.get(getAdapterPosition()).getName()));
                    }else{
                        builder.setTitle(mContext.getResources().getString(R.string.dontsubsClub,mClubs.get(getAdapterPosition()).getName()));
                    }
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(flag){
                                subscribe.setBackgroundResource(R.drawable.minus);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"subscribe","post"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }else {
                                subscribe.setBackgroundResource(R.drawable.ic_flag_black_48dp);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"subscribe","delete"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(MainActivity.getUser().addClub(mClubs.get(getAdapterPosition()))){
                                subscribe.setBackgroundResource(R.drawable.minus);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"subscribe","post"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }else {
                                subscribe.setBackgroundResource(R.drawable.ic_flag_black_48dp);
                                String[] strings = {"https://club-app.csse.rose-hulman.edu/api/users/"+MainActivity.username+"/clubs/"+mClubs.get(getAdapterPosition()).getName(),"subscribe","delete"};
                                (new PostClub(ClubAdapter.this)).execute(strings);
                            }
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
