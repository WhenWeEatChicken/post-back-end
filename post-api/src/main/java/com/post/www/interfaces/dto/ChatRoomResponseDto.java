package com.post.www.interfaces.dto;

import com.post.www.domain.ChatMessage;
import com.post.www.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomResponseDto {

    private String roomId;
    private String name;
    private List<ChatMessage> chatMessages;

    @Builder
    public ChatRoomResponseDto(ChatRoom entity) {
        this.roomId = entity.getRoomId();
        this.name = entity.getName();
        this.chatMessages = entity.getChatMessages();
    }

}
