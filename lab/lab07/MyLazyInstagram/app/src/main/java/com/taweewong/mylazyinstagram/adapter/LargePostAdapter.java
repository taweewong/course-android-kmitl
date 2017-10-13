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

class LargePostViewHolder extends RecyclerView.ViewHolder {
    ImageView userImageSmall;
    TextView usernameTextViewSmall;
    ImageView postImageLarge;
    TextView likeTextSmall;
    TextView commentTextSmall;

    public LargePostViewHolder(View itemView) {
        super(itemView);
        userImageSmall = itemView.findViewById(R.id.userImageSmall);
        usernameTextViewSmall = itemView.findViewById(R.id.usernameTextViewSmall);
        postImageLarge = itemView.findViewById(R.id.postImageLarge);
        likeTextSmall = itemView.findViewById(R.id.likeTextSmall);
        commentTextSmall = itemView.findViewById(R.id.commentTextSmall);
    }
}

public class LargePostAdapter extends RecyclerView.Adapter<LargePostViewHolder>{
    private Context context;
    private Post[] posts;

    public LargePostAdapter(Context context, Post[] posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public LargePostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_large_item, parent, false);
        LargePostViewHolder holder = new LargePostViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(LargePostViewHolder holder, int position) {
        ImageView userImageSmall = holder.userImageSmall;
        TextView usernameTextViewSmall = holder.usernameTextViewSmall;
        ImageView postImageLarge = holder.postImageLarge;
        TextView likeTextSmall = holder.likeTextSmall;
        TextView commentTextSmall = holder.commentTextSmall;

        Glide.with(context).load("http://cf.c.ooyala.com/c0MDgyYjE6pFQaWHF7dbJzqtDYo4p17q/promo318116930").into(userImageSmall);

        Glide.with(context).load(posts[position].getUrl()).into(postImageLarge);
        likeTextSmall.setText(posts[position].getLike() + " likes");
        commentTextSmall.setText(posts[position].getComment() + " comments");
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
