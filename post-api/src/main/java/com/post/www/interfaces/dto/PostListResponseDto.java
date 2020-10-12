package com.post.www.interfaces.dto;

import com.post.www.config.enums.PostStatus;
import com.post.www.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PostListResponseDto {

    private Long idx;
    private String contents;
    private String title;
    private PostStatus status;
    private LocalDateTime publishDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public PostListResponseDto(Post entity) {
        this.idx = entity.getIdx();
        this.contents = entity.getContents();
        this.title = entity.getTitle();
        this.status = entity.getStatus();
        this.publishDate = entity.getPublishDate();
        this.createDate = entity.getCreatedDate();
        this.updateDate = entity.getModifiedDate();
    }
}
