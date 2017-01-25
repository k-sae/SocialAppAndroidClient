package com.example.kemo.socializer.View.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.FragmentNavigator;
import com.example.kemo.socializer.View.IntentNavigator;

public class ProfileActivity extends AppCompatActivity implements IntentNavigator, FragmentNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new ClientLoggedUser.GetFriendInfo(getIntent().getStringExtra(Intent.EXTRA_TEXT)) {
            @Override
            public void pick(final UserInfo userInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        assert toolbar != null;
                        toolbar.setTitle(userInfo.getFullName());
                    }
                });

            }
        };

        navigate(new ProfileFragment());
    }
    @Override
    public void navigate(String extra, Class<?> cls) {
        Intent intent = new Intent(this, cls).putExtra(Intent.EXTRA_TEXT,
                extra);
        startActivity(intent);
        if (cls == ProfileActivity.class)
        finish();
    }
    @Override
    public void navigate(final Fragment fragment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.postsListViewFragment_container,
                                fragment)
                        .commit();
            }
        });

    }
}

