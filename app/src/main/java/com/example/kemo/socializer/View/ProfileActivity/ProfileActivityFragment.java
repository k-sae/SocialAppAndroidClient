package com.example.kemo.socializer.View.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
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
        //TODO

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }
    private void fetchData()
    {
        //TODO
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (id.equals(ClientLoggedUser.id)) {
            profileAdapter.getPosts().add(null);
        }
        new ClientLoggedUser.getPosts(1, id) {
            @Override
            public void onFinish(ArrayList<Post> posts) {
                if (profileAdapter.getPosts().size() > 1) {
                    if (profileAdapter.getPosts().get(0) == null) {
                        profileAdapter.getPosts().clear();
                        profileAdapter.getPosts().add(null);
                    } else {
                        profileAdapter.getPosts().clear();
                    }
                }
                profileAdapter.getPosts().addAll(posts);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profileAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }
}

