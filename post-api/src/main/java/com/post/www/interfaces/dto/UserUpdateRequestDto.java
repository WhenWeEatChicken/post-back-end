package com.post.www.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String name;

    private String comments;

    private String contents;

    @Builder
    public UserUpdateRequestDto(@NotEmpty String nickname, @NotEmpty String name, String comments, String contents) {
        this.nickname = nickname;
        this.name = name;
        this.comments = comments;
        this.contents = contents;
    }
}
