package com.post.www.application;

import com.post.www.domain.ChatMessage;
import com.post.www.domain.ChatMessageRepository;
import com.post.www.interfaces.dto.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage addChatMessage(Long userIdx, ChatMessageRequestDto message){
        ChatMessage chatMessage = ChatMessage.builder()
                .type(message.getType())
                .sendIdx(userIdx)
                .chatRoom(message.getChatRoom())
                .message(message.getMessage())
                .build();
        return chatMessageRepository.save(chatMessage);
    }
}
