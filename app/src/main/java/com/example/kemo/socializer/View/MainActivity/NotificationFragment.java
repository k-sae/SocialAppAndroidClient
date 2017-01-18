package com.example.kemo.socializer.View.MainActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Notification;
import com.example.kemo.socializer.SocialAppGeneral.SocialArrayList;
import com.example.kemo.socializer.View.Adapters.NotificationAdapter;
import com.example.kemo.socializer.View.MainActivityFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends MainActivityFragment {
    private NotificationAdapter notificationAdapter;
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (notificationAdapter == null) {
            notificationAdapter = new NotificationAdapter();
            new ClientLoggedUser.LoadNotification() {
                @Override
                public void onFinish(SocialArrayList list) {
                    for (String o : list.getItems()
                            ) {
                        addNotification(Notification.fromJsonString(o));
                    }
                }
            };
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        onStart();
        notificationAdapter.setContext(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.notification_listView);
        listView.setAdapter(notificationAdapter);
        return view;
    }
    void addNotification(final Notification notification)
    {

        if (!(getActivity() == null)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notificationAdapter.getNotifications().add(0, notification);
                    notificationAdapter.notifyDataSetChanged();
                }
            });
        }
        else
        {
            notificationAdapter.getNotifications().add(0, notification);
        }
    }
}
