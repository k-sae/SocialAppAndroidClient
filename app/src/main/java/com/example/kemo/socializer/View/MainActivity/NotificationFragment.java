package com.example.kemo.socializer.View.MainActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Notification;
import com.example.kemo.socializer.View.Adapters.NotificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private NotificationAdapter notificationAdapter;
    public NotificationFragment() {
        // Required empty public constructor
        notificationAdapter = new NotificationAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        notificationAdapter.setContext(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.notification_listView);
        listView.setAdapter(notificationAdapter);
        return view;
    }
    void addNotification(Notification... notifications)
    {
        for (Notification notification: notifications
             ) {
            notificationAdapter.getNotifications().add(0,notification);
        }
        if (!(getActivity() == null))
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notificationAdapter.notifyDataSetChanged();
            }
        });

    }
}
