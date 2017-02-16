package edu.rosehulman.krystal.rhitclub.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rosehulman.krystal.rhitclub.R;

/**
 * Created by KrystalYang on 1/27/17.
 */

public class User implements Parcelable{

    private String mName;
    private boolean isOfficer;
    private String username;
    private String email;
    private ArrayList<String> mClubs = new ArrayList<>();
    private ArrayList<String> mEvents = new ArrayList<>();
    private ArrayList<String> mSubs = new ArrayList<>();
    private HashMap<String,String> title = new HashMap<>();

    public User(String un) {
        this.username = un;
        this.isOfficer = false;
    }

    public User(String user,String n, String club, String title){
        this.username = user;
        this.mName = n;
        this.title.put(club,title);
    }

    protected User(Parcel in){
        this.mName = in.readString();
        in.readList(mClubs,Club.class.getClassLoader());
        in.readList(mEvents,Event.class.getClassLoader());
        this.isOfficer = (in.readByte() != 0);
        this.username = in.readString();
        this.email = in.readString();
        in.readMap(title,HashMap.class.getClassLoader());
    }

    public boolean addClub(Club club){
        for(String c:mClubs){
            if(c.equals(club.getName())){
                mClubs.remove(c);
                return false;
            }
        }
        mClubs.add(club.getName());
        return true;
    }

    public boolean addEvent(Event event){
        for(String e:mEvents){
            if(e.equals(event.getName())){
                mEvents.remove(e);
                return false;
            }
        }
        mEvents.add(event.getName());
        return true;
    }

    public boolean subsClub(Club club){
        for(String c:mSubs){
            if(c.equals(club.getName())){
                mSubs.remove(c);
                return false;
            }
        }
        mSubs.add(club.getName());
        return true;
    }

    public boolean containsClub(Club club){
        for(String c:mClubs){
            if(c.equals(club.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean containsEvent(Event event){
        for(String e:mEvents){
            if(e.equals(event.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean containsSubs(Club club){
        for(String c:mSubs){
            if(c.equals(club.getName())){
                return true;
            }
        }
        return false;
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
        parcel.writeByte((byte) (isOfficer ? 1 : 0));
        parcel.writeList(mClubs);
        parcel.writeList(mEvents);
        parcel.writeList(mSubs);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeMap(title);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<String> getmClubs() {
        return mClubs;
    }

    public void setmClubs(ArrayList<String> mClubs) {
        this.mClubs = mClubs;
    }

    public List<String> getmEvents() {
        return mEvents;
    }

    public void setmEvents(ArrayList<String> mEvents) {
        this.mEvents = mEvents;
    }

    public List<String> getmSubs() {
        return mSubs;
    }

    public void setmSubs(ArrayList<String> mSubs) {
        this.mSubs = mSubs;
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public void setOfficer(boolean officer) {
        isOfficer = officer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, String> getTitle() {
        return title;
    }

    public void setTitle(HashMap<String, String> title) {
        this.title = title;
    }

}
