package com.post.www.interfaces;

import com.post.www.application.PostService;
import com.post.www.domain.Post;
import com.post.www.application.PostNotFoundException;
import com.post.www.interfaces.dto.PostResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
        List<PostResponseDto> posts = new ArrayList<>();
        posts.add(new PostResponseDto(
                Post.builder()
                        .idx(1L)
                        .userIdx(1L)
                        .title("JOKER")
                        .contents("Seoul")
                        .publishDate(LocalDateTime.now())
                        .build()
        ));
        Page<PostResponseDto> page = new PageImpl(posts);
        given(postService.getPosts(PageRequest.of(0, 3))).willReturn(page);

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("")
                ));

    }

    @Test
    public void detail() throws Exception {
        PostResponseDto post = new PostResponseDto(
                Post.builder()
                        .idx(1L)
                        .userIdx(1L)
                        .title("JOKER")
                        .contents("Seoul")
                        .publishDate(LocalDateTime.now())
                        .build()
        );
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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";

        LocalDateTime currenttime = LocalDateTime.now();
        given(postService.addPost(any(), any(), any(), any(), any())).willReturn(
                Post.builder()
                        .idx(3L)
                        .userIdx(1L)
                        .title("JOKER")
                        .contents("Seoul")
                        .publishDate(currenttime)
                        .build()
        );

        mvc.perform(post("/posts")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"JOKER\",\"contents\":\"Seoul\"" +
                        ",\"publishDate\":\"2011-11-11\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/posts/3"))
                .andExpect(content().string("{}"));

        verify(postService).addPost(any(),any(), any(), any(), any());

    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"\",\"contents\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(postService, never()).addPost(any(),any(), any(), any(), any());

    }


    @Test
    public void update() throws Exception {
        LocalDateTime currenttime = LocalDateTime.now();

        given(postService.updatePost(eq(1L), any())).willReturn(
                Post.builder()
                        .idx(3L)
                        .userIdx(1L)
                        .title("JOKER")
                        .contents("Seoul")
                        .publishDate(currenttime)
                        .build()
        );
        mvc.perform(patch("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"JOKER Bar\"," +
                        "\"contents\":\"Busan\",\"publishDate\":\"2011-11-11\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));

        verify(postService)
                .updatePost(eq(1L), any());
    }
}