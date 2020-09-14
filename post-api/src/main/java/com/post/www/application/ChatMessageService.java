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

    public ChatMessage addChatMessage(ChatMessageRequestDto message){
        ChatMessage chatMessage = ChatMessage.builder()
                .type(message.getType())
                .rev_idx(1L)
                .send_idx(2L)
                .room_idx(3L)
                .message(message.getMessage())
                .build();
        return chatMessageRepository.save(chatMessage);
    }
}
