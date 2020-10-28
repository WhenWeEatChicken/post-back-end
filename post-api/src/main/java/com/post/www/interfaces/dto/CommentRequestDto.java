package com.post.www.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long postIdx;

    @NotNull
    private Long commentIdx;

    @NotNull
    private String contents;
}
