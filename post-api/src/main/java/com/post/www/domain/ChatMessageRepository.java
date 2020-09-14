package com.post.www.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    @Override
    ChatMessage save(ChatMessage chatMessage);
}
