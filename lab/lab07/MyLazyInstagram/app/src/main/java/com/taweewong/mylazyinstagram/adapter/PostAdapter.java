package com.taweewong.mylazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstagram.R;
import com.taweewong.mylazyinstagram.model.Post;

class Holder extends RecyclerView.ViewHolder {

    public ImageView image;

    public Holder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.postImage);
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
        Holder holder = new Holder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ImageView image = holder.image;
        Glide.with(context).load(posts[position].getUrl()).into(image);
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
