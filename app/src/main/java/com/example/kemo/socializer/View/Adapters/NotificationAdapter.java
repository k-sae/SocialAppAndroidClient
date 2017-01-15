package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Notification;
import com.example.kemo.socializer.View.Packer.GeneralPacker;

import java.util.ArrayList;

/**
 * Created by kemo on 07/01/2017.
 */
public class NotificationAdapter extends BaseAdapter {
    private ArrayList<Notification> notifications;
    private Context context;

    public NotificationAdapter() {
        notifications = new ArrayList<>();
    }

    public NotificationAdapter(Context context) {
        this.context = context;
        notifications = new ArrayList<>();
    }

    public NotificationAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int i) {
        return notifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.notification_view, viewGroup, false);
        }
        GeneralPacker.from(context).packNotification(view, notifications.get(i));
        return view;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
