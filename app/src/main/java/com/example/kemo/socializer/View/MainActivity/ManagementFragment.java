package com.example.kemo.socializer.View.MainActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.FragmentNavigator;
import com.example.kemo.socializer.View.IntentNavigator;
import com.example.kemo.socializer.View.MainActivity.Authentication.AuthenticationFragment;
import com.example.kemo.socializer.View.MainActivityFragment;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagementFragment extends MainActivityFragment {


    public ManagementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_managment, container, false);
        view.findViewById(R.id.management_Profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IntentNavigator)getActivity()).navigate(ClientLoggedUser.id, ProfileActivity.class);
            }
        });
        view.findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentNavigator)getActivity()).navigate(new AuthenticationFragment());
            }
        });
        return view;
    }

}
