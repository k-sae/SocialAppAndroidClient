package com.example.kemo.socializer.View.ProfileActivity;

import android.content.Intent;
import android.os.Handler;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.PostsListViewFragment;

import java.util.ArrayList;

/**
 * Created by kemo on 18/01/2017.
 */
public class ProfileFragment extends PostsListViewFragment {
    @Override
    protected void fetchData() {

        Intent intent = getActivity().getIntent();
         final String id = intent.getStringExtra(Intent.EXTRA_TEXT);
        profileAdapter.setUserId(id);
        new ClientLoggedUser.GetFriendInfo(id + "") {
            @Override
            public void pick(final UserInfo userInfo) {
                profileAdapter.getObjects().clear();
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        profileAdapter.getObjects().add(userInfo);
                        if (id.equals(ClientLoggedUser.id)) {
                            profileAdapter.getObjects().add(null);
                        }
                        profileAdapter.notifyDataSetChanged();
                    }
                });
                fetchPosts(id);
            }
        };

    }
        private void fetchPosts(String id)
    {
        //TODO
        new ClientLoggedUser.getProfilePosts(1, id) {
            @Override
            public void onFinish(final ArrayList<Post> posts) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profileAdapter.getObjects().addAll(posts);
                        profileAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }
}
