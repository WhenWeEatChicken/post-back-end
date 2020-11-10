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

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @Builder
    public UserAddRequestDto(@NotEmpty String nickname, @NotEmpty UserType type, @NotEmpty String name, @NotEmpty String email, @NotEmpty String password, String comments, String contents) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
