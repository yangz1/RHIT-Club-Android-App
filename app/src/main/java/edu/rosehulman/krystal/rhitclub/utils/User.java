package edu.rosehulman.krystal.rhitclub.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.krystal.rhitclub.R;

/**
 * Created by KrystalYang on 1/27/17.
 */

public class User implements Parcelable{

    private String mName;
    private List<Club> mClubs;
    private List<Event> mEvents;

    public User(String mName, List<Club> mClubs, List<Event> mEvents) {
        this.mName = mName;
        this.mClubs = mClubs;
        this.mEvents = mEvents;
    }

    protected User(Parcel in){
        this.mName = in.readString();
        in.readList(mClubs,Club.class.getClassLoader());
        in.readList(mEvents,Event.class.getClassLoader());
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Club> getmClubs() {
        return mClubs;
    }

    public void setmClubs(List<Club> mClubs) {
        this.mClubs = mClubs;
    }

    public List<Event> getmEvents() {
        return mEvents;
    }

    public void setmEvents(List<Event> mEvents) {
        this.mEvents = mEvents;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeList(mClubs);
        parcel.writeList(mEvents);
    }
}
