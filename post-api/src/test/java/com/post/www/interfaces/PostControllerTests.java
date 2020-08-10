package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import com.post.www.domain.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .userIdx(1L)
                .title("JOKER")
                .contents("Seoul")
                .publishdate("")
                .build());
        Page<Post> page = new PageImpl(posts);
        given(postService.getPosts(PageRequest.of(0, 3))).willReturn(page);

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("")
                ));

    }

    @Test
    public void detail() throws Exception {
        Post post = Post.builder()
                .idx(1L)
                .userIdx(1L)
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
                    .userIdx(1L)
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .publishdate(post.getPublishdate())
                    .build();
        });

        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"JOKER\",\"contents\":\"Seoul\",\"publishdate\":\"\",\"createdate\":\"\",\"updatedate\":\"\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/posts/3"))
                .andExpect(content().string("{}"));

        verify(postService).addPost(any());

    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"\",\"contents\":\"\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void update() throws Exception {
        mvc.perform(patch("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"JOKER Bar\"," +
                        "\"contents\":\"Busan\",\"publishdate\":\"\"}"))
                .andExpect(status().isOk());

        verify(postService)
                .updatePost(1L, 1L, "JOKER Bar", "Busan", "");
    }
}