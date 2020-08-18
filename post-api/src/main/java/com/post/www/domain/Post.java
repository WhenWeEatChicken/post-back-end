package com.post.www.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @Column(name = "user_idx")
    private Long userIdx;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String title;

    @NotNull
    private LocalDateTime publishDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    public void updatePost(@NotNull Long userIdx, @NotEmpty String title, @NotEmpty String contents, @NotNull LocalDateTime publishDate) {
        this.userIdx = userIdx;
        this.title = title;
        this.contents = contents;
        this.publishDate = publishDate;
    }

}
