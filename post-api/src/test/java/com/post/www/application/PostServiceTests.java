package com.post.www.application;

import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class PostServiceTests {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockPostRepository();
    }

    private void mockPostRepository() {
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .idx(1L)
                .user(any())
                .title("Seoul")
                .contents("Bob zip")
                .build();
        posts.add(post);
        Page<Post> page = new PageImpl(posts);
        given(postRepository.findAll(PageRequest.of(0, 3))).willReturn(page);

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));
    }

    @Test
    public void getPosts() {
        Page<PostResponseDto> posts = postService.getPosts(PageRequest.of(0, 3));

        List<PostResponseDto> list = posts.getContent();
        PostResponseDto responseDto = list.get(0);
        assertThat(responseDto.getIdx()).isEqualTo(1L);
    }

    @Test
    public void getPost() {
        PostResponseDto responseDto = postService.getPost(1L);

        assertThat(responseDto.getIdx()).isEqualTo(1L);
    }

    @Test
    public void getPostWithNotExisted() {
        assertThatThrownBy(() -> {
            postService.getPost(404L);
        }).isInstanceOf(PostNotFoundException.class);
    }

    @Test
    public void addPost() {
        given(postRepository.save(any())).will(invocation -> {
            Post post = invocation.getArgument(0);
            return post;
        });

        Long userIdx = 1L;
        String title = "BeRyong";
        String contents = "Busan";
        String publishDate = "2011-11-11";
        PostType type = PostType.NOTICE;
        Post created = postService.addPost(userIdx, title, contents, publishDate, type);
        assertThat(created.getTitle()).isEqualTo("BeRyong");
    }

    @Test
    public void updatePost() {
        Post post = Post.builder()
                .idx(1L)
                .title("Bob zip")
                .contents("Seoul")
                .publishDate(LocalDateTime.now())
                .build();

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("BeRyong")
                .contents("Busan")
                .publishDate("2011-11-11")
                .build();

        postService.updatePost(1L, postRequestDto);

        assertThat(post.getTitle()).isEqualTo("BeRyong");
        assertThat(post.getContents()).isEqualTo("Busan");
    }
}