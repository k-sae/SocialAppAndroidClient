package com.example.kemo.socializer.View.MainActivity;

import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Separator;
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
                            getFriendsAdapter().getItems().clear();
                            getFriendsAdapter().getItems().add(new Separator(getString(R.string.requests)));
                            getFriendsAdapter().getItems().addAll(requests);
                            getFriendsAdapter().getItems().add(new Separator(getString(R.string.friends)));
                            getFriendsAdapter().notifyDataSetChanged();
                            getFriends();
                        }
                    });
                }
                else {
                    getFriendsAdapter().getItems().clear();
                    getFriendsAdapter().getItems().add(new Separator(getString(R.string.requests)));
                    getFriendsAdapter().getItems().addAll(requests);
                    getFriendsAdapter().getItems().add(new Separator(getString(R.string.friends)));
                }
            }
        };
    }
    protected void getFriends()
    {
        new ClientLoggedUser.GetFriends() {
            @Override
            public void onFinish(final ArrayList<String> ids) {
                if (getActivity()!= null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFriendsAdapter().getItems().addAll(ids);
                            getFriendsAdapter().notifyDataSetChanged();
                        }
                    });
                }
                else {
                    getFriendsAdapter().getItems().addAll(ids);
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
                    getFriendsAdapter().getItems().add(id);
                    getFriendsAdapter().notifyDataSetChanged();
                }
            });
        }
        else getFriendsAdapter().getItems().add(id);
    }
}
