package com.post.www.interfaces.dto;

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

    @NotNull
    private Long userIdx;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String title;

    @NotEmpty
    private String publishDate;

    @Builder
    public PostRequestDto(@NotNull Long userIdx, @NotEmpty String contents, @NotEmpty String title, @NotEmpty String publishDate) {
        this.userIdx = userIdx;
        this.contents = contents;
        this.title = title;
        this.publishDate = publishDate;
    }

    public Post toEntity(){
        return Post.builder()
                .userIdx(userIdx)
                .title(title)
                .contents(contents)
                .publishDate(LocalDateTime.parse(publishDate+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
