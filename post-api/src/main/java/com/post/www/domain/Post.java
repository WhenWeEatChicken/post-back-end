package com.post.www.domain;


import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
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
    private PostType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_idx" , referencedColumnName = "idx")
    private User user;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String title;

    @NotNull
    private PostStatus status;

    private LocalDateTime publishDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<File> files;

    public void updatePost(@NotEmpty String title, @NotEmpty String contents, @NotNull PostStatus status) {
        this.title = title;
        this.contents = contents;
        this.status = status;
        if(status.equals(PostStatus.Y) && this.publishDate == null){
            this.publishDate = LocalDateTime.now();
        }
    }

}
