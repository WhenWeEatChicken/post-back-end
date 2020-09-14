package com.post.www.interfaces;

import com.post.www.application.UserService;
import com.post.www.domain.User;
import com.post.www.interfaces.dto.UserDetailResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void detail() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsIm5hbWUiOiJkbGgxMTA2IiwiaWF0IjoxNTk5NzYzNzA4LCJleHAiOjE1OTk3NzA5MDh9.FOGcW68hfO98X1GGmZkNsTalagksBdlaY_2-DhSOjBk";
        UserDetailResponseDto responseDto = new UserDetailResponseDto(
                User.builder()
                        .idx(3L)
                        .name("john")
                        .nickname("john")
                        .email("test@test.com")
                        .password("1234")
                        .build()
        );
        given(userService.getUser(3L)).willReturn(responseDto);

        mvc.perform(get("/user")
                .header("Authorization", "Bearer " + token)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"john\"")))
                .andExpect(content().string(containsString("\"nickname\":\"john\"")));
    }
}