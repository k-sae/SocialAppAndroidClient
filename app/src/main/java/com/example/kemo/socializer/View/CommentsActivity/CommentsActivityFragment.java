package com.example.kemo.socializer.View.CommentsActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kemo.socializer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommentsActivityFragment extends Fragment {

    public CommentsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }
}
