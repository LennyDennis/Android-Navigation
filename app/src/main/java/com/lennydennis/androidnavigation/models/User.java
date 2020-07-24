package com.lennydennis.androidnavigation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String profileImage;
    private String name;
    private String gender;
    private String interestedIn;
    private String status;

    public User(String profileImage, String name, String gender, String interestedIn, String status) {
        this.profileImage = profileImage;
        this.name = name;
        this.gender = gender;
        this.interestedIn = interestedIn;
        this.status = status;
    }

    public User(){

    }

    protected User(Parcel in) {
        profileImage = in.readString();
        name = in.readString();
        gender = in.readString();
        interestedIn = in.readString();
        status = in.readString();
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileImage);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(interestedIn);
        dest.writeString(status);
    }
}
