package com.example.mobil.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
    /**
     * A User id-ja
     */
    private String id;
    /**
     * A User keresztneve
     */
    private String firstName;
    /**
     * A User vezetékneve
     */
    private String lastName;
    /**
     * A User teljes neve, számolva a vezeteknev+keresztnevbol (pl.: Timer Soma -> timersoma)
     */
    private String fullName;
    /**
     * A User teljes neve, számolva a Vezeteknev + Keresztnevbol (pl.: Timer Soma)
     */
    private String formattedFullName;
    /**
     * A user email címe
     */
    private String email;
    /**
     * A user jelszava
     */
    private String password;
    /**
     * Törölt-e a User
     */
    private boolean deleted;
    /**
     * Létrehozás dátuma
     */
    private String created_at;
    /**
     * Frissítés dátuma
     */
    private String updated_at;

    // Konstruktorok

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName.toLowerCase(Locale.ROOT)+firstName.toLowerCase(Locale.ROOT);
        this.formattedFullName = lastName.substring(0, 1).toUpperCase(Locale.ROOT) + lastName.substring(1) + " " + firstName.substring(0, 1).toUpperCase(Locale.ROOT) + firstName.substring(1);
        this.email = email;
        this.password = password;
        this.deleted = false;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

        this.created_at = formatter.format(new Date(System.currentTimeMillis()));
        this.updated_at = created_at;
    }

    // Getterek, Setterek

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFormattedFullName() {
        return formattedFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
}
