package com.post.www.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String name;

    private String comments;

    private String contents;

    private MultipartFile photo;

    @Builder
    public UserUpdateRequestDto(@NotEmpty String nickname, @NotEmpty String name, String comments, String contents, MultipartFile photo) {
        this.nickname = nickname;
        this.name = name;
        this.comments = comments;
        this.contents = contents;
        this.photo = photo;
    }
}
