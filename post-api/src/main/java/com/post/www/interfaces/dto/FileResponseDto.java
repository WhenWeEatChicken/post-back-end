package com.post.www.interfaces.dto;

import com.post.www.domain.PostFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileResponseDto {

    private Long idx;
    private String filePath;
    private String fileName;

    @Builder
    public FileResponseDto(PostFile entity){
        this.idx = entity.getIdx();
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
    }
}
