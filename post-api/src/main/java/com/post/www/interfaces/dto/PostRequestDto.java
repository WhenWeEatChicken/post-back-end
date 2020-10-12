package com.post.www.interfaces.dto;

import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    @NotNull
    private PostType type;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String title;

    @NotNull
    private PostStatus status;

    private MultipartFile file;

    @Builder
    public PostRequestDto(@NotEmpty PostType type, @NotEmpty String contents, @NotEmpty String title, @NotNull PostStatus status, MultipartFile file) {
        this.type = type;
        this.contents = contents;
        this.title = title;
        this.status = status;
        this.file = file;
    }

}
