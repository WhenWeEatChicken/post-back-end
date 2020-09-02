package com.post.www.interfaces;

import com.post.www.domain.ChatRoomRepository;
import com.post.www.interfaces.dto.ChatRoomDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"6.ChatRoomController"})
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅 리스트 화면
    @ApiOperation(value = "방 리스트", notes = "생성된 채팅방을 조회합니다. (뷰연동)")
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @ApiOperation(value = "방 리스트", notes = "생성된 채팅방을 조회합니다.")
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDto> room() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 생성
    @ApiOperation(value = "방 생성", notes = "채팅방을 생성합니다.")
    @PostMapping("/room")
    @ResponseBody
    public ChatRoomDto createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }
    // 채팅방 입장 화면
    @ApiOperation(value = "방 상세", notes = "생성된 채팅방에 입장합니다. (뷰 연동)")
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    // 특정 채팅방 조회
    @ApiOperation(value = "방 상세", notes = "해당 채팅방을 조회합니다.")
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDto roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
