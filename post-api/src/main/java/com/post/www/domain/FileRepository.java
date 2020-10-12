package com.post.www.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByIdx(Long idx);

    @Override
    File save(File entity);
}
