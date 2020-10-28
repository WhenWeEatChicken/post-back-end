package com.post.www.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_idx" , referencedColumnName = "idx")
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_idx", referencedColumnName = "idx")
    private User user;

    @NotNull
    @Column(name="comment_idx")
    private Long commentIdx;

    @NotNull
    private String contents;

    @NotEmpty
    @Column(name="is_del")
    private String isDel;

    public void updateComment(@NotNull String contents) {
        this.contents = contents;
    }
}
