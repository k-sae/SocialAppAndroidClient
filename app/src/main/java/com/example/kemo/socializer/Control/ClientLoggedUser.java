package com.example.kemo.socializer.Control;

import com.example.kemo.socializer.Connections.MainServerConnection;
import com.example.kemo.socializer.SocialAppGeneral.Command;
import com.example.kemo.socializer.SocialAppGeneral.LoginInfo;

/**
 * Created by kemo on 24/12/2016.
 */
public class ClientLoggedUser {
    public static String id;
    public static abstract class Login
    {
        public Login(LoginInfo loginInfo)
        {
            Command command = new Command();
            command.setKeyWord(LoginInfo.NEW_LOGIN);
            command.setSharableObject(loginInfo.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    onFinish(cmd.getObjectStr());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(String id);
    }
}
