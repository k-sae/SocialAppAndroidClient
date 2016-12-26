package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kemo.socializer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentFragment extends Fragment implements CallBack {
    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        view.findViewById(R.id.home_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(new HomeFragment());
            }
        });
        view.findViewById(R.id.friends_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(new FriendsFragment());
            }
        });
        return view;
    }
//     <T> T myfun(Class<T> classOfT) //important
//    {
//        Object o;
//       return Primitives.wrap(classOfT).cast(o);
//    }
    @Override
    public void navigate(final Fragment fragment) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.sub_container,
                                fragment)
                        .commit();
            }
        });

    }
}
