package com.post.www.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotEmpty
    private String roomId;

    @NotEmpty
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;
}
