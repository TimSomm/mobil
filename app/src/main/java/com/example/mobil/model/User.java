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
     * A user email címe
     */
    private String email;
    /**
     * A user jelszava
     */
    private String password;
    /**
     * A User születési dátuma
     */
    private String birthDate;
    /**
     * A User jogköre
     */
    private String role;
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

    public User(String firstName, String lastName, String email, String password, String birthDate, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName.toLowerCase(Locale.ROOT)+firstName.toLowerCase(Locale.ROOT);
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean hasRight(String right, String role) {
        try {
            return UserPermissions.roleHasRight(right, role);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean softDelete() {
        if (!this.isDeleted()) {
            return false;
        }

        this.setDeleted(true);
        return true;
    }
}
