package com.example.kemo.socializer.Control;

import com.example.kemo.socializer.SocialAppGeneral.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by kemo on 08/11/2016.
 */
public abstract class CommandRequest {
    private Socket serverConnection;
    private Command command;
    public CommandRequest(Socket serverConnection, Command   command)
    {
        this.serverConnection = serverConnection;
        this.command = command;
    }
    void run() {
        try {
            //send command to server
            DataOutputStream dataOutputStream = new DataOutputStream(serverConnection.getOutputStream());
            dataOutputStream.writeUTF(command.toString());
            //receive server command
            DataInputStream dataInputStream = new DataInputStream(serverConnection.getInputStream());
            //read string from server
            final String s = dataInputStream.readUTF();
            //start these function in another thread inorder to prevent time consuming
            Thread thread = new Thread()
            {
                @Override
                public void run() {
                    super.run();
                    analyze(Command.fromString(s));
                }
            };
          thread.start();

        } catch (SocketException e) {
            //TODO #kareem
            //notify for user dc
        }catch (SocketTimeoutException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public abstract void  analyze(Command cmd);
}
