package com.example.kemo.socializer.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
                new ClientLoggedUser.Login(loginInfo) {
                    @Override
                    public void onFinish(String id) {
                        if (!id.equals( "-1"))
                        {
                            ClientLoggedUser.id = id;
                            ((CallBack)getActivity()).navigate(new ContentFragment());
                        }
                        else
                        { getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterFragment.this.getActivity(), "wrong mail or password", Toast.LENGTH_LONG).show();
                            }
                        });
                        }
                    }
                };
            }
        });
        return view;
    }
}
