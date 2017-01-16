package com.example.kemo.socializer.View.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.Adapters.ProfileAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {
    private ProfileAdapter profileAdapter;

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileAdapter = new ProfileAdapter(getActivity());

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = (ListView) view.findViewById(R.id.profile_ListView);
        listView.setAdapter(profileAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getProfileInfo();
    }
    private void fetchPosts(String id)
    {
        //TODO
        new ClientLoggedUser.getPosts(1, id) {
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
    private void getProfileInfo()
    {
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
}

