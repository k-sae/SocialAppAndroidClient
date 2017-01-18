package com.example.kemo.socializer.View.MainActivity.RegisterContent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.Adapters.FragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationFragment extends Fragment {


    public AuthenticationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.authentication_ViewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setFragTitle(getString( R.string.login));
        fragmentAdapter.getFragments().add(loginFragment);
        viewPager.setAdapter(fragmentAdapter);
        return view;
    }

}
