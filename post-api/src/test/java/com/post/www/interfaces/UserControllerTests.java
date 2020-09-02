package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        User mockUser = User.builder()
                .idx(3L)
                .nickname("dlh1106")
                .name("도훈")
                .email("dlh1106@naver.com")
                .password("password")
                .build();

        given(userService.addUser(any()))
                .willReturn(mockUser);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\":\"dlh1106\",\"name\":\"도훈\",\"email\":\"dlh1106@naver.com\",\"password\":\"password\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/user/3"))
                .andExpect(content().string("{}"));

        verify(userService).addUser(any());
    }
}