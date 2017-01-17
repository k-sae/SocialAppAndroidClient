package com.example.kemo.socializer.View.MainActivity;

import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.View.UsersListViewFragment;

import java.util.ArrayList;

/**
 * Created by kemo on 17/01/2017.
 */
public class FriendRequestFragment extends UsersListViewFragment {
    @Override
    protected void fetchData() {
       new ClientLoggedUser.GetFriendReq() {
            @Override
            public void onFinish(final ArrayList<String> requests) {
                if (getActivity()!= null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFriendsAdapter().getIds().clear();
                            getFriendsAdapter().getIds().addAll(requests);
                            getFriendsAdapter().notifyDataSetChanged();
                        }
                    });
                }
                else {
                    getFriendsAdapter().getIds().clear();
                    getFriendsAdapter().getIds().addAll(requests);
                }
            }
        };
    }
    public void addRequest(final String id)
    {
        if (getActivity()!= null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getFriendsAdapter().getIds().add(id);
                    getFriendsAdapter().notifyDataSetChanged();
                }
            });
        }
        else getFriendsAdapter().getIds().add(id);
    }
}
