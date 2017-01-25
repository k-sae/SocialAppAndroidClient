package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Adapters.ProfileAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class PostsListViewFragment extends MainActivityFragment {
    protected ProfileAdapter profileAdapter;

    public PostsListViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileAdapter = new ProfileAdapter(getActivity());

        View view = inflater.inflate(R.layout.fragment_posts_listview, container, false);
        ListView listView = (ListView) view.findViewById(R.id.profile_ListView);
        listView.setAdapter(profileAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }
    protected abstract void fetchData();
}

