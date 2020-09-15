package com.post.www.interfaces.dto;

import com.post.www.domain.Post;
import com.post.www.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostResponseDto {

    private Long idx;
    private String contents;
    private String title;
    private LocalDateTime publishDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public PostResponseDto(Post entity) {
        this.idx = entity.getIdx();
        this.contents = entity.getContents();
        this.title = entity.getTitle();
        this.publishDate = entity.getPublishDate();
        this.createDate = entity.getCreatedDate();
        this.updateDate = entity.getModifiedDate();
    }
}
