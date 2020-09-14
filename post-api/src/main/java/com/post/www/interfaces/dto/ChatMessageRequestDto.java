package com.post.www.interfaces.dto;

import com.post.www.config.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public void updateMessage(String message){
        this.message = message;
    }
}
