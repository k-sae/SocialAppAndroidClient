package com.example.kemo.socializer.View.MainActivity.RegisterContent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kemo.socializer.Control.ClientLoggedUser;
import com.example.kemo.socializer.Control.CredentialsUtl;
import com.example.kemo.socializer.Control.LoginCredentials;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.LoginInfo;
import com.example.kemo.socializer.View.FragmentNavigator;
import com.example.kemo.socializer.View.MainActivity.ContentFragment;
import com.example.kemo.socializer.View.MainActivityFragment;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends MainActivityFragment {

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText emailTextEdit = (EditText) view.findViewById(R.id.email_editText);
        final EditText passwordTextEdit = (EditText) view.findViewById(R.id.password_editText);
        final Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LoginInfo loginInfo = new LoginInfo();
                loginInfo.setEmail(emailTextEdit.getText().toString());
                loginInfo.setPassword(passwordTextEdit.getText().toString());
                new ClientLoggedUser.Login(loginInfo) {
                    @Override
                    public void onFinish(String id) {
                        if (!id.equals( "-1"))
                        {
                            ClientLoggedUser.id = id;
                            Realm realm = CredentialsUtl.getRealmInstance(getActivity());
                            clearRealmItems(realm);
                            realm.beginTransaction();
                            realm.copyToRealm( CredentialsUtl.fromLoginInfo(loginInfo));
                            realm.commitTransaction();
                            realm.close();
                            ((FragmentNavigator)getActivity()).navigate(new ContentFragment());
                        }
                        else
                        { getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginFragment.this.getActivity(), "wrong mail or password", Toast.LENGTH_LONG).show();
                            }
                        });
                        }
                    }
                };
            }
        });
        return view;
    }
    private void clearRealmItems(Realm realm)
    {
        RealmResults<LoginCredentials> realmResults = realm.where(LoginCredentials.class).findAll();
        realm.beginTransaction();
        realmResults.clear();
        realm.commitTransaction();
    }
}
