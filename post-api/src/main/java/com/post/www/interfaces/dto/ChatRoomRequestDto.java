package com.post.www.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

    @NotEmpty
    private String name;

    @Builder
    public ChatRoomRequestDto(@NotEmpty String name) {
        this.name = name;
    }
}
