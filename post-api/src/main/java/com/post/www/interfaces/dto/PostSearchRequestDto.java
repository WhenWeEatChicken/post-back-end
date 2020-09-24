package com.post.www.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchRequestDto {

    @NotEmpty
    private String title;

    @Builder
    public PostSearchRequestDto(@NotEmpty String title) {
        this.title = title;
    }
}
