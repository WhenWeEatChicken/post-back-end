package com.post.www.interfaces.dto;

import com.post.www.config.enums.PostStatus;
import com.post.www.domain.File;
import com.post.www.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PostDetailResponseDto {

    private Long idx;
    private String contents;
    private String title;
    private PostStatus status;
    private LocalDateTime publishDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<FileResponseDto> files;

    @Builder
    public PostDetailResponseDto(Post entity) {
        this.idx = entity.getIdx();
        this.contents = entity.getContents();
        this.title = entity.getTitle();
        this.status = entity.getStatus();
        this.publishDate = entity.getPublishDate();
        this.createDate = entity.getCreatedDate();
        this.updateDate = entity.getModifiedDate();
        if(entity.getFiles() != null){
            this.files = entity.getFiles().stream().map(FileResponseDto::new).collect(Collectors.toList());
        }
    }
}
