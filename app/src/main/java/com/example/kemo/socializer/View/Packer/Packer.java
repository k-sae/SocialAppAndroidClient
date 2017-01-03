package com.example.kemo.socializer.View.Packer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.IntentNavigator;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

/**
 * Created by kemo on 26/12/2016.
 */
public class Packer {

    private Context context;
    private Packer(Context context)
    {
        this.context = context;
    }
    public void packImageView(ImageView imageView, String id)
    {
        //TODO
        imageView.setImageResource(R.drawable.ic_friends);
    }
    public static Packer from(Context context)
    {
        return new Packer(context);

    }
    public  Packer packPostView(final View postViewer, final Post post)
    {
        ((TextView) postViewer.findViewById(R.id.post_content)).setText(post.getContent());
        final TextView textView = (TextView) postViewer.findViewById(R.id.friend_view_textView);
        postViewer.findViewById(R.id.comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IntentNavigator)context).navigate(post.getOwnerId() + "");
            }
        });
        new ClientLoggedUser.GetFriendInfo(post.getOwnerId() + "") {
            @Override
            public void pick(final UserInfo userInfo) {
               new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(userInfo.getFullName());
                        packImageView((ImageView) postViewer.findViewById(R.id.friend_view_imageView),userInfo.getProfileImage());
                    }
                });
            }
        };
        postViewer.findViewById(R.id.comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProfileActivity.class).putExtra(Intent.EXTRA_TEXT,
                        "1");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        return this;
    }
}
