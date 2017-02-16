package edu.rosehulman.krystal.rhitclub.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.rosehulman.krystal.rhitclub.R;

/**
 * Created by KrystalYang on 1/20/17.
 */

public class Event implements Parcelable {
    private String name;
    private String holder;
    private String des;
    private String room;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Event(String name, String club) {
        this.name = name;
        this.holder = club;
    }

    public Event(){

    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    protected Event(Parcel in){
        this.name = in.readString();
        this.holder = in.readString();
        this.des = in.readString();
        this.room = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(holder);
        parcel.writeString(des);
        parcel.writeString(room);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

}
