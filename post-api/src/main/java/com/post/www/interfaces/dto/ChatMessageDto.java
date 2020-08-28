package com.post.www.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    //메시지 타입 : 입장 채팅 퇴장
    public enum MessageType{
        ENTER, TALK, OUT
    }

    private  MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public void updateMessage(String message){
        this.message = message;
    }
}
