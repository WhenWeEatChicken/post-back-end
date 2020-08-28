package com.post.www.application;

import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List list = posts.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
        Page<PostResponseDto> responseDtos = new PageImpl<PostResponseDto>(list);
        return responseDtos;
    }

    public PostResponseDto getPost(Long idx) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));

        return new PostResponseDto(post);
    }

    public Post addPost(PostRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Post updatePost(Long idx, PostRequestDto requestDto) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));
        if (post != null) {
            Long user_idx = requestDto.getUserIdx();
            String title = requestDto.getTitle();
            String contents = requestDto.getContents();
            String publishDate = requestDto.getPublishDate();

            post.updatePost(user_idx, title, contents, LocalDateTime.parse(publishDate+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return post;
    }
}
