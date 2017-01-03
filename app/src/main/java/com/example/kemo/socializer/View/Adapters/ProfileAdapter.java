package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.View.Packer.Packer;

import java.util.ArrayList;

/**
 * Created by kemo on 28/12/2016.
 */
public class ProfileAdapter extends BaseAdapter {
    private ArrayList<Post> posts;
    private Context context;

    public ProfileAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public ProfileAdapter(Context context) {
        this.context = context;
        posts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       view = inflateLayout(view, i , viewGroup);
        if (posts.get(i) != null)
        {
            Packer.from(context).packPostView(view, posts.get(i));
        }
        return view;
    }
    private View inflateLayout(View view, int i, ViewGroup viewGroup)
    {
        if (view == null) {
            if (posts.get(i) == null) {
                view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup, false);
            }
            else
            {
                view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
            }
        }
        else if(view.findViewById(R.id.post_content) == null && posts.get(i) == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup,false);
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
        }
        return  view;
    }
}
