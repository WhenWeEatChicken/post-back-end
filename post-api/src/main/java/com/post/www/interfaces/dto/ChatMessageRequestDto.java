package com.post.www.interfaces.dto;

import com.post.www.config.enums.MessageType;
import com.post.www.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {

    @NotNull
    private MessageType type;

    private Long chatRoomIdx;

    @NotNull
    private String roomId;

    private String message;

    public void updateMessage(String message){
        this.message = message;
    }
}
