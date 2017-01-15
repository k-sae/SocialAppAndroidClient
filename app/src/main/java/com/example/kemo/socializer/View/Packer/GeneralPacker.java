package com.example.kemo.socializer.View.Packer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.*;
import com.example.kemo.socializer.View.Adapters.StackAdapter;
import com.example.kemo.socializer.View.CommentsActivity.CommentsActivity;
import com.example.kemo.socializer.View.IntentNavigator;

import java.util.ArrayList;

/**
 * Created by kemo on 26/12/2016.
 */
public class GeneralPacker {
    final String ADD_FRIEND = "Add friend";
    final String ACCEPT = "Accept";
    final String DECLINE = "Decline";
    final String REMOVE_FRIEND = "Remove friend";
    final String CANCEL_REQUEST = "Cancel request";
    private Context context;

    public GeneralPacker showProfileImage(boolean showProfileImage) {
        this.showProfileImage = showProfileImage;
        return this;
    }

    private boolean showProfileImage;
    private GeneralPacker(Context context)
    {
        showProfileImage = true;
        this.context = context;
    }
    public static GeneralPacker from(Context context)
    {
        return new GeneralPacker(context);
    }
    public GeneralPacker packPostView(final View postViewer, final Post post)
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
    public GeneralPacker packCommentView(View commentView, Comment comment)
    {
        ((TextView) commentView.findViewById(R.id.comment_content)).setText(comment.getCommentcontent());
        packFriendView(commentView, comment.getOwnerID() + "");
        return this;
    }
    public GeneralPacker packFriendView(final View friendView, final String id)
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
    public GeneralPacker packUserInfo(View infoView, UserInfo userInfo, String id)
    {
        ((TextView)infoView.findViewById(R.id.user_name)).setText(userInfo.getFullName());
        ((TextView)infoView.findViewById(R.id.user_gender)).setText(userInfo.getGender());
        ((TextView)infoView.findViewById(R.id.user_birthDate)).setText(userInfo.getBirthDate());
        setProfileImage(((ImageView)infoView.findViewById(R.id.profile_pic)), userInfo.getProfileImage());
        setRelationSpinner(infoView, id);
        return this;
    }
    private void setRelationSpinner(View infoView, final String id)
    {
        //TODO
        //load it from strings.xml later
        Spinner spinner = (Spinner) infoView.findViewById(R.id.relation_spinner);
        final ArrayAdapter<String> arrayAdapter = new  ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item,
                new ArrayList<String>());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                switch (s) {
                    case ADD_FRIEND:
                       new ClientLoggedUser.addFriend(id) {
                            @Override
                            public void onFinish(Command cmd) {
                                setAdapterData(arrayAdapter,id);
                            }
                        };
                        break;
                    case ACCEPT:
                        new ClientLoggedUser.AcceptFriendReq(id) {
                            @Override
                            public void onFinish(Command cmd) {
                                setAdapterData(arrayAdapter,id);
                            }
                        };
                        break;
                    case REMOVE_FRIEND:
                        new ClientLoggedUser.RemoveFriend(id) {
                            @Override
                            public void onFinish(Command cmd) {
                                setAdapterData(arrayAdapter,id);
                            }
                        };
                        break;
                    case CANCEL_REQUEST:
                        new ClientLoggedUser.CancelFriendReq(id) {
                            @Override
                            public void onFinish(Command cmd) {
                                setAdapterData(arrayAdapter,id);
                            }
                        };
                        break;
                    case DECLINE:
                        new ClientLoggedUser.DeclineFriendReq(id) {
                            @Override
                            public void onFinish(Command cmd) {
                                setAdapterData(arrayAdapter,id);
                            }
                        };
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       setAdapterData(arrayAdapter,id);
        spinner.setAdapter(arrayAdapter);
    }
    private void setAdapterData(final ArrayAdapter<String> arrayAdapter, String id)
    {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.clear();
                arrayAdapter.add("Choose Action");
            }});
        new ClientLoggedUser.GetRelation(id) {
            @Override
            public void onFinish(final String s) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (s.equals(Relations.NOT_FRIEND.toString())) {
                            arrayAdapter.add(ADD_FRIEND);
                        } else if (s.equals(Relations.FRIEND_REQ.toString())) {
                            arrayAdapter.add(ACCEPT);
                            arrayAdapter.add(DECLINE);
                        } else if (s.equals(Relations.FRIEND.toString())) {
                            arrayAdapter.add(REMOVE_FRIEND);
                        } else if (s.equals(Relations.PENDING.toString())) {
                            arrayAdapter.add(CANCEL_REQUEST);
                        }
                    }
                });
            }
        };
    }
    private void setProfileImage(ImageView imageView, String id)
    {
        if (showProfileImage)
           ImageViewPacker.from(context).withID(id).to(imageView).pack();
        else {
            imageView.getLayoutParams().width = 0;
        }

    }
    public GeneralPacker packPostWriter(final View postWriter, final StackAdapter stackAdapter)
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
    public GeneralPacker packNotification(View view, Notification notification)
    {
        ((TextView)view.findViewById(R.id.notification_details)).setText(String.format("%s%s",
                notification.getKeyword().toString().toLowerCase(),
                context.getString(R.string.notification_constant_message)));
        packFriendView(view, notification.getIdSender());
        return this;
    }
}
