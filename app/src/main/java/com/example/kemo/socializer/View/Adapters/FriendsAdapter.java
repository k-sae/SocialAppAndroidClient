package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.AppUser;
import com.example.kemo.socializer.View.ImageViewer.Packer;

import java.util.ArrayList;

/**
 * Created by kemo on 26/12/2016.
 */
public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private volatile ArrayList<AppUser> appUsers;

    public FriendsAdapter(Context context, ArrayList<AppUser> appUsers) {
        this.context = context;
        this.appUsers = appUsers;
    }

    public FriendsAdapter(Context context) {
        this.context = context;
        appUsers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return appUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return appUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.friend_view,viewGroup, false);
        }
        ((TextView)view.findViewById(R.id.friend_view_textView)).setText(appUsers.get(i).getUserInfo().getFullName());
        Packer.setImageFromServer((ImageView) view.findViewById(R.id.friend_view_imageView),appUsers.get(i).getUserInfo().getProfileImage());
        return view;
    }

    public ArrayList<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(ArrayList<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
