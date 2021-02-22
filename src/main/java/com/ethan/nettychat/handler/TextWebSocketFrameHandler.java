package com.ethan.nettychat.handler;

import com.ethan.nettychat.common.RandomName;
import com.ethan.nettychat.domain.ChatService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 消息处理类
 *
 * @author ethan_peng
 * @date 2021/02/20 11:34:12
 */
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    ChatService chatService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) {
        Channel incoming = ctx.channel();
        String username = chatService.getUsername(incoming.id() + "");
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + username + "]：" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[你][" + username + "]：" + msg.text()));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress());
        String uName = new RandomName().getRandomName();

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[新用户] - " + uName + " 加入"));
        }
        chatService.addUser(incoming.id() + "", uName, incoming);
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        String uName = chatService.getUsername(incoming.id() + "");
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[用户] - " + uName + " 离开"));
        }
        chatService.removeUser(incoming.id() + "");

        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + chatService.getUsername(incoming.id() + "") + "上线");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + chatService.getUsername(incoming.id() + "") + "离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + chatService.getUsername(incoming.id() + "") + "异常");
        cause.printStackTrace();
        ctx.close();
    }

}
