package com.example.kemo.socializer.View.MainActivity;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.kemo.socializer.Connections.ReceiveServerNotification;
import com.example.kemo.socializer.Connections.ServerNotFound;
import com.example.kemo.socializer.Connections.TransmissionFailureListener;
import com.example.kemo.socializer.Connections.UtilityConnection;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.AppUser;
import com.example.kemo.socializer.SocialAppGeneral.Command;
import com.example.kemo.socializer.SocialAppGeneral.LoggedUser;
import com.example.kemo.socializer.SocialAppGeneral.Notification;
import com.example.kemo.socializer.View.Adapters.FragmentAdapter;
import com.example.kemo.socializer.View.FragmentNavigator;
import com.example.kemo.socializer.View.IntentNavigator;
import com.example.kemo.socializer.View.MainActivity.Authentication.AuthenticationFragment;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentFragment extends Fragment implements SearchView.OnQueryTextListener,
        SearchView.OnSuggestionListener {
    private HomeFragment homeFragment;
    private FriendRequestFragment friendRequestFragment;
    private NotificationFragment notificationFragment;
    private SearchView searchView;
    public ContentFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        friendRequestFragment = new FriendRequestFragment();
        notificationFragment = new NotificationFragment();
        homeFragment.setFragTitle(getString(R.string.home_frag));
        friendRequestFragment.setFragTitle(getString(R.string.friendReq_frag));
        notificationFragment.setFragTitle(getString(R.string.notification_frag));
        startNotifications();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
       searchView = (SearchView) view.findViewById(R.id.search_bar_searchView);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.main_ViewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        fragmentAdapter.getFragments().add(homeFragment);
        fragmentAdapter.getFragments().add(friendRequestFragment);
        fragmentAdapter.getFragments().add(notificationFragment);
        viewPager.setAdapter(fragmentAdapter);
        searchView.setOnSuggestionListener(this);
        searchView.setOnQueryTextListener(this);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Choose Action");
        strings.add(getString(R.string.profile));
        strings.add(getString(R.string.logout));
        final ArrayAdapter<String> arrayAdapter = new  ArrayAdapter<>(getActivity(),
                R.layout.spinner_item,strings
               );
       Spinner spinner  = (Spinner) view.findViewById(R.id.management_spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    ((IntentNavigator) getActivity()).navigate(ClientLoggedUser.id, ProfileActivity.class);
                } else if (i == 2) {
                    ((FragmentNavigator) getActivity()).navigate(new AuthenticationFragment());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void startNotifications()
    {
        final int PORT_NO = 6100;
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        final ReceiveServerNotification receiveServerCommand = new ReceiveServerNotification(
                                new UtilityConnection(ClientLoggedUser.id, PORT_NO) {
                                    //TODO #Polymorphism #override
                                    @Override
                                    public void startConnection() {
                                        super.startConnection();
                                       //removed fetching notifications from here bec its seems to introduce bugs
                                    }
                                }
                                        .getConnectionSocket()) {
                            @Override
                            public void Analyze(Command command) {
                                if (command.getKeyWord().equals(Notification.NEW_NOTIFICATION))
                                {
                                    notificationFragment.addNotification(Notification.fromJsonString(command.getObjectStr()));
                                }
                                else if (command.getKeyWord().equals(LoggedUser.FRIEND_REQ))
                                {
                                    friendRequestFragment.addRequest(command.getObjectStr());
                                }
                            }
                        };
                        receiveServerCommand.setTransmissionFailureListener(new TransmissionFailureListener() {
                            @Override
                            public void onDisconnection() {
                                try {
                                    receiveServerCommand.getRemote().close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    receiveServerCommand.setRemote(new UtilityConnection(ClientLoggedUser.id, PORT_NO).getConnectionSocket());
                                } catch (ServerNotFound serverNotFound) {
                                    serverNotFound.printStackTrace();
                                }
                            }
                        });
                        receiveServerCommand.start();
                    } catch (ServerNotFound ignored) {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    //search area
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 1)
        new ClientLoggedUser.Search(s) {
            @Override
            public void onFinish(ArrayList<String> items) {
                final String[] colsNames = {"_id","suggestions"};
                //to avoid database i replaced cursor with matrixCursor
                final MatrixCursor matrixCursor = new MatrixCursor(colsNames);
                for (String item: items
                     ) {
                    AppUser appUser = AppUser.fromJsonString(item);
                    matrixCursor.addRow(new String[]{appUser.getID(),appUser.getUserInfo().getFullName()});
                }
                final int[] itemsLayouts = {R.id.search_item};
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity()
                                ,R.layout.search_items_suggestions,
                                matrixCursor,new String[]{colsNames[1]},itemsLayouts);
                        searchView.setSuggestionsAdapter(simpleCursorAdapter);
                    }
                });

            }
        };

        return true;
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int i) {
       MatrixCursor matrixCursor = (MatrixCursor) searchView.getSuggestionsAdapter().getItem(i);
        ((IntentNavigator)getActivity()).navigate(matrixCursor.getString(0), ProfileActivity.class);
        return true;
    }
}
