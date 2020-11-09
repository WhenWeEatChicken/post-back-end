package com.post.www.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<PostFile, Long> {

    Optional<PostFile> findByIdx(Long idx);

    @Override
    PostFile save(PostFile entity);
}
