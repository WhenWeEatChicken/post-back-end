package com.post.www.application;

import com.post.www.domain.ChatMessage;
import com.post.www.domain.ChatMessageRepository;
import com.post.www.domain.ChatRoom;
import com.post.www.domain.ChatRoomRepository;
import com.post.www.interfaces.dto.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    public ChatMessage addChatMessage(Long userIdx, ChatMessageRequestDto message){
        //TODO: 후에 익셉션 던져주기
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElse(null);

        ChatMessage chatMessage = ChatMessage.builder()
                .type(message.getType())
                .sendIdx(userIdx)
                .chatRoom(chatRoom)
                .message(message.getMessage())
                .build();
        return chatMessageRepository.save(chatMessage);
    }
}
