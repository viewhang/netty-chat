package com.ethan.nettychat.domain;

import io.netty.channel.Channel;

/**
 * 用户
 *
 * @author ethan_peng
 * @date 2021/02/20 04:34:28
 */
public class User {
    public User() {
    }

    public User(String id, String username, Channel channel) {
        this.id = id;
        this.username = username;
        this.channel = channel;
    }

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 通道
     */
    private Channel channel;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Channel getChannel() {
        return channel;
    }
}
