package com.post.www.interfaces;

import com.post.www.application.CommentService;
import com.post.www.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    CommentService commentService;

    @Test
    void create() throws Exception {
        given(commentService.addComment(any())).willReturn(
                Comment.builder()
                .idx(3L)
                .contents("BBBB")
                .build()
        );

        mvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"post\":{\"postIdx\":1},\"contents\":\"BBBB\",\"commentIdx\":\"0\",\"userIdx\":\"0\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/comment/3"))
                .andExpect(content().string("{}"));

        verify(commentService).addComment(any());
    }
    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIdx\":1,\"title\":\"\",\"contents\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}