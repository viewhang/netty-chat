package com.ethan.nettychat.controller;

import com.ethan.nettychat.common.Result;
import com.ethan.nettychat.domain.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天控制器
 *
 * @author ethan_peng
 * @date 2021/02/20 03:59:27
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    /**
     * 取所有用户
     *
     * @return {@link Result}
     */
    @GetMapping("/users")
    public Result getAllUsers() {
        return Result.successData(chatService.getAllUser());
    }

    /**
     * 给指定用户发送信息
     *
     * @param message
     * @return
     */
    @PostMapping("/send-one")
    public Result sendMessage(String toUsername, String message) {
        chatService.sendOne(toUsername, message);
        return Result.success();
    }

}
