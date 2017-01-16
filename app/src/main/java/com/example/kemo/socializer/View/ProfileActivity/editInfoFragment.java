package com.example.kemo.socializer.View.ProfileActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.UserInfo;
import com.example.kemo.socializer.View.Packer.ImageViewPacker;

import java.io.IOException;
import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class editInfoFragment extends Fragment {
    private EditText editUserInfo;
    private InputStream profileImageStream;
    private ImageView imageView;
    private byte[] profileImageByteArray;
    String s;
    public editInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
        setLayout(view);
        return view;
    }
    private void setLayout(final View view)
    {
        Intent intent = getActivity().getIntent();
        String id   = intent.getStringExtra(Intent.EXTRA_TEXT);
        editUserInfo = (EditText) view.findViewById(R.id.edit_UserName);
        imageView = (ImageView) view.findViewById(R.id.profile_pic_edit);
        view.findViewById(R.id.change_profileImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
            }
        });
        new ClientLoggedUser.GetFriendInfo(id + "") {
            @Override
            public void pick(final UserInfo userInfo) {
                ImageViewPacker.from(getActivity())
                        .to(imageView).
                        withID(userInfo.getProfileImage())
                        .pack();
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        editUserInfo.setText(userInfo.getFullName());
                    }
                });
                view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //// TODO: 16/01/2017
                        if (profileImageStream != null) //this mean the image has been updated
                        {
                            new ClientLoggedUser.UploadImage(profileImageByteArray) {
                                @Override
                                public void onFinish(String id) {
                                    userInfo.setFullName(editUserInfo.getText().toString());
                                    userInfo.setProfileImage(id);
                                    new ClientLoggedUser.UpdateInfo(userInfo) {
                                        @Override
                                        public void onFinish(String s) {
                                            getActivity().finish();
                                        }
                                    };
                                }
                            };
                        }
                        else
                        {
                            userInfo.setFullName(editUserInfo.getText().toString());
                            new ClientLoggedUser.UpdateInfo(userInfo) {
                                @Override
                                public void onFinish(String s) {
                                    getActivity().finish();
                                }
                            };
                        }
                    }
                });
                view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().finish();
                    }
                });
            }
        };

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                profileImageStream = getActivity().getContentResolver().openInputStream(data.getData());
                profileImageByteArray = new byte[profileImageStream.available()];
                profileImageStream.read(profileImageByteArray);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(profileImageByteArray,0,profileImageByteArray.length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
