package com.post.www.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , Long> {
    List<ChatRoom> findAll();

    Optional<ChatRoom> findByRoomId(String roomId);

    @Override
    ChatRoom save(ChatRoom chatRoom);
}
