package com.example.mobil.model;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseClient {

    private final FirebaseAuth firebaseAuth = getAuthInstance();

    public FirebaseAuth getAuthInstance() {
        if (firebaseAuth != null) {
            return this.firebaseAuth;
        }

        return FirebaseAuth.getInstance();
    }
}
