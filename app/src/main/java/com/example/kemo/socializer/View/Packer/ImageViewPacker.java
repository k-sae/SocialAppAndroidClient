package com.example.kemo.socializer.View.Packer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import com.example.kemo.socializer.Connections.ServerConnection;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.SocialAppGeneral.Command;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by kemo on 15/01/2017.
 */
public class ImageViewPacker  {
    private Context context;
    private String id;
    private ImageView imageView;
    private Bitmap resultedImage;
    private ImageViewPacker(Context context) {
        this.context = context;
    }

    public static ImageViewPacker from(Context context)
    {
        return new ImageViewPacker(context);
    }
    public ImageViewPacker to(ImageView imageView)
    {
        this.imageView = imageView;
        return this;
    }
    public ImageViewPacker withID(String id)
    {
        this.id = id;
        return this;
    }
    public void pack()
    {
        //TODO
//        imageView.setImageResource(R.drawable.ic_friends);
        Thread thread  = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    //TODO: #Config
                    new ServerConnection("192.168.43.195", 6010) {
                        @Override
                        public void startConnection() {
                            try {
                                sendCommand(connectionSocket);
                                connectionSocket.setSoTimeout(100000);
//                                final ObjectInputStream objectOutputStream = new ObjectInputStream(connectionSocket.getInputStream());
                                byte[] bytes = (byte[]) new ObjectInputStream(connectionSocket.getInputStream()).readObject();
                                resultedImage = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(resultedImage);
                                    }
                                });

                            } catch (IOException ignored) { //ignored for now TODO
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageResource(R.drawable.ic_friends);
                                    }
                                });
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                } catch (Exception e) {
                    //ignore for now
                }
            }
        };
        thread.start();


    }
    private void sendCommand(Socket socket)
    {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Command command = new Command();
            String DOWNLOADIMAGE = "download_image";
            command.setKeyWord(DOWNLOADIMAGE);
            command.setSharableObject(id);
            dataOutputStream.writeUTF(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
