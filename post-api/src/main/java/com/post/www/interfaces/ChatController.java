package com.post.www.interfaces;

import com.post.www.application.ChatMessageService;
import com.post.www.config.enums.MessageType;
import com.post.www.interfaces.dto.ChatMessageRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Api(tags = {"5.ChatController"})
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;

    @ApiOperation(value = "메세지 캐치", notes = "요청받은 메시지를 구독중인 사용자에게 전송")
    @MessageMapping("/chat/message")
    public void message(ChatMessageRequestDto message) {
        if (MessageType.ENTER.equals(message.getType())) {
            message.updateMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        addChatMessage(message);
    }

    public void addChatMessage(ChatMessageRequestDto message){
        chatMessageService.addChatMessage(message);
    }
}