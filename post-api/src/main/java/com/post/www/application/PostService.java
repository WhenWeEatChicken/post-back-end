package com.post.www.application;

import com.post.www.application.exception.PostNotFoundException;
import com.post.www.application.exception.UserNotExistedException;
import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import com.post.www.interfaces.dto.PostSearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostResponseDto> getPosts(PostSearchRequestDto requestDto, Pageable pageable) {
        String title = requestDto.getTitle();
        Page<Post> posts;
        if (title != null && !title.equals("")) {
             posts = postRepository.findAllByTitleLike(title, pageable);
        }else{
             posts = postRepository.findAll(pageable);
        }
        List<PostResponseDto> list = posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    public PostResponseDto getPost(Long idx) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));

        return new PostResponseDto(post);
    }

    public Post addPost(Long userIdx, String title, String contents, PostStatus status, PostType type) {
        User user = userRepository.findByIdx(userIdx)
                .orElseThrow(() -> new UserNotExistedException(userIdx));
        Post post = Post.builder()
                .user(user)
                .type(type)
                .title(title)
                .contents(contents)
                .status(status)
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long idx, PostRequestDto requestDto) {
        Post post = postRepository.findByIdx(idx)
                .orElseThrow(() -> new PostNotFoundException(idx));
        String title = requestDto.getTitle();
        String contents = requestDto.getContents();
        PostStatus status = requestDto.getStatus();

        post.updatePost(title, contents, status);
        return post;
    }
}
