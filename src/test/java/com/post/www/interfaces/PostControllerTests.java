package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import com.post.www.domain.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Test
    public void list() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(Post.builder()
                .idx(1L)
                .user_idx(1L)
                .title("JOKER")
                .contents("Seoul")
                .publishdate("")
                .build());

        given(postService.getPosts()).willReturn(posts);

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"idx\":1")
                ))
                .andExpect(content().string(
                        containsString("\"title\":\"JOKER\"")
                ));
    }

    @Test
    public void detail() throws Exception {
        Post post = Post.builder()
                .idx(1L)
                .user_idx(1L)
                .title("JOKER")
                .contents("Seoul")
                .publishdate("")
                .build();
        given(postService.getPost(1L)).willReturn(post);
        mvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"idx\":1")
                ))
                .andExpect(content().string(
                        containsString("\"title\":\"JOKER\"")
                ));

    }
    @Test
    public void detailWithNotExisted() throws Exception {
        given(postService.getPost(404L))
                .willThrow(new PostNotFoundException(404L));

        mvc.perform(get("/posts/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void create() throws Exception {
        given(postService.addPost(any())).will(invocation -> {
            Post post = invocation.getArgument(0);
            return Post.builder()
                    .idx(3L)
                    .user_idx(1L)
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .publishdate(post.getPublishdate())
                    .build();
        });

        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_idx\":1,\"title\":\"JOKER\",\"contents\":\"Seoul\",\"publishdate\":\"\",\"createdate\":\"\",\"updatedate\":\"\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/posts/3"))
                .andExpect(content().string("{}"));

        verify(postService).addPost(any());

    }
}