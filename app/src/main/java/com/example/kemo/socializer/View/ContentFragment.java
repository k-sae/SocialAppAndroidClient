package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kemo.socializer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContentFragment extends Fragment implements FragmentNavigator {
    private HomeFragment homeFragment;
    private FriendsFragment friendsFragment;
    public ContentFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        friendsFragment = new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        view.findViewById(R.id.home_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(homeFragment);
            }
        });
        view.findViewById(R.id.friends_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(friendsFragment);
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
