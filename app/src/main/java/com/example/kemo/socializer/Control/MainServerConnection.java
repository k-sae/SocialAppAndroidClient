package com.example.kemo.socializer.Control;

import com.example.kemo.socializer.Connections.ServerConnection;
import com.example.kemo.socializer.Connections.ServerNotFound;
import com.example.kemo.socializer.NetworkConfiguration;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kemo on 08/11/2016.
 */
public class MainServerConnection extends ServerConnection {
    public static Socket mainConnectionSocket;
    private static boolean isActiveConnection;
    public MainServerConnection() throws Exception {
    }
    public void reconnect()
    {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void connect() throws Exception
    {
        if (mainConnectionSocket !=null)
        {
            if (mainConnectionSocket.isClosed())
            {
                start();
            }
        }
        else if (!isActiveConnection)
        {
            isActiveConnection = true;
            start();
        }
    }
    private void start() throws ServerNotFound {
        //NOTE: u should enter a valid server ip address
        // servers are found at: https://drive.google.com/file/d/0B30apTkO0d5qN0o0YXFseFIyZ1k/view
        //with source code at:
        //                      https://github.com/kareem2048/SocialAppImagesServer
        //                      https://github.com/kareem2048/SocialAppServer
        //enter the ip address of the remote sever
        super.connect(NetworkConfiguration.ipAddress,6000);
    }
    public void endConnection()
    {
        if (mainConnectionSocket != null)
        {
            try {
                mainConnectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startConnection() {
        mainConnectionSocket = connectionSocket;
        isActiveConnection = false;
    }
}
