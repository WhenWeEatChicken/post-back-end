package com.post.www.domain;

import com.post.www.config.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class File extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotEmpty
    private String filePath;

    @NotEmpty
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "ref_idx" , referencedColumnName = "idx")
    private Post post;

    @NotNull
    private FileType refType;

}
