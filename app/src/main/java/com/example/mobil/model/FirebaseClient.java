package com.example.mobil.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void saveUser(User user, FirebaseUser currentUser) {
        String uid = currentUser.getUid();
        user.setId(uid);
        firebaseFirestore.collection(FirebaseClient.USER_COLLECTION).add(user);
    }

    public void deleteAuthUser(FirebaseUser user) {
        user.delete();
    }

    public boolean softDeleteUser(String id) {
        DocumentReference docRef = firebaseFirestore.collection(USER_COLLECTION).document(id);
        firebaseFirestore.runTransaction(transaction -> {
            transaction.update(docRef, "deleted", true);
            return true;
        });
        return true;
    }

    public boolean deleteUser(String id) {
        firebaseFirestore.collection(USER_COLLECTION).document(id).delete();
        return true;
    }

    public CollectionReference getCollection(String collectionName) {
        return firebaseFirestore.collection(collectionName);
    }
}
