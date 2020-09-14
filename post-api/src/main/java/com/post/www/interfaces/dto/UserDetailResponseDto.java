package com.post.www.interfaces.dto;

import com.post.www.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserDetailResponseDto {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private String photo;

    private String comments;

    private String contents;

    @Builder
    public UserDetailResponseDto(User entity){
        this.nickname = entity.getNickname();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.photo = entity.getPhoto();
        this.comments = entity.getComments();
        this.contents = entity.getContents();
    }
}
