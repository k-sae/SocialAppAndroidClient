package com.example.kemo.socializer.View.Packer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Comment;
import com.example.kemo.socializer.SocialAppGeneral.Notification;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.Adapters.StackAdapter;
import com.example.kemo.socializer.View.CommentsActivity.CommentsActivity;
import com.example.kemo.socializer.View.IntentNavigator;

/**
 * Created by kemo on 26/12/2016.
 */
public class Packer {

    private Context context;

    public Packer showProfileImage(boolean showProfileImage) {
        this.showProfileImage = showProfileImage;
        return this;
    }

    private boolean showProfileImage;
    private Packer(Context context)
    {
        showProfileImage = true;
        this.context = context;
    }
    public Packer packImageView(ImageView imageView, String id)
    {
        //TODO
        imageView.setImageResource(R.drawable.ic_friends);
        return this;
    }
    public static Packer from(Context context)
    {
        return new Packer(context);
    }
    public  Packer packPostView(final View postViewer, final Post post)
    {
        ((TextView) postViewer.findViewById(R.id.post_content)).setText(post.getContent());
        packFriendView(postViewer, post.getOwnerId() + "");
        postViewer.findViewById(R.id.comment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CommentsActivity.class).putExtra(Intent.EXTRA_TEXT,
                        post.convertToJsonString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return this;
    }
    public Packer packCommentView(View commentView, Comment comment)
    {
        ((TextView) commentView.findViewById(R.id.comment_content)).setText(comment.getCommentcontent());
        packFriendView(commentView, comment.getOwnerID() + "");
        return this;
    }
    public Packer packFriendView(final View friendView, final String id)
    {
        final TextView textView = (TextView) friendView.findViewById(R.id.friend_view_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IntentNavigator)context).navigate(id + "");
            }
        });
        new ClientLoggedUser.GetFriendInfo(id + "") {
            @Override
            public void pick(final UserInfo userInfo) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(userInfo.getFullName());
                        setProfileImage((ImageView) friendView.findViewById(R.id.friend_view_imageView),
                                userInfo.getProfileImage());
                    }
                });
            }
        };
        return this;
    }
    public Packer packUserInfo(View infoView, UserInfo userInfo)
    {
        ((TextView)infoView.findViewById(R.id.user_name)).setText(userInfo.getFullName());
        ((TextView)infoView.findViewById(R.id.user_gender)).setText(userInfo.getGender());
        ((TextView)infoView.findViewById(R.id.user_birthDate)).setText(userInfo.getBirthDate());
        setProfileImage(((ImageView)infoView.findViewById(R.id.profile_pic)), userInfo.getProfileImage());
        return this;
    }
    private void setProfileImage(ImageView imageView, String id)
    {
        if (showProfileImage)
            packImageView(imageView,id);
        else {
            imageView.getLayoutParams().width = 0;
        }

    }
    public Packer packPostWriter(final View postWriter, final StackAdapter stackAdapter)
    {
        postWriter.findViewById(R.id.post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Post post=new Post();
                post.setOwnerId(Long.parseLong(ClientLoggedUser.id));
                post.setContent(((EditText)postWriter.findViewById(R.id.editText)).getText().toString());
                post.setPostPos(Long.parseLong(ClientLoggedUser.id));
                new ClientLoggedUser.addUserPost(post) {
                    @Override
                    public void onFinish(String result) {
                        stackAdapter.insertTop(post);
                    }
                };
            }
        });

        return this;
    }
    public Packer packNotification(View view, Notification notification)
    {
        ((TextView)view.findViewById(R.id.notification_details)).setText(String.format("%s%s",
                notification.getKeyword().toString().toLowerCase(),
                context.getString(R.string.notification_constant_message)));
        packFriendView(view, notification.getIdSender());
        return this;
    }
}
