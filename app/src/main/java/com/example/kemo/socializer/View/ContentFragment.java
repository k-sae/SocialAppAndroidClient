package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kemo.socializer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentFragment extends Fragment {

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ImageView imageView = (ImageView)  view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ouch!!", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
