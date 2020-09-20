package com.post.www.application;

import com.post.www.domain.ChatRoom;
import com.post.www.domain.ChatRoomRepository;
import com.post.www.interfaces.dto.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
//    private Map<String, ChatRoomResponseDto> chatRoomMap;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomResponseDto> getRooms() {
        // 채팅방 생성순서 최근 순으로 반환
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List list = chatRooms.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
        return list;
    }

    public ChatRoomResponseDto getRoom(String code) {
        // TODO: null 예외처리 해야함
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(code).orElse(null);
        return new ChatRoomResponseDto(chatRoom);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(name)
                .build();

        return chatRoomRepository.save(chatRoom);
    }
}
