package com.post.www.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    public void updatePost(Long userIdx, String title, String contents, LocalDateTime publishDate) {
        if (userIdx != null) this.userIdx = userIdx;
        if (title != null && !title.isEmpty()) this.title = title;
        if (contents != null && !contents.isEmpty()) this.contents = contents;
        if (publishDate != null) this.publishDate = publishDate;
    }

}
