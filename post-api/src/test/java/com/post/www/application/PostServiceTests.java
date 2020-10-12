package com.post.www.application;

import com.post.www.application.exception.PostNotFoundException;
import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import com.post.www.domain.User;
import com.post.www.domain.UserRepository;
import com.post.www.interfaces.dto.PostRequestDto;
import com.post.www.interfaces.dto.PostResponseDto;
import com.post.www.interfaces.dto.PostSearchRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
class PostServiceTests {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;


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
        String title = "Seoul";
        given(postRepository.findAll(PageRequest.of(0, 3))).willReturn(page);
        given(postRepository.findAllByTitleLike(title, PageRequest.of(0, 3))).willReturn(page);

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));
    }

    @Test
    public void getPosts() {
        PostSearchRequestDto requestDto = PostSearchRequestDto.builder()
                .title("Seoul")
                .build();
        Page<PostResponseDto> posts = postService.getPosts(requestDto, PageRequest.of(0, 3));

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
        User user = User.builder()
                .idx(1L)
                .build();
        given(userRepository.findByIdx(any())).willReturn(Optional.of(user));

        given(postRepository.save(any())).will(invocation -> {
            Post post = invocation.getArgument(0);
            return post;
        });

        Long userIdx = 1L;
        String title = "BeRyong";
        String contents = "Busan";
        PostType type = PostType.NOTICE;
        PostStatus status = PostStatus.Y;
        Post created = postService.addPost(userIdx, title, contents, status, type);
        assertThat(created.getTitle()).isEqualTo("BeRyong");
    }

    @Test
    public void updatePost() {
        Post post = Post.builder()
                .idx(1L)
                .user(User.builder().idx(1L).build())
                .title("Bob zip")
                .contents("Seoul")
                .status(PostStatus.Y)
                .build();

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("BeRyong")
                .contents("Busan")
                .status(PostStatus.Y)
                .build();

        postService.updatePost(1L, postRequestDto);

        assertThat(post.getTitle()).isEqualTo("BeRyong");
        assertThat(post.getContents()).isEqualTo("Busan");
    }
}