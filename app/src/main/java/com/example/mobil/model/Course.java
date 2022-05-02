package com.example.mobil.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Course implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String short_description;
    private ArrayList<String> usersList;
    private int userCount;
    private String ownerId;
    private String ownerName;
    private double price;
    private String start_date;
    private String created_at;
    private String updated_at;

    public Course(String title, String description, String short_description, String ownerId, String ownerName, double price, String start_date) {
        this.title = title;
        this.description = description;
        this.short_description = short_description;
        this.userCount = 0;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.price = price;
        this.start_date = start_date;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

        this.created_at = formatter.format(new Date(System.currentTimeMillis()));
        this.updated_at = created_at;
    }

    protected Course(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        short_description = in.readString();
        usersList = in.createStringArrayList();
        userCount = in.readInt();
        ownerId = in.readString();
        ownerName = in.readString();
        price = in.readDouble();
        start_date = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public ArrayList<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<String> usersList) {
        this.usersList = usersList;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String owner) {
        this.ownerId = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(short_description);
        parcel.writeStringList(usersList);
        parcel.writeInt(userCount);
        parcel.writeString(ownerId);
        parcel.writeString(ownerName);
        parcel.writeDouble(price);
        parcel.writeString(start_date);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}
