package com.example.kemo.socializer.View.Adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Post;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.Packer.GeneralPacker;

import java.util.ArrayList;

/**
 * Created by kemo on 28/12/2016.
 */
public class ProfileAdapter extends BaseAdapter implements StackAdapter {
    private ArrayList<Object> objects;
    private Context context;
    private String userId;
    public ProfileAdapter(ArrayList<Object> posts, Context context) {
        this.objects = posts;
        this.context = context;
    }

    public ProfileAdapter(Context context) {
        this.context = context;
        objects = new ArrayList<>();
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       view = inflateLayout(view, objects.get(i) , viewGroup);
        return view;
    }
    private View inflateLayout(View view, Object o, ViewGroup viewGroup)
    {

        if (view == null)
        {
            //3 cases
            if (o == null)
            {
                //from writer
                view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup, false);

            }
            else if(o instanceof Post)
            {
                //from post viewer
                view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
            }
            else if(o instanceof UserInfo)
            {
                //from info viewer
                view = LayoutInflater.from(context).inflate(R.layout.info_view, viewGroup,false);
            }
        }
        //3 cases
        if (o == null)
        {
            //from writer
            if (view.findViewById(R.id.post_button) == null)
            view = LayoutInflater.from(context).inflate(R.layout.post_writer, viewGroup, false);
            GeneralPacker.from(context).packPostWriter(view,this);
        }
        else if(o instanceof Post)
        {
            //from post viewer
            if (view.findViewById(R.id.post_content) == null)
            view = LayoutInflater.from(context).inflate(R.layout.postview, viewGroup,false);
            GeneralPacker.from(context).packPostView(view, (Post) o);
        }
        else if(o instanceof UserInfo)
        {
            //from info viewer
            if (view.findViewById(R.id.profile_pic) == null)
            view = LayoutInflater.from(context).inflate(R.layout.info_view, viewGroup,false);
            GeneralPacker.from(context).packUserInfo(view, (UserInfo)o, userId);
        }
        return  view;
    }

    @Override
    public void insertTop(Object o) {
        if (objects.size() > 2)
        {
            if(objects.get(1) == null)
            {
                objects.add(2,o);
            }
            else objects.add(1,0);
        }
        else  objects.add(o);
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
             notifyDataSetChanged();
            }
        });

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
