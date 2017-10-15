package com.taweewong.mylazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstagram.R;
import com.taweewong.mylazyinstagram.model.Post;

class Holder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView likeText;
    TextView commentText;

    Holder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.postImage);
        likeText = itemView.findViewById(R.id.likeText);
        commentText = itemView.findViewById(R.id.commentText);
    }
}

public class PostAdapter extends RecyclerView.Adapter<Holder>{
    private Context context;
    private Post[] posts;

    public PostAdapter(Context context, Post[] posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_item, null, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ImageView image = holder.image;
        TextView likeText = holder.likeText;
        TextView commentText = holder.commentText;

        Glide.with(context).load(posts[position].getUrl()).into(image);
        likeText.setText(String.valueOf(posts[position].getLike()));
        commentText.setText(String.valueOf(posts[position].getComment()));
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
