package com.example.kemo.socializer.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.AppUser;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.Adapters.FriendsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private FriendsAdapter friendsAdapter;
    public FriendsFragment() {
        // Required empty public constructor
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
        fetchData();
        return view;
    }
    private void fetchData()
    {
        new ClientLoggedUser.GetFriends() {
            @Override
            public void onFinish(ArrayList<String> ids) {
                for (final String id: ids
                        ) {
                    new ClientLoggedUser.GetFriendInfo(id) {
                        @Override
                        public void pick(UserInfo userInfo) {
                            AppUser appUser = new AppUser();
                            appUser.setUserInfo(userInfo);
                            appUser.setID(id);
                            friendsAdapter.getAppUsers().add(appUser);
                            FriendsFragment.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    friendsAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    };
                }

            }
        };
    }
}
