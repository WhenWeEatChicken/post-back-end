package com.post.www.interfaces.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatRoomDto {
    private String roomId;
    private String name;


    public static ChatRoomDto create(String name){
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.name = name;
        return chatRoomDto;
    }
}
