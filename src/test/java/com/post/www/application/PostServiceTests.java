package com.post.www.application;

import com.post.www.domain.Post;
import com.post.www.domain.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        List<Post> restaurants = new ArrayList<>();
        Post restaurant = Post.builder()
                .idx(1L)
                .user_idx(1L)
                .title("Seoul")
                .contents("Bob zip")
                .build();
        restaurants.add(restaurant);

        given(postRepository.findAll()).willReturn(restaurants);
    }

    @Test
    public void getPosts(){
        List<Post> posts = postService.getPosts();

        Post post = posts.get(0);
        assertThat(post.getIdx()).isEqualTo(1L);
    }

    @Test
    public void addRestaurant() {
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
}