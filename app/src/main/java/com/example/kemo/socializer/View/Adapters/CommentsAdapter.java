package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Comment;
import com.example.kemo.socializer.View.Packer.Packer;

import java.util.ArrayList;

/**
 * Created by kemo on 04/01/2017.
 */
public class CommentsAdapter extends BaseAdapter {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentsAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    public CommentsAdapter(Context context) {
        this.context = context;
        comments = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
       return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view =  LayoutInflater.from(context).inflate(R.layout.comment_view,viewGroup, false);
        }
        Packer.from(context).packCommentView(view, comments.get(i));
        return view;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}
