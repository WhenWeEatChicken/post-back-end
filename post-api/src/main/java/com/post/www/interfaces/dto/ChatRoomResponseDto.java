package com.post.www.interfaces.dto;

import com.post.www.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    private String roomId;
    private String name;

    @Builder
    public ChatRoomResponseDto(ChatRoom entity) {
        this.roomId = entity.getRoomId();
        this.name = entity.getName();
    }

}
