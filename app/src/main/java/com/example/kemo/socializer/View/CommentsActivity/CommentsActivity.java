package com.example.kemo.socializer.View.CommentsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.IntentNavigator;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

public class CommentsActivity extends AppCompatActivity implements IntentNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void navigate(String extra) {
        Intent intent = new Intent(this, ProfileActivity.class).putExtra(Intent.EXTRA_TEXT,
                extra);
        startActivity(intent);
    }
}
