package com.post.www.domain;

import com.post.www.config.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    private UserType type;

    @NotEmpty
    private String nickname;

    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String photo;

    private String comments;

    private String contents;

    public void updateUser(String nickname, String name, String comments, String contents, String filePath) {
        this.nickname = nickname;
        this.name = name;
        this.comments = comments;
        this.contents = contents;
        this.photo = filePath;
    }
}

