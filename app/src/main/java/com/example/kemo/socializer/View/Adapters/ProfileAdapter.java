package com.example.kemo.socializer.View.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.IntentNavigator;

import java.util.ArrayList;

/**
 * Created by kemo on 28/12/2016.
 */
public class ProfileAdapter extends BaseAdapter {
    private ArrayList<Post> posts;
    private Activity context;

    public ProfileAdapter(ArrayList<Post> posts, Activity context) {
        this.posts = posts;
        this.context = context;
    }

    public ProfileAdapter(Activity context) {
        this.context = context;
        posts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       view = inflateLayout(view, i , viewGroup);
        if (posts.get(i) != null)
        {
            ((TextView)view.findViewById(R.id.post_content)).setText(posts.get(i).getContent());
            final TextView textView = (TextView) view.findViewById(R.id.friend_view_textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((IntentNavigator)context).navigate(posts.get(i).getOwnerId() + "");
                }
            });
            new ClientLoggedUser.GetFriendInfo(posts.get(i).getOwnerId() + "") {
                @Override
                public void pick(final UserInfo userInfo) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(userInfo.getFullName());
                        }
                    });
                }
            };
        }
        return view;
    }
    private View inflateLayout(View view, int i, ViewGroup viewGroup)
    {
        if (view == null) {
            if (posts.get(i) == null) {
                view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup, false);
            }
            else
            {
                view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
            }
        }
        else if(view.findViewById(R.id.post_content) == null && posts.get(i) == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup,false);
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
        }
        return  view;
    }
}
