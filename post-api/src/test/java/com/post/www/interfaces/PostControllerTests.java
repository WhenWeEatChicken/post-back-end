package com.post.www.interfaces;

import com.post.www.application.PostFileService;
import com.post.www.application.PostService;
import com.post.www.config.enums.PostStatus;
import com.post.www.config.enums.PostType;
import com.post.www.domain.Post;
import com.post.www.application.exception.PostNotFoundException;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.PostDetailResponseDto;
import com.post.www.interfaces.dto.PostListResponseDto;
import com.post.www.interfaces.dto.PostSearchRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
    @MockBean
    private PostFileService postFileService;

    @Test
    public void list() throws Exception {
        List<PostListResponseDto> posts = new ArrayList<>();
        posts.add(new PostListResponseDto(
                Post.builder()
                        .idx(eq(1L))
                        .user(any())
                        .title("JOKER")
                        .contents("Seoul")
                        .publishDate(LocalDateTime.now())
                        .build()
        ));
        Page<PostListResponseDto> page = new PageImpl(posts);
        PostSearchRequestDto requestDto = PostSearchRequestDto.builder()
                .title("JOKER")
                .build();
        given(postService.getPosts(requestDto, PageRequest.of(0, 3))).willReturn(page);

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("")
                ));

    }

    @Test
    public void detail() throws Exception {
        PostDetailResponseDto post = new PostDetailResponseDto(
                Post.builder()
                        .idx(1L)
                        .user(User.builder().idx(1L).build())
                        .title("JOKER")
                        .contents("Seoul")
                        .status(PostStatus.Y)
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
        given(postService.getPost(101L))
                .willThrow(new PostNotFoundException(101L));

        mvc.perform(get("/posts/101"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYsIm5hbWUiOiJzdHJpbmciLCJpYXQiOjE2MDM4NjU3NzQsImV4cCI6MTYwMzg3Mjk3NH0.MT22kid_83uH4gGgkVmYRX7yekhcp4kWip-Tv8wN_ng";

        given(postService.addPost(any(), any(), any(), any(), any())).willReturn(
                Post.builder()
                        .idx(3L)
                        .type(PostType.NOTICE)
                        .user(User.builder().idx(3L).build())
                        .title("JOKER")
                        .contents("Seoul")
                        .status(PostStatus.Y)
                        .build()
        );
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        mvc.perform(multipart("/posts")
                .file(multipartFile)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .content("{\"type\":\"NOTICE\",\"userIdx\":1,\"title\":\"JOKER\",\"contents\":\"Seoul\"" +
//                        ",\"status\":\"Y\"}")

                .param("type","NOTICE")
                .param("userIdx","1")
                .param("title","JOKER")
                .param("contents","Seoul")
                .param("status","Y")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/posts/3"))
                .andExpect(content().string("{}"));

        verify(postService).addPost(any(), any(), any(), any(), any());
        verify(postFileService).addFile(any(), any(), any(), any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/posts")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("type","NOTICE")
//                .content("{\"userIdx\":1,\"title\":\"\",\"contents\":\"\"}")
        )
                .andExpect(status().isBadRequest());

        verify(postService, never()).addPost(any(), any(), any(), any(), any());

    }


    @Test
    public void update() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYsIm5hbWUiOiJzdHJpbmciLCJpYXQiOjE2MDM4NjU3NzQsImV4cCI6MTYwMzg3Mjk3NH0.MT22kid_83uH4gGgkVmYRX7yekhcp4kWip-Tv8wN_ng";

        mvc.perform(patch("/posts/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"JOKER Bar\"," +
                        "\"contents\":\"Busan\",\"status\":\"Y\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));

        verify(postService)
                .updatePost(eq(1L),eq(6L), any());
    }
}