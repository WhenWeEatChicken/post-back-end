package com.post.www.application;

import com.post.www.domain.Post;
import com.post.www.domain.PostNotFoundException;
import com.post.www.domain.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
                .userIdx(1L)
                .title("Seoul")
                .contents("Bob zip")
                .build();
        posts.add(post);
        Page<Post> page = new PageImpl(posts);
        given(postRepository.findAll(PageRequest.of(0,3))).willReturn(page);

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));
    }

    @Test
    public void getPosts(){
        Page<Post> posts = postService.getPosts(PageRequest.of(0,3));

        List<Post> list = posts.getContent();
        Post post = list.get(0);
        assertThat(post.getIdx()).isEqualTo(1L);
    }

    @Test
    public void getPost() {
        Post post = postService.getPost(1L);

        assertThat(post.getIdx()).isEqualTo(1L);
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
            post.setIdx(3L);
            return post;
        });

        Post post = Post.builder()
                .title("BeRyong")
                .contents("Busan")
                .publishdate("")
                .build();

        Post created = postService.addPost(post);
        assertThat(created.getIdx()).isEqualTo(3L);
    }

    @Test
    public void updatePost() {
        Post post = Post.builder()
                .idx(1L)
                .title("Bob zip")
                .contents("Seoul")
                .publishdate("")
                .build();

        given(postRepository.findByIdx(1L))
                .willReturn(Optional.of(post));

        postService.updatePost(1L, 1L, "Sool zip", "Busan","");

        assertThat(post.getTitle()).isEqualTo("Sool zip");
        assertThat(post.getContents()).isEqualTo("Busan");
    }
}