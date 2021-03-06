package com.post.www.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.post.www.config.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    private MessageType type;

    @NotNull
    private Long sendIdx;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_idx", referencedColumnName = "idx")
    @JsonIgnore
    private ChatRoom chatRoom;

    @NotEmpty
    private String message;



}
