package com.post.www.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private Post post;

    @NotNull
    @Column(name="user_idx")
    private Long userIdx;

    @NotNull
    @Column(name="comment_idx")
    private Long commentIdx;

    @NotNull
    private String contents;

    @NotEmpty
    @Column(name="is_del")
    private String isDel;

}
