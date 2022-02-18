package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.example.connect.daos.PostDao;
import com.example.connect.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton createPost;
    PostAdapter postAdapter;
    PostDao postDao;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        postDao = new PostDao();
        recyclerView = findViewById(R.id.recyclerViewForPosts);
        createPost = findViewById(R.id.openPostCreation);
        createPost.setOnClickListener(view -> {
            Intent openPostActivity = new Intent(getApplicationContext(), CreatePostActivity.class);
            startActivity(openPostActivity);
        });

        setUpRecyclerView();
        Toast.makeText(this, "This is successfully launched app", Toast.LENGTH_SHORT).show();

    }

    public void setUpRecyclerView() {
        CollectionReference postCollection = postDao.getPostCollection();

        Query query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Post> posts = new FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();
        postAdapter = new PostAdapter(posts, (v, position) -> postDao.updateLikes(position, getApplicationContext()));
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        postAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        postAdapter.stopListening();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_landscape_home);
        } else {
            setContentView(R.layout.activity_home);

        }
    }
}