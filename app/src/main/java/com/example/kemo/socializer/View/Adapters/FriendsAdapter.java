package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Packer.GeneralPacker;
import com.example.kemo.socializer.View.Separator;

import java.util.ArrayList;

/**
 * Created by kemo on 26/12/2016.
 */
public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private volatile ArrayList<Object> items;

    public FriendsAdapter(Context context, ArrayList<Object> items) {
        this.context = context;
        this.items = items;
    }

    public FriendsAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            if (items.get(i) instanceof String)
            view = LayoutInflater.from(context).inflate(R.layout.friend_view,viewGroup, false);
            else
                view = LayoutInflater.from(context).inflate(R.layout.separator_layout,viewGroup, false);
        }
        if (items.get(i) instanceof String) {
            if (view.findViewById(R.id.friend_view_textView) == null) {
               view = LayoutInflater.from(context).inflate(R.layout.friend_view, viewGroup, false);
            }
            GeneralPacker.from(context).packFriendView(view, (String) items.get(i));
        }
        else if (items.get(i) instanceof Separator)
        {
            if (view.findViewById(R.id.separator_content) == null)
               view = LayoutInflater.from(context).inflate(R.layout.separator_layout,viewGroup, false);
            ((TextView) view.findViewById(R.id.separator_content)).setText(((Separator) items.get(i)).getContent());
        }
        return view;
    }

    public ArrayList<Object> getItems() {
        return items;
    }

    public void setItems(ArrayList<Object> items) {
        this.items = items;
    }
}
