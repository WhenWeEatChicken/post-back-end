package com.post.www.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Optional<Comment> findByIdx(Long idx);

    Comment save(Comment comment);
}
