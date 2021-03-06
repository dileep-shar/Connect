package com.example.connect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.connect.Utils.timePassed;
import com.example.connect.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    ViewGroup parent;
    String postId;
    CustomItemClickListener listener;
    timePassed t = new timePassed();

    public PostAdapter(@NonNull FirestoreRecyclerOptions options, CustomItemClickListener listener) {
        super(options);
        this.listener = listener;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_single_post,parent,false);
            PostViewHolder p = new PostViewHolder(view,viewType);
            view.setOnClickListener(view1 -> listener.onItemClick(view1, getSnapshots().getSnapshot(p.getAbsoluteAdapterPosition()).getId()));

            return p;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post, parent, false);
            PostViewHolder p = new PostViewHolder(view, viewType);

            view.setOnClickListener(view1 -> listener.onItemClick(view1, getSnapshots().getSnapshot(p.getAbsoluteAdapterPosition()).getId()));
            return p;
        }

    }


    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getUid();

        holder.name.setText(model.getCreatedBy().getUserName());
        holder.content.setText(model.getContent());
        holder.countLikes.setText(String.format("%d", model.getLikedBy().size()));

        holder.created.setText(t.getTimePassed(model.getCreatedAt()));
        Glide.with(holder.profile_picture.getContext()).load(model.getCreatedBy().getImageUrl()).circleCrop().into(holder.profile_picture);
        if (model.getLikedBy().contains(currentUserId)) {
            holder.likebutton.setImageDrawable(ContextCompat.getDrawable(holder.likebutton.getContext(), R.drawable.liked_heart_image_liked));
        } else {
            holder.likebutton.setImageDrawable(ContextCompat.getDrawable(holder.likebutton.getContext(), R.drawable.liked_heart_image_not));
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView name, content, countLikes, created;
        ImageView profile_picture, likebutton;

        public PostViewHolder(@NonNull View itemView,int num) {
            super(itemView);
            if(num==1) {
                name = itemView.findViewById(R.id.userName);
                content = itemView.findViewById(R.id.content_display);
                profile_picture = itemView.findViewById(R.id.profile_picture);
                created = itemView.findViewById(R.id.posted_time);
                countLikes = itemView.findViewById(R.id.count_likes);
                likebutton = itemView.findViewById(R.id.likeButton);
            }else{
                name = itemView.findViewById(R.id.userName_mine);
                content = itemView.findViewById(R.id.content_display_mine);
                profile_picture = itemView.findViewById(R.id.profile_picture_mine);
                created = itemView.findViewById(R.id.posted_time_mine);
                countLikes = itemView.findViewById(R.id.count_likes_mine);
                likebutton = itemView.findViewById(R.id.likeButton_mine);
            }
        }
    }

    @Override
    public int getItemViewType(int pos) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getUid();
        if (getSnapshots().get(pos).getCreatedBy().getUserId().equals(currentUserId))
            return 0;
        else return 1;
    }

//    public class PostViewHolderMine extends RecyclerView.ViewHolder {
//        TextView name, content, countLikes, created;
//        ImageView profile_picture, likebutton;
//
//        public PostViewHolderMine(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.userName);
//            content = itemView.findViewById(R.id.content_display);
//            profile_picture = itemView.findViewById(R.id.profile_picture);
//            created = itemView.findViewById(R.id.posted_time);
//            countLikes = itemView.findViewById(R.id.count_likes);
//            likebutton = itemView.findViewById(R.id.likeButton);
//        }
//    }

    public interface CustomItemClickListener {
        public void onItemClick(View v, String a);
    }

}
