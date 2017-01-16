package com.example.kemo.socializer.Control;

import com.example.kemo.socializer.Connections.CommandRequest;
import com.example.kemo.socializer.Connections.CommandsExecutor;
import com.example.kemo.socializer.Connections.ServerConnection;
import com.example.kemo.socializer.SocialAppGeneral.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by kemo on 24/12/2016.
 */
public class ClientLoggedUser {
    public static final String ADD_FRIEND = "add_Friend";
    private static final String GET_FRIENDS = "get_Friends";
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

    public static abstract class Login {
        protected Login(LoginInfo loginInfo) {
            Command command = new Command();
            command.setKeyWord(LoginInfo.NEW_LOGIN);
            command.setSharableObject(loginInfo.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    onFinish(cmd.getObjectStr());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest, 0);
        }

        public abstract void onFinish(String id);
    }

    public static abstract class GetFriends {
        public GetFriends() {
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

    public static abstract class GetFriendInfo {
        protected GetFriendInfo(String id) {
            Command command = new Command();
            command.setKeyWord(UserInfo.PICK_INFO);
            command.setSharableObject(id);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
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

    public abstract static class addComment {
        public addComment(AttachmentSender attachmentSender) {
            Command command = new Command();
            command.setKeyWord(AttachmentSender.ATTACHMENT_USER);
            command.setSharableObject(attachmentSender.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    if (cmd.getKeyWord().equals(AttachmentSender.ATTACHMENT_USER)) {
                        Post b = Post.fromJsonString(cmd.getObjectStr());
                        if (b.getId() != 0) {
                            onFinish(b);
                        }
                    }
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Post post);
    }

    public abstract static class addUserPost {
        public addUserPost(Post post) {
            Command command = new Command();
            command.setKeyWord(Post.SAVE_POST_USER);
            command.setSharableObject(post.convertToJsonString());
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
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

    public static abstract class LoadNotification {
        public LoadNotification() {
            Command command = new Command();
            command.setKeyWord(Notification.LOAD_NOTI);

            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(SocialArrayList.convertFromJsonString(commandFromServer.getObjectStr()));
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(SocialArrayList list);
    }

    public static abstract class GetRelation {
        public GetRelation(String id) {
            Command command = new Command();
            command.setKeyWord(LoggedUser.GET_RELATION_STATUS);
            command.setSharableObject(id);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    onFinish(cmd.getObjectStr());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(String s);
    }

    public static abstract class AcceptFriendReq {
        public AcceptFriendReq(String id) {
            Command command = initialize(id);
            command.setKeyWord(LoggedUser.ACCEPT_FRIEND);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(commandFromServer);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Command cmd);
    }

    public static abstract class DeclineFriendReq {
        public DeclineFriendReq(String id) {
            Command command = initialize(id);
            command.setKeyWord(LoggedUser.DECLINE_FRIEND);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(commandFromServer);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Command cmd);
    }

    public static abstract class RemoveFriend {
        public RemoveFriend(String id) {
            Command command = initialize(id);
            command.setKeyWord(LoggedUser.REMOVE_FRIEND);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(commandFromServer);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Command cmd);
    }

    public static abstract class CancelFriendReq {
        public CancelFriendReq(String id) {
            Command command = initialize(id);
            command.setKeyWord(LoggedUser.CANCEL_FRIEND_REQ);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(commandFromServer);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Command cmd);
    }

    public static abstract class addFriend {
        public addFriend(String id) {
            Command command = initialize(id);
            command.setKeyWord(LoggedUser.ADD_FRIEND);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command commandFromServer) {
                    onFinish(commandFromServer);
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }

        public abstract void onFinish(Command cmd);
    }

    private static Command initialize(String id) {
        Command command = new Command();
        command.setSharableObject(id);
        return command;
    }

    public static abstract class Search {
        public Search(String key) {
            Command command = new Command();
            command.setKeyWord(LoggedUser.SEARCH_WITH_DETAILS);
            command.setSharableObject(key);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket, command) {
                @Override
                public void analyze(Command cmd) {
                    SocialArrayList socialArrayList = SocialArrayList.convertFromJsonString(cmd.getObjectStr());
                    onFinish((ArrayList<String>) socialArrayList.getItems());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(ArrayList<String> items);
    }
    public static abstract class UploadImage {
        public UploadImage(final byte[] bytes) {
            final String UPLOADIMAGE = "upload_image";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO: #Config
                        new ServerConnection("192.168.43.195", 6010) {
                            @Override
                            public void startConnection() {
                                try {
                                    DataOutputStream dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
                                    Command command = new Command();
                                    command.setKeyWord(UPLOADIMAGE);
                                    dataOutputStream.writeUTF(command.toString());
                                    connectionSocket.setSoTimeout(100000);
                                    DataInputStream dataInputStream = new DataInputStream(connectionSocket.getInputStream());
                                    command = Command.fromString(dataInputStream.readUTF());
                                    String id = command != null ? command.getObjectStr() : null;
                                    //noinspection ResultOfMethodCallIgnored
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
                                    objectOutputStream.writeObject(bytes);
                                    connectionSocket.close();
                                    onFinish(id);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        public abstract void onFinish(String id);
    }

    public static abstract class UpdateInfo {
        public UpdateInfo(UserInfo userInfo)
        {
            Command command = new Command();
            command.setKeyWord(UserInfo.EDIT_INFO);
            command.setSharableObject(userInfo);
            CommandRequest commandRequest = new CommandRequest(MainServerConnection.mainConnectionSocket,command) {
                @Override
                public void analyze(Command cmd) {
                    onFinish(cmd.getObjectStr());
                }
            };
            CommandsExecutor.getInstance().add(commandRequest);
        }
        public abstract void onFinish(String s);
    }
}
