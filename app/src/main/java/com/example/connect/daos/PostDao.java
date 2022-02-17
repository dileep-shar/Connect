package com.example.connect.daos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.connect.HomeActivity;
import com.example.connect.models.Post;
import com.example.connect.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeReference;

public class PostDao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Task<DocumentSnapshot> task;
    CollectionReference postCollection = db.collection("posts");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public CollectionReference getPostCollection() {
        return postCollection;
    }

    public void addPost(String content, Context context) {

        String currentUserId = auth.getCurrentUser().getUid();
        System.out.println(currentUserId);
        UserDao userDao = new UserDao();
        task = userDao.getUserById(currentUserId);

        task.addOnCompleteListener(taske -> {
//                User user = new User(task.getResult());
            DocumentSnapshot documentSnapshot = taske.getResult();

//            User user = documentSnapshot.toObject(User.class);
            User user = new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getDisplayName(), auth.getCurrentUser().getPhotoUrl().toString());
            Long currentTime = System.currentTimeMillis();
            Post post = new Post(content, user, currentTime, null);

          postCollection.document().set(post);


        });
//        User user  = userDao.getUserById(currentUserId).addOnCompleteListener(task -> {
//
//        }).getResult().toObject(User.class);

    }

    public Task<DocumentSnapshot> getPostById(String postId) {
        return postCollection.document(postId).get();
    }

    public void updateLikes(String id,Context context) {
        String currentUserId = auth.getCurrentUser().getUid();

        getPostById(id).addOnCompleteListener(task1 -> {

            DocumentSnapshot d = task1.getResult();
           Object content = d.get("content");

            Long createdAt = (Long)d.get("createdAt");
            if(task1.isSuccessful()) {

            }

            Map<String,Object> createdBy = (Map)d.get("createdBy");
            ArrayList<String> likedBy = (ArrayList<String>) d.get("likedBy");

            User cby = new User((String) createdBy.get("userId"),(String)createdBy.get("userName"),createdBy.get("imageUrl").toString());

            Post post = new Post((String)d.get("content"),cby,(Long)d.get("createdAt"),likedBy);


            if (post.getLikedBy().contains(currentUserId)) {
                post.getLikedBy().remove(currentUserId);
            } else {
                post.getLikedBy().add(currentUserId);
            }
            postCollection.document(id).set(post);
        });
    }

}
