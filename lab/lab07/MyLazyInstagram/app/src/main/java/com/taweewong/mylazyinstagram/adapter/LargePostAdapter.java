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
import com.taweewong.mylazyinstagram.model.UserProfile;

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
    private String urlProfile;
    private String user;
    private Post[] posts;

    public LargePostAdapter(Context context, UserProfile userProfile) {
        this.context = context;
        this.posts = userProfile.getPosts();
        this.urlProfile = userProfile.getUrlProfile();
        this.user = userProfile.getUser();
    }

    @Override
    public LargePostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_large_item, parent, false);

        return new LargePostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LargePostViewHolder holder, int position) {
        ImageView userImageSmall = holder.userImageSmall;
        TextView usernameTextViewSmall = holder.usernameTextViewSmall;
        ImageView postImageLarge = holder.postImageLarge;
        TextView likeTextSmall = holder.likeTextSmall;
        TextView commentTextSmall = holder.commentTextSmall;

        Glide.with(context).load(urlProfile).into(userImageSmall);
        usernameTextViewSmall.setText(user);
        Glide.with(context).load(posts[position].getUrl()).into(postImageLarge);
        likeTextSmall.setText(context.getString(R.string.post_like, posts[position].getLike()));
        commentTextSmall.setText(context.getString(R.string.post_comment, posts[position].getComment()));
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
