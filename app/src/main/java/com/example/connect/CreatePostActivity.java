package com.example.connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.connect.daos.PostDao;

public class CreatePostActivity extends AppCompatActivity {
Button postButton ;
EditText content;
PostDao postDao;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        postButton = findViewById(R.id.postButton);
        content  = findViewById(R.id.editTextTextForPost);
        postButton.setOnClickListener(view -> {
         if(!content.getText().toString().isEmpty()){
            postDao = new PostDao();
            postDao.addPost(content.getText().toString(),getApplicationContext());
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
         }
        });
    }
}