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
    private Parcelable[] mClubs;
    private Parcelable[] mEvents;

    public User(String mName, Parcelable[] mClubs, Parcelable[] mEvents) {
        this.mName = mName;
        this.mClubs = mClubs;
        this.mEvents = mEvents;
    }

    protected User(Parcel in){
        this.mName = in.readString();
        this.mClubs = in.readParcelableArray(Club.class.getClassLoader());
        this.mEvents = in.readParcelableArray(Event.class.getClassLoader());
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

    public Parcelable[] getmClubs() {
        return mClubs;
    }

    public void setmClubs(Parcelable[] mClubs) {
        this.mClubs = mClubs;
    }

    public Parcelable[] getmEvents() {
        return mEvents;
    }

    public void setmEvents(Parcelable[] mEvents) {
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
        parcel.writeParcelableArray(mClubs,i);
        parcel.writeParcelableArray(mEvents,i);
    }
}
