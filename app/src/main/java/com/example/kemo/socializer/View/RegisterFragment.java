package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.LoginInfo;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterFragment extends Fragment {

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText emailTextEdit = (EditText) view.findViewById(R.id.email_editText);
        final EditText passwordTextEdit = (EditText) view.findViewById(R.id.password_editText);
        final Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setEmail(emailTextEdit.getText().toString());
                loginInfo.setPassword(passwordTextEdit.getText().toString());
                new ClientLoggedUser().new Login(loginInfo) {
                    @Override
                    public void onFinish(String id) {
                        Log.w("result", id);
                    }
                };
            }
        });
        return view;
    }
}
