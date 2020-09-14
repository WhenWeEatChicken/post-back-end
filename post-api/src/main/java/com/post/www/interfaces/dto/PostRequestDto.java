package com.post.www.interfaces.dto;

import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    @NotEmpty
    private PostType type;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String title;

    @NotEmpty
    private String publishDate;

    @Builder
    public PostRequestDto(@NotEmpty PostType type, @NotEmpty String contents, @NotEmpty String title, @NotEmpty String publishDate) {
        this.type = type;
        this.contents = contents;
        this.title = title;
        this.publishDate = publishDate;
    }

}
