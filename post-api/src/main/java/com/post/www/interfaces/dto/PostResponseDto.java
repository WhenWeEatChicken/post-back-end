package com.post.www.interfaces.dto;

import com.post.www.config.enums.PostStatus;
import com.post.www.domain.File;
import com.post.www.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {

    private Long idx;
    private String contents;
    private String title;
    private PostStatus status;
    private LocalDateTime publishDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<File> files;

    @Builder
    public PostResponseDto(Post entity) {
        this.idx = entity.getIdx();
        this.contents = entity.getContents();
        this.title = entity.getTitle();
        this.status = entity.getStatus();
        this.publishDate = entity.getPublishDate();
        this.createDate = entity.getCreatedDate();
        this.updateDate = entity.getModifiedDate();
        this.files = entity.getFiles();
    }
}
