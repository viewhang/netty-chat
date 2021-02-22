package com.ethan.nettychat.domain;

import com.ethan.nettychat.handler.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 聊天服务
 *
 * @author ethan_peng
 * @date 2021/02/20 04:14:23
 */
@Service
public class ChatService {
    //用来存放每个客户端对应的webSocket对象
    private CopyOnWriteArrayList<User> userList = new CopyOnWriteArrayList<>();

    /**
     * 获取所有用户
     */
    public List<User> getAllUser() {
        return userList;
    }

    /**
     * 添加用户
     *
     * @param id       id
     * @param username 用户名
     */
    public void addUser(String id, String username, Channel channel) {
        userList.add(new User(id, username, channel));
    }

    /**
     * 删除用户
     *
     * @param id id
     */
    public void removeUser(String id) {
        User user = getUser(id);
        userList.remove(user);
    }

    /**
     * 获得用户名
     *
     * @param id id
     * @return {@link String}
     */
    public String getUsername(String id) {
        User user = getUser(id);
        return user.getUsername();
    }

    /**
     * 获得用户名
     *
     * @param id id
     * @return {@link String}
     */
    public User getUser(String id) {
        return userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * 获取用户
     */
    public User getUserByUsername(String username) {
        return userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }


    /**
     * 群发消息
     *
     * @param sendUsername 发送用户名
     * @param message      消息
     */
    public void sendAll(String sendUsername, String message) {
        User user = getUserByUsername(sendUsername);
        ChannelGroup channels = TextWebSocketFrameHandler.channels;
        for (Channel channel : channels) {
            if (channel != user.getChannel()) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + user.getUsername() + "]：" + message));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[你][" + user.getUsername() + "]：" + message));
            }
        }
    }

    /**
     * 给指定用户发送消息
     *
     * @param username 用户名
     * @param message  消息
     */
    public void sendOne(String username, String message) {
        User user = getUserByUsername(username);
        if (user == null) {
            return;
        }
        Channel channel = user.getChannel();
        channel.writeAndFlush(new TextWebSocketFrame("您收到一条私信：" + message));
    }

}
