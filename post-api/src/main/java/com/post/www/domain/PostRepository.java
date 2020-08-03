package com.post.www.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findAll();

//    Page<Post> findAllBy();

    Optional<Post> findById(Long idx);

    Post save(Post post);
}
