package com.example.kemo.socializer.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Adapters.FriendsAdapter;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class UsersListViewFragment extends MainActivityFragment {

    public FriendsAdapter getFriendsAdapter() {
        return friendsAdapter;
    }

    private FriendsAdapter friendsAdapter;
    public UsersListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        friendsAdapter = new FriendsAdapter(getActivity());
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ListView listView = (ListView) view.findViewById(R.id.friends_listView);
        //bind the list to the adapter
        listView.setAdapter(friendsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class).putExtra(Intent.EXTRA_TEXT,
                        friendsAdapter.getIds().get(i));
                startActivity(intent);
            }
        });
        return view;
    }
    protected abstract void fetchData();
}
