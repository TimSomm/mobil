package com.example.mobil.model;

import java.util.ArrayList;
import java.util.Date;

public class Course {
    private String id;
    private String title;
    private String category;
    private ArrayList<User> usersList;
    private int userCount;
    private User owner;
    private boolean deleted;
    private Date created_at;
    private Date updated_at;

    public Course() {
    }

    public Course(String title, String category, ArrayList<User> usersList, int userCount, User owner, boolean deleted, Date created_at, Date updated_at) {
        this.title = title;
        this.category = category;
        this.usersList = usersList;
        this.userCount = userCount;
        this.owner = owner;
        this.deleted = deleted;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
