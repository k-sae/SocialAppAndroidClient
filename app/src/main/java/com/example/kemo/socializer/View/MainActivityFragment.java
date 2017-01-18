package com.example.kemo.socializer.View;

import android.support.v4.app.Fragment;

/**
 * Created by kemo on 17/01/2017.
 */
public class MainActivityFragment extends Fragment {
    public String getFragTitle() {
        return fragTitle;
    }

    public void setFragTitle(String fragTitle) {
        this.fragTitle = fragTitle;
    }

    private String fragTitle;
    }
