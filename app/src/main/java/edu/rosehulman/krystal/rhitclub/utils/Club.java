package edu.rosehulman.krystal.rhitclub.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.krystal.rhitclub.R;

/**
 * Created by KrystalYang on 1/20/17.
 */

public class Club implements Parcelable{
    private String name;
    private String type;
    private String description;
    private String officer;
    private String officerEmail;
    private boolean isOfficer;
    private ArrayList<String> members = new ArrayList<>();
    private ArrayList<String> subscribers = new ArrayList<>();
    private ArrayList<User> managers = new ArrayList<>();
    private String image;

    public Club(String name,String des,String offi,String imag) {
        this.name = name;
        this.officer = offi;
        this.description = des;
        this.image = imag;
    }

    public Club() {
    }

    protected Club(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.officer = in.readString();
        this.image = in.readString();
        this.type = in.readString();
        this.officerEmail = in.readString();
        in.readStringList(members);
        in.readStringList(subscribers);
        in.readList(managers,User.class.getClassLoader());
    }

    public static final Creator<Club> CREATOR = new Creator<Club>() {
        @Override
        public Club createFromParcel(Parcel in) {
            return new Club(in);
        }

        @Override
        public Club[] newArray(int size) {
            return new Club[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(officer);
        parcel.writeString(image);
        parcel.writeString(officerEmail);
        parcel.writeString(type);
        parcel.writeStringList(members);
        parcel.writeStringList(subscribers);
        parcel.writeList(managers);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOfficerEmail() {
        return officerEmail;
    }

    public void setOfficerEmail(String officerEmail) {
        this.officerEmail = officerEmail;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public void setisOfficer(boolean officer) {
        isOfficer = officer;
    }

    public ArrayList<User> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<User> managers) {
        this.managers = managers;
    }

}
