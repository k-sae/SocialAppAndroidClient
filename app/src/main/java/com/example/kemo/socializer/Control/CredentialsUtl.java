package com.example.kemo.socializer.Control;

import android.content.Context;
import com.example.kemo.socializer.SocialAppGeneral.LoginInfo;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by kemo on 13/01/2017.
 */
public class CredentialsUtl
{
    public static LoginInfo toLoginInfo(LoginCredentials loginCredentials)
    {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail(loginCredentials.getEmail());
        loginInfo.setPassword(loginCredentials.getPassword());
        return loginInfo;
    }
    public static Realm getRealmInstance(Context context)
    {
       return Realm.getInstance(new RealmConfiguration.Builder(context).name("Login_Credentials").build());
    }
    public static LoginCredentials fromLoginInfo(LoginInfo loginInfo)
    {
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setEmail(loginInfo.getEMAIL());
        loginCredentials.setPassword(loginInfo.getPassword());
        return loginCredentials;
    }
}
