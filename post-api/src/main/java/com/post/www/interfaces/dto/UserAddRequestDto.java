package com.post.www.interfaces.dto;

import com.post.www.config.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserAddRequestDto {

    @NotEmpty
    private String nickname;

    @NotNull
    private UserType type;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String photo;

    private String comments;

    private String contents;

    @Builder
    public UserAddRequestDto(@NotEmpty String nickname, @NotEmpty UserType type, @NotEmpty String name, @NotEmpty String email, @NotEmpty String password, String photo, String comments, String contents) {
        this.nickname = nickname;
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.comments = comments;
        this.contents = contents;
    }
}
