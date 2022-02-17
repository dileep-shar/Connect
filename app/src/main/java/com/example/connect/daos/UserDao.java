package com.example.connect.daos;

import com.example.connect.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class UserDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     CollectionReference userCollection = db.collection("users");

     public void addUser(User user){
       if(user!=null){
         userCollection.document(user.getUserId()).set(user);


       }
     }
     public Task<DocumentSnapshot> getUserById(String uid){
         return userCollection.document(uid).get();
     }
}