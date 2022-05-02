package com.example.mobil.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FirebaseClient {

    public static final String USER_COLLECTION = "users";
    private final FirebaseAuth firebaseAuth = getAuthInstance();
    private final FirebaseFirestore firebaseFirestore = getFirestoreInstance();

    private FirebaseAuth getAuthInstance() {
        if (firebaseAuth != null) {
            return this.firebaseAuth;
        }

        return FirebaseAuth.getInstance();
    }

    private FirebaseFirestore getFirestoreInstance() {
        if (firebaseFirestore != null) {
            return this.firebaseFirestore;
        }

        return FirebaseFirestore.getInstance();
    }

    public Task<AuthResult> registerWithEmailAndPassword(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> loginWithEmailAndPasword(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public void logOut() {
        firebaseAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public Task<DocumentSnapshot> getUser(String id) {
        return firebaseFirestore.collection(USER_COLLECTION).document(id).get();
    }

    public void saveUser(User user) {
        firebaseFirestore.collection(FirebaseClient.USER_COLLECTION).document(user.getId()).set(user);
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).updatePassword(user.getPassword());
        firebaseAuth.getCurrentUser().updateEmail(user.getEmail());
    }

    public void deleteAuthUser(FirebaseUser user) {
        user.delete();
    }

    public void deleteUser(User user) {
        firebaseFirestore.collection(USER_COLLECTION).document(user.getId()).delete();
    }

    public CollectionReference getCollection(String collectionName) {
        return firebaseFirestore.collection(collectionName);
    }
}
