package com.example.kemo.socializer.View.MainActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.View.PostsListViewFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends PostsListViewFragment {
    @Override//empty method
    // bec i don't need the original method
    protected void fetchData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        fetchPosts();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    private void fetchPosts()
    {
        new ClientLoggedUser.GetHomePosts(1) {
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
