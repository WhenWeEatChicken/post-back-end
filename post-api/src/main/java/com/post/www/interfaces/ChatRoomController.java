package com.post.www.interfaces;

import com.post.www.application.ChatRoomService;
import com.post.www.domain.ChatRoom;
import com.post.www.interfaces.dto.ChatRoomResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"6.ChatRoomController"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 모든 채팅방 목록 반환
    @ApiOperation(value = "방 리스트", notes = "생성된 채팅방을 조회합니다.")
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> room() {
        return chatRoomService.getRooms();
    }

    // 채팅방 생성
    @ApiOperation(value = "방 생성", notes = "채팅방을 생성합니다.")
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomService.createChatRoom(name);
    }

    // 특정 채팅방 조회
    @ApiOperation(value = "방 상세", notes = "해당 채팅방을 조회합니다.")
    @GetMapping("/room/{roomId}")
    public ChatRoomResponseDto roomInfo(@PathVariable String roomId) {
        return chatRoomService.getRoom(roomId);
    }
}
