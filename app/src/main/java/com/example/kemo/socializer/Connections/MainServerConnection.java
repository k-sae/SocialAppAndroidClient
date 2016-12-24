package com.example.kemo.socializer.Connections;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kemo on 08/11/2016.
 */
public class MainServerConnection {
    public static Socket mainConnectionSocket;
    private static boolean isActiveConnection;
    public MainServerConnection() throws Exception {
        if (mainConnectionSocket !=null)
        {
            if (mainConnectionSocket.isClosed())
            {
                startConnection();
            }
        }
        else if (!isActiveConnection)
        {
            isActiveConnection = true;
            startConnection();
        }
    }

    //if no parameters passed set default connection
    //TODO #kareem
    //after creating users levels accept user of type registeredUser or login user
    private void startConnection() throws Exception {
        //here i will check for user info and choose whether to continue the connection or to end it
        new ServerConnection("192.168.0.195",6000) {
            @Override
            public void startConnection() {
                mainConnectionSocket = connectionSocket;
                isActiveConnection = false;
            }
        };
    }
    public static void endConnection()
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
}
