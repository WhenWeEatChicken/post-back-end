package com.post.www.interfaces.dto;

import com.post.www.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    @NotNull
    private Post post;

    @NotNull
    private Long userIdx;

    @NotNull
    private Long commentIdx;

    @NotNull
    private String contents;
}
