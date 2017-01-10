package com.example.kemo.socializer.Control;

import com.example.kemo.socializer.Connections.CommandRequest;
import com.example.kemo.socializer.Connections.CommandsExecutor;
import com.example.kemo.socializer.SocialAppGeneral.*;

import java.util.ArrayList;

/**
 * Created by kemo on 24/12/2016.
 */
public class ClientLoggedUser {
    public static final String ADD_FRIEND="add_Friend";
    private static final String GET_FRIENDS="get_Friends";
    public static final String FRIEND_REQ = "friend_req";
    public static final String FETCH_REQS = "fetch_reqs";
    public static final String GET_RELATION_STATUS = "get_relation_status";
    public static final String ACCEPT_FRIEND = "accept_friend_req";
    public static final String DECLINE_FRIEND = "decline_friend_req";
    public static final String REMOVE_FRIEND = "remove_friend";
    public static final String CANCEL_FRIEND_REQ = "cancel_friend_req";
    public static final String DEACTIVATE = "deactivate";
    public static final String REACTIVATE = "reactivate";
    public static String id;
    public static abstract class Login
    {
        protected Login(LoginInfo loginInfo)
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
    public static abstract class GetFriends
    {
        public GetFriends()
        {
            Command command = new Command();
            command.setKeyWord(GET_FRIENDS);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {

                    onFinish((ArrayList<String>) SocialArrayList.convertFromJsonString(cmd.getObjectStr()).getItems());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(ArrayList<String> ids);
    }
    public static abstract class GetFriendInfo
    {
        protected GetFriendInfo(String id)
        {
            Command command = new Command();
            command.setKeyWord(UserInfo.PICK_INFO);
            command.setSharableObject(id);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket,command) {
                @Override
                public void analyze(Command cmd) {
                    UserInfo userInfo = UserInfo.fromJsonString(cmd.getObjectStr());
                    pick(userInfo);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void pick(UserInfo userInfo);
    }
    public abstract static class getPosts {
        public getPosts(long numberPost, String id) {
            //TODO
            //Rename it
            PostSender postSender = new PostSender(numberPost, Long.parseLong(id));
            Command command = new Command();
            command.setKeyWord(Post.LOAD_POST_USERS);
            command.setSharableObject(postSender.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    if (cmd.getKeyWord().equals(Post.LOAD_POST_USERS)) {
                        SocialArrayList list = SocialArrayList.convertFromJsonString(cmd.getObjectStr());
                        ArrayList<Post> posts = new ArrayList<>();
                        for (int i = 0; i < list.getItems().size(); i++) {
                            posts.add(Post.fromJsonString(list.getItems().get(i)));
                        }

                        onFinish(posts);
                    }
                }

            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(ArrayList<Post> posts);
    }
    public abstract static class addComment
    {
        public addComment(AttachmentSender attachmentSender) {
            Command command = new Command();
            command.setKeyWord(AttachmentSender.ATTACHMENT_USER);
            command.setSharableObject(attachmentSender.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket,command) {
                @Override
                public void analyze(Command cmd) {
                    if (cmd.getKeyWord().equals(AttachmentSender.ATTACHMENT_USER)) {
                        Post b= Post.fromJsonString(cmd.getObjectStr());
                        if(b.getId() !=0) {
                            onFinish(b);
                        }
                    }
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(Post post);
    }
    public abstract static class addUserPost
    {
        public addUserPost(Post post)
        {
            Command command = new Command();
            command.setKeyWord(Post.SAVE_POST_USER);
            command.setSharableObject(post.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket,command) {
                @Override
                public void analyze(Command cmd) {
                    if (cmd.getKeyWord().equals(Post.SAVE_POST_USER)) {
                      onFinish(cmd.getObjectStr());
                    }
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(String result);
    }
    public static abstract class LoadNotification
    {
        public LoadNotification()
        {
            Command command = new Command();
            command.setKeyWord(Notification.LOAD_NOTI);

            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish (SocialArrayList.convertFromJsonString(commandFromServer.getObjectStr()));
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(SocialArrayList list );
    }
}
