package com.post.www.interfaces.dto;

import com.post.www.domain.File;
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
    public FileResponseDto(File entity){
        this.idx = entity.getIdx();
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
    }
}
