package com.post.www.interfaces.dto;

import com.post.www.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserAddRequestDto {

    @NotEmpty
    private String nickname;

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
    public UserAddRequestDto(@NotEmpty String nickname, @NotEmpty String name, @NotEmpty String email, @NotEmpty String password, String photo, String comments, String contents) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.comments = comments;
        this.contents = contents;
    }

}
