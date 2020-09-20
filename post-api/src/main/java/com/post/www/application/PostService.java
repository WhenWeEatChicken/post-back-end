package com.post.www.application;

import com.post.www.application.exception.PostNotFoundException;
import com.post.www.application.exception.UserNotExistedException;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
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
    private final UserRepository userRepository;

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List list = posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        Page<PostResponseDto> responseDtos = new PageImpl<PostResponseDto>(list);
        return responseDtos;
    }

    public PostResponseDto getPost(Long idx) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));

        return new PostResponseDto(post);
    }

    public Post addPost(Long userIdx, String title, String contents, String publishDate, PostType type) {
        User user = userRepository.findByIdx(userIdx)
                .orElseThrow(() -> new UserNotExistedException(userIdx));
        Post post = Post.builder()
                .user(user)
                .type(type)
                .title(title)
                .contents(contents)
                .publishDate(LocalDateTime.parse(publishDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long idx, PostRequestDto requestDto) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));
        String title = requestDto.getTitle();
        String contents = requestDto.getContents();
        String publishDate = requestDto.getPublishDate();

        post.updatePost(title, contents, LocalDateTime.parse(publishDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return post;
    }
}
