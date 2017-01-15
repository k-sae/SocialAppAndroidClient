package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Packer.GeneralPacker;

import java.util.ArrayList;

/**
 * Created by kemo on 26/12/2016.
 */
public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private volatile ArrayList<String> ids;

    public FriendsAdapter(Context context, ArrayList<String> ids) {
        this.context = context;
        this.ids = ids;
    }

    public FriendsAdapter(Context context) {
        this.context = context;
        ids = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Object getItem(int i) {
        return ids.get(i);
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
        GeneralPacker.from(context).packFriendView(view, ids.get(i));
        return view;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }
}
